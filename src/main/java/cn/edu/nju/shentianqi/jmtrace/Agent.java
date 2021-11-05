package cn.edu.nju.shentianqi.jmtrace;

import cn.edu.nju.shentianqi.jmtrace.logger.Log;

import java.lang.instrument.Instrumentation;

class SomeClass {
    int field;
    static int staticField;
    int otherField;

    boolean aBoolean;
    byte aByte;
    char aChar;
    int anInt;
    float aFloat;
    long aLong;
    double aDouble;
}

public class Agent {
    public static void premain(String agentArgs, Instrumentation inst) {
        Log.out("premain() run");
        Runtime.getRuntime().addShutdownHook(new Thread(Log::printRes));
    }

    public static void agentmain(String agentArgs, Instrumentation inst) {
        premain(agentArgs, inst);
    }

    public static void main(String[] args) throws InterruptedException {
        Log.enableStdOut();
        Log.out("main() run");
        usage();
        test1();
        test2();
        test3();
    }

    private static void usage() {
        Log.out("This program is a Java Agent program, and now it runs with the -jar option, then the test program from https://github.com/keyile/jmtrace will be executed at this time.");
    }

    private static void test1() {
        int[] a = new int[10];
        int i;
        for (i = 0; i < a.length; i++) {
            a[i] = i;
        }
        i = a[a.length - 1];
        SomeClass someObj = new SomeClass();
        SomeClass.staticField = i;
        someObj.otherField = someObj.field;
    }

    private static void test2() throws InterruptedException {
        for (int i = 0; i < 2; i++) {
            new Thread(Agent::test1).start();
        }
        Thread.sleep(50);
        int i = SomeClass.staticField;
    }

    private static void test3() {
        SomeClass o = new SomeClass();
        o.aBoolean = true;
        o.aByte = 3;
        o.aChar = 'z';
        o.anInt = 32;
        o.aFloat = 0.2f;
        o.aLong = 999L;
        o.aDouble = 4.5;

        String s = (o.aBoolean + " " + (o.aByte + o.aChar + o.anInt + o.aFloat + o.aLong + o.aDouble));
    }
}
