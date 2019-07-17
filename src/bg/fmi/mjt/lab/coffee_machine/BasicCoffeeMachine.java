package bg.fmi.mjt.lab.coffee_machine;

import bg.fmi.mjt.lab.coffee_machine.container.BasicContainer;
import bg.fmi.mjt.lab.coffee_machine.container.Container;
import bg.fmi.mjt.lab.coffee_machine.supplies.Beverage;
import bg.fmi.mjt.lab.coffee_machine.supplies.Espresso;

public class BasicCoffeeMachine implements CoffeeMachine {
    private BasicContainer definite_container;
    public BasicCoffeeMachine()
    {
        definite_container = new BasicContainer();
    }
    public Product brew(Beverage beverage)
    {
        if(!(beverage instanceof Espresso && definite_container.getCurrentWater() >= 30 && definite_container.getCurrentCoffee() >= 10))
        {
            return null;
        }
        else
        {
            definite_container.updateSupplies(beverage);
            return new Product(beverage.getName(),1, null);
        }
    }
    public Container getSupplies()
    {
        return definite_container;
    }
    public void refill()
    {
        definite_container.refill();
    }

}
