package csc413;

public class Overriding {

    static class MyClassA {
        void doStuff() {
            System.out.println("Hello from A");
        }

        static void doStuffStatic() {
            System.out.println("Hello from A");
        }
    }

    static class MyClassB extends MyClassA {
        void doStuff() {
            System.out.println("Hello from B");
        }

        void doStuffSuper() {
            super.doStuff();
        }

    }


    public static void main(String[] args) {
        //Overriding overriding = new Overriding();
        //MyClassA myclassA = overriding.new MyClassA(); // if not static
        MyClassA myClassA = new MyClassA();
        MyClassA myClassB = new MyClassB();

        myClassA.doStuff();
        myClassB.doStuff();

        MyClassA.doStuffStatic();
        MyClassA.doStuffStatic();

        //cast manually tell it which reference type
        MyClassB myClassB1 = (MyClassB) myClassB;
        myClassB1.doStuffSuper();

    }
}
