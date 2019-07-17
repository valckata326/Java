package bg.fmi.mjt.lab.coffee_machine;

public class Product {
    private String name;
    private int quantity;
    private String luck;

    public Product(String name ,int quantity, String luck)
    {
        this.setName(name);
        this.setQuantity(quantity);
        this.setLuck(luck);
    }
    public String getName()
    {
        return this.name;
    }

    public int getQuantity() {
        return quantity;
    }

    public String getLuck() {
        return luck;
    }

    private void setName(String name) {
        this.name = name;
    }

    private void setQuantity(int quantity) {
        this.quantity = quantity;
    }

    private void setLuck(String luck) {
        this.luck = luck;
    }
}
