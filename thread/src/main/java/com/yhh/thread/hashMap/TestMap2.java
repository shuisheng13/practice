package com.yhh.thread.hashMap;

import java.util.HashMap;
import java.util.Map;
import java.util.concurrent.CountDownLatch;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

public class TestMap2 {

    private static ExecutorService executorService;
    private static class WriteThread implements Runnable{

        private CountDownLatch countDownLatch;
        private int i;
        private Map<String,Object> map;

        public WriteThread(CountDownLatch countDownLatch, int i, Map<String, Object> map) {
            this.countDownLatch = countDownLatch;
            this.i = i;
            this.map = map;
        }

        public void run() {
            countDownLatch.countDown();
            map.put(String.valueOf(i),i);

            System.out.println("当前写线程 ------->>>>>>>    " +Thread.currentThread().getId());
        }
    }

    private static class ReadThread implements Runnable{

        private Map<String,Object> map ;

        public ReadThread(Map<String, Object> map) {
            this.map = map;
        }

        public void run() {
            for (int i = 0; i <500 ; i++) {
                boolean flag = !String.valueOf(i).equals(String.valueOf(map.get(String.valueOf(i))));//||null==map.get(String.valueOf(i));
                if(flag){
                    System.out.println("map  ------>  key : "+i+"     value :  "+map.get(String.valueOf(i)));
                }
            }
        }
    }

    public static void main(String[] args) throws InterruptedException {
        executorService = Executors.newFixedThreadPool(500);
        CountDownLatch count = new CountDownLatch(500);
        Map<String,Object> map = new HashMap<String, Object>();
        for (int i = 0; i <500 ; i++) {
            WriteThread writeThread = new WriteThread(count,i,map);
            executorService.submit(writeThread);
        }
        count.await();
        new Thread(new ReadThread(map)).start();
    }
}
