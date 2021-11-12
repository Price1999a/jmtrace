package cn.edu.nju.shentianqi.jmtrace.logger;

public class Data {
    String RW, name;
    int hashcode;
    long pid;

    public Data(String _rw, long _pid, int _hashcode, String _name) {
        RW = _rw;
        name = _name;
        pid = _pid;
        hashcode = _hashcode;
    }

    public int getHashcode() {
        return hashcode;
    }

    public long getPid() {
        return pid;
    }

    public String getName() {
        return name;
    }

    public String getRW() {
        return RW;
    }

    @Override
    public String toString() {
        return String.format("%s\t%d\t%08x\t%s", RW, pid, hashcode, name);
    }
}
