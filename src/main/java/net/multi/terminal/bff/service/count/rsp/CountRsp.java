package net.multi.terminal.bff.service.count.rsp;

import lombok.Data;
import lombok.ToString;
import net.multi.terminal.bff.model.ApiRsp;

@Data
@ToString
public class CountRsp extends ApiRsp {
    private Integer count;
    public CountRsp(String respCode, String respDesc) {
        super(respCode, respDesc);
    }
}