package net.multi.terminal.bff.core.serializer.jsonjson;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import io.netty.handler.codec.http.HttpResponseStatus;
import lombok.extern.slf4j.Slf4j;
import net.multi.terminal.bff.constant.MsgCode;
import net.multi.terminal.bff.core.identity.ApiIdentity;
import net.multi.terminal.bff.core.codec.CommonMsg;
import net.multi.terminal.bff.core.serializer.AbtractMsgSerializer;
import net.multi.terminal.bff.exception.SystemException;
import net.multi.terminal.bff.model.ApiReq;
import net.multi.terminal.bff.model.ApiRsp;
import org.springframework.stereotype.Component;

import java.util.Objects;

@Slf4j
@Component("DoubleJsonMsgSerializer")
public class DoubleJsonMsgSerializer extends AbtractMsgSerializer {


    @Override
    public ApiReq deserialize(ApiIdentity identity, CommonMsg commonMsg) throws SystemException {
        try {
            Objects.requireNonNull(commonMsg);
            Objects.requireNonNull(commonMsg.getMessage());
            ApiReq req = new ApiReq();
            final JSONObject jsonObject = JSON.parseObject(commonMsg.getMessage());
            req.setClientId(identity.getClientId());
            req.setApiName(identity.getApiName());
            req.setVersion(identity.getVersion());
            req.setBody(jsonObject);
            return req;
        } catch (Exception e) {
            throw new SystemException(e, MsgCode.E_11006, HttpResponseStatus.BAD_REQUEST);
        }
    }

    @Override
    public String serialize(ApiRsp rsp) throws SystemException {
        return JSON.toJSONString(rsp);
    }

}
