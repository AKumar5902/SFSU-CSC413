package classwork;

public class AccessTest2 {
    public static void main(String[] args) {
        AccessTest test = new AccessTest();
       // doesn't work since private
        // System.out.println(test.atest);
        System.out.println(test.btest);
        System.out.println(test.ctest);
        System.out.println(test.dtest);
    }
}
