package net.multi.terminal.bff.model;

import com.alibaba.fastjson.JSONObject;
import io.netty.handler.codec.http.HttpHeaders;
import lombok.Data;

import javax.validation.constraints.NotNull;

/**
 * Api请求对象父类
 */
@Data
public class ApiReq {
    /**
     * 系统自动注入：由客户端通过Head中clientId属性传递进来
     * 接入方需要申请客户端的ID
     */
    private String clientId;
    /**
     * 全终端使用：Api版本(待开发)
     */
    private String version;
    /**
     * 全终端使用：Api名称
     */
    @NotNull(message = "接口名称不能为空")
    private String application;
    /**
     * app端使用：签名
     */
    private String signature;
    /**
     * app端使用：手机串号（表示手机唯一的手机终端）
     */
    private String terminalPhysicalNo;
    /**
     * app端使用：终端系统
     */
    private String terminalOs;
    /**
     * app端使用：终端手机号
     */
    private String mobileNum;
    /**
     * 系统自动注入：自动将外部ID转换成会员系统ID
     * ApiInvoker 类需要添加 @MemberId
     */
    private String memberId;
    /**
     * 系统自动注入：终端IP
     */
    private String clientIp;

    /**
     * 系统自动注入：报文头
     */
    private HttpHeaders headers;
    /**
     * 系统自动注入：报文体
     */
    /**
     * 系统自动注入：接收报文体
     */
    private JSONObject body;
    /**
     * Session上下文
     */
    private ApiSession session;

}
