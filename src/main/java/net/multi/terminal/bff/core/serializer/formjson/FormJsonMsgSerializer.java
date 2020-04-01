package net.multi.terminal.bff.core.serializer.formjson;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.google.common.base.Splitter;
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

import java.util.List;
import java.util.Map;

@Slf4j
@Component("FormJsonMsgSerializer")
public class FormJsonMsgSerializer extends AbtractMsgSerializer {

    @Override
    public ApiReq deserialize(String clientId, CommonMsg commonMsg) throws Exception {
        ApiReq req = null;
        try {
            Map<String, String> params = Splitter.on("&").omitEmptyStrings().withKeyValueSeparator("=").split(commonMsg.getMessage());
            JSONObject jsonObject = new JSONObject();
            for (String key : params.keySet()) {
                List<String> values = Splitter.on(",").splitToList(params.get(key));
                if (!values.isEmpty()) {
                    jsonObject.put(key, values.get(0));
                }
            }
            req = new ApiReq();
            req.setClientId(clientId);
            req.setApplication(getApiName(jsonObject.getString(MessageKeys.APPLICATION)));
            req.setVersion(jsonObject.getString(MessageKeys.VERSION));
            req.setBody(jsonObject);
        } catch (Exception e) {
            throw new ApiException(e, MsgCode.E_11006, HttpResponseStatus.BAD_REQUEST);
        }
        return req;
    }

    @Override
    public String serialize(ApiRsp rsp) throws Exception {
        return JSON.toJSONString(rsp);
    }

}
