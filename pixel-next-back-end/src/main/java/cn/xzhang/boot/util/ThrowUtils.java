package cn.xzhang.boot.util;

import cn.xzhang.boot.common.exception.ErrorCode;
import cn.xzhang.boot.common.exception.ServiceException;
import cn.xzhang.boot.common.exception.util.ServiceExceptionUtil;


/**
 * @Author code_zhang
 * @Date 2024/12/17 13:28
 */
public class ThrowUtils {


    public static void throwIf(boolean condition, ServiceException exception) {
        if (condition) {
            throw exception;
        }
    }

    public static void throwIf(boolean condition, ErrorCode errorCode) {
        throwIf(condition, new ServiceException(errorCode));
    }

    public static void throwIf(boolean condition, ErrorCode errorCode, Object... message) {
        throwIf(condition, ServiceExceptionUtil.exception0(errorCode.getCode(), errorCode.getMsg(), message));
    }
}
