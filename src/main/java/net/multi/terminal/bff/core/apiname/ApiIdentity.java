package net.multi.terminal.bff.core.apiname;

import lombok.Data;

@Data
public class ApiIdentity {
    private String clientId;
    private String apiName;

    public ApiIdentity(String clientId, String apiName) {
        this.clientId = clientId;
        this.apiName = apiName;
    }
}
