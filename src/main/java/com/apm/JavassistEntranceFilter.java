package com.apm;

import javassist.ClassPool;
import javassist.CtClass;
import javassist.CtMethod;

import java.lang.instrument.ClassDefinition;
import java.lang.instrument.Instrumentation;

/**
 * JavassistEntrance 字节码入口拦截器
 *
 * @author huangfu
 * @date 2020年9月25日08:57:22
 */
public class JavassistEntranceFilter {
    public static void premain(String args, Instrumentation instrumentation) throws Exception {
        System.out.println("拦截器生效" + args);
        /**
         * javaagent 总结
         * 1.instrumentation addTransformer 类装载拦截
         * 2.只能拦截未装载过的类
         * 3.instrumentation#retransformClasses方法 重新装载类 ，必须开启相关参数
         * 4.instrumentation.redefineClasses 重新定义一个类 ，不能添加新方法 ，必须开启相关参数
         *
         */
        ClassPool pool = new ClassPool();
        pool.appendSystemPath();
        CtClass ctClass = pool.get("com.apm.UserService");
        CtMethod sayHello = ctClass.getDeclaredMethod("sayHello");
        sayHello.insertAfter("System.out.println(\"皇甫科星  拦截器添加的---\");");
        instrumentation.redefineClasses(new ClassDefinition(Class.forName("com.apm.UserService"), ctClass.toBytecode()));
    }
}
