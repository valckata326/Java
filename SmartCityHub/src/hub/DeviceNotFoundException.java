package bg.sofia.uni.fmi.mjt.smartcity.hub;

public class DeviceNotFoundException extends RuntimeException {
    public DeviceNotFoundException(String message){
        super(message);
    }
}
