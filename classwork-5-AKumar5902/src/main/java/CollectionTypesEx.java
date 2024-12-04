import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.stream.Collectors;

public class CollectionTypesEx {

    public static void main(String[] args) {
        Set<String> stringSet = Set.of("A", "B", "C");


        List<String> stringList = stringSet.stream().toList();
        System.out.println(stringList);

        List<Double> doubles = List.of(1.2d, 1.3d, 1.4d);
        Set<Double> doubleSet = doubles.stream().collect(Collectors.toSet());

        System.out.println(doubleSet);

        Map<String, String> myMap = stringSet.stream()
                .collect(Collectors.toMap(String::toLowerCase, item -> item));
        System.out.println(myMap);
    }
}
