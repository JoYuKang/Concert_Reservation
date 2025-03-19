package kr.hhplus.be.server.support.infra.lock;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.reflect.MethodSignature;
import org.redisson.api.RLock;
import org.redisson.api.RedissonClient;
import org.springframework.expression.EvaluationContext;
import org.springframework.expression.ExpressionParser;
import org.springframework.expression.spel.standard.SpelExpressionParser;
import org.springframework.expression.spel.support.StandardEvaluationContext;
import org.springframework.stereotype.Component;

import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

@Aspect
@Component
@RequiredArgsConstructor
public class DistributedLockAop {

    private final RedissonClient redissonClient;


    @Around("@annotation(lockAnnotation)")
    public Object around(ProceedingJoinPoint joinPoint, DistributedLockAspect lockAnnotation) throws Throwable {
        // SpEL을 이용해 좌석별 key 리스트 생성
        List<String> lockKeys = generateKeys(joinPoint, lockAnnotation.key());

        // Redisson MultiLock 사용
        List<RLock> locks = lockKeys.stream()
                .map(redissonClient::getLock)
                .toList();

        RLock multiLock = redissonClient.getMultiLock(locks.toArray(new RLock[0]));

        boolean acquired = false;
        try {
            acquired = multiLock.tryLock(lockAnnotation.waitTime(), lockAnnotation.leaseTime(), lockAnnotation.timeUnit());
            if (!acquired) {
                throw new RuntimeException("Failed to acquire lock: " + lockKeys);
            }

            return joinPoint.proceed();
        } finally {
            if (acquired) {
                multiLock.unlock();
            }
        }
    }

    private List<String> generateKeys(ProceedingJoinPoint joinPoint, String keyExpression) {
        ExpressionParser parser = new SpelExpressionParser();
        EvaluationContext context = new StandardEvaluationContext();

        MethodSignature signature = (MethodSignature) joinPoint.getSignature();
        Object[] args = joinPoint.getArgs();
        String[] paramNames = signature.getParameterNames();

        for (int i = 0; i < paramNames.length; i++) {
            context.setVariable(paramNames[i], args[i]);
        }

        String rawKey = parser.parseExpression(keyExpression).getValue(context, String.class);

        // 좌석 목록을 개별 Key로 변환
        return Arrays.stream(rawKey.split(","))
                .map(seat -> "concert-" + context.lookupVariable("concertId") + "-seat-" + seat.trim())
                .collect(Collectors.toList());
    }
}
