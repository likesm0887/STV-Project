package config;

public class Config {
    private String devicesName = "";
    private int androidVersion = 0;
    private int appiumPort = 0;

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
