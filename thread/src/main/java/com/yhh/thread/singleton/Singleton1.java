package com.yhh.thread.singleton;

/***
 * 饿汉式
 * 对象一开始就创建 单例
 * 111111
 * 11111
 */
public class Singleton1 {


    private static Singleton1 singleton1 = new Singleton1();

    private Singleton1(){

    }
    public static Singleton1 getInstance(){
       return  singleton1;
    }

}
