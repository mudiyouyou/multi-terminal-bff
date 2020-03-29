package net.multi.terminal.bff.core.serializer.jsonjson;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import io.netty.handler.codec.http.HttpResponseStatus;
import lombok.extern.slf4j.Slf4j;
import net.multi.terminal.bff.constant.MsgCode;
import net.multi.terminal.bff.core.codec.CommonMsg;
import net.multi.terminal.bff.core.constant.MessageKeys;
import net.multi.terminal.bff.core.serializer.AbtractMsgSerializer;
import net.multi.terminal.bff.exception.SystemException;
import net.multi.terminal.bff.model.ApiReq;
import net.multi.terminal.bff.model.ApiRsp;
import org.springframework.stereotype.Component;

@Slf4j
@Component("DoubleJsonMsgSerializer")
public class DoubleJsonMsgSerializer extends AbtractMsgSerializer {


    @Override
    public ApiReq deserialize(String clientId, CommonMsg commonMsg) throws Exception {
        ApiReq req = null;
        try {
            req = new ApiReq();
            final JSONObject jsonObject = JSON.parseObject(commonMsg.getMessage());
            req.setClientId(clientId);
            req.setApplication(getApiName(jsonObject.getString(MessageKeys.APPLICATION)));
            req.setVersion(jsonObject.getString(MessageKeys.VERSION));
            req.setBody(jsonObject);
        } catch (Exception e) {
            throw new SystemException(e, MsgCode.E_11006, HttpResponseStatus.BAD_REQUEST);
        }
        return req;
    }

    @Override
    public String serialize(ApiRsp rsp) throws Exception {
        return JSON.toJSONString(rsp);
    }

}
