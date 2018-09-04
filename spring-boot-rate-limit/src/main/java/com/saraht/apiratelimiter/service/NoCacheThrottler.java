package com.saraht.apiratelimiter.service;
import com.saraht.apiratelimiter.service.util.RateLimitCounter;
import com.saraht.apiratelimiter.service.util.Data;

import java.util.concurrent.atomic.AtomicInteger;

public class NoCacheThrottler implements RateThrottler {

    private static RateLimitCounter rateLimitCounter = RateLimitCounter.getInstance();
    private int rateLimit;
    private long timeLimit;

    NoCacheThrottler(int rateLimit, long timeLimit) {
        this.rateLimit = rateLimit;
        this.timeLimit = timeLimit;
    }

    /**
     * @param id is unique identification for the particular requestor i.e ip/clientId/etc
     * @returns boolean value stating throttling status of the program.
     * True if api calls has reached to configured number, false if there are rooms for more api call.
     */
    private boolean requestLimitExceeded(String id){
        Data data = rateLimitCounter.getRateLimitCounter(id);
        int count = data.incrementCount();
        if (count > rateLimit) {
            if (System.currentTimeMillis() - data.getLastUpdated() > timeLimit) {
                data.setCount(new AtomicInteger(0));
                return false;
            } else {
                return true;
            }
        } else {
            return false;
        }
    }


    @Override
    public boolean isThrottled(String id) {
        if (rateLimitCounter.contains(id)) {
            if (!requestLimitExceeded(id)) {
                rateLimitCounter.increment(id);
                rateLimitCounter.getRateLimitCounter(id).setLastUpdated(System.currentTimeMillis());
            } else {
               return true;
            }
        } else {
            rateLimitCounter.add(id);
        }

        return false;
    }
}


