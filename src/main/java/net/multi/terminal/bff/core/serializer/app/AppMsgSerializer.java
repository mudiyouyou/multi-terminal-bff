package net.multi.terminal.bff.core.serializer.app;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import io.netty.handler.codec.http.HttpResponseStatus;
import lombok.extern.slf4j.Slf4j;
import net.multi.terminal.bff.constant.MsgCode;
import net.multi.terminal.bff.core.codec.CommonMsg;
import net.multi.terminal.bff.core.constant.MessageKeys;
import net.multi.terminal.bff.core.serializer.AbtractMsgSerializer;
import net.multi.terminal.bff.exception.ApiException;
import net.multi.terminal.bff.model.ApiReq;
import net.multi.terminal.bff.model.ApiRsp;
import org.springframework.stereotype.Component;


@Slf4j
@Component("AppMsgSerializer")
public class AppMsgSerializer extends AbtractMsgSerializer {
    @Override
    public ApiReq deserialize(String clientId, CommonMsg msg) throws Exception {
        ApiReq req = null;
        try {
            JSONObject jsonObject = JSON.parseObject(msg.getMessage());
            req = new ApiReq();
            req.setVersion(msg.getVersion());
            req.setBody(jsonObject);
            req.setClientId(clientId);
            req.setApplication(getApiName(jsonObject.getString(MessageKeys.APPLICATION)));
            req.setMobileNum(jsonObject.getString(MessageKeys.MOBILE_NUM));
            req.setTerminalPhysicalNo(jsonObject.getString(MessageKeys.TERMINAL_PHYSICAL_NO));
        } catch (Exception e) {
            throw new ApiException(e, MsgCode.E_11006,HttpResponseStatus.BAD_REQUEST);
        }
        return req;
    }



    public String serialize(ApiRsp rsp) throws Exception {
        return JSON.toJSONString(rsp);
    }

}
