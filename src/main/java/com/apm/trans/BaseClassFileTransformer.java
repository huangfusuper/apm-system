package com.apm.trans;

import com.apm.filter.RuleFilter;

import java.lang.instrument.IllegalClassFormatException;
import java.security.ProtectionDomain;

/**
 * @author huangfu
 */
public abstract class BaseClassFileTransformer implements RuleFilter {

    /**
     * 抽象方法 所有的子类必须实现该初始化方法
     * 该初始化方法可作用于类修改由系统调用
     *
     * @param loader              类加载器
     * @param className           类名称
     * @param classBeingRedefined 重定义的类
     * @param protectionDomain    保护域
     * @param classfileBuffer     类文件缓冲区
     * @return 修改后的类的字节
     * @throws IllegalClassFormatException 异常
     */
    public abstract byte[] doTransform(ClassLoader loader, String className, Class<?> classBeingRedefined,
                                       ProtectionDomain protectionDomain, byte[] classfileBuffer) throws IllegalClassFormatException;


    /**
     * java方法构建器
     * @param oldMethodName 原始方法名称
     * @return 构建器构建的方法名称
     */
    public String javaAgentMethodNameBuild(String oldMethodName){
        return String.format("%s$JavaAgentMethod",oldMethodName);
    }
}
