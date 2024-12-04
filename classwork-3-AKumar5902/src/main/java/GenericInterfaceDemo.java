interface Myinterface<T> {
    void doStuff(T a);
}

class MyClass implements Myinterface<String> {
    @Override
    public void doStuff(String a) {
        System.out.println(a);
    }
}

class MyClassInt implements Myinterface<Integer> {

    @Override
    public void doStuff(Integer a) {
        System.out.println(a);
    }
}

class MyGenericClass<T> implements Myinterface<T> {

    @Override
    public void doStuff(T a) {
        System.out.println(a);

    }
}

public class GenericInterfaceDemo {

    public static void main(String[] args) {
        MyClass myClass = new MyClass();
        myClass.doStuff("Hello");

        MyClassInt myClassInt = new MyClassInt();
        myClassInt.doStuff(123);

        MyGenericClass<String> myGenericClass = new MyGenericClass<>();
        myGenericClass.doStuff("Hello World");

    }
}
