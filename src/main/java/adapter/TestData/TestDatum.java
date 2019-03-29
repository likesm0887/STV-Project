package adapter.TestData;

public class TestDatum {
    private String component;
    private String xPath;
    private String variableKey;
    private boolean isVariableRequired;

    public TestDatum(String component, String xPath, String variableKey) {
        this.component = component;
        this.xPath = xPath;
        this.variableKey = variableKey;
        this.isVariableRequired = isVariableRequired(variableKey);
    }

    public String getXPath() {
        if (isVariableRequired)
            throw new RuntimeException("There is a variable required by this component");
        return xPath;
    }

    public String getXPathWithVariable(String variable) {
        return xPath.replace(variableKey, variable);
    }

    private boolean isVariableRequired(String variableKey) {
        if (variableKey == null || variableKey.isEmpty())
            return false;
        return true;
    }

    public String toString() {
        return "Component: " + component + ", xPath: " + xPath + ", variableKey: " + variableKey;
    }
}
