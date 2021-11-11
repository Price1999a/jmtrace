package cn.edu.nju.shentianqi.jmtrace;

import cn.edu.nju.shentianqi.jmtrace.asm.ClassMonitor;
import cn.edu.nju.shentianqi.jmtrace.logger.Log;
import org.objectweb.asm.Opcodes;

import java.lang.instrument.Instrumentation;

public class Agent {
    public static final int apiVersion = Opcodes.ASM9;

    public static void premain(String agentArgs, Instrumentation inst) {
        Log.out("premain() run");
        inst.addTransformer(new ClassMonitor(), true);
        Runtime.getRuntime().addShutdownHook(new Thread(Log::printRes));
    }

    public static void agentmain(String agentArgs, Instrumentation inst) {
        premain(agentArgs, inst);
    }

}
