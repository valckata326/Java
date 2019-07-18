package bg.fmi.mjt.lab.coffee_machine.supplies;

import bg.fmi.mjt.lab.coffee_machine.supplies.Beverage;

public class Mochaccino implements Beverage {
    private static final double coffee = 18;
    private static final double milk = 150;
    private static final double chocolate = 10;
    private static final String name = "Mochaccino";

    @Override
    public double getWater() {
        return 0;
    }

    @Override
    public double getCacao() {
        return chocolate;
    }

    @Override
    public  double getCoffee() {
        return coffee;
    }

    @Override
    public  double getMilk() {
        return milk;
    }

    @Override
    public  String getName() {
        return name;
    }
}
