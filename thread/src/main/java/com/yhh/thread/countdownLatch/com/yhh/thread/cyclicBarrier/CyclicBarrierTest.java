package com.yhh.thread.countdownLatch.com.yhh.thread.cyclicBarrier;

import java.util.concurrent.BrokenBarrierException;
import java.util.concurrent.CyclicBarrier;

public class CyclicBarrierTest {

    private static class WaitThread extends  Thread{
        private CyclicBarrier cclicBarrier;

        public WaitThread(CyclicBarrier cclicBarrier) {
            this.cclicBarrier = cclicBarrier;
        }

        @Override
        public void run(){
            try {
                Thread.sleep(5000);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
            System.out.println("写入数据完毕 等待其他线程到达目标");
            try {
                cclicBarrier.await();
            } catch (InterruptedException e) {
                e.printStackTrace();
            } catch (BrokenBarrierException e) {
                e.printStackTrace();
            }
            System.out.println("所有线程写入完毕   执行其他任务");
        }

    }

    public static void main(String[] args) {
        int n = 10;
        CyclicBarrier cyclicBarrier = new CyclicBarrier(n);
        for (int i = 0; i <n ; i++) {
            WaitThread waitThread = new WaitThread(cyclicBarrier);
            waitThread.start();
        }





    }


}
