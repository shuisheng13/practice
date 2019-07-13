package com.yhh.thread.hashMap;

import com.sun.scenario.effect.impl.prism.PrImage;
import org.omg.CORBA.OBJ_ADAPTER;

import java.util.HashMap;
import java.util.Map;
import java.util.concurrent.CountDownLatch;

public class TestHashMap {



    private static class  WriteThread0  implements Runnable{
        private CountDownLatch countDownLatch;
        private Map<String,Object> hashMap;

        public WriteThread0(CountDownLatch countDownLatch, Map<String, Object> hashMap) {
            this.countDownLatch = countDownLatch;
            this.hashMap = hashMap;
        }

        public void run() {
            for (int i = 0; i <1000 ; i++) {
                countDownLatch.countDown();
                hashMap.put(String.valueOf(i),String.valueOf(i));
            }
            System.out.println("hashMap read  write0  success ");
        }
    }
    private static class  WriteThread1  implements Runnable{
        private CountDownLatch countDownLatch;
        private Map<String,Object> hashMap;

        public WriteThread1(CountDownLatch countDownLatch, Map<String, Object> hashMap) {
            this.countDownLatch = countDownLatch;
            this.hashMap = hashMap;
        }

        public void run() {
            for (int i = 1000; i <2000 ; i++) {
                countDownLatch.countDown();
                hashMap.put(String.valueOf(i),String.valueOf(i));
            }
            System.out.println("hashMap read  write1  success ");
        }
    }

    private static class ReadThread implements Runnable{
        private Map<String,Object> hashMap;
        //private CountDownLatch countDownLatch;


        public ReadThread(Map<String, Object> hashMap) {
            this.hashMap = hashMap;
        }

        public void run() {
            for (int i = 0; i <2000 ; i++) {
                //countDownLatch.countDown();
                boolean flag = !String.valueOf(i).equals(hashMap.get(String.valueOf(i)))||hashMap.get(String.valueOf(i))==null;
                if(flag){
                    System.out.println("hashMap read  key : "+String.valueOf(i)+"    value :  "+hashMap.get(String.valueOf(i)));
                }
            }
        }
    }

    public static void main(String[] args) throws InterruptedException {
        CountDownLatch countDownLatch = new CountDownLatch(200);
        Map<String,Object> hashMap = new HashMap<String, Object>();
        new Thread(new WriteThread0(countDownLatch,hashMap)).start();
        new Thread(new WriteThread1(countDownLatch,hashMap)).start();
        countDownLatch.await();
        new Thread(new ReadThread(hashMap)).start();
    }
}
