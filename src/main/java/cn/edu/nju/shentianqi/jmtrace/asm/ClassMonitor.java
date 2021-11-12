package cn.edu.nju.shentianqi.jmtrace.asm;

import cn.edu.nju.shentianqi.jmtrace.Agent;
import cn.edu.nju.shentianqi.jmtrace.logger.Log;
import org.objectweb.asm.*;

import java.lang.instrument.ClassFileTransformer;
import java.lang.instrument.IllegalClassFormatException;
import java.security.ProtectionDomain;

public class ClassMonitor implements ClassFileTransformer {
    @Override
    public byte[] transform(ClassLoader loader, String className, Class<?> classBeingRedefined, ProtectionDomain protectionDomain, byte[] classfileBuffer) throws IllegalClassFormatException {
        if (isSkippable(className)) return null;
        Log.out("transform: " + className);
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
                || className.startsWith("sun")
                || className.startsWith("jdk")
                || className.startsWith(("java"))
                ;
    }
}

class MethodAdaptor extends MethodVisitor {
    public MethodAdaptor(MethodVisitor mv) {
        super(Agent.apiVersion, mv);
    }

    @Override
    public void visitFieldInsn(int opcode, String owner, String name, String descriptor) {
        if (opcode == Opcodes.GETSTATIC) {
            //mv.visitLdcInsn(Type.getType("Lcn/edu/nju/shentianqi/test/Main;"));
            mv.visitLdcInsn(Type.getType("L" + owner + ";"));
            mv.visitLdcInsn(name);
            //logGetStatic(Ljava/lang/Class;Ljava/lang/String;)V
            mv.visitMethodInsn(Opcodes.INVOKESTATIC,
                    "cn/edu/nju/shentianqi/jmtrace/logger/Log",
                    "logGetStatic",
                    "(Ljava/lang/Class;Ljava/lang/String;)V",
                    false);
        } else if (opcode == Opcodes.PUTSTATIC) {
            mv.visitLdcInsn(Type.getType("L" + owner + ";"));
            mv.visitLdcInsn(name);
            mv.visitMethodInsn(Opcodes.INVOKESTATIC,
                    "cn/edu/nju/shentianqi/jmtrace/logger/Log",
                    "logPutStatic",
                    "(Ljava/lang/Class;Ljava/lang/String;)V",
                    false);

        } else if (opcode == Opcodes.GETFIELD) {
            //..., objectref →
            //..., value
            //logGetField(Ljava/lang/Object;Ljava/lang/String;)V
            mv.visitInsn(Opcodes.DUP);
            mv.visitLdcInsn(name);
            mv.visitMethodInsn(Opcodes.INVOKESTATIC,
                    "cn/edu/nju/shentianqi/jmtrace/logger/Log",
                    "logGetField",
                    "(Ljava/lang/Object;Ljava/lang/String;)V",
                    false);
        } else if (opcode == Opcodes.PUTFIELD) {
            //..., objectref, value →
            //...
            //这里value就有两种情况了 可能是宽类型
            if (Type.getType(descriptor).getSize() == 1) {
                mv.visitInsn(Opcodes.DUP2);
                mv.visitInsn(Opcodes.POP);
                mv.visitLdcInsn(name);
                mv.visitMethodInsn(Opcodes.INVOKESTATIC,
                        "cn/edu/nju/shentianqi/jmtrace/logger/Log",
                        "logPutField",
                        "(Ljava/lang/Object;Ljava/lang/String;)V",
                        false);
            } else if (Type.getType(descriptor).getSize() == 2) {
                //value1 value2
                mv.visitInsn(Opcodes.DUP2_X1);
                //value2 value1 value2
                mv.visitInsn(Opcodes.POP2);
                //value2 value1
                mv.visitInsn(Opcodes.DUP);
                //value2 value1 value1(object)
                mv.visitLdcInsn(name);
                //value2 value1 value1(object) value1(name)
                mv.visitMethodInsn(Opcodes.INVOKESTATIC,
                        "cn/edu/nju/shentianqi/jmtrace/logger/Log",
                        "logPutField",
                        "(Ljava/lang/Object;Ljava/lang/String;)V",
                        false);
                //value2 value1
                mv.visitInsn(Opcodes.DUP_X2);
                //value1 value2 value1
                mv.visitInsn(Opcodes.POP);
                //value1 value2
                // nothing to say
            }
        }
        super.visitFieldInsn(opcode, owner, name, descriptor);
    }

    @Override
    public void visitInsn(int opcode) {
        if (opcode >= Opcodes.IALOAD && opcode <= Opcodes.SALOAD) {
            //..., arrayref, index →
            //..., value
            mv.visitInsn(Opcodes.DUP2);
            //logArrayLoad(Ljava/lang/Object;I)V
            mv.visitMethodInsn(Opcodes.INVOKESTATIC,
                    "cn/edu/nju/shentianqi/jmtrace/logger/Log",
                    "logArrayLoad",
                    "(Ljava/lang/Object;I)V",
                    false);
        }
        super.visitInsn(opcode);
    }
}
