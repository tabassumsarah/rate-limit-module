package com.saraht.apiratelimiter.service;
/**
 * This interface provides isThrottled method which takes identification as an argument.
 * Any concrete rate throttler should implement this interface.
 */
public interface RateThrottler {
    boolean isThrottled(String id);
}
