package net.multi.terminal.bff.config;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.jmx.export.annotation.ManagedAttribute;
import org.springframework.jmx.export.annotation.ManagedMetric;
import org.springframework.jmx.export.annotation.ManagedOperation;
import org.springframework.jmx.export.annotation.ManagedResource;
import org.springframework.stereotype.Component;

@Component
@ManagedResource(objectName = "net.multi.terminal.bff:type=HystrixConfig")
public class DynamicConfigMBeanImpl implements DynamicConfigMBean {
    @Value("${hystrix.circuitBreakerEnabled}")
    private boolean circuitBreakerEnable = false;
    private int circuitBreakerRequestVolumeThreshold = 5;
    private int circuitBreakerErrorThresholdPercentage = 50;
    private int circuitBreakerSleepWindowInMilliseconds = 1000;
    private int threadPoolCoreSize = 2;
    private int threadPoolMaximumSize = 20;
    @ManagedMetric
    public boolean getCircuitBreakerEnabled() {
        return circuitBreakerEnable;
    }
    @ManagedMetric
    public int getCircuitBreakerRequestVolumeThreshold() {
        return circuitBreakerRequestVolumeThreshold;
    }
    @ManagedMetric
    public int getCircuitBreakerErrorThresholdPercentage() {
        return circuitBreakerErrorThresholdPercentage;
    }
    @ManagedAttribute
    public int getCircuitBreakerSleepWindowInMilliseconds() {
        return circuitBreakerSleepWindowInMilliseconds;
    }
    @ManagedMetric
    public int getThreadPoolCoreSize() {
        return threadPoolCoreSize;
    }
    @ManagedMetric
    public int getThreadPoolMaximumSize() {
        return threadPoolMaximumSize;
    }
    @ManagedOperation
    public void setCircuitBreakerEnable(boolean circuitBreakerEnable) {
        this.circuitBreakerEnable = circuitBreakerEnable;
    }
    @ManagedOperation
    public void setCircuitBreakerRequestVolumeThreshold(int circuitBreakerRequestVolumeThreshold) {
        this.circuitBreakerRequestVolumeThreshold = circuitBreakerRequestVolumeThreshold;
    }
    @ManagedOperation
    public void setCircuitBreakerErrorThresholdPercentage(int circuitBreakerErrorThresholdPercentage) {
        this.circuitBreakerErrorThresholdPercentage = circuitBreakerErrorThresholdPercentage;
    }
    @ManagedOperation
    public void setCircuitBreakerSleepWindowInMilliseconds(int circuitBreakerSleepWindowInMilliseconds) {
        this.circuitBreakerSleepWindowInMilliseconds = circuitBreakerSleepWindowInMilliseconds;
    }
    @ManagedOperation
    public void setThreadPoolCoreSize(int threadPoolCoreSize) {
        this.threadPoolCoreSize = threadPoolCoreSize;
    }
    @ManagedOperation
    public void setThreadPoolMaximumSize(int threadPoolMaximumSize) {
        this.threadPoolMaximumSize = threadPoolMaximumSize;
    }
}
