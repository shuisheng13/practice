package com.yhh.thread.lock.readOrWriteLock;

import java.util.concurrent.locks.ReentrantReadWriteLock;

public class ReadWriteLockDemol {

    private int num = 0;
    private final ReentrantReadWriteLock lock = new ReentrantReadWriteLock();
    private ReentrantReadWriteLock.ReadLock readLock = lock.readLock();
    private ReentrantReadWriteLock.WriteLock writeLock = lock.writeLock();


    public void read(){
        readLock.lock();
        try{
            System.out.println(Thread.currentThread().getName()+"  读取num值 为 ：  "+num);
        }finally {
            readLock.unlock();
        }
    }
    public void write(){
        writeLock.lock();
        try{
            Thread.sleep(2000);
            num++;
            System.out.println(Thread.currentThread().getName()+"  写 num值 为 ：  "+num);
        } catch (InterruptedException e) {
            e.printStackTrace();
        } finally {
            writeLock.unlock();
        }
    }


    private static class ReadThread implements Runnable{
        private ReadWriteLockDemol readWriteLockDemol;

        public ReadThread(ReadWriteLockDemol readWriteLockDemol) {
            this.readWriteLockDemol = readWriteLockDemol;
        }

        public void run() {
            readWriteLockDemol.read();
        }
    }

    private static class Writehread implements Runnable{
        private ReadWriteLockDemol readWriteLockDemol;

        public Writehread(ReadWriteLockDemol readWriteLockDemol) {
            this.readWriteLockDemol = readWriteLockDemol;
        }

        public void run() {
            readWriteLockDemol.write();
        }
    }


    public static void main(String[] args) {


         final ReadWriteLockDemol demol = new ReadWriteLockDemol();

        for (int i = 0; i <10 ; i++) {
            new Thread(new Writehread(demol)).start();
        }

        for (int i = 0; i <1000 ; i++) {
            new Thread(new ReadThread(demol)).start();
        }

    }


}
