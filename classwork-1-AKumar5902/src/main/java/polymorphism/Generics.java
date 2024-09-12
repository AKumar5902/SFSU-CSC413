package polymorphism;

public class Generics<T> {

    private T myvalue;

    public Generics(T myvalue) {
        this.myvalue = myvalue;
    }

    void printValue(){
        System.out.println(myvalue + "!");
    }

    public static void main(String[] args) {
        Generics<String> myGeneric = new Generics<>("My String");
        myGeneric.printValue();

        Generics<Integer> myGeneric2 = new Generics<>(1234);
        myGeneric2.printValue();
    }
}
