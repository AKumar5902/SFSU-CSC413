package csc413;

public class HelloWorld {

    public static void main(String[] args) {
        // Singleton singleton = new Singleton(); doesn't work
        Singleton singleton = Singleton.getInstance();
        System.out.println(singleton.counter);
        add();
        System.out.println(singleton.counter);


    }

    static void add() {
        Singleton singleton = Singleton.getInstance();
        singleton.counter++;
    }
}
