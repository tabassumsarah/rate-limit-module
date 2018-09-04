package com.saraht.apiratelimiter;

import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.stereotype.Component;

/**
 * This class provides config object from corresponding properties file.
 */
@Component
@ConfigurationProperties("base")
public class BaseConfiguration {

    private int retryAfter;
    private long timeLimit;
    private int rateLimit;
    private String retryMessage;
    private String throttlerName;

    public void setTimeLimit(long timeLimit) {
        this.timeLimit = timeLimit;
    }

    public String getThrottlerName() {
        return throttlerName;
    }

    public void setThrottlerName(String throttlerName) {
        this.throttlerName = throttlerName;
    }

    public int getRetryAfter() {
        return retryAfter;
    }

    public void setRetryAfter(int retryAfter) {
        this.retryAfter = retryAfter;
    }

    public long getTimeLimit() {
        return timeLimit;
    }

    public void setTimeLimit(int timeLimit) {
        this.timeLimit = timeLimit;
    }

    public int getRateLimit() {
        return rateLimit;
    }

    public void setRateLimit(int rateLimit) {
        this.rateLimit = rateLimit;
    }

    public String getRetryMessage() {
        return retryMessage;
    }

    public void setRetryMessage(String retryMessage) {
        this.retryMessage = retryMessage;
    }
}