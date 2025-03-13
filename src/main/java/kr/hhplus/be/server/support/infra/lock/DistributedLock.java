package kr.hhplus.be.server.support.infra.lock;


import java.util.concurrent.TimeUnit;

public interface DistributedLock {

    boolean lock(String key, long waitTime, long leaseTime, TimeUnit timeUnit);
    void unlock(String key);
}
