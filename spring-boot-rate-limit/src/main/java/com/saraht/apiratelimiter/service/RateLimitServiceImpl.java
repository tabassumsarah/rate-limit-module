package com.saraht.apiratelimiter.service;

import com.saraht.apiratelimiter.BaseConfiguration;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.el.PropertyNotFoundException;

@Service
public class RateLimitServiceImpl extends RateLimitService {
    // configuration from properties file is auto wired here so that corresponding throttler implementation can be
    // initialized with rate limit values.
    @Autowired
    private BaseConfiguration configuration;

    @Override
    RateThrottler getThrottler() throws Exception {
        // Throttler can be configured from properties file.
        if (configuration.getThrottlerName().equals("no-cache")) {
            return (RateThrottler) new NoCacheThrottler(configuration.getRateLimit(), configuration.getTimeLimit());
        }

        // other throttler specific code goes here
        throw new PropertyNotFoundException("Throttler is not configured");

    }
}
