package com.apm.entity;

import java.util.Arrays;

/**
 * 服务方法的信息载体
 *
 * @author huangfu
 * @date 2020/9/26 22:21
 */
public class ServiceStatisticsEntity extends BaseStatisticsEntity {
    /**
     * 类全限定名
     */
    private String className;
    /**
     * 简短的名
     */
    private String simpleClassName;
    /**
     * 方法名
     */
    private String methodName;
    /**
     * 方法参数
     */
    private Object[] args;
    /**
     * 返回值类型
     */
    private String returnType;
    /**
     * 返回值
     */
    private Object returnValue;

    public String getClassName() {
        return className;
    }

    public void setClassName(String className) {
        this.className = className;
    }

    public String getSimpleClassName() {
        return simpleClassName;
    }

    public void setSimpleClassName(String simpleClassName) {
        this.simpleClassName = simpleClassName;
    }

    public String getMethodName() {
        return methodName;
    }

    public void setMethodName(String methodName) {
        this.methodName = methodName;
    }

    public Object[] getArgs() {
        return args;
    }

    public void setArgs(Object[] args) {
        this.args = args;
    }

    public String getReturnType() {
        return returnType;
    }

    public void setReturnType(String returnType) {
        this.returnType = returnType;
    }

    public Object getReturnValue() {
        return returnValue;
    }

    public void setReturnValue(Object returnValue) {
        this.returnValue = returnValue;
    }

    @Override
    public String toString() {
        return "ServiceStatisticsEntity{" +
                "className='" + className + '\'' +
                ", simpleClassName='" + simpleClassName + '\'' +
                ", methodName='" + methodName + '\'' +
                ", args=" + Arrays.toString(args) +
                ", returnType='" + returnType + '\'' +
                ", returnValue=" + returnValue +
                '}' + String.format("%s\n%s\n",getStartTime(),getUseTime());
    }
}
