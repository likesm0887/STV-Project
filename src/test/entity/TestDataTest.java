package entity;

import org.junit.Before;
import org.junit.Test;

import java.lang.reflect.Field;
import java.util.HashMap;
import java.util.Map;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;

public class TestDataTest {
    private TestData testData;
    private Map<String, Map<String, TestDatum>> wholeTestData;
    private TestDatum c1;
    private TestDatum c2;
    private TestDatum c3;
    private TestDatum c4;

    @Before
    public void setUp() throws Exception {
        testData = new TestData();
        Field field = testData.getClass().getDeclaredField("wholeTestData");
        field.setAccessible(true);
        wholeTestData = (Map<String, Map<String, TestDatum>>) field.get(testData);

        c1 = new TestDatum("c1", "x1", null);
        c2 = new TestDatum("c2", "x2[@id='${ID}']", "${ID}");
        c3 = new TestDatum("c3", "x3", null);
        c4 = new TestDatum("c4", "x4", null);
    }

    @Test
    public void addSheetTestData() {
        Map<String, TestDatum> sheet1 = new HashMap<>();
        TestDatum c1 = new TestDatum("c1", "x1", null);
        TestDatum c2 = new TestDatum("c2", "x2[@id='${ID}']", "${ID}");
        sheet1.put("c1", c1);
        sheet1.put("c2", c2);
        testData.addSheetTestData("s1", sheet1);

        assertEquals(1, wholeTestData.size());
        assertNotNull(wholeTestData.get("s1"));
        assertEquals(2, wholeTestData.get("s1").size());
        assertEquals(c1, wholeTestData.get("s1").get("c1"));
        assertEquals(c2, wholeTestData.get("s1").get("c2"));
    }

    private void prepareTestData() {
        Map<String, TestDatum> sheet1 = new HashMap<>();
        sheet1.put("c1", c1);
        sheet1.put("c2", c2);
        Map<String, TestDatum> sheet2 = new HashMap<>();
        sheet2.put("c3", c3);
        sheet2.put("c4", c4);
        testData.addSheetTestData("s1", sheet1);
        testData.addSheetTestData("s2", sheet2);
    }

    @Test
    public void getTestDatumInSpecificSheet() {
        prepareTestData();
        TestDatum datum = testData.getTestDatum("s1", "c1");
        assertEquals(c1, datum);
    }

    @Test (expected = RuntimeException.class)
    public void getTestDatumWhichViewDoesNotExist() {
        prepareTestData();
        testData.getTestDatum("not exist", "c1");
    }
}