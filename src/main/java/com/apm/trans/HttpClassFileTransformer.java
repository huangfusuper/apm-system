package com.apm.trans;

import java.lang.instrument.IllegalClassFormatException;
import java.security.ProtectionDomain;

/**
 * http方法转换器
 *
 * @author huangfu
 */
public class HttpClassFileTransformer extends BaseClassFileTransformer {


    @Override
    public byte[] doTransform(ClassLoader loader, String className, Class<?> classBeingRedefined, ProtectionDomain protectionDomain, byte[] classfileBuffer) throws IllegalClassFormatException {
        return new byte[0];
    }

    @Override
    public boolean dataMatching(String verifyTheData) {
        return false;
    }
}
