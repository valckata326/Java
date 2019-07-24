package bg.sofia.uni.fmi.mjt.carstore.car;

import java.util.Comparator;

public class TotalComparator implements Comparator<Car> {
    @Override
    public int compare(Car first, Car second)
    {
        int comparation = first.getModel().compareTo(second.getModel());
        if(comparation == 0)
        {
            return Integer.compare(first.getYear(),second.getYear());
        }
        return comparation;
    }
}
