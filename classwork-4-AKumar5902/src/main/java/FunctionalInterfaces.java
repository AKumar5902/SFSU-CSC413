@FunctionalInterface
interface MyInterface{
    Integer addInt(Integer a ,Integer b);
}

@FunctionalInterface
interface MyInterface2{
    public void addInts(Integer a, Integer b);
}

public class FunctionalInterfaces {

    public static void main(String[] args) {
        MyInterface myLambda = (a,b) -> a +b;

        System.out.println(myLambda.addInt(1 ,2));

        MyInterface2 myLambda2 = (a,b) -> {
            System.out.println(a);
            System.out.println(b);

        };
        myLambda2.addInts(3, 4);
    }
}
