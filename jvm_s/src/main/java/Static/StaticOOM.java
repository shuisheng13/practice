package Static;

/***
 * 模拟java栈溢出 将栈内存设置小
 * -Xss256k
 * java.lang.StackOverflowError
 */
public class StaticOOM {

    private static int length = 1;
    public void test(){
        length++;
        test();
    }

    public static void main(String[] args) {
        try{
            StaticOOM staticOOM = new StaticOOM();
            staticOOM.test();
        }catch (Throwable e){
            System.out.println(length);
            e.printStackTrace();
        }

    }

}
