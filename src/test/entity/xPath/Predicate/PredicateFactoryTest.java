package entity.xPath.Predicate;

import entity.xPath.NodeAttribute;
import org.junit.Before;
import org.junit.Test;

import static org.junit.Assert.assertEquals;

public class PredicateFactoryTest {
    private PredicateFactory factory;

    @Before
    public void setUp() throws Exception {
        factory = new PredicateFactory();
    }

    @Test
    public void createUnaryPredicate() {
        Predicate predicate = factory.createUnaryPredicate(NodeAttribute.TEXT, "test");
        assertEquals("@text='test'", predicate.toString());
    }

    @Test
    public void createAndPredicate() {
        Predicate predicate = factory.createAndPredicate(NodeAttribute.TEXT, "test");
        assertEquals("and @text='test'", predicate.toString());
    }

    @Test
    public void createOrPredicate() {
        Predicate predicate = factory.createOrPredicate(NodeAttribute.TEXT, "test");
        assertEquals("or @text='test'", predicate.toString());
    }
}