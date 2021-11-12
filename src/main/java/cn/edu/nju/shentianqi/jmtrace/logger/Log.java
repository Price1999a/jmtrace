package cn.edu.nju.shentianqi.jmtrace.logger;

import java.util.ArrayList;
import java.util.List;

public class Log {
    private static boolean verbose = false;

    private static List<Data> res = new ArrayList<>();

    public static void out(Object x) {
        if (verbose)
            System.out.println("[stdout:]\t" + x);
    }

    public static void enableStdOut() {
        verbose = true;
    }

    public static void err(Object x) {
        System.err.println("[stderr:]\t" + x);
    }

    public static void printRes() {
        for (Data s : res) {
            System.out.println(s);
        }
    }

    public static synchronized boolean addRes(String rw, long pid, int hashcode, String name) {
        return res.add(new Data(rw, pid, hashcode, name));
    }

    public static void main(String[] args) {
        enableStdOut();
        out(123412);
        out("fsd");
        err(52f);
        err("gs gdf");
        out(String.format("%#010x", 213));
    }

    /**
     * R 1032 b026324c6904b2a9 cn.edu.nju.ics.Foo.someField
     * 读写
     * pid
     * 唯一值（标识当前访问对象）
     * 当前访问的对象或数组索引
     */

    public static void logGetStatic(Class<?> c, String name) {
        addRes("R",
                Thread.currentThread().getId(),
                System.identityHashCode(c) ^ System.identityHashCode(name),
                c.getCanonicalName() + "." + name);
    }

    public static void logPutStatic(Class<?> c, String name) {
        addRes("W",
                Thread.currentThread().getId(),
                System.identityHashCode(c) ^ System.identityHashCode(name),
                c.getCanonicalName() + "." + name);
    }

    public static void logGetField(Object o, String fieldName) {
        addRes("R",
                Thread.currentThread().getId(),
                System.identityHashCode(o) ^ System.identityHashCode(fieldName),
                o.getClass().getCanonicalName() + "." + fieldName);
    }

    public static void logPutField(Object o, String fieldName) {
        addRes("W",
                Thread.currentThread().getId(),
                System.identityHashCode(o) ^ System.identityHashCode(fieldName),
                o.getClass().getCanonicalName() + "." + fieldName);
    }

}
