package classwork;

public class AccessTest {
    private int atest;

    int btest;

    protected int ctest;

    public int dtest;

    public static void main(String[] args) {
        AccessTest test = new AccessTest();
        System.out.println(test.atest);
        System.out.println(test.btest);
        System.out.println(test.ctest);
        System.out.println(test.dtest);
    }
}
