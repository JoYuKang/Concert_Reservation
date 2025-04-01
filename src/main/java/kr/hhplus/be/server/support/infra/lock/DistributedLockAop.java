package kr.hhplus.be.server.support.infra.lock;

import kr.hhplus.be.server.member.interfaces.dto.request.MemberRequest;
import kr.hhplus.be.server.reservation.interfaces.request.ReservationRequest;
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
import org.springframework.transaction.support.TransactionSynchronization;
import org.springframework.transaction.support.TransactionSynchronizationManager;

import java.util.List;

@Aspect
@Slf4j
@Component
@RequiredArgsConstructor
public class DistributedLockAop {

    private final RedissonClient redissonClient;

    @Around("@annotation(distributedLock)")
    public Object around(ProceedingJoinPoint joinPoint, DistributedLock distributedLock) throws Throwable {
        // SpEL을 이용해 좌석별 key 리스트 생성
        List<String> lockKeys = generateKeys(joinPoint, distributedLock.key());
        log.info("lockKeys: {}", lockKeys);
        // Redisson MultiLock 사용
        List<RLock> locks = lockKeys.stream()
                .map(redissonClient::getLock)
                .toList();

        RLock multiLock = redissonClient.getMultiLock(locks.toArray(new RLock[0]));

        boolean acquired = false;
        try {
            acquired = multiLock.tryLock(distributedLock.waitTime(), distributedLock.leaseTime(), distributedLock.timeUnit());
            if (!acquired) {
                throw new RuntimeException("Failed to acquire lock: " + lockKeys);
            }

            Object result = joinPoint.proceed(); // 비즈니스 로직 실행

            // 트랜잭션이 활성화된 경우, 커밋 후에 락 해제 콜백 등록
            if (TransactionSynchronizationManager.isSynchronizationActive()) {
                TransactionSynchronizationManager.registerSynchronization(new TransactionSynchronization() {
                    @Override
                    public void afterCommit() {
                        multiLock.unlock();
                        log.info("Lock released after commit for keys: {}", lockKeys);
                    }
                    @Override public void suspend() {}
                    @Override public void resume() {}
                    @Override public void flush() {}
                    @Override public void beforeCommit(boolean readOnly) {}
                    @Override public void beforeCompletion() {}
                    @Override public void afterCompletion(int status) {}
                });
            } else {
                // 트랜잭션이 없으면 즉시 해제
                multiLock.unlock();
                log.info("Lock released immediately for keys: {}", lockKeys);
            }
            return result;
        } catch (Throwable t) {
            if (acquired && !TransactionSynchronizationManager.isSynchronizationActive()) {
                multiLock.unlock();
            }
            throw t;
        }
    }

    /**
     * generateKeys 메서드
     * 1. EvaluationContext에 메서드 매개변수를 등록
     * 2. 인자 타입에 따라 MemberRequest와 ReservationRequest를 구분하여 키를 생성
     *    - MemberRequest: "Member-<id>" 단일 키 반환
     *    - ReservationRequest: 각 좌석마다 "Seat-<concertId>-<seat>" 형식의 키 리스트 반환
     * 3. 위 조건에 해당하지 않으면 SpEL 표현식을 평가하여 단일 키를 반환
     */
    private List<String> generateKeys(ProceedingJoinPoint joinPoint, String keyExpression) {
        ExpressionParser parser = new SpelExpressionParser();
        EvaluationContext context = new StandardEvaluationContext();

        MethodSignature signature = (MethodSignature) joinPoint.getSignature();
        Object[] args = joinPoint.getArgs();
        String[] paramNames = signature.getParameterNames();
        for (int i = 0; i < paramNames.length; i++) {
            context.setVariable(paramNames[i], args[i]);
        }

        // 인자 타입에 따라 분기: MemberRequest와 ReservationRequest 처리
        for (Object arg : args) {
            if (arg instanceof MemberRequest memberRequest) {
                // MemberRequest의 경우 "Member-<id>" 형태의 단일 키 생성
                return List.of("Member-" + memberRequest.getId());
            } else if (arg instanceof ReservationRequest reservationRequest) {
                // ReservationRequest의 경우 각 좌석에 대해 "Seat-<concertId>-<seat>" 키 리스트 생성
                Long concertId = reservationRequest.getConcertId();
                return reservationRequest.getSeatNumbers().stream()
                        .map(seat -> "Seat-" + concertId + "-" + seat)
                        .toList();
            }
        }

        // 만약 위 조건에 해당하는 인자가 없다면 SpEL 표현식 결과를 평가해서 단일 키로 반환
        Object keyResult = parser.parseExpression(keyExpression).getValue(context);
        if (keyResult instanceof String) {
            return List.of((String) keyResult);
        }
        return List.of(String.valueOf(keyResult));
    }
}
