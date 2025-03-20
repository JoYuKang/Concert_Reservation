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
import org.springframework.transaction.support.TransactionTemplate;
import org.springframework.stereotype.Component;

import java.util.Arrays;
import java.util.Collection;
import java.util.List;

@Aspect
@Slf4j
@Component
@RequiredArgsConstructor
public class DistributedLockAop {

    private final RedissonClient redissonClient;

    @Around("@annotation(lockAnnotation)")
    public Object around(ProceedingJoinPoint joinPoint, DistributedLockAspect lockAnnotation) throws Throwable {
        // SpEL을 이용해 좌석별 key 리스트 생성
        List<String> lockKeys = generateKeys(joinPoint, lockAnnotation.key());
        log.info("lockKeys: {}", lockKeys);
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

        // 메서드 매개변수를 SpEL 컨텍스트에 등록
        for (int i = 0; i < paramNames.length; i++) {
            context.setVariable(paramNames[i], args[i]);
        }

        // SpEL 표현식 평가
        Object keyResult = parser.parseExpression(keyExpression).getValue(context);

        if (keyResult instanceof String keyString) {
            // "1-[1, 4]" 같은 문자열이 들어올 수 있으므로 처리
            String[] parts = keyString.split("-");
            if (parts.length < 2) {
                throw new IllegalArgumentException("Invalid key format: " + keyString);
            }

            String concertId = parts[0].trim();
            String seatNumbersStr = parts[1].trim();

            // 쉼표로 분리하여 좌석 개별 처리
            return Arrays.stream(seatNumbersStr.split(","))
                    .map(String::trim)
                    .map(seat -> "Seat-" + concertId + "-" + seat)
                    .toList();
        }

        throw new IllegalArgumentException("Invalid key expression result: " + keyResult);

    }
}
