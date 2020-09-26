package com.apm.filter;

/**
 * *********************************************************************
 * 数据校验过滤器接口
 * *********************************************************************
 *
 * @author huangfu
 * @version 1.0
 * @date 2020/09/26/ 22:15
 */
public interface RuleFilter {
    /**
     * 验证数据规则
     *
     * @param verifyTheData 需要验证的数据
     * @return 该数据是否匹配预设规则
     */
    boolean dataMatching(String verifyTheData);
}
