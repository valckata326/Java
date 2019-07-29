package bg.sofia.uni.fmi.mjt.carstore;

import bg.sofia.uni.fmi.mjt.carstore.car.Car;
import bg.sofia.uni.fmi.mjt.carstore.car.SportsCar;
import bg.sofia.uni.fmi.mjt.carstore.enums.EngineType;
import bg.sofia.uni.fmi.mjt.carstore.enums.Model;
import bg.sofia.uni.fmi.mjt.carstore.enums.Region;
import bg.sofia.uni.fmi.mjt.carstore.exception.CarNotFoundException;

public class Main {
    public static void  main(String[] args)
    {
        Car new_car = new SportsCar(Model.AUDI, 2000, 2000, EngineType.DIESEL, Region.BURGAS);
        Car new_car1 = new SportsCar(Model.AUDI, 2000, 2000, EngineType.DIESEL, Region.BURGAS);
        CarStore new_ = new CarStore();
        new_.add(new_car);
        new_.add(new_car1);
        System.out.println("sss");
    }
}
