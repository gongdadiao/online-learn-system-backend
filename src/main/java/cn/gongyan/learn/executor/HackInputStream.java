package cn.gongyan.learn.executor;

import cn.gongyan.learn.utils.CommonValues;

import java.io.*;

/**
 * @author 龚研
 * @desc HackInputStream
 * @date 2020/5/6
 * @qq 1085704190
 **/
public class HackInputStream extends InputStream {
    private ThreadLocal<FileInputStream> in; // 每个线程的标准输出流
    private ThreadLocal<Boolean> trouble; // 每个线程的标准输出写入过程是否抛出IOException

    private void ensure()  {
        if(in.get()==null){
            try {
                in.set(new FileInputStream(new File(CommonValues.sysInputFileName)));
            } catch (FileNotFoundException e) {
                e.printStackTrace();
            }
        }
    }
    @Override
    public int read(byte[] b) throws IOException {
        ensure();
        return in.get().read(b);
    }

    @Override
    public int read(byte[] b, int off, int len) throws IOException {
        ensure();
        return in.get().read(b, off, len);
    }

    @Override
    public long skip(long n) throws IOException {
        ensure();
       return in.get().skip(n);
    }

    @Override
    public synchronized void mark(int readlimit) {
        ensure();
        in.get().mark(readlimit);
    }

    @Override
    public synchronized void reset() throws IOException {
//        ensure();
//        in.get().reset();
        try {
            in.set(new FileInputStream(new File(CommonValues.sysInputFileName)));
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        }
    }

    @Override
    public boolean markSupported() {
        ensure();
        return in.get().markSupported();
    }

    public HackInputStream() {
        super();
        in = new ThreadLocal<>();
        try {
            in.set(new FileInputStream(new File(CommonValues.sysInputFileName)));
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        }
        trouble = new ThreadLocal<>();
    }

    @Override
    public int available() throws IOException {
        ensure();
        return in.get().available();
    }

    @Override
    public void close() throws IOException {
        ensure();
        in.get().close();
    }

    @Override
    public int read() throws IOException {
        ensure();
        return in.get().read();
    }
}
