package com.example.consumer.hystrix.service;

import com.netflix.hystrix.HystrixCircuitBreaker;
import com.netflix.hystrix.HystrixCommandKey;
import com.netflix.hystrix.contrib.javanica.annotation.HystrixCommand;
import com.netflix.hystrix.contrib.javanica.annotation.HystrixProperty;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

@Slf4j
@Service
public class HystrixTestService {

    @HystrixCommand(
            fallbackMethod = "timeoutHandler", commandProperties = {
            @HystrixProperty(name = "execution.isolation.thread.timeoutInMilliseconds", value = "3000"),
//            @HystrixProperty(name= "execution.isolation.strategy", value="SEMAPHORE"),
    })
    public String getResult() {
        sleep(20000);

        return "Hello World!";
    }

    public String timeoutHandler() {
        log.info("Timeout!");
        return "Timout! This is the result from timeoutHandler.";
    }

    private void sleep(int millis) {
        try {
            Thread.sleep(millis);
        } catch (InterruptedException e) {
            throw new RuntimeException(e);
        }
    }

    // -- circuit breaker example
    //

    @HystrixCommand(commandKey = "testingHystrixCommand", fallbackMethod = "checkMoreThanZeroFallbackMethod", commandProperties = {
            @HystrixProperty(name = "circuitBreaker.enabled", value = "true"),      // enable the circuit breaker
            @HystrixProperty(name = "metrics.rollingStats.timeInMilliseconds",value = "1000"),
            @HystrixProperty(name = "circuitBreaker.requestVolumeThreshold", value = "10"),
            @HystrixProperty(name = "circuitBreaker.sleepWindowInMilliseconds", value = "10000"),
            @HystrixProperty(name = "circuitBreaker.errorThresholdPercentage", value = "60"),
    })
    public String checkMoreThanZero(int input) {
        if (input < 1) {
            throw new RuntimeException("The number must be more than zero");
        }
        return "The entered number is more than zero! " + input;
    }

    private String checkMoreThanZeroFallbackMethod(int input) {
        HystrixCircuitBreaker breaker = HystrixCircuitBreaker.Factory.getInstance(HystrixCommandKey.Factory.asKey("testingHystrixCommand"));
        if (breaker != null) {
            log.info("allowRequest: {}", breaker.allowRequest());
        }
        return "The number must be more than zero!";
    }



}
