package net.multi.terminal.bff.model;

import lombok.Data;
import net.multi.terminal.bff.core.codec.ApiCodec;
import net.multi.terminal.bff.core.serializer.MsgSerializer;
import net.multi.terminal.bff.core.session.SessionInjector;

/**
 * 客户端上下文
 * 通过client-setting.json配置不同的客户端参数
 */
@Data
public class ClientContext {
    /**
     * 客户端ID
     */
    private String clientId;
    /**
     * 报文序列化器
     */
    private MsgSerializer serializer;
    /**
     * 报文序列化器 Spring bean name
     */
    private String serializerName;
    /**
     * 报文编码器
     */
    private ApiCodec apiCodec;
    /**
     * 报文编码器 Spring bean name
     */
    private String apiCodecName;
    /**
     * Session注入器
     */
    private SessionInjector injector;
    /**
     * Session注入器Spring bean name
     */
    private String sessionInjectorName;
    /**
     * 响应报文类型
     */
    private String rspContentType;
}
