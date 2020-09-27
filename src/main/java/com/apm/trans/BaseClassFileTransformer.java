package com.apm.trans;

import com.apm.exceptions.ApmException;
import com.apm.filter.RuleFilter;
import javassist.*;

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
    public byte[] doTransform(ClassLoader loader, String className, Class<?> classBeingRedefined,
                                       ProtectionDomain protectionDomain, byte[] classfileBuffer) throws IllegalClassFormatException {
        try {
            return buildCtClass(loader, className).toBytecode();
        } catch (Exception e) {
            System.err.println(ApmException.getStackTrace(e));
            throw ApmException.wrap(e);
        }
    }

    /**
     * 构建个类对象 修改后的
     * @param loader 类加载器
     * @param className 类名称
     * @return 修改后的类
     * @throws Exception 异常信息
     */
    public CtClass buildCtClass(ClassLoader loader, String className) throws Exception {
        //定义类池
        ClassPool pool = new ClassPool();
        //加载当前项目内的所由类路径
        pool.insertClassPath(new LoaderClassPath(loader));
        //获取类的操作对象
        CtClass ctClass = pool.get(className);
        //获取方法级操作对象
        CtMethod[] declaredMethods = ctClass.getDeclaredMethods();
        for (CtMethod declaredMethod : declaredMethods) {
            //屏蔽非公共方法
            if (!Modifier.isPublic(declaredMethod.getModifiers())) {
                continue;
            }
            //屏蔽静态方法
            if (Modifier.isStatic(declaredMethod.getModifiers())) {
                continue;
            }
            //屏蔽本地方法
            if (Modifier.isNative(declaredMethod.getModifiers())) {
                continue;
            }
            //修改原始方法
            buildMethod(ctClass, declaredMethod);
        }
        return ctClass;
    }

    /**
     * 构建统计方法对象
     *
     * @param ctClass  类对象
     * @param ctMethod 方法对象
     * @throws Exception 异常
     */
    public abstract void buildMethod(CtClass ctClass, CtMethod ctMethod) throws Exception;

    /**
     * java方法构建器
     * @param oldMethodName 原始方法名称
     * @return 构建器构建的方法名称
     */
    public String javaAgentMethodNameBuild(String oldMethodName){
        return String.format("%s$JavaAgentMethod",oldMethodName);
    }
}
