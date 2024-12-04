package csc413;

import java.lang.reflect.Method;
import java.util.Arrays;
import java.util.concurrent.atomic.AtomicInteger;

public class TestRunner {

    @MyTest
    public void test1() {
        customAssertEquals(1 == 2, false);
    }


    @MyTest
    public void test2() {
        customAssertEquals(1 == 1, false);

    }
    @MyTest
    public void test3() {
        customAssertEquals(1 == 1, true);

    }

    static void customAssertEquals(boolean input, boolean desiredAnswer) {
        if (input != desiredAnswer) {
            throw new RuntimeException("Got wrong answer, got " + input +
                    "Should have been" + desiredAnswer);
        }

    }

    public static void main(String[] args) {
        Method[] methods = TestRunner.class.getMethods();
        var test = new TestRunner();

        AtomicInteger totalPassedTest = new AtomicInteger();
        AtomicInteger totalTests = new AtomicInteger();

        Arrays.stream(methods)
                .filter(method -> method.isAnnotationPresent(MyTest.class))
                .forEach(method -> {
                    totalTests.getAndIncrement();
                    System.out.println("Running test: " + method.getName());
                    try {
                        method.invoke(test);
                        totalPassedTest.getAndIncrement();
                    } catch (Exception e) {
                        System.out.println("Failed Test : " + method.getName());
                       // throw new RuntimeException(e);
                    }
                });
        System.out.println("Total results " + totalPassedTest.getAndIncrement() + "/"
                + totalTests.getAndIncrement());
    }
}
