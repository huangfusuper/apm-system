package com.apm;

import javassist.*;

import java.lang.instrument.Instrumentation;

/**
 * JavassistEntrance 字节码入口拦截器
 *
 * @author huangfu
 * @date 2020年9月25日08:57:22
 */
public class JavassistEntranceFilter {
    public static void premain(String args, Instrumentation instrumentation) throws Exception {
        //添加一个类拦截器
        //ASM class组成原理 使用要求高
        //Javassist java编码
        instrumentation.addTransformer((loader, className, classBeingRedefined, protectionDomain, classfileBuffer) -> {
            //创建一个类池  只有存在这个类池的类才能被修改
            ClassPool classPool = new ClassPool();
            //将当前系统的classLoader当中的类增加到类池里面   通过classLoader 可以找到使用当前加载器加载的全部的类
            //ByteArrayClassPath  把一个字节加入到类池
            //ClassClassPath  把一个Class加入到类池
            //DirClassPath 把目录下的所有的Class加入到类池
            //JarClassPath 把一个jar包里的class加入到类池
            //JarDirClassPath 把一个目录下的jar的class加入到类池
            //LoaderClassPath 把一个加载器加载的类加载到类池
            //URLClassPath 把一个url加载到类池
            classPool.appendSystemPath();
            //获取对应的类
            try {
                String bodyTemp = "{\n\t%s\n\t%s\n\t%s\n\t%s\n}";
                CtClass ctClass = classPool.get("com.apm.UserService");
                CtMethod sayHello = ctClass.getDeclaredMethod("sayHello");
                CtMethod copyMethod = CtNewMethod.copy(sayHello, ctClass, null);
                sayHello.setName(sayHello.getName() + "$javaagent");
                copyMethod.setBody(String.format(bodyTemp, "long startTime = System.currentTimeMillis();",
                        sayHello.getName() + "();", "long end = System.currentTimeMillis();",
                        "System.out.println(end - startTime);"));

                //把修改后的类重新装载进JVM
                ctClass.addMethod(copyMethod);
                ctClass.toClass();
            } catch (NotFoundException | CannotCompileException e) {
                e.printStackTrace();
            }
            return new byte[0];
        });

    }
}
