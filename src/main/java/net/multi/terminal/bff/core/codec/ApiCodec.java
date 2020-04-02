package net.multi.terminal.bff.core.codec;

import net.multi.terminal.bff.exception.SystemException;

public interface ApiCodec {
    String DEFAULT_CODEC = "EmptyApiCodec";
    CommonMsg decode(String encodeMsg) throws SystemException;

    String encode(CommonMsg commonMsg) throws SystemException;
}
