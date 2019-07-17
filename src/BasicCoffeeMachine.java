public class BasicCoffeeMachine implements CoffeeMachine {
    private Container definite_container;
    public BasicCoffeeMachine()
    {
        definite_container = new BasicContainer();
    }
    public Product brew(Beverage beverage)
    {
        if(!(beverage instanceof Espresso || definite_container.getCurrentWater() < 10))
        {
            return null;
        }
        else
        {

        }
    }

}
