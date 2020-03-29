package net.multi.terminal.bff.config;

import org.springframework.stereotype.Component;

@Component
public class DynamicConfigMBeanImpl implements DynamicConfigMBean {
    @Override
    public boolean getCircuitBreakerEnabled() {
        return false;
    }

    @Override
    public int getCircuitBreakerRequestVolumeThreshold() {
        return 5;
    }

    @Override
    public int getCircuitBreakerErrorThresholdPercentage() {
        return 50;
    }

    @Override
    public int getCircuitBreakerSleepWindowInMilliseconds() {
        return 5;
    }

    @Override
    public int getThreadPoolCoreSize() {
        return 1;
    }

    @Override
    public int getThreadPoolMaximumSize() {
        return 10;
    }
}
