package bg.sofia.uni.fmi.mjt.carstore.car;

import bg.sofia.uni.fmi.mjt.carstore.enums.CarType;
import bg.sofia.uni.fmi.mjt.carstore.enums.EngineType;
import bg.sofia.uni.fmi.mjt.carstore.enums.Model;
import bg.sofia.uni.fmi.mjt.carstore.enums.Region;

public class SportsCar extends Car {

    public SportsCar(Model model, int year, int price, EngineType type, Region region)
    {
        super(model, year, price, type, region);
    }
    protected void setCarType()
    {
        this.carType = CarType.SportsCar;
    }
}
