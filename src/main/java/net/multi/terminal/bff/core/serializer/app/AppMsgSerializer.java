package net.multi.terminal.bff.core.serializer.app;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import io.netty.handler.codec.http.HttpResponseStatus;
import lombok.extern.slf4j.Slf4j;
import net.multi.terminal.bff.constant.MsgCode;
import net.multi.terminal.bff.core.identity.ApiIdentity;
import net.multi.terminal.bff.core.codec.CommonMsg;
import net.multi.terminal.bff.core.constant.MessageKeys;
import net.multi.terminal.bff.core.serializer.AbtractMsgSerializer;
import net.multi.terminal.bff.exception.SystemException;
import net.multi.terminal.bff.model.ApiReq;
import net.multi.terminal.bff.model.ApiRsp;
import org.springframework.stereotype.Component;

import java.util.Objects;


@Slf4j
@Component("AppMsgSerializer")
public class AppMsgSerializer extends AbtractMsgSerializer {
    @Override
    public ApiReq deserialize(ApiIdentity identity, CommonMsg msg) throws SystemException {

        try {
            Objects.requireNonNull(msg);
            Objects.requireNonNull(msg.getMessage());
            JSONObject jsonObject = JSON.parseObject(msg.getMessage());
            Objects.requireNonNull(jsonObject);
            ApiReq req = new ApiReq();
            req.setVersion(msg.getVersion());
            req.setBody(jsonObject);
            req.setClientId(identity.getClientId());
            req.setApiName(identity.getApiName());
            req.setVersion(identity.getVersion());
            req.setMobileNum(jsonObject.getString(MessageKeys.MOBILE_NUM));
            req.setTerminalPhysicalNo(jsonObject.getString(MessageKeys.TERMINAL_PHYSICAL_NO));
            return req;
        } catch (Exception e) {
            throw new SystemException(e, MsgCode.E_11006, HttpResponseStatus.BAD_REQUEST);
        }
    }


    public String serialize(ApiRsp rsp) throws SystemException {
        return JSON.toJSONString(rsp);
    }

}
