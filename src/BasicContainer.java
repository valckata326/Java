public class BasicContainer extends Container{


    private  final double max_coffee = 600;
    private  final double max_water = 600;

    private double currentCoffee;
    private double currentWater;

    public BasicContainer()
    {
        this.setCurrentCoffee(getMax_coffee());
        this.setCurrentWater(getMax_water());
    }

    public void refill()
    {
        this.setCurrentWater(this.getMax_water());
        this.setCurrentCoffee(this.getMax_coffee());
    }

    @Override
    public double getCurrentChocolate() {
        return 0;
    }

    @Override
    public double getCurrentMilk() {
        return 0;
    }

    @Override
    public double getCurrentCoffee() {
        return currentCoffee;
    }

    @Override
    public double getCurrentWater() {
        return currentWater;
    }

    void updateSupplies(Beverage beverage)
    {
        setCurrentCoffee(currentCoffee-beverage.getCoffee());
        setCurrentWater(currentWater-beverage.getWater());
    }

    private void setCurrentCoffee(double currentCoffee) {
        if(currentCoffee >= 0 && currentCoffee <= getMax_coffee())
        {
            this.currentCoffee = currentCoffee;
        }

    }

    private void setCurrentWater(double currentWater) {
        if(currentWater >= 0 && currentWater <= getMax_water())
        {
            this.currentWater = currentWater;
        }
    }

    public double getMax_coffee() {
        return max_coffee;
    }

    public double getMax_water() {
        return max_water;
    }
}
