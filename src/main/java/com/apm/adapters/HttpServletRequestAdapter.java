package com.apm.adapters;

import com.apm.exceptions.ApmException;

import java.lang.reflect.Method;
import java.util.Enumeration;
import java.util.HashMap;
import java.util.Map;

/**
 * http适配器
 *
 * @author huangfu
 * @date 2020年9月27日18:30:50
 */
public class HttpServletRequestAdapter {
    /**
     * 目标对象
     */
    private final Object target;
    /**
     * 获取请求的uri方法
     */
    private final Method getRequestUri$JavaAgent;
    /**
     * 获取请求的rul方法
     */
    private final Method getRequestUrl$JavaAgent;

    /**
     * 获取请求方法的url
     */
    private final Method getMethod$JavaAgent;
    /**
     * 获取请求头
     */
    private final Method getHeader$JavaAgent;
    private final Method getHeaderNames$JavaAgent;
    /**
     * 获取servletPath
     */
    private final Method getContextPath$JavaAgent;

    /**
     * 获取目标类名
     */
    private final static String TARGET_CLASSNAME = "javax.servlet.http.HttpServletRequest";

    public HttpServletRequestAdapter(Object target) {
        this.target = target;
        try {
            Class<?> targetClass = target.getClass().getClassLoader().loadClass(TARGET_CLASSNAME);
            getRequestUri$JavaAgent = targetClass.getDeclaredMethod("getRequestURI");
            getMethod$JavaAgent = targetClass.getDeclaredMethod("getMethod");
            getHeader$JavaAgent = targetClass.getDeclaredMethod("getHeader", String.class);
            getRequestUrl$JavaAgent = targetClass.getDeclaredMethod("getRequestURL");
            getHeaderNames$JavaAgent = targetClass.getDeclaredMethod("getHeaderNames");
            getContextPath$JavaAgent = targetClass.getDeclaredMethod("getContextPath");
        } catch (Exception e) {
            throw ApmException.wrap(e);
        }
    }

    /**
     * 获取请求uri
     *
     * @return 返回请求的uri
     */
    public String getRequestUri() {
        try {
            return (String) getRequestUri$JavaAgent.invoke(target);
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }

    /**
     * 获取请求url
     *
     * @return 返回请求的url
     */
    public String getRequestUrl() {
        try {
            return getRequestUrl$JavaAgent.invoke(target).toString();
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }

    /**
     * 获取contextPath
     *
     * @return 返回看路径信息
     */
    public String getContextPath() {
        try {
            return getContextPath$JavaAgent.invoke(target).toString();
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }

    /**
     * 获取请求方式
     *
     * @return 请求方式
     */
    public String getMethod() {
        try {
            return (String) getMethod$JavaAgent.invoke(target);
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }

    /**
     * 获取指定Key的handler
     *
     * @return 返回对应的值
     */
    public Map<String, String> getHeaderMap() {
        Map<String, String> handlerMap = new HashMap<>(8);
        try {
            //所有的头名称
            Enumeration headerNames = (Enumeration) getHeaderNames$JavaAgent.invoke(target);
            while (headerNames.hasMoreElements()) {
                String headerName = (String) headerNames.nextElement();
                //获取请求头的值
                String handlerValue = getHeader(headerName);
                //构建结果
                handlerMap.put(headerName, handlerValue);
            }
            return handlerMap;
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }

    /**
     * 获取请求头的值
     *
     * @param name handler的key
     * @return 对应的值
     */
    public String getHeader(String name) {
        try {
            return (String) getHeader$JavaAgent.invoke(target, name);
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }
}
