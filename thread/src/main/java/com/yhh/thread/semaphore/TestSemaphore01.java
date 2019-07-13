package com.yhh.thread.semaphore;

import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;
import java.util.concurrent.Semaphore;

public class TestSemaphore01 {

    private Semaphore semaphore;

    private static class WriteThread implements Runnable{
        private Semaphore semaphore;
        private Map<String,Object> map;

        public WriteThread(Semaphore semaphore, Map<String, Object> map) {
            this.semaphore = semaphore;
            this.map = map;
        }

        public void run() {
            try {
                semaphore.acquire(1);
                map.put(Thread.currentThread().getName(),Thread.currentThread().getId());
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
            semaphore.release(1);
        }
    }
    private static class ReadThread implements Runnable{
        private Map<String,Object> map;

        public ReadThread(Map<String, Object> map) {
            this.map = map;
        }

        public void run() {
            Iterator entries = map.entrySet().iterator();
            while (entries.hasNext()){
                Map.Entry entry = (Map.Entry) entries.next();
                String key = (String)entry.getKey();
                Long value = (Long)entry.getValue();
                System.out.println("Key = " + key + ", Value = " + value);
            }
        }
    }

    public static void main(String[] args) {
        Semaphore semaphore = new Semaphore(20);
        Map<String,Object> map = new HashMap<String, Object>();
        for (int i = 0; i <20 ; i++) {
          new Thread(new WriteThread(semaphore,map)).start();
        }
        try {
            Thread.sleep(5000);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        new Thread(new ReadThread(map)).start();
    }

}
