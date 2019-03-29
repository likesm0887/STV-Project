package adapter.TestData;

import org.junit.Before;
import org.junit.Test;

import java.io.IOException;
import java.lang.reflect.Field;
import java.util.Map;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;

public class TestDataParserTest {
    private String SIMPLE_TEST_DATA = "./src/test/resources/simple_test_data.xlsx";
    private String ERROR_FORMAT_TEST_DATA = "./src/test/resources/error_format_test_data.xlsx";
    private TestDataParser parser;
    private TestData testData;
    private Map<String, Map<String, TestDatum>> wholeTestData;
    private final String SHEET1_NAME = "View1";
    private final String SHEET2_NAME = "View2";

    @Before
    public void setUp() throws Exception {
        parser = new TestDataParser(SIMPLE_TEST_DATA);
        testData = parser.getTestData();
        Field field = testData.getClass().getDeclaredField("wholeTestData");
        field.setAccessible(true);
        wholeTestData = (Map<String, Map<String, TestDatum>>) field.get(testData);
    }

    @Test
    public void parseSimpleTestData() throws IOException {
        parser.parse();

        assertEquals(2, wholeTestData.size());
        assertNotNull(wholeTestData.get(SHEET1_NAME));
        assertNotNull(wholeTestData.get(SHEET2_NAME));
    }

    @Test (expected = RuntimeException.class)
    public void parseErrorFormatTestData() throws IOException {
        TestDataParser parser = new TestDataParser(ERROR_FORMAT_TEST_DATA);
        parser.parse();
    }
}