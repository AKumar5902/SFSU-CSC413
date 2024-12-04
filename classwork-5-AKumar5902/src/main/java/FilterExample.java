import com.google.gson.Gson;

import java.util.ArrayList;
import java.util.List;

class MyObject {

    int variableA = 0;


    public MyObject(int variableA) {
        this.variableA = variableA;
    }


}


public class FilterExample {


    public static void main(String[] args) {
        Gson gson = new Gson();
        List<MyObject> myObjectList = new ArrayList<>();
        for (int i = 0; i < 10; i++) {
            var myObject = new MyObject(i);
            myObjectList.add(myObject);
        }

        System.out.println(gson.toJson(myObjectList));

        List<MyObject> myFilteredList = myObjectList.stream() //Stream<MyObject>
                //.collect(Collectors.toList())
                .filter(myObj -> {
                    if (myObj.variableA % 2 != 0) {
                        return true;
                    }
                    if (myObj.variableA > 5) {
                        return true;
                    }
                    return false;
                })
                .map(myObject -> myObject.variableA)
                .map(a -> a + 1) // Stream<Integers>
                .map(MyObject::new)
                .toList();

        System.out.println(gson.toJson(myFilteredList));
    }
}
