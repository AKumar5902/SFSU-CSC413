package csc413;

abstract class Auto{
    abstract int getSeats();
}

class Car extends Auto{
    int getSeats(){
        return 4;
    }
}
class Truck extends Auto{
    int getSeats(){
        return 3;
    }
}
public class Factory {

    public static void main(String[] args) {
        Auto auto =Factory.createInstance("Prius");
        System.out.println(auto.getSeats());

        Auto truck =Factory.createInstance("Sierra 1500");
        System.out.println(truck.getSeats());
    }

    static Auto createInstance(String name){
        if(name.equals("Prius")){
            return new Car();
        }
        if(name.equals("Sierra 1500")){
            return new Truck();
        }
        return null;
    }
}
