package entity;

public class Config {
    private String devicesName = "";
    private int androidVersion = 0;
    private int appiumPort = 0;
    private String serialNumber = "";
    public Config(String devicesName, String serialNumber, int androidVersion, int appiumPort) {
        this.devicesName = devicesName;
        this.serialNumber = serialNumber;
        this.androidVersion = androidVersion;
        this.appiumPort = appiumPort;
    }


    public String getSerialNumber() {
        return serialNumber;
    }

    public void setSerialNumber(String serialNumber) {
        serialNumber = serialNumber;
    }

    public String getDevicesName() {
        return devicesName;
    }

    public void setDevicesName(String devicesName) {
        this.devicesName = devicesName;
    }

    public int getAndroidVersion() {
        return androidVersion;
    }

    public void setAndroidVersion(int androidVersion) {
        this.androidVersion = androidVersion;
    }

    public int getAppiumPort() {
        return appiumPort;
    }

    public void setAppiumPort(int appiumPort) {
        this.appiumPort = appiumPort;
    }


}
