package entity;

import java.util.HashMap;
import java.util.Map;

public class TestData {
    private Map<String, Map<String, TestDatum>> wholeTestData;

    public TestData() {
        wholeTestData = new HashMap<>();
    }

    public void addSheetTestData(String sheetName, Map<String, TestDatum> sheetTestData) {
        wholeTestData.put(sheetName, sheetTestData);
    }

    public TestDatum getTestDatum(String viewName, String componentName) {
        if (wholeTestData.get(viewName) == null)
            throw new RuntimeException("The view \"" + viewName +"\" doesn't exist");
        TestDatum testDatum = wholeTestData.get(viewName).get(componentName);
        if (testDatum != null)
            return testDatum;
        else
            throw new RuntimeException("Element '" + componentName + "'" + "is not belong to " + viewName);
    }

    public String toString() {
        return wholeTestData.toString();
    }
}
