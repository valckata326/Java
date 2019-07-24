package bg.sofia.uni.fmi.mjt.carstore.car;

import bg.sofia.uni.fmi.mjt.carstore.enums.CarType;
import bg.sofia.uni.fmi.mjt.carstore.enums.EngineType;
import bg.sofia.uni.fmi.mjt.carstore.enums.Model;
import bg.sofia.uni.fmi.mjt.carstore.enums.Region;

import java.util.Random;

public abstract class Car {
    private Model model;
    private int yearOfCreation;
    private int Price;
    private EngineType type;
    private Region region;
    private int carID;
    private String registrationNumber;
    protected CarType carType;
    Car(Model model, int year, int price, EngineType type, Region region)
    {
        setModel(model);
        setYearOfCreation(year);
        setPrice(price);
        setType(type);
        setRegion(region);
        setRegistrationNumber();
    }

    public Model getModel() {
        return model;
    }

    public int getYear() {
        return yearOfCreation;
    }

    public int getPrice() {
        return Price;
    }

    public EngineType getEngineType() {
        return type;
    }

    public String getRegistrationNumber() {
        return this.registrationNumber;
    }

    private void setModel(Model model) {
        this.model = model;
    }

    private void setYearOfCreation(int yearOfCreation) {
        this.yearOfCreation = yearOfCreation;
    }

    private void setPrice(int price) {
        Price = price;
    }

    private void setType(EngineType type) {
        this.type = type;
    }

    private void setRegistrationNumber() {


        String regPrefix = getRegion().getPrefix();
        String regNumber = getRegion().getRegistrationNumber();

        Random r = new Random();
        char c = (char)(r.nextInt(26) + 'a');
        char d = (char)(r.nextInt(26) + 'a');

        this.registrationNumber = regPrefix + regNumber + c + d;
        region.setRegistrationInt();

    }

    public Region getRegion() {
        return region;
    }

    private void setRegion(Region region) {
        this.region = region;
    }

    public CarType getCarType() {
        return carType;
    }
    abstract void setCarType();


}
