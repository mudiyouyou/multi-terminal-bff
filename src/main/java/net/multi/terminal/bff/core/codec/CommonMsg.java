package net.multi.terminal.bff.core.codec;

import lombok.Data;
import lombok.RequiredArgsConstructor;

@Data
public class CommonMsg {
    private String version;
    private byte[] secretKey;
    private String message;
}
