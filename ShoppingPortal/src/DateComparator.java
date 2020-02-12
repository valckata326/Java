package bg.sofia.uni.fmi.mjt.shopping.portal;

import bg.sofia.uni.fmi.mjt.shopping.portal.offer.Offer;

import java.time.LocalDate;
import java.util.Comparator;

public class DateComparator implements Comparator<PriceStatistic> {
    @Override
    public int compare(PriceStatistic first, PriceStatistic second) {
        if (first.getDate().isBefore(second.getDate())) {
            return 1;
        }
        return -1;
    }
}
