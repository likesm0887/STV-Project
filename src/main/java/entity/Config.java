package entity;

public class Config {
    private String devicesName = "";
    private int androidVersion = 0;
    private int appiumPort = 0;
    private String serialNumber = "";
    private String testDataPath = "";
    private String scriptPath = "";

    public Config(String devicesName, String serialNumber, int androidVersion, int appiumPort, String testDataPath, String scriptPath) {
        this.devicesName = devicesName;
        this.serialNumber = serialNumber;
        this.androidVersion = androidVersion;
        this.appiumPort = appiumPort;
        this.testDataPath = testDataPath;
        this.scriptPath = scriptPath;
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


    public String getTestDataPath() {
        return testDataPath;
    }
    public void setTestDataPath(String testDataPath) {
        this.testDataPath = testDataPath;
    }

    public String getScriptPath() {
        return scriptPath;
    }

    public void setScriptPath(String scriptPath) {
        this.scriptPath = scriptPath;
    }
}
