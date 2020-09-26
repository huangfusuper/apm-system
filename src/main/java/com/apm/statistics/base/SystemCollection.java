package com.apm.statistics.base;

import com.apm.filter.RuleFilter;

import java.lang.instrument.Instrumentation;

/**
 * 采集方法的接口
 *
 * @author huangfu
 * @date 2020/9/26 22:25
 */
public interface SystemCollection {
    /**
     * 统计方法
     * @param ruleFilter 规则过滤器
     * @param instrumentation javaAgent 拦截器
     */
    void collection(RuleFilter ruleFilter, Instrumentation instrumentation);
}
