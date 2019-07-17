public class Cappuccino implements Beverage {
    private static final double coffee = 18;
    private static final double milk = 150;
    private static final String name = "Cappuccino";

    @Override
    public double getCoffee()
    {
        return coffee;
    }

    @Override
    public double getMilk()
    {
        return milk;
    }

    @Override
    public double getChocolate() {
        return 0;
    }

    @Override
    public String getName() {
        return name;
    }

    @Override
    public double getWater() {
        return 0;
    }
}
