package com.yhh.thread.lock;

import java.util.concurrent.BrokenBarrierException;
import java.util.concurrent.CyclicBarrier;
import java.util.concurrent.locks.ReentrantLock;

public class ReetrantLockDemol {

    private static int num =0;
    private static ReentrantLock reentrantLock = new ReentrantLock();
    private static CyclicBarrier cyclicBarrier = new CyclicBarrier(100);

    /***
     * 测试加锁
     */
    public static void test1(){
        //reentrantLock.lock();
        try{
            Thread.sleep(10);
            num++;
            System.out.println("num ------>  "+num);
        } catch (InterruptedException e) {
            e.printStackTrace();
        } finally {
            //reentrantLock.unlock();
        }
    }

    public static void test2() throws InterruptedException {
        Thread.sleep(10);
        num++;
        System.out.println("num ------>  "+num);
    }

    /***
     * 加锁线程
     */
    private static class LockThread implements Runnable{
        private ReentrantLock reentrantLock;
        private CyclicBarrier cyclicBarrier;

        public LockThread(ReentrantLock reentrantLock, CyclicBarrier cyclicBarrier) {
            this.reentrantLock = reentrantLock;
            this.cyclicBarrier = cyclicBarrier;
        }
        public void run() {
            try {
                cyclicBarrier.await();
                for (int i = 0; i <100 ; i++) {
                    test1();
                }
            } catch (InterruptedException e) {
                e.printStackTrace();
            } catch (BrokenBarrierException e) {
                e.printStackTrace();
            }
        }
    }

    private static class noLockThread implements Runnable{
        public void run() {
            for (int i = 0; i <100 ; i++) {

                try {
                    test2();
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }
        }
    }

    public static void main(String[] args) {
         ReentrantLock reentrantLock = new ReentrantLock();
         CyclicBarrier cyclicBarrier = new CyclicBarrier(100);
        for (int i = 0; i <100 ; i++) {
            Thread thread = new Thread(new LockThread(reentrantLock,cyclicBarrier));
            thread.start();
        }
        /*for (int i = 0; i <100 ; i++) {
            Thread thread = new Thread(new noLockThread());
            thread.start();
        }*/

    }


}
