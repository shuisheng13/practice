package com.yhh.thread.semaphore;

import java.util.concurrent.*;

public class TestSemaphore02 {

    private static Semaphore semaphore = new Semaphore(5);
    private static ThreadPoolExecutor threadPoolExecutor =
            new ThreadPoolExecutor(10,20,60, TimeUnit.SECONDS,new LinkedBlockingQueue<Runnable>());


    private static class TaskThread implements Runnable{
        private String name;
        private String age;

        public TaskThread( String name, String age) {
            this.name = name;
            this.age = age;
        }

        public void run() {
            try {
                semaphore.acquire(1);
                System.out.println(" 我是 : "+name+"  ,我今年  "+age+"  岁");
                Thread.sleep(10000);
                System.out.println("我准备释放资源了  ------------》》》》》》》》");
                semaphore.release(1);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }

        }
    }

    public static void main(String[] args) {
        Semaphore semaphore = new Semaphore(5);
        String [] names = {"张三","李四","王五","赵二","龙七","虎三","牛大","鼠二"};
        int[] ages = {20,21,22,23,24,25,26,27,28};
        for (int i = 0; i <8 ; i++) {
            TaskThread task = new TaskThread(names[i],String.valueOf(ages[i]));
            threadPoolExecutor.submit(task);
        }
        threadPoolExecutor.shutdown();

    }
}
