package com.yhh.practice.container.hashMap;


import java.util.HashMap;
import java.util.Map;
import java.util.Scanner;

/***
 * 算法  求某个数字是否是2的幂次方
 */
public class algorithmTest {


    public static void main(String[] args) {

        Map<String,String> hashMap = new HashMap<>();
        String val = hashMap.put("1","sdfsdfsd");
        System.out.println(val);
        Scanner scanner = new Scanner(System.in);
        System.out.println("请输入你需要计算的数字");
        while (scanner.hasNext()){
            int nx = scanner.nextInt();
            int old = nx;
            while (true){
                if((nx&(nx-1))==0){
                    System.out.println(" "+old +" 是幂等性算法");
                    break;
                }else{
                    System.out.println(" "+old +" 不是幂等性算法");
                    break;
                }
            }


        }


    }



}
