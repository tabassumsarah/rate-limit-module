package com.saraht.apiratelimiter.service.util;

import java.util.concurrent.atomic.AtomicInteger;

public class Data {
    private long lastUpdated;
    private AtomicInteger count;

    public long getLastUpdated() {
        return lastUpdated;
    }

    public void setLastUpdated(long lastUpdated) {
        this.lastUpdated = lastUpdated;
    }

    public AtomicInteger getCount() {
        return count;
    }

    public void setCount(AtomicInteger count) {
        this.count = count;
    }

    public int incrementCount() {
        return count.getAndIncrement();
    }

    public Data() {
        count = new AtomicInteger(0);
    }
}
