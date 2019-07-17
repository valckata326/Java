package bg.fmi.mjt.lab.coffee_machine;

import bg.fmi.mjt.lab.coffee_machine.container.Container;
import bg.fmi.mjt.lab.coffee_machine.container.PremiumContainer;
import bg.fmi.mjt.lab.coffee_machine.supplies.Beverage;
import bg.fmi.mjt.lab.coffee_machine.supplies.Cappuccino;
import bg.fmi.mjt.lab.coffee_machine.supplies.Espresso;
import bg.fmi.mjt.lab.coffee_machine.supplies.Mochaccino;

public class PremiumCoffeeMachine implements CoffeeMachine {
    private PremiumContainer definite_container;
    private String[] lucks = {"If at first you don't succeed call it version 1.0.",
            "Today you will make magic happen!",
            "Have you tried turning it off and on again?",
            "Life would be much more easier if you had the source code."};
    private int indexOfLuck;
    private boolean toRefill;
    public PremiumCoffeeMachine()
    {
        toRefill = false;
        definite_container = new PremiumContainer();
        indexOfLuck = 0;
    }
    public PremiumCoffeeMachine(boolean newToRefill)
    {
        toRefill = newToRefill;
        definite_container = new PremiumContainer();
        indexOfLuck = 0;

    }
    public Product brew(Beverage beverage)
    {
        if(beverage instanceof Espresso || beverage instanceof Mochaccino || beverage instanceof Cappuccino)
        {
            //enough ingredients;
            if(beverage.getWater() <= definite_container.getCurrentWater()
                    && beverage.getCoffee() <= definite_container.getCurrentCoffee()
                    && beverage.getMilk() <= definite_container.getCurrentMilk()
                    && beverage.getCacao() <=definite_container.getCurrentCacao())
            {
                definite_container.updateSupplies(beverage);
                if(indexOfLuck > 3)
                {
                    indexOfLuck = 0;
                }
                return new Product(beverage.getName(), 1, lucks[indexOfLuck++]);
            }
            else if(toRefill) // not enough ingredients but refill available
            {
                definite_container.refill();
                definite_container.updateSupplies(beverage);
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
            if(beverage instanceof Espresso || beverage instanceof Mochaccino || beverage instanceof Cappuccino)
            {
                for(int i = 1; i <= quantity;i++)
                {
                    if(beverage.getWater() <= definite_container.getCurrentWater()
                            && beverage.getCoffee() <= definite_container.getCurrentCoffee()
                            && beverage.getMilk() <= definite_container.getCurrentMilk()
                            && beverage.getCacao() <=definite_container.getCurrentCacao())
                    {
                        definite_container.updateSupplies(beverage);
                    }
                    else if(toRefill)
                    {
                        definite_container.refill();
                        definite_container.updateSupplies(beverage);
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
        return definite_container;
    }
    public void refill()
    {
        definite_container.refill();
    }
}
