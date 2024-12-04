package csc413;

class BaseClass{
    @Deprecated
    public void doStuff(){

    }

}

@MyCustomAnnotation
public class HelloWorld extends BaseClass {

    @MyCustomAnnotation
    public int aa = 2;


    @MyCustomAnnotation
    public static void main(String[] args) {
        System.out.println("Hello World");
        @MyCustomAnnotation
        int a =2;
        System.out.println(a);
    }

    @Override
    public void doStuff(){

    }
}
