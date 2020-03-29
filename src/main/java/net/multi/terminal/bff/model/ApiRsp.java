package net.multi.terminal.bff.model;

import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;

/**
 * Api响应对象父类
 */
@Data
@EqualsAndHashCode(callSuper = false)
@NoArgsConstructor
public class ApiRsp {
    private String respCode;
    private String respDesc;

    public ApiRsp(String respCode, String respDesc) {
        this.respCode = respCode;
        this.respDesc = respDesc;
    }

}
