package net.multi.terminal.bff.core.interceptor;

import com.netflix.hystrix.*;
import lombok.extern.slf4j.Slf4j;
import net.multi.terminal.bff.config.DynamicConfigMBean;
import net.multi.terminal.bff.constant.MsgCode;
import net.multi.terminal.bff.core.apimgr.ApiInvoker;
import net.multi.terminal.bff.exception.BusinessException;
import net.multi.terminal.bff.model.ApiReq;
import net.multi.terminal.bff.model.ApiRsp;

/**
 * Api断路器和水密舱，用于防止后台系统宕机造成的雪崩现象
 */
@Slf4j
public class ApiHystrixCommand extends HystrixCommand<ApiRsp> {
    private final ApiInvoker invoker;
    private final ApiReq req;

    public ApiHystrixCommand(ApiInvoker invoker, ApiReq req, DynamicConfigMBean configMBean) {
        super(Setter
                .withGroupKey(HystrixCommandGroupKey.Factory.asKey(invoker.getClass().getSimpleName()))
                .andCommandKey(HystrixCommandKey.Factory.asKey(req.getApplication()))
                .andCommandPropertiesDefaults(HystrixCommandProperties
                        .Setter()
                        .withCircuitBreakerEnabled(configMBean.getCircuitBreakerEnabled())
                        .withCircuitBreakerRequestVolumeThreshold(configMBean.getCircuitBreakerRequestVolumeThreshold())
                        .withCircuitBreakerErrorThresholdPercentage(configMBean.getCircuitBreakerErrorThresholdPercentage())
                        .withCircuitBreakerSleepWindowInMilliseconds(configMBean.getCircuitBreakerSleepWindowInMilliseconds())
                )
                .andThreadPoolPropertiesDefaults(HystrixThreadPoolProperties
                        .Setter()
                        .withCoreSize(configMBean.getThreadPoolCoreSize())
                        .withMaximumSize(configMBean.getThreadPoolMaximumSize())
                )
        );
        this.invoker = invoker;
        this.req = req;
    }

    @Override
    protected ApiRsp run() throws Exception {
        return invoker.doInvoke(req);
    }

    @Override
    protected ApiRsp getFallback() {
        if (getFailedExecutionException() instanceof BusinessException) {
            BusinessException e = (BusinessException) getFailedExecutionException();
            log.error("调用错误",e);
            return new ApiRsp(e.getErrorCode(), e.getErrorDesc());
        }
        return new ApiRsp(MsgCode.E_19999.getCode(), MsgCode.E_19999.getMessage());
    }
}
