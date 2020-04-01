package net.multi.terminal.bff.core.clientmgr;


import com.alibaba.fastjson.JSON;
import io.netty.handler.codec.http.HttpResponseStatus;
import net.multi.terminal.bff.constant.MsgCode;
import net.multi.terminal.bff.core.codec.ApiCodec;
import net.multi.terminal.bff.core.serializer.MsgSerializer;
import net.multi.terminal.bff.core.session.SessionInjector;
import net.multi.terminal.bff.exception.ApiException;
import net.multi.terminal.bff.model.ClientContext;
import org.apache.commons.lang.StringUtils;
import org.springframework.beans.BeansException;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.ApplicationContext;
import org.springframework.context.ApplicationContextAware;
import org.springframework.core.io.Resource;
import org.springframework.stereotype.Component;

import javax.annotation.PostConstruct;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * 客户端管理器
 * 读取client-setting.json文件获取支持的所有客户端信息
 */
@Component
public class ClientContextMgr implements ApplicationContextAware {
    @Value("classpath:client-setting.json")
    private Resource clientSetting;
    private Map<String, ClientContext> map = new HashMap<>();
    private ApplicationContext applicationContext;

    @PostConstruct
    public void preHandle() throws ApiException, IOException {
        if (!clientSetting.exists() || !clientSetting.isReadable()) {
            throw new ApiException("在classpath上没有找到文件client-setting.json");
        }
        byte[] content = Files.readAllBytes(Paths.get(clientSetting.getURI()));
        List<ClientContext> list = JSON.parseArray(new String(content, "utf-8"), ClientContext.class);
        for (ClientContext c : list) {
            injectSerializer(c);
            injectCodec(c);
            injectSessionInjector(c);
            map.put(c.getClientId(), c);
        }
    }

    private void injectSessionInjector(ClientContext c) {
        c.setInjector(applicationContext.getBean(c.getSessionInjectorName(),SessionInjector.class));
    }

    private void injectCodec(ClientContext c) {
        if (StringUtils.isEmpty(c.getApiCodecName())) {
            c.setApiCodec(applicationContext.getBean(ApiCodec.DEFAULT_CODEC,ApiCodec.class));
        } else {
            c.setApiCodec(applicationContext.getBean(c.getApiCodecName(), ApiCodec.class));
        }
    }

    private void injectSerializer(ClientContext c) {
        c.setSerializer(applicationContext.getBean(c.getSerializerName(), MsgSerializer.class));
    }

    public ClientContext getContext(String clientId) throws ApiException {
        if (null == clientId) {
            throw new ApiException(MsgCode.E_11000, HttpResponseStatus.BAD_REQUEST);
        }
        ClientContext context = map.get(clientId);
        if (null == context) {
            throw new ApiException(MsgCode.E_11000, HttpResponseStatus.BAD_REQUEST);
        }
        return context;
    }

    @Override
    public void setApplicationContext(ApplicationContext applicationContext) throws BeansException {
        this.applicationContext = applicationContext;
    }
}
