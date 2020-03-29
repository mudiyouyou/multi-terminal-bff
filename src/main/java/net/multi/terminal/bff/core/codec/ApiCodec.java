package net.multi.terminal.bff.core.codec;

public interface ApiCodec {
    String DEFAULT_CODEC = "EmptyApiCodec";
    CommonMsg decode(String clientId, String encodeMsg) throws Exception;

    String encode(CommonMsg commonMsg) throws Exception;
}
