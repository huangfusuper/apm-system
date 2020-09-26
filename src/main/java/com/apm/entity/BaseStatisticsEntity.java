package com.apm.entity;

/**
 * 基础的信息  每种类型的都必须统计的
 *
 * @date 2020/9/26 22:14:56
 * @author huangfu
 */
public class BaseStatisticsEntity {
    /**
     * 开始时间
     */
    private long startTime;
    /**
     * 结束时间
     */
    private long endTime;
    /**
     * 调用时间
     */
    private long useTime;
    /**
     * 错误类型
     */
    private String errorType;
    /**
     * 错误信息
     */
    private String errorMsg;

    public long getStartTime() {
        return startTime;
    }

    public void setStartTime(long startTime) {
        this.startTime = startTime;
    }

    public long getEndTime() {
        return endTime;
    }

    public void setEndTime(long endTime) {
        this.endTime = endTime;
    }

    public long getUseTime() {
        return useTime;
    }

    public void setUseTime(long useTime) {
        this.useTime = useTime;
    }

    public String getErrorType() {
        return errorType;
    }

    public void setErrorType(String errorType) {
        this.errorType = errorType;
    }

    public String getErrorMsg() {
        return errorMsg;
    }

    public void setErrorMsg(String errorMsg) {
        this.errorMsg = errorMsg;
    }
}
