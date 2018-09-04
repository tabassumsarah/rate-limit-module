
package com.saraht.apiratelimiter;

import com.saraht.apiratelimiter.service.RateLimitService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import javax.servlet.*;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

@Component
public class RequestResponseRateLimitFilter implements Filter {
    private final static Logger LOG = LoggerFactory.getLogger(RequestResponseRateLimitFilter.class);

    @Value("${retry.after}")
    private String retryAfter;

    @Override
    public void init(final FilterConfig filterConfig) throws ServletException {
        LOG.info("Initializing filter :{}", this);
    }

    @Autowired
    private RateLimitService rateLimitService;

    @Override
    public void doFilter(final ServletRequest request, final ServletResponse response, final FilterChain chain)
            throws IOException, ServletException {
        HttpServletRequest req = (HttpServletRequest) request;
        HttpServletResponse res = (HttpServletResponse) response;

        String ipAddress = req.getHeader("X-Forwarded-For");

        if (ipAddress == null || ipAddress.length() == 0 || "unknown".equalsIgnoreCase(ipAddress)) {
            ipAddress = request.getRemoteAddr();
        }

        try {
            if (rateLimitService.isRateLimited(ipAddress)) {
                res.addIntHeader("Retry-After", Integer.parseInt(retryAfter));
                res.sendError(429, "Rate limit exceeded. Try again in " + retryAfter + " seconds");
            }
        } catch (Exception e) {
            LOG.error(e.getMessage());
        }

        LOG.info("Logging Request  {} : {}", req.getMethod(), req.getRequestURI());
        chain.doFilter(request, response);
        LOG.info("Ip -> Response  {} : {}", ipAddress, res.getContentType());
    }

    @Override
    public void destroy() {
        LOG.warn
                ("Destructing filter :{}", this);
    }
}

