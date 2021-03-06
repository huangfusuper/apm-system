package com.apm.utils;

/**
 * java-agent 配置文件主键key
 *
 * @author huangfu
 */
public enum JavaAgentPropertiesKey {
    /**
     * 是否开启service监控
     */
    ENABLE_SERVICE_MONITOR("filter.service.enable","false","是否开启service监控 false不开启（默认）  true开启"),
    /**
     * 是否开启http监控
     */
    ENABLE_HTTP_MONITOR("filter.http.enable","false","是否开启http监控 false不开启（默认）  true开启"),
    /**
     * 服务过滤器包主键
     */
    FILTER_SERVICE_PACKAGE("filter.service.package","*","服务过滤器包主键！"),
    /**
     * 服务前缀
     */
    FILTER_SERVICE_CLASS_PREFIX("filter.service.class.prefix","*","服务类前缀！"),

    ;
    /**
     * 键名称
     */
    private final String keyName;

    /**
     * 默认值
     */
    private final String defaultValue;

    /**
     * 介绍
     */
    private final String introduction;

    JavaAgentPropertiesKey(String keyName, String defaultValue, String introduction) {
        this.keyName = keyName;
        this.defaultValue = defaultValue;
        this.introduction = introduction;
    }

    @Override
    public String toString() {
        return String.format("%s:%s", keyName, introduction);
    }

    public String getKeyName() {
        return keyName;
    }

    public String getIntroduction() {
        return introduction;
    }

    public String getDefaultValue() {
        return defaultValue;
    }
}
