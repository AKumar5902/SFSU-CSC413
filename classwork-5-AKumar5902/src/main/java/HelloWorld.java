import java.util.List;
import java.util.stream.Collectors;

public class HelloWorld {

    public static void main(String[] args) {
        List<Integer> myInts = List.of(1, 2, 3, 4);
        System.out.println(myInts);

        List<Integer> myMappedInts = myInts.stream()
                // todo: some mappigngs
                //.map(i -> i + 1)
                .map(HelloWorld::applyIncrement)
                .map(a -> a * 2)
                .collect(Collectors.toList());

        System.out.println(myMappedInts);

        System.out.println(myInts == myMappedInts);

    }

    static Integer applyIncrement(Integer i){
        return i+1;
    }
}
