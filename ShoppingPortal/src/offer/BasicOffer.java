package bg.sofia.uni.fmi.mjt.shopping.portal.offer;

import java.time.LocalDate;

public abstract class BasicOffer implements Offer {
    protected String productName;
    protected LocalDate date;
    protected String description;
    protected double price;
    protected double shippingPrice;
    protected double totalPrice;

    public BasicOffer(String productName, LocalDate date, String description,
                      double price, double shippingPrice) {
        this.productName = productName;
        this.date = date;
        this.description = description;
        this.price = price;
        this.shippingPrice = shippingPrice;
        this.totalPrice = price + shippingPrice;
    }
    @Override
    public String getProductName() {
        return this.productName;
    }
    @Override
    public LocalDate getDate() {
        return this.date;
    }
    @Override
    public String getDescription() {
        return this.description;
    }
    @Override
    public double getPrice() {
        return this.price;
    }
    @Override
    public double getShippingPrice() {
        return this.shippingPrice;
    }
    @Override
    public double getTotalPrice() {
        return this.totalPrice;
    }
    @Override
    public boolean checkIfTheSame(Offer offer) {
        return (this.productName.toLowerCase().equals(offer.getProductName().toLowerCase()))
                && (this.date.compareTo(offer.getDate()) == 0)
                && (this.totalPrice == offer.getTotalPrice());

    }
    @Override
    public void print() {
        System.out.println(this.getProductName() + " "
                + this.getDate() + " " + this.getTotalPrice());
    }
}
