package com.apm.entity;

import java.util.Map;

/**
 * http方法数据载体
 *
 * @author huangfu
 * @date 2020年9月27日18:22:32
 */
public class HttpStatisticsEntity extends BaseStatisticsEntity {
    /**
     * 请求的uri
     */
    private String uri;
    /**
     * 请求的url
     */
    private String url;
    /**
     * 访问方式
     */
    private String method;
    /**
     * 请求头
     */
    private Map<String, String> handlerMap;
    /**
     * 请求路径
     */
    private String contextPath;

    public String getUri() {
        return uri;
    }

    public void setUri(String uri) {
        this.uri = uri;
    }

    public String getUrl() {
        return url;
    }

    public void setUrl(String url) {
        this.url = url;
    }


    public String getMethod() {
        return method;
    }

    public void setMethod(String method) {
        this.method = method;
    }

    public Map<String, String> getHandlerMap() {
        return handlerMap;
    }

    public void setHandlerMap(Map<String, String> handlerMap) {
        this.handlerMap = handlerMap;
    }

    public String getContextPath() {
        return contextPath;
    }

    public void setContextPath(String contextPath) {
        this.contextPath = contextPath;
    }

    @Override
    public String toString() {
        return "HttpStatisticsEntity{" +
                "uri='" + uri + '\'' +
                ", url='" + url + '\'' +
                ", method='" + method + '\'' +
                ", handlerMap=" + handlerMap +
                ", contextPath='" + contextPath + '\'' +
                '}' + String.format("%s-------%s", getUseTime(), getModelType());
    }
}
