package bg.sofia.uni.fmi.mjt.smartcity;

import bg.sofia.uni.fmi.mjt.smartcity.device.SmartDevice;

import java.util.Comparator;

public class TotalComparator implements Comparator<SmartDevice> {
    @Override
    public int compare(SmartDevice first, SmartDevice second){
        return (int) (first.getTotalPowerConsumption() - second.getTotalPowerConsumption());
    }
}
