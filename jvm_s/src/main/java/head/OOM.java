package head;

import java.util.ArrayList;
import java.util.List;

/***
 * -Xmx10m -Xms10m -XX:+PrintGC
 * 模拟堆内存溢出
 * GC overhead limit exceeded 不断的GC 一点一点填充堆内存
 * java.lang.OutOfMemoryError: Java heap space 堆内存大小不足以放入某个巨型数组
 *
 */
public class OOM {


    public static void main(String[] args) {
        /*List<Object> objectList = new ArrayList<Object>();
        int i = 0;
        while (true){
            if(i%10000==0) System.out.println(i);
            objectList.add(new OOM());
            i++;
        }*/

        String [] at = new String [1000000000];


    }
}
