package net.multi.terminal.bff.core.identity;

import lombok.Data;

@Data
public class ApiIdentity {
    private String clientId;
    private String apiName;
    private String version;
    public ApiIdentity(String clientId, String apiName) {
        this.clientId = clientId;
        this.apiName = apiName;
    }
}
