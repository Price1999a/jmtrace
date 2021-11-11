package cn.edu.nju.shentianqi.jmtrace.asm;

import cn.edu.nju.shentianqi.jmtrace.Agent;
import org.objectweb.asm.ClassReader;
import org.objectweb.asm.ClassVisitor;
import org.objectweb.asm.ClassWriter;
import org.objectweb.asm.MethodVisitor;

import java.lang.instrument.ClassFileTransformer;
import java.lang.instrument.IllegalClassFormatException;
import java.security.ProtectionDomain;

public class ClassMonitor implements ClassFileTransformer {
    @Override
    public byte[] transform(ClassLoader loader, String className, Class<?> classBeingRedefined, ProtectionDomain protectionDomain, byte[] classfileBuffer) throws IllegalClassFormatException {
        if (isSkippable(className)) return null;
        ClassReader classReader = new ClassReader(classfileBuffer);
        ClassWriter classWriter = new ClassWriter(ClassWriter.COMPUTE_MAXS);
        ClassVisitor classVisitor = new ClassVisitor(Agent.apiVersion, classWriter) {
            @Override
            public MethodVisitor visitMethod(int access, String name, String descriptor, String signature, String[] exceptions) {
                return new MethodAdaptor(super.visitMethod(access, name, descriptor, signature, exceptions));
                //return super.visitMethod(access, name, descriptor, signature, exceptions);
            }
        };
        classReader.accept(classVisitor, ClassReader.SKIP_DEBUG);
        return classWriter.toByteArray();
    }

    private boolean isSkippable(String className) {
        //防止奇怪的lambda匿名类
        if (className == null) return true;
        return className.startsWith("cn/edu/nju/shentianqi/jmtrace")
//                || className.startsWith("sun")
//                || className.startsWith("jdk")
                ;
    }
}

class MethodAdaptor extends MethodVisitor {
    public MethodAdaptor(MethodVisitor mv) {
        super(Agent.apiVersion, mv);
    }

    @Override
    public void visitFieldInsn(int opcode, String owner, String name, String descriptor) {
        super.visitFieldInsn(opcode, owner, name, descriptor);
    }
}
