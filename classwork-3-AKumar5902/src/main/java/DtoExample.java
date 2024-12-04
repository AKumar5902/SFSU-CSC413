import com.google.gson.Gson;

class MyDto{
    Integer aint;

    String bstring;

    public MyDto(Integer a, String b){
        this.aint=a;

        this.bstring=b;
    }
}

public class DtoExample {

    public static void main(String[] args) {
        MyDto myDto = new MyDto(1234, "ABC"); //{"aint":1234,"bstring":"ABC"}

        Gson gson = new Gson();

        String jsonString = gson.toJson(myDto);
        System.out.println(jsonString);

        MyDto myDto2= gson.fromJson("{\"aint\":4321,\"bstring\":\"EFG\"}", MyDto.class);
        System.out.println(myDto2.aint);
        System.out.println(myDto2.bstring);
    }
}
