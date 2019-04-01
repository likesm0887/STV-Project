package entity.xPath.Predicate;

import entity.xPath.NodeAttribute;
import org.junit.Test;

import static org.junit.Assert.assertEquals;

public class AndPredicateTest {
    @Test (expected = NullPointerException.class)
    public void constructorWithNullNodeAttribute() {
        new AndPredicate(null, "test");
    }

    @Test (expected = NullPointerException.class)
    public void constructorWithNullValue() {
        new AndPredicate(NodeAttribute.TEXT, null);
    }

    @Test (expected = NumberFormatException.class)
    public void checkIllegalIndexAttributeValue() {
        new AndPredicate(NodeAttribute.INDEX, "1a");
    }

    @Test
    public void andPredicateString() {
        Predicate predicate = new AndPredicate(NodeAttribute.TEXT, "test");
        assertEquals("and @text='test'", predicate.toString());
    }
}