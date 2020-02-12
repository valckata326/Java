package bg.sofia.uni.fmi.mjt.shopping.portal.offer;

import java.time.LocalDate;
import java.util.Collection;

public interface Offer {
    public String getProductName();
    public LocalDate getDate();
    public String getDescription();
    public double getPrice();
    public double getShippingPrice();
    public double getTotalPrice();
    public boolean checkIfTheSame(Offer offer);
    public void print();

}
