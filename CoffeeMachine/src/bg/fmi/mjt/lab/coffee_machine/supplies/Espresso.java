package bg.fmi.mjt.lab.coffee_machine.supplies;

import bg.fmi.mjt.lab.coffee_machine.supplies.Beverage;

public class Espresso implements Beverage {
    private static final double coffee = 10;
    private static final double water = 30;
    private static final String name = "Espresso";

    @Override
    public double getCoffee()
    {
        return coffee;
    }
    @Override
    public double getWater()
    {
        return water;
    }
    @Override
    public String getName()
    {
        return name;
    }

    @Override
    public double getCacao() {
        return 0;
    }
    @Override
    public double getMilk()
    {
        return 0;
    }
}
