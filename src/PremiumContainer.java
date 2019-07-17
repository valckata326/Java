public class PremiumContainer extends Container{
    private  final double maxCoffee = 1000;
    private  final double maxWater = 1000;
    private final double maxMilk = 1000;
    private final double maxChocolate = 300;

    private double currentCoffee;
    private double currentWater;
    private double currentMilk;
    private double currentChocolate;

    public PremiumContainer()
    {
        this.setCurrentWater(getMaxWater());
        this.setCurrentCoffee(getMaxCoffee());
        this.setCurrentMilk(getMaxMilk());
        this.setCurrentChocolate(getMaxChocolate());

    }

    public void refill() {
        this.setCurrentWater(getMaxWater());
        this.setCurrentCoffee(getMaxCoffee());
        this.setCurrentChocolate(getMaxChocolate());
        this.setCurrentMilk(getMaxMilk());
    }

    @Override
    public double getCurrentChocolate() {
        return currentChocolate;
    }

    @Override
    public double getCurrentMilk() {
        return currentMilk;
    }

    @Override
    public double getCurrentWater()
    {
        return currentWater;
    }
    @Override
    public double getCurrentCoffee()
    {
        return currentCoffee;
    }

    void updateSupplies(Beverage beverage) {
        this.setCurrentCoffee(this.getCurrentCoffee() - beverage.getCoffee());
        this.setCurrentWater(this.getCurrentWater() - beverage.getWater());
        this.setCurrentMilk(this.getCurrentMilk() - beverage.getMilk());
        this.setCurrentChocolate(this.getCurrentChocolate() - beverage.getChocolate());
    }

    public double getMaxMilk() {
        return maxMilk;
    }

    public double getMaxChocolate() {
        return maxChocolate;
    }

    private void setCurrentMilk(double currentMilk) {
        if(currentMilk >= 0 && currentMilk <= 1000)
        {
            this.currentMilk = currentMilk;
        }

    }

    private void setCurrentChocolate(double currentChocolate) {
        if(currentChocolate >= 0 && currentChocolate <= 300)
        {
            this.currentChocolate = currentChocolate;
        }

    }

    public double getMaxCoffee() {
        return maxCoffee;
    }

    public double getMaxWater() {
        return maxWater;
    }

    public void setCurrentCoffee(double currentCoffee) {
        if(currentCoffee >= 0 && currentCoffee <= 1000)
        {
            this.currentCoffee = currentCoffee;
        }
    }

    public void setCurrentWater(double currentWater) {
        if(currentWater >= 0 && currentWater <= 1000)
        {
            this.currentWater = currentWater;
        }

    }
}
