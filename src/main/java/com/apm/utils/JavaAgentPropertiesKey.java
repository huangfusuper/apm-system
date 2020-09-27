package com.apm.utils;

/**
 * java-agent 配置文件主键key
 *
 * @author huangfu
 */
public enum JavaAgentPropertiesKey {
    /**
     * 服务过滤器包主键
     */
    FILTER_SERVICE_PACKAGE("filter.service.package","服务过滤器包主键！"),
    /**
     * 服务前缀
     */
    FILTER_SERVICE_CLASS_PREFIX("filter.service.class.prefix","服务类前缀！"),
    /**
     * http请求方法
     */
    FILTER_HTTP_URL_METHOD("filter.http.url.method","http请求方法！"),
    ;
    /**
     * 键名称
     */
    private final String keyName;
    /**
     * 介绍
     */
    private final String introduction;

    JavaAgentPropertiesKey(String keyName, String introduction) {
        this.keyName = keyName;
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
}
