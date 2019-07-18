package bg.fmi.mjt.lab.coffee_machine;

import bg.fmi.mjt.lab.coffee_machine.container.BasicContainer;
import bg.fmi.mjt.lab.coffee_machine.container.Container;
import bg.fmi.mjt.lab.coffee_machine.supplies.Beverage;
import bg.fmi.mjt.lab.coffee_machine.supplies.Espresso;

public class BasicCoffeeMachine implements CoffeeMachine {
    private BasicContainer definiteContainer    ;
    public BasicCoffeeMachine()
    {
        definiteContainer = new BasicContainer();
    }
    public Product brew(Beverage beverage)
    {
        if(!(beverage instanceof Espresso
                && definiteContainer.getCurrentWater() >= beverage.getWater()
                && definiteContainer.getCurrentCoffee() >= beverage.getCoffee()))
        {
            return null;
        }
        else
        {
            definiteContainer.updateSupplies(beverage);
            return new Product(beverage.getName(),1, null);
        }
    }
    public Container getSupplies()
    {
        return definiteContainer;
    }
    public void refill()
    {
        definiteContainer.refill();
    }

}
