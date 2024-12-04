public class SimpleTestLogic {

    public static int addInt(int a, int b){

        // todo and logic
        return a + b;
    }

    public static String printMessage(int a, int b) {
        if (a == 0) {
            return "We are at 0";
        }
        if (b > 10) {
            return "B10";
        }
        if (a == b) {
            return "a = b";

        }
        return "fallback";
    }
}
