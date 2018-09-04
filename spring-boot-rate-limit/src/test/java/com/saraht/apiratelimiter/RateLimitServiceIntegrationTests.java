
package com.saraht.apiratelimiter;

import static junit.framework.TestCase.assertFalse;
import static junit.framework.TestCase.assertTrue;

import com.saraht.apiratelimiter.service.RateLimitService;
import com.saraht.apiratelimiter.service.RateLimitServiceImpl;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;


@RunWith(SpringRunner.class)
@SpringBootTest

public class RateLimitServiceIntegrationTests {

    @Autowired
    private RateLimitService service;

    @Autowired
    private BaseConfiguration configuration;

    @Before
    public void before() throws Exception {
        configuration.setRateLimit(3);
        configuration.setTimeLimit(50000);
        configuration.setThrottlerName("no-cache");
    }

    @Test
    public void testShouldReturnRateLimitFalseIfMapDoesNotContainID() throws Exception {
        String client1Id = "client1";
        assertFalse(service.isRateLimited(client1Id));

    }

    @Test
    public void testShouldReturnRateLimitTrueIfNumberOfCallsFromSameClientIsGreaterThanRateLimit() throws Exception {
        String client1Id = "client2";
         for(int i=0;i<3;i++){
             service.isRateLimited(client1Id);
         }
        assertTrue(service.isRateLimited(client1Id));
    }

    @Test
    public void testShouldReturnRateLimitFalseIfNumberOfCallsFromDifferentClientLessThanRateLimit() throws Exception {
        String client3Id = "client3";
        String client4Id = "client4";
        for (int i = 0; i < 2; i++) {
            service.isRateLimited(client3Id);
            service.isRateLimited(client4Id);

        }
        assertFalse(service.isRateLimited(client3Id));
        assertFalse(service.isRateLimited(client4Id));
    }


    @Test
    public void testShouldReturnRateLimitFalseIfNumberOfCallsAreGreaterThanRateLimitButWithinTimeLimit() throws Exception {
        String client1Id = "client5";
        configuration.setTimeLimit(500);
        for (int i = 0; i < 3; i++) {
            service.isRateLimited(client1Id);
        }
        Thread.sleep(600);
        assertFalse(service.isRateLimited(client1Id));
    }

    @Test
    public void testShouldThrowExceptionWhenRateLimitServiceUnavailable() throws Exception {
        service = new RateLimitServiceImpl();
        String client1Id = "client1";
        try {
            service.isRateLimited(client1Id);
        } catch (Exception e) {
            assertTrue(e.getMessage().equals("Rate limit service is unavailable."));
        }

    }

    @Test
    public void testShouldThrowExceptionWhenThrottleNotAvailable() throws Exception {
        configuration.setThrottlerName("unavailable");
        String client1Id = "client1";
        try {
            service.isRateLimited(client1Id);
        } catch (Exception e) {
            assertTrue(e.getMessage().equals("Throttler is not configured"));
        }
    }
}
