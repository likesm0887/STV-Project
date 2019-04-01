package entity;

import org.junit.Test;

import static org.junit.Assert.assertEquals;

public class TestDatumTest {
    @Test
    public void createTestDatumWithoutVariable() {
        TestDatum actual = new TestDatum("component", "//xpath", null);
        assertEquals("//xpath", actual.getXPath());
    }

    @Test
    public void createTestDatumWithVariable() {
        TestDatum actual = new TestDatum("component", "//xpath/with/variable[id='${ID}']", "${ID}");
        assertEquals("//xpath/with/variable[id='123']", actual.getXPathWithVariable("123"));
    }

    @Test (expected = RuntimeException.class)
    public void getXPathWhichRequireVariable() {
        TestDatum actual = new TestDatum("component", "//xpath/with/variable[id='${ID}']", "${ID}");
        actual.getXPath();
    }
}