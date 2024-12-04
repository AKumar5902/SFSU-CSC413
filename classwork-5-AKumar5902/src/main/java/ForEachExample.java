import java.util.List;

public class ForEachExample {
    public static void main(String[] args) {
        List<String> strings = List.of("A", "B", "C");


        // if map or filters not needed can skip it
        strings.forEach(item -> System.out.println(item));
        strings.stream().forEach(item -> System.out.println(item));

        strings.stream()
                .map(str -> str + str)
                .forEach(item -> System.out.println(item));
    }
}
