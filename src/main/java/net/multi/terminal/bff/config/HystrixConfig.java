package net.multi.terminal.bff.config;

public interface HystrixConfig {

    boolean getCircuitBreakerEnabled();

    int getCircuitBreakerRequestVolumeThreshold();

    int getCircuitBreakerErrorThresholdPercentage();

    int getCircuitBreakerSleepWindowInMilliseconds();

    int getThreadPoolCoreSize();

    int getThreadPoolMaximumSize();
}
