package bg.sofia.uni.fmi.mjt.smartcity.device;

import bg.sofia.uni.fmi.mjt.smartcity.enums.DeviceType;

import java.time.LocalDateTime;

public class SmartLamp extends BasicSmartDevice {
    private static int uniqueNumber = 0;
    public SmartLamp(String name, double powerConsumption, LocalDateTime installationDateTime){
        super(name,powerConsumption,installationDateTime);
        this.type = DeviceType.LAMP;
        this.ID = type.getShortName() + "-" + this.getName() + "-" + uniqueNumber;
        uniqueNumber++;
    }
}
