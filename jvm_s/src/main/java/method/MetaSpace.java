package method;

import java.util.ArrayList;
import java.util.List;

/***
 * 方法区/元数据区 内存溢出
 * -XX:MaxMetaspaceSize=3M
 *MaxMetaspaceSize is too small 元数据区内存大小以至于不足空间运行方法
 *
 */
public class MetaSpace {


    public static void main(String[] args) {
        List<Object> objectList = new ArrayList<Object>();
        int i = 0;
        while (true){
            if(i%10000==0) System.out.println(i);
            objectList.add(new MetaSpace());
            i++;
        }

        //String [] at = new String [1000000000];


    }
}
