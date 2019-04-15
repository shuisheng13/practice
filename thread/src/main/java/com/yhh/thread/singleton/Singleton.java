package com.yhh.thread.singleton;

/***
 * 懒汉式
 * 对象在使用的时候在创建
 */
public class Singleton {

    private static Singleton  lan = null;

    private Singleton (){

    }

    public static synchronized  Singleton getInstance(){
        if(null==lan){
            lan = new Singleton();
        }
        return  lan;
    }

}
