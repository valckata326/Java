package bg.sofia.uni.fmi.mjt.carstore.car;

import java.util.Comparator;

public class TotalComparator implements Comparator<Car> {
    @Override
    public int compare(Car first, Car second)
    {
        int comparator = first.getModel().compareTo(second.getModel());
        if(comparator == 0)
        {
            if(Integer.compare(first.getYear(),second.getYear()) == 0)
            {
                return first.getPrice() - second.getPrice();
            }
            return Integer.compare(first.getYear(),second.getYear());
        }
        return comparator;
    }
}
