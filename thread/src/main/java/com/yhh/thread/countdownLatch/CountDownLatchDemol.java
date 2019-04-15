package com.yhh.thread.countdownLatch;

import java.util.concurrent.CountDownLatch;

public class CountDownLatchDemol {

     static CountDownLatch countDownLatch = new CountDownLatch(3);


    private static class FreeThread implements Runnable{
        private CountDownLatch countDownLatch;
        public FreeThread(CountDownLatch countDownLatch) {
            this.countDownLatch= countDownLatch;
        }

        public void run() {
            try {
                Thread.sleep(2000);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
            System.out.println(Thread.currentThread().getName()+"  FreeThread 运行结束");
            countDownLatch.countDown();
        }
    }
    private static class BusinessThread implements Runnable{
        private CountDownLatch countDownLatch;
        public BusinessThread(CountDownLatch countDownLatch) {
            this.countDownLatch= countDownLatch;
        }
        public void run() {
            try {
                Thread.sleep(2000);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
            System.out.println(Thread.currentThread().getName()+"  BusinessThread 运行结束");
            countDownLatch.countDown();
        }
    }

    public static void main(String[] args) throws InterruptedException {
        CountDownLatch countDownLatch = new CountDownLatch(2);
        Thread t1 = new Thread(new FreeThread(countDownLatch));
        Thread t2 = new Thread(new BusinessThread(countDownLatch));
        t1.start();t2.start();
        System.out.println("等待子业务线程执行........");
        countDownLatch.await();
        System.out.println("主线程执行结束........");
    }
}
