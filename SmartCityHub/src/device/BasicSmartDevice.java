package bg.sofia.uni.fmi.mjt.smartcity.device;

import bg.sofia.uni.fmi.mjt.smartcity.enums.DeviceType;

import java.time.Duration;
import java.time.LocalDateTime;

public abstract class BasicSmartDevice implements SmartDevice {
    protected String ID;
    private String name;
    private double powerConsumption;
    private LocalDateTime installationTime;
    protected DeviceType type;
    public BasicSmartDevice(String name, double powerConsumption, LocalDateTime installationDateTime){
        this.name = name;
        this.powerConsumption = powerConsumption;
        this.installationTime = installationDateTime;
    }
    @Override
    public String getId(){
        return this.ID;
    }
    @Override
    public String getName(){
        return this.name;
    }
    @Override
    public double getPowerConsumption(){
        return this.powerConsumption;
    }
    @Override
    public LocalDateTime getInstallationDateTime(){
        return this.installationTime;
    }
    @Override
    public DeviceType getType(){
        return this.type;
    }

    public void setType(DeviceType type){
        this.type = type;
    }
    @Override
    public double getTotalPowerConsumption() {
        return this.powerConsumption *
                Duration.between(this.installationTime, LocalDateTime.now()).toHours();
    }
}
