package com.apm.utils;

import com.apm.entity.BaseStatisticsEntity;
import com.apm.exceptions.ApmException;

/**
 * 统计方法枚举
 *
 * @author huangfu
 * @date 2020年9月27日14:51:25
 */
public class StatisticsMethodUtils {

    /**
     * 方法初始化
     *
     * @param baseStatisticsEntity 基础类型
     * @param modelType            统计类型
     */
    public static void begin(BaseStatisticsEntity baseStatisticsEntity, String modelType) {
        baseStatisticsEntity.setStartTime(System.currentTimeMillis());
        baseStatisticsEntity.setModelType(modelType);
    }

    /**
     * 设置错误信息
     *
     * @param baseStatisticsEntity 基础类型
     * @param throwable            错误信息
     */
    public static void setErrorData(BaseStatisticsEntity baseStatisticsEntity, Throwable throwable) {
        baseStatisticsEntity.setErrorType(throwable.getClass().getTypeName());
        baseStatisticsEntity.setErrorMsg(ApmException.getStackTrace(throwable));
    }

    /**
     * 设置结束信息
     *
     * @param baseStatisticsEntity 基础类型
     */
    public static void end(BaseStatisticsEntity baseStatisticsEntity) {
        baseStatisticsEntity.setEndTime(System.currentTimeMillis());
        baseStatisticsEntity.setUseTime(baseStatisticsEntity.getEndTime() - baseStatisticsEntity.getStartTime());
    }
}
