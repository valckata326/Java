package bg.sofia.uni.fmi.mjt.smartcity.device;

import bg.sofia.uni.fmi.mjt.smartcity.enums.DeviceType;

import java.time.LocalDateTime;

public interface SmartDevice {
    public String getId();
    public String getName();
    public double getPowerConsumption();
    public LocalDateTime getInstallationDateTime();
    public DeviceType getType();
    public double getTotalPowerConsumption();
}
