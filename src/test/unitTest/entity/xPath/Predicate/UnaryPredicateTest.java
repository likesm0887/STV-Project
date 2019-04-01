package entity.xPath.Predicate;

import entity.xPath.NodeAttribute;
import org.junit.Test;

import static org.junit.Assert.assertEquals;

public class UnaryPredicateTest {
    @Test (expected = NullPointerException.class)
    public void constructorWithNullNodeAttribute() {
        new UnaryPredicate(null, "test");
    }

    @Test (expected = NullPointerException.class)
    public void constructorWithNullValue() {
        new UnaryPredicate(NodeAttribute.TEXT, null);
    }

    @Test (expected = NumberFormatException.class)
    public void checkIllegalIndexAttributeValue() {
        new UnaryPredicate(NodeAttribute.INDEX, "1a");
    }

        @Test
    public void unaryPredicateString() {
        Predicate predicate = new UnaryPredicate(NodeAttribute.TEXT, "test");
        assertEquals("@text='test'", predicate.toString());
    }
}