package bg.sofia.uni.fmi.mjt.carstore;

import bg.sofia.uni.fmi.mjt.carstore.car.Car;
import bg.sofia.uni.fmi.mjt.carstore.exception.CarNotFoundException;
import bg.sofia.uni.fmi.mjt.carstore.car.TotalComparator;
import bg.sofia.uni.fmi.mjt.carstore.enums.Model;
import bg.sofia.uni.fmi.mjt.carstore.enums.Region;

import java.util.*;

public class CarStore{
    private TreeSet<Car> carStore;
    private Map<Region, Integer> globalCounter = new EnumMap<Region, Integer>(Region.class);
    private int totalPriceForCars;
    public CarStore()
    {
        totalPriceForCars = 0;
        carStore = new TreeSet<>(new TotalComparator());
        for(Region region : Region.values())
        {
            globalCounter.put(region, 1000);
        }
    }
    public boolean add(Car car)
    {
        if(car != null && !(carStore.contains(car)))
        {
            carStore.add(car);
            totalPriceForCars += car.getPrice();
            int newCount = globalCounter.get(car.getRegion());
            newCount++;
            globalCounter.put(car.getRegion(),newCount);
            return true;
        }
        else
        {
            return false;
        }
    }
    public boolean addAll(Collection<Car> cars)
    {
        boolean toReturn = false;
        for(Car car : cars)
        {
            if(add(car))
            {
                toReturn = true;
            }
        }
        return toReturn;
    }
    public boolean remove (Car car)
    {
        if(car != null && carStore.contains(car))
        {
            carStore.remove(car);
            totalPriceForCars -= car.getPrice();
            return true;
        }
        else
        {
            return carStore.remove(car);
        }


    }
    public Collection<Car> getCarsByModel(Model model)
    {
        TreeSet<Car> carsByModel = new TreeSet<>(new TotalComparator());
        for(Car car : carStore)
        {
            if(car.getModel() == model)
            {
                carsByModel.add(car);
            }
        }
        return carsByModel;
    }
    public Car getCarByRegistrationNumber(String registrationNumber) throws CarNotFoundException
    {
        Car toReturn;
        for(Car car : carStore)
        {
            if(car.getRegistrationNumber().equals(registrationNumber))
            {
                toReturn = car;
                return toReturn;
            }
        }
        throw new CarNotFoundException("Registration number not found!");
    }
    public Collection<Car> getCars()
    {
        return this.carStore;
    }
    public Collection<Car> getCars(Comparator<Car> car)
    {
        TreeSet<Car> withComparator = new TreeSet<Car>(car);
        withComparator.addAll(carStore);
        return withComparator;
    }
    public Collection<Car> getCars(Comparator<Car> car, boolean isReversed)
    {
        TreeSet<Car> reversed = (TreeSet<Car>) carStore.descendingSet();
        if(isReversed)
        {
            return reversed;
        }
        else
        {
            return getCars(car);
        }

    }
    public int getNumberOfCars()
    {
        return this.carStore.size();
    }
    public int getTotalPriceForCars()
    {
        return this.totalPriceForCars;
    }
}
