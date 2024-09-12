package polymorphism;

public class Polymorphism {

    public static void main(String[] args) {
        Polymorphism a = new Polymorphism();
        a.doStuff(1);
        a.doSutff("1");
    }

    void doSutff(String a){
        System.out.println(a + '!');
    }

    void doStuff(Integer a){
        System.out.println(a*2);
    }

}
