package bg.sofia.uni.fmi.mjt.shopping.portal;

import bg.sofia.uni.fmi.mjt.shopping.portal.offer.Offer;

import java.util.Comparator;

public class TotalPriceComp implements Comparator<Offer> {
    @Override
    public int compare(Offer first, Offer second) {
        if (first.getTotalPrice() <= second.getTotalPrice()) {
            return -1;
        } else {
            return 1;
        }
    }
}
