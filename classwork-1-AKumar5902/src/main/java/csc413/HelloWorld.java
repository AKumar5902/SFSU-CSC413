package csc413;

import classwork.AccessTest;

public class HelloWorld extends AccessTest{

    public static void main(String[] args) {

        AccessTest test = new AccessTest();
      //  System.out.println(test.atest);
      //  System.out.println(test.btest);
      //  System.out.println(test.ctest);
        System.out.println(test.dtest);
        HelloWorld helloWorld = new HelloWorld();
        System.out.println(helloWorld.ctest);
        System.out.println("Hello World");

    }
}
