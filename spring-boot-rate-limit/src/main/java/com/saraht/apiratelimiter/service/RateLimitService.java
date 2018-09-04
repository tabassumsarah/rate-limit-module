package com.saraht.apiratelimiter.service;

import javax.el.PropertyNotFoundException;

/**
 * This abstract class contains method which states if rate is limited for the particular requestor or not.
 * Any class extending this contract should provide corresponding rate throttler.
 */
public abstract class RateLimitService {
    /**
     * @param id is unique identifier of the requestor
     * @return if this calls from this requestor is throttled or not
     * @throws Exception e.
     */
    public boolean isRateLimited(String id) throws Exception {
        RateThrottler rateThrottler;
        try {
            rateThrottler = getThrottler();
            return rateThrottler.isThrottled(id);
        }
        catch (Exception e) {
            if(e instanceof PropertyNotFoundException){
                throw  new Exception(e.getMessage());
            }
            throw new Exception("Rate limit service is unavailable.");
        }

    }

    /**
     * @returns Corresponding throttler to be used by this module.
     * Such as noCache/cache or thirdParty enabled caching throttler
     */
    abstract RateThrottler getThrottler() throws Exception;
}
