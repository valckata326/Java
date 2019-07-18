package bg.fmi.mjt.lab.coffee_machine;

import bg.fmi.mjt.lab.coffee_machine.container.Container;
import bg.fmi.mjt.lab.coffee_machine.container.PremiumContainer;
import bg.fmi.mjt.lab.coffee_machine.supplies.Beverage;
import bg.fmi.mjt.lab.coffee_machine.supplies.Cappuccino;
import bg.fmi.mjt.lab.coffee_machine.supplies.Espresso;
import bg.fmi.mjt.lab.coffee_machine.supplies.Mochaccino;

public class PremiumCoffeeMachine implements CoffeeMachine {
    private PremiumContainer definiteContainer;
    private String[] lucks = {"If at first you don't succeed call it version 1.0.",
            "Today you will make magic happen!",
            "Have you tried turning it off and on again?",
            "Life would be much more easier if you had the source code."};
    private int indexOfLuck;
    private boolean toRefill;
    public PremiumCoffeeMachine()
    {
        toRefill = false;
        definiteContainer = new PremiumContainer();
        indexOfLuck = 0;
    }
    public PremiumCoffeeMachine(boolean newToRefill)
    {
        toRefill = newToRefill;
        definiteContainer = new PremiumContainer();
        indexOfLuck = 0;

    }
    public Product brew(Beverage beverage)
    {
        if(isBeverageAvailable(beverage))
        {
            //enough ingredients;
            if(isSuppliesAvailable(beverage))
            {
                definiteContainer.updateSupplies(beverage);
                if(indexOfLuck > 3)
                {
                    indexOfLuck = 0;
                }
                return new Product(beverage.getName(), 1, lucks[indexOfLuck++]);
            }
            else if(toRefill) // not enough ingredients but refill available
            {
                definiteContainer.refill();
                definiteContainer.updateSupplies(beverage);
                if(indexOfLuck > 3)
                {
                    indexOfLuck = 0;
                }
                return new Product(beverage.getName(), 1, lucks[indexOfLuck++]);
            }
            else // not enough ingredients and refill not available;
            {
                return null;
            }
        }
        else
        {
            return null;
        }

    }
    public Product brew(Beverage beverage, int quantity)
    {
        if(quantity <= 0 || quantity > 3)
        {
            return null;
        }
        else
        {
            if(isBeverageAvailable(beverage))
            {
                for(int i = 1; i <= quantity;i++)
                {
                    if(isSuppliesAvailable(beverage))
                    {
                        definiteContainer.updateSupplies(beverage);
                    }
                    else if(toRefill)
                    {
                        definiteContainer.refill();
                        definiteContainer.updateSupplies(beverage);
                    }
                    else
                    {
                        return null;
                    }
                }
                return new Product(beverage.getName(), quantity, lucks[indexOfLuck++]);

            }
            else
            {
                return null;
            }
        }

    }
    public Container getSupplies() {
        return definiteContainer;
    }
    public void refill()
    {
        definiteContainer.refill();
    }
    boolean isBeverageAvailable(Beverage beverage)
    {
        return beverage instanceof Espresso || beverage instanceof Mochaccino || beverage instanceof Cappuccino;
    }
    boolean isSuppliesAvailable(Beverage beverage)
    {
        return beverage.getWater() <= definiteContainer.getCurrentWater()
                && beverage.getCoffee() <= definiteContainer.getCurrentCoffee()
                && beverage.getMilk() <= definiteContainer.getCurrentMilk()
                && beverage.getCacao() <= definiteContainer.getCurrentCacao();
    }
}
