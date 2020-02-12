package bg.sofia.uni.fmi.mjt.smartcity.hub;

import bg.sofia.uni.fmi.mjt.smartcity.TotalComparator;
import bg.sofia.uni.fmi.mjt.smartcity.device.SmartDevice;
import bg.sofia.uni.fmi.mjt.smartcity.enums.DeviceType;

import java.util.*;
import java.util.stream.Collectors;

public class SmartCityHub {
    private Map<String, SmartDevice> smartDevices;
    public SmartCityHub(){
        smartDevices = new LinkedHashMap<>();
    }
    public void register(SmartDevice device){
        validateDevice(device);
        if(smartDevices.containsKey(device.getId())){
            throw new DeviceAlreadyRegisteredException("Device already exists!");
        }
        else {
            smartDevices.put(device.getId(), device);
        }
    }
    public void unregister(SmartDevice device){
        validateDevice(device);
        if (!this.smartDevices.containsKey(device.getId())) {
            throw new DeviceNotFoundException(device.getId());
        }
        this.smartDevices.remove(device.getId());
    }
    public void validateDevice(SmartDevice device){
        if(device == null){
            throw new IllegalArgumentException();
        }
    }
    public SmartDevice getDeviceById(String Id){
        if(Id == null) {
            throw new IllegalArgumentException();
        }
        if(this.smartDevices.containsKey(Id)){
            return this.smartDevices.get(Id);
        }
        throw new DeviceNotFoundException("This device was not found");
    }
    public int getDeviceQuantityPerType(DeviceType type){
        int counterOfDevices = 0;
        for(String key: smartDevices.keySet()){
            if(smartDevices.get(key).getType() == type){
                counterOfDevices++;
            }
        }
        return counterOfDevices;
    }
    public Collection<String> getTopNDevicesByPowerConsumption(int n){
        if(n < 0){
            throw new IllegalArgumentException("The number should not be negative!");
        }
        else{
            return this.smartDevices.values()
                    .stream()
                    .sorted((first,second)->
                            (int)(first.getTotalPowerConsumption() - second.getTotalPowerConsumption()))
                    .map(SmartDevice::getId)
                    .limit(n)
                    .collect(Collectors.toSet());
        }

    }
    public Collection<SmartDevice> getFirstNDevicesByRegistration(int n){
        int totalDevicesToReturn = n;
        if(n < 0){
            throw new IllegalArgumentException("The number should not be negative!");
        }
        if(n > smartDevices.size()){
            totalDevicesToReturn = smartDevices.size();
        }
        return new ArrayList<>(this.smartDevices.values())
                .subList(0, totalDevicesToReturn);
    }
}
