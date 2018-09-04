package com.saraht.apiratelimiter.service.util;

import org.springframework.stereotype.Component;
import java.util.Iterator;
import java.util.concurrent.ConcurrentHashMap;


@Component
public class RateLimitCounter {
    private static RateLimitCounter rateLimitCounter;
    private static ConcurrentHashMap<String,Data> inMemoryTracker = new ConcurrentHashMap<>();

    private RateLimitCounter(){}

    private static class SingletonHelper{
        private static final RateLimitCounter INSTANCE = new RateLimitCounter();
    }

    public static RateLimitCounter getInstance(){
        rateLimitCounter = SingletonHelper.INSTANCE;
        return rateLimitCounter;
    }

    public void setRateLimitCount(ConcurrentHashMap<String, Data> map) {
        this.inMemoryTracker = map;
    }

    public Data getRateLimitCounter(String id) {
        return inMemoryTracker.get(id);
    }

    public boolean contains(String id) {
        Iterator keys = (Iterator) inMemoryTracker.keys();
        while (keys.hasNext()) {
            if (keys.next().equals(id)) {
                return true;
            }
        }
        return false;
    }

    public void add (String id) {
        Data data = new Data();
        data.incrementCount();
        data.setLastUpdated(System.currentTimeMillis());
        inMemoryTracker.put(id, data);
    }

    public void increment (String id) {
        Data data = rateLimitCounter.getRateLimitCounter(id);
        data.incrementCount();
    }

}
