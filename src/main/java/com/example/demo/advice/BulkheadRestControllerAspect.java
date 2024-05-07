package com.example.demo.advice;

import io.github.resilience4j.bulkhead.Bulkhead;
import io.github.resilience4j.bulkhead.BulkheadRegistry;
import lombok.extern.slf4j.Slf4j;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.springframework.stereotype.Component;

@Aspect
@Component
@Slf4j
public record BulkheadRestControllerAspect(BulkheadRegistry bulkheadRegistry) {

//    @Around("within(com.example.demo.controller..*)")
    @Around("execution(* com.example.demo.controller.TodoController.*(..))")
    public Object proceedInternal(ProceedingJoinPoint proceedingJoinPoint) throws Throwable {
        log.info("todo is executed");
        return process(proceedingJoinPoint, "todo");
    }

    private Object process(ProceedingJoinPoint proceedingJoinPoint, String bulkheadName) throws Throwable {
        Bulkhead bulkhead = bulkheadRegistry.bulkhead(bulkheadName);
        return bulkhead.executeCheckedSupplier(proceedingJoinPoint::proceed);
    }
}