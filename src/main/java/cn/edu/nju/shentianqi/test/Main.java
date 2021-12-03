package cn.edu.nju.shentianqi.test;

import cn.edu.nju.shentianqi.jmtrace.logger.Log;

class SomeClass {
    int field;
    static int staticField = 1;
    int otherField;

    boolean aBoolean;
    byte aByte;
    char aChar;
    int anInt;
    float aFloat;
    long aLong;
    double aDouble;
    String aString;

    static String aStringStatic = "static";
}

public class Main {
    public class Tiny {
        int k;
        int das$1;
    }

    public Tiny t = new Tiny();

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
        Class<?> c = Main.class;
        Log.out(c);
        Main main = new Main();
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
        i = a[0];
        SomeClass someObj = new SomeClass();
        SomeClass.staticField = i;
        someObj.otherField = someObj.field;
        //Log.out("test1() function end");
    }

    private static void test2() throws InterruptedException {
        for (int i = 0; i < 2; i++) {
            new Thread(Main::test1).start();
        }
        Thread.sleep(500);
        int i = SomeClass.staticField;
        //Log.out("test2() function end");
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
        o.aString = "极限竞速地平线";

        String s = (o.aBoolean + " " + (o.aByte + o.aChar + o.anInt + o.aFloat + o.aLong + o.aDouble) + o.aString);
        //Log.out("test3() function end");
        double[] doubles = new double[2];
        for (int i = 0; i < doubles.length; i++) {
            doubles[i] = Math.PI * i;
        }
        for (double aDouble : doubles) {
            s += aDouble;
            s += " ";
        }
        StringBuilder[] sbs = new StringBuilder[2];
        for (int i = 0; i < sbs.length; i++)
            sbs[i] = new StringBuilder("StringBuilder");
        sbs[1].append(32);
        sbs[0].append("4567f");
        s += SomeClass.aStringStatic;
        for (StringBuilder sb : sbs) {
            s += sb.toString() + " ";
        }
        Log.out("test3 " + s);
    }
}
