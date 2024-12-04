package csc413;

import java.lang.reflect.Field;
import java.lang.reflect.Method;
import java.util.Arrays;

public class ReflectionDemo {


    public Integer aa;
    @MyCustomAnnotation
    public Integer bb;



    public void doStuffA(){
        System.out.println("Say Hello A");
        System.out.println(aa + bb);
    }

    public void doStuffB(){
        System.out.println("Say Hello B");

    }

    public static void main(String[] args) {
        Field[] fields= ReflectionDemo.class.getFields();
        Method[] methods = ReflectionDemo.class.getMethods();
        var demo = new ReflectionDemo();

        Arrays.stream(fields).forEach(
                field -> {
                    System.out.println("Field Name: " + field.getName());
                    if(field.isAnnotationPresent(MyCustomAnnotation.class)){
                        System.out.println("Annotation found!");
                    }
                });

        Arrays.stream(methods).forEach(
                method -> {
                    System.out.println("Method Name: " + method.getName());
                    if(method.getName().equals("doStuffB")){
                        try {
                            method.invoke(demo);
                        } catch (Exception e) {
                            throw new RuntimeException(e);
                        }
                    }
                });
    }
}
