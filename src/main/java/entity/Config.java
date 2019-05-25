package entity;

public class Config {
    private String devicesName = "";
    private int androidVersion = 0;
    private int appiumPort = 0;
    private String serialNumber = "";
    private String testDataPath = "";
    private String scriptPath = "";
    private boolean isTestAnomaly;

    public Config(String devicesName, String serialNumber, int androidVersion, int appiumPort, String testDataPath, String scriptPath, boolean isTestAnomaly) {
        this.devicesName = devicesName;
        this.serialNumber = serialNumber;
        this.androidVersion = androidVersion;
        this.appiumPort = appiumPort;
        this.testDataPath = testDataPath;
        this.scriptPath = scriptPath;
        this.isTestAnomaly = isTestAnomaly;
    }

    public String getSerialNumber() {
        return serialNumber;
    }

    public String getDevicesName() {
        return devicesName;
    }

    public int getAndroidVersion() {
        return androidVersion;
    }

    public int getAppiumPort() {
        return appiumPort;
    }


    public String getTestDataPath() {
        return testDataPath;
    }

    public String getScriptPath() {
        return scriptPath;
    }

    public boolean isTestAnomaly() {
        return isTestAnomaly;
    }
}
