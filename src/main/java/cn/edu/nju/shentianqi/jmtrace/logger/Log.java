package cn.edu.nju.shentianqi.jmtrace.logger;

import java.util.ArrayList;
import java.util.List;

public class Log {
    private static boolean verbose = false;

    private static List<String> res = new ArrayList<>();

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
        for (String s : res) {
            System.out.println(s);
        }
    }

    public static synchronized boolean addRes(String s) {
        return res.add(s);
    }

    public static void main(String[] args) {
        verbose = true;
        out(123412);
        out("fsd");
        err(52f);
        err("gs gdf");
    }
}
