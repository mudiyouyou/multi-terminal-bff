package net.multi.terminal.bff.core.apimgr;

import lombok.extern.slf4j.Slf4j;
import net.multi.terminal.bff.constant.MsgCode;
import net.multi.terminal.bff.exception.BusinessException;
import net.multi.terminal.bff.model.ApiReq;
import net.multi.terminal.bff.model.ApiRsp;
import org.springframework.beans.BeanUtils;

import javax.validation.ConstraintViolation;
import javax.validation.Validation;
import javax.validation.Validator;
import javax.validation.ValidatorFactory;
import java.lang.reflect.InvocationTargetException;
import java.util.Set;

/**
 * Api执行器,用于调用Api方法
 * 处理Api方法调用前后的通用逻辑
 */
@Slf4j
public class ApiInvoker {
    private ApiRunContext runContext;

    public ApiInvoker(ApiRunContext runContext) {
        this.runContext = runContext;
    }

    public final ApiRsp doInvoke(ApiReq inputMessage) throws BusinessException {
//        checkParameter(inputMessage);
        Object input = convertInput(inputMessage);
        //参数校验
        try {
            return invoke(input);
        } catch (IllegalAccessException e) {
            throw new BusinessException(e, MsgCode.E_11009);
        } catch (InvocationTargetException e) {
            throw new BusinessException(e, MsgCode.E_11009);
        }
    }

    private ApiRsp invoke(Object input) throws InvocationTargetException, IllegalAccessException {
        return (ApiRsp) runContext.getMethod().invoke(runContext.getInstance(), input);
    }


    private Object convertInput(ApiReq inputMessage) {
        Object bodyObj = inputMessage.getBody().toJavaObject(runContext.getArgType());
        BeanUtils.copyProperties(inputMessage, bodyObj);
        return bodyObj;
    }

    public void checkParameter(Object t) throws BusinessException {
        ValidatorFactory factory = Validation.buildDefaultValidatorFactory();
        Validator validator = factory.getValidator();
        Set<ConstraintViolation<Object>> constraintViolations = validator.validate(t);
        if (!constraintViolations.isEmpty()) {
            StringBuilder buffer = new StringBuilder();
            buffer.append(MsgCode.E_10001.getMessage());
            constraintViolations.forEach((s) -> {
                buffer.append("," + s.getMessage());
            });
            BusinessException be = new BusinessException(MsgCode.E_10001, buffer.toString());
            throw be;
        }
    }
}
