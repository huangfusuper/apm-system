package com.apm.exceptions;

import java.io.PrintWriter;
import java.io.StringWriter;

/**
 * @author huangfu
 */
public class ApmException extends RuntimeException {
    public ApmException(String message) {
        super(message);
    }

    /**
     * 异常转换
     *
     * @param throwable 异常信息
     * @return 异常类
     */
    public static ApmException wrap(Throwable throwable) {
        return new ApmException(getStackTrace(throwable));
    }

    /**
     * 获取堆栈信息
     *
     * @param throwable 异常信息
     * @return 堆栈信息
     */
    public static String getStackTrace(Throwable throwable) {
        StringWriter sw = new StringWriter();
        try (PrintWriter pw = new PrintWriter(sw)) {
            throwable.printStackTrace(pw);
            return sw.toString();
        }
    }
}
