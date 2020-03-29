package net.multi.terminal.bff.config;

public interface DynamicConfigMBean {

    boolean getCircuitBreakerEnabled();

    int getCircuitBreakerRequestVolumeThreshold();

    int getCircuitBreakerErrorThresholdPercentage();

    int getCircuitBreakerSleepWindowInMilliseconds();

    int getThreadPoolCoreSize();

    int getThreadPoolMaximumSize();
}
