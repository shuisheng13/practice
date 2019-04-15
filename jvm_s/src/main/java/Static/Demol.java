package Static;

public class Demol {

    private static class  Test{
        private int id;
        private String st ;

    }

    public static void main(String[] args) {
        for (int i = 0; i <100000000 ; i++) {
            Test test = new Test();
            System.out.println("success");
        }
    }

}
