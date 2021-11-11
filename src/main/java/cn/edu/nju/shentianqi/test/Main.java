package cn.edu.nju.shentianqi.test;

import cn.edu.nju.shentianqi.jmtrace.Agent;
import cn.edu.nju.shentianqi.jmtrace.logger.Log;

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

public class Main {
    public static void main(String[] args) throws InterruptedException {
        Log.enableStdOut();
        Log.out("main() run");
        usage();
        test1();
        Log.out("test1() end");
        test2();
        Log.out("test2() end");
        test3();
        Log.out("test3() end");
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
            new Thread(Main::test1).start();
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
