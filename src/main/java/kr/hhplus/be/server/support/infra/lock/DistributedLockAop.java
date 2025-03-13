package kr.hhplus.be.server.support.infra.lock;

import lombok.RequiredArgsConstructor;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.springframework.stereotype.Component;

import java.util.concurrent.TimeUnit;

@Aspect
@Component
@RequiredArgsConstructor
public class DistributedLockAop {

    private final DistributedLock distributedLock;


    @Around("@annotation(lockAnnotation)")
    public Object around(ProceedingJoinPoint joinPoint, DistributedLockAspect lockAnnotation) throws Throwable {
        String key = lockAnnotation.key();
        long waitTime = lockAnnotation.waitTime();
        long leaseTime = lockAnnotation.leaseTime();
        TimeUnit timeUnit = lockAnnotation.timeUnit();

        if (distributedLock.lock(key, waitTime, leaseTime, timeUnit)) {
            try {
                return joinPoint.proceed();
            } finally {
                distributedLock.unlock(key);
            }
        } else {
            throw new RuntimeException("Failed to acquire lock: " + key);
        }
    }
}
