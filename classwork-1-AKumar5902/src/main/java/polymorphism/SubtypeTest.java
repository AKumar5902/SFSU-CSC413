package polymorphism;

class MyClassA{
    void doStuff(){
        System.out.println("Hello from A");
    }
}

class MyClassB extends MyClassA{
    void doStuffB(){
        System.out.println("Hello form B");
    }
}

class MyClassC extends  MyClassB{

}

public class SubtypeTest {
    public static void main(String[] args) {
        MyClassA a = new MyClassA();
        MyClassB b = new MyClassB();
        MyClassC c = new MyClassC();
        a.doStuff();
        b.doStuff();
        c.doStuff();


    }

    static void doStuff(MyClassA myClassA){
        myClassA.doStuff();
    }

    static void doStuff(MyClassB myClassB){
        myClassB.doStuff();
    }

}
