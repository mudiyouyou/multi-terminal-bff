package net.multi.terminal.bff.constant;


import net.multi.terminal.bff.model.ApiRsp;

/**
 * 响应码列表
 */
public enum MsgCode {

    E_11000("非法的客户端ID"),
    E_11001("只支持POST请求"),
    E_11002("启动异常"),
    E_11003("该接口未提供"),
    E_11004("解密错误"),
    E_11005("加密错误"),
    E_11006("报文格式错误"),
    E_11007("校验加密错误"),
    E_11008("签名失败"),
    E_11009("无法调用目标方法"),
    E_11010("无法定位APP端设备号"),
    E_19999("系统未知错误"),
    E_00000("成功"),
    E_10001("输入参数不合法"),
    E_10002("登录已过期,请重新登录"),
    E_10006("您的账户已在其他设备上登录,请重新登录"),
    E_10009("用户已存在");
    private String message;

    public String getCode() {
        return this.name().replace("E_","");
    }

    public String getMessage() {
        return message;
    }

    public ApiRsp getRsp() {
        return new ApiRsp(this.getCode(), message);
    }

    private MsgCode(String name) {
        this.message = name;
    }

    @Override
    public String toString() {
        return this.getCode() + "_" + this.message;
    }

}
