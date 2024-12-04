import java.util.function.BiFunction;
import java.util.function.Function;
import java.util.function.Predicate;

class SomeVeryVeryVeryLongClassName {

}


public class HelloWorld {

    public static void main(String[] args) {

        var myInt = 123;
        System.out.println(myInt);

        // myInt="123";

        var myClass = new SomeVeryVeryVeryLongClassName();
        System.out.println(myClass);

        Function<String, String> myLambda = input -> input + "!";
        System.out.println(myLambda.apply("Hello"));

        doStuff(a -> a + "?");
        doStuff(b -> b + "?**");


        BiFunction<String, Integer, String> aciiMaker = (text, count) -> {
            String output = "";
            for (int i = 0; i < count; i++) {
                String line = "";
                for (int j = 0; j < i; j++) {
                    line += "*";
                }
                output += line + text + "\n";
            }
            return output;
        };
        System.out.println(aciiMaker.apply("Hello", 10));


        Predicate<Integer> intFilter = (myNum) -> myInt > 10;
        System.out.println(intFilter.test(9));
        System.out.println(intFilter.test(10));
        System.out.println(intFilter.test(11));


        doStuff(HelloWorld::myDoStuff);
        var hello = new HelloWorld();
        doStuff(hello::myDoStuff2);
    }

    String myDoStuff2(String input) {
        return "$" + input + "$";
    }

    static String myDoStuff(String input) {
        return "*" + input + "*";
    }

    static void doStuff(Function<String, String> myLambda) {
        System.out.println(myLambda.apply("Hello"));
    }
}
