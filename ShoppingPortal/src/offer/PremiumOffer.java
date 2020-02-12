package bg.sofia.uni.fmi.mjt.shopping.portal.offer;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.time.LocalDate;

public class PremiumOffer extends BasicOffer {
    private double discount;
    private static final double HUNDRED_PERCENT = 100.0;
    public PremiumOffer(String productName, LocalDate date, String description,
                        double price, double shippingPrice, double discount) {
        super(productName, date, description, price, shippingPrice);
        if (discount >= 0 && discount <= HUNDRED_PERCENT) {
            this.discount = discount;
        }
        else {
            throw new IllegalArgumentException();
        }
        BigDecimal bd = new BigDecimal(discount).setScale(2, RoundingMode.HALF_UP);
        this.discount = bd.doubleValue();
        setTotalPrice();
    }
    public double getDiscount() {
        return this.discount;
    }
    public void setTotalPrice() {
        this.totalPrice = this.totalPrice - (discount * totalPrice) / HUNDRED_PERCENT;
    }
}
