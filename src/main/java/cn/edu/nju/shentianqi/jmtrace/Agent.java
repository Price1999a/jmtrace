package cn.edu.nju.shentianqi.jmtrace;

import java.lang.instrument.Instrumentation;

public class Agent {
    public static void premain(String agentArgs, Instrumentation inst) {
        System.out.println("premain() run");
    }

    public static void agentmain(String agentArgs, Instrumentation inst) {
        premain(agentArgs, inst);
    }

    public static void main(String[] args) {
        System.out.println("main() run");
    }
}
