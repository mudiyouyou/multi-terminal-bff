package net.multi.terminal.bff.core.serializer.formjson;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.google.common.base.Splitter;
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

import java.util.List;
import java.util.Map;
import java.util.Objects;

@Slf4j
@Component("FormJsonMsgSerializer")
public class FormJsonMsgSerializer extends AbtractMsgSerializer {

    @Override
    public ApiReq deserialize(ApiIdentity identity, CommonMsg commonMsg) throws SystemException {
        ApiReq req = null;
        try {
            Objects.requireNonNull(commonMsg);
            Objects.requireNonNull(commonMsg.getMessage());
            Map<String, String> params = Splitter.on("&").omitEmptyStrings().withKeyValueSeparator("=").split(commonMsg.getMessage());
            Objects.requireNonNull(params);
            JSONObject jsonObject = new JSONObject();
            for (String key : params.keySet()) {
                List<String> values = Splitter.on(",").splitToList(params.get(key));
                if (!values.isEmpty()) {
                    jsonObject.put(key, values.get(0));
                }
            }
            req = new ApiReq();
            req.setClientId(identity.getClientId());
            req.setApiName(identity.getApiName());
            req.setVersion(identity.getVersion());
            req.setBody(jsonObject);
        } catch (Exception e) {
            throw new SystemException(e, MsgCode.E_11006, HttpResponseStatus.BAD_REQUEST);
        }
        return req;
    }

    @Override
    public String serialize(ApiRsp rsp) throws SystemException {
        return JSON.toJSONString(rsp);
    }

}
