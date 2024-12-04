package csc413;

import java.util.List;
import java.util.function.Consumer;
import java.util.function.Supplier;

public class ConsumerSupplierDemo {


    public static void main(String[] args) {
        String lastItem = "D";
        Supplier<List<String>> myListGenerator = () ->{
            return List.of("A", "B", "C", lastItem);
        };

        List<String> dummyData = myListGenerator.get();

        Consumer<String> myStringConsumer = (stringInput) -> {
            //apply your logic here
            System.out.println("item is " + stringInput);
        };

        dummyData.forEach(myStringConsumer);
    }
}
