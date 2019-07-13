package com.yhh.thread.hashMap;

import java.util.Hashtable;

public class HashMapTable {

    private HashMapTable hashMapTable = new HashMapTable();

    private static class putThead1 implements Runnable{
        private Hashtable hashMapTable;

        public putThead1(Hashtable hashMapTable) {
            this.hashMapTable = hashMapTable;
        }
        public void run() {
            for (int i = 0; i <1000000 ; i++) {
                hashMapTable.put(String.valueOf(i),i);
                try {
                    Thread.sleep(100);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }

        }
    }
    private static class getThead1 implements Runnable{
        private Hashtable hashMapTable;

        public getThead1(Hashtable hashMapTable) {
            this.hashMapTable = hashMapTable;
        }

        public void run() {
            for (int i = 0; i <1000000 ; i++) {
                hashMapTable.get(String.valueOf(i));
                int con =  Integer.valueOf(hashMapTable.get(String.valueOf(i)).toString());
                System.out.println(Thread.currentThread().getName()+"  hashTable get "+con);
                try {
                    Thread.sleep(100);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }

        }
    }

    private static class getThead2 implements Runnable{
        private Hashtable hashMapTable;

        public getThead2(Hashtable hashMapTable) {
            this.hashMapTable = hashMapTable;
        }

        public void run() {
            for (int i = 0; i <1000000 ; i++) {
              int con =  Integer.valueOf(hashMapTable.get(String.valueOf(i)).toString());
                System.out.println(Thread.currentThread().getName()+"  hashTable get "+con);
                try {
                    Thread.sleep(100);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }

        }
    }

    public static void main(String[] args) {
        Hashtable<String,Object> hashtable = new Hashtable<String, Object>();
        new Thread(new putThead1(hashtable)).start();
        try {
            Thread.sleep(5000);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        new Thread(new getThead1(hashtable)).start();
        new Thread(new getThead2(hashtable)).start();



    }

}
