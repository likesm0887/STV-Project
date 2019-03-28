package adapter.TestData;

import java.util.Objects;

public class TestDatum {
    private String component;
    private String xPath;
    private String variableKey;
    private boolean isVariableRequired;

    public TestDatum(String component, String xPath, String variableKey) {
        this.component = component;
        this.xPath = xPath;
        this.variableKey = variableKey;
        this.isVariableRequired = variableKey != null;
    }

    public String getXPath() {
        if (isVariableRequired)
            throw new RuntimeException("There is a variable required by this component");
        return xPath;
    }

    public String getXPathWithVariable(String variable) {
        return xPath.replace(variableKey, variable);
    }
}
