package com.example.demo.config;

import io.github.resilience4j.circuitbreaker.CircuitBreakerConfig;
import io.github.resilience4j.circuitbreaker.CircuitBreakerConfig.SlidingWindowType;
import io.github.resilience4j.timelimiter.TimeLimiterConfig;
import java.time.Duration;
import org.springframework.cloud.circuitbreaker.resilience4j.Resilience4JCircuitBreakerFactory;
import org.springframework.cloud.circuitbreaker.resilience4j.Resilience4JConfigBuilder;
import org.springframework.cloud.client.circuitbreaker.Customizer;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.client.RestTemplate;

@Configuration
public class RestConfig {
    @Bean
    public RestTemplate restTemplate() {
        return new RestTemplate();
    }

//    @Bean
//    public Customizer<Resilience4JCircuitBreakerFactory> globalCustomConfiguration() {
//        TimeLimiterConfig timeLimiterConfig = TimeLimiterConfig.custom()
//            .timeoutDuration(Duration.ofSeconds(4))
//            .build();
//        CircuitBreakerConfig circuitBreakerConfig = CircuitBreakerConfig.custom()
//            .failureRateThreshold(10)
//            .waitDurationInOpenState(Duration.ofMillis(1000))
//            .slidingWindowSize(2)
//            .slidingWindowType(SlidingWindowType.COUNT_BASED)
//            .build();
//
//        return factory -> factory.configureDefault(id -> new Resilience4JConfigBuilder(id)
//            .timeLimiterConfig(timeLimiterConfig)
//            .circuitBreakerConfig(circuitBreakerConfig)
//            .build());
//    }

//    @Bean
//    public Customizer<Resilience4JCircuitBreakerFactory> specificCustomConfiguration1() {
//
//        TimeLimiterConfig timeLimiterConfig = TimeLimiterConfig.custom()
//            .timeoutDuration(Duration.ofSeconds(10))
//            .build();
//        CircuitBreakerConfig circuitBreakerConfig = CircuitBreakerConfig.custom()
//            .failureRateThreshold(50)
//            .waitDurationInOpenState(Duration.ofSeconds(5))
//            .automaticTransitionFromOpenToHalfOpenEnabled(true)
//            .permittedNumberOfCallsInHalfOpenState(2)
//            .slidingWindowType(SlidingWindowType.COUNT_BASED)
//            .slidingWindowSize(10)
//            .build();
//
//        return factory -> factory.configure(builder -> builder.circuitBreakerConfig(circuitBreakerConfig)
//            .timeLimiterConfig(timeLimiterConfig).build(), "circuitBreaker");
//    }
}