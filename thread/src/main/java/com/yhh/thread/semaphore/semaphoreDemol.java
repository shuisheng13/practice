package com.yhh.thread.semaphore;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.concurrent.Semaphore;

public class semaphoreDemol {
    private static SimpleDateFormat sf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss.SSS");

    private static class BusinessThread extends  Thread{
        private Semaphore semaphore;

        public BusinessThread(Semaphore semaphore) {
            this.semaphore = semaphore;
        }

        @Override
        public void run(){
            try {
                this.semaphore.acquire(2);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
            System.out.println(Thread.currentThread().getName()+sf.format(new Date())+"  获取了连接许可");

            try {
                Thread.sleep(5000);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
            this.semaphore.release(1);
            System.out.println(Thread.currentThread().getName()+sf.format(new Date())+"  释放了连接许可");
        }
    }


    public static void main(String[] args) {

        Semaphore semaphore = new Semaphore(3);

        for (int i = 0; i <10 ; i++) {
            new BusinessThread(semaphore).start();
        }



    }

}
