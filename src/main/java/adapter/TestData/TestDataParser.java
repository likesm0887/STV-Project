package adapter.TestData;

import java.util.Collections;
import java.util.HashMap;
import java.util.Map;

public class TestDataParser {
    private Map<String, TestDatum> testData;

    public TestDataParser() {
        testData = new HashMap<>();
    }

    public void parse() {

    }

    public Map<String, TestDatum> getTestData() {
        return Collections.unmodifiableMap(testData);
    }
}
