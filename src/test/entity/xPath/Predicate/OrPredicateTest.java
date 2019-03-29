package entity.xPath.Predicate;

import entity.xPath.NodeAttribute;
import org.junit.Test;

import static org.junit.Assert.assertEquals;

public class OrPredicateTest {
    @Test (expected = NullPointerException.class)
    public void constructorWithNullNodeAttribute() {
        new OrPredicate(null, "test");
    }

    @Test (expected = NullPointerException.class)
    public void constructorWithNullValue() {
        new OrPredicate(NodeAttribute.TEXT, null);
    }

    @Test (expected = NumberFormatException.class)
    public void checkIllegalIndexAttributeValue() {
        new OrPredicate(NodeAttribute.INDEX, "1a");
    }

    @Test
    public void orPredicateString() {
        Predicate predicate = new OrPredicate(NodeAttribute.TEXT, "test");
        assertEquals("or @text='test'", predicate.toString());
    }
}