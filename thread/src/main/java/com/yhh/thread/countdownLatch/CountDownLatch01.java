package com.yhh.thread.countdownLatch;

import java.util.concurrent.CountDownLatch;

/***
 * 实例  控制100个线程同时请求某个接口
 */
public class CountDownLatch01 {

    private CountDownLatch countDownLatch = new CountDownLatch(100);

    public CountDownLatch01(CountDownLatch countDownLatch) {
        this.countDownLatch = countDownLatch;
    }

    /***
     * 处理业务任务类
     */
    private static class Task implements  Runnable{
        private CountDownLatch countDownLatch;
        public Task(CountDownLatch countDownLatch) {
            this.countDownLatch = countDownLatch;
        }

        public void run() {

            countDownLatch.countDown();
            try {
                Thread.sleep(10000);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
            System.out.println("当前线程名 ------------------>>>>>>>>   "+Thread.currentThread().getName());
        }
    }

    public static void main(String[] args) {
        CountDownLatch count = new CountDownLatch(101);
        for (int i = 0; i <100; i++) {
            new Thread(new Task(count),"thread "+i).start();
        }
        try {
            count.await();
        } catch (InterruptedException e) {
        }
        try {
            Thread.sleep(2000);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        System.out.println("主程序运行结束 ------------------------------->>>>>>>>>");

    }


}
