import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

public class IteratorDemo {


    public static void main(String[] args) {

        //  List<Integer> myInts = List.of(1,2,3,4,5); // unmodifiable
        List<Integer> myInts = new ArrayList<>();
        myInts.add(1);
        myInts.add(5);
        myInts.add(4);
        myInts.add(3);
        myInts.add(2);
        myInts.addAll(List.of(1, 2, 3, 4, 5));

        System.out.println(myInts);

        Iterator<Integer> iterator = myInts.iterator();
        while (iterator.hasNext()) { // has another unvisited item
            Integer currentItem = iterator.next();
            System.out.println(currentItem);

            if (currentItem <= 2) {
                iterator.remove(); // directly modify original collection
            }
        }


        System.out.println(myInts);

    }
}
