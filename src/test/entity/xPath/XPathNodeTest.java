package entity.xPath;

import entity.xPath.Predicate.Predicate;
import org.junit.Before;
import org.junit.Test;

import java.lang.reflect.Field;
import java.util.List;

import static org.junit.Assert.assertEquals;

public class XPathNodeTest {
    private XPathNode node;
    private List<Predicate> predicates;

    @Before
    public void setUp() throws Exception {
        node = new XPathNode("identifier");
        Field field = node.getClass().getDeclaredField("predicates");
        field.setAccessible(true);
        predicates = (List<Predicate>) field.get(node);
    }

    @Test
    public void nodeWithoutPredicate() {
        assertEquals("identifier", node.getFullFormat());
    }

    @Test
    public void nodeWithOnlyOnePredicate() {
        node.which(NodeAttribute.TEXT, "test");

        assertEquals(1, predicates.size());
        assertEquals("identifier[@text='test']", node.getFullFormat());
    }

    @Test
    public void nodeWithAndPredicate() {
        node.which(NodeAttribute.TEXT, "p1");
        node.and(NodeAttribute.CLASS, "p2");

        assertEquals(2, predicates.size());
        assertEquals("identifier[@text='p1' and @class='p2']", node.getFullFormat());
    }

    @Test
    public void nodeWithOrPredicate() {
        node.which(NodeAttribute.TEXT, "p1");
        node.or(NodeAttribute.CLASS, "p2");

        assertEquals(2, predicates.size());
        assertEquals("identifier[@text='p1' or @class='p2']", node.getFullFormat());
    }

    @Test
    public void nodeWithComplexPredicates() {
        node.which(NodeAttribute.TEXT, "p1");
        node.and(NodeAttribute.CLASS, "p2");
        node.or(NodeAttribute.INDEX, "3");

        assertEquals(3, predicates.size());
        assertEquals("identifier[@text='p1' and @class='p2' or @index='3']", node.getFullFormat());
    }

    @Test (expected = IllegalStateException.class)
    public void whichMethodCanBeInvokeOnlyOneTime() {
        node.which(NodeAttribute.TEXT, "p1");
        node.which(NodeAttribute.CLASS, "p2");
    }

    @Test (expected = IllegalStateException.class)
    public void whichMethodShouldBeCalledBeforeAndMethod() {
        node.and(NodeAttribute.TEXT, "test");
    }

    @Test (expected = IllegalStateException.class)
    public void whichMethodShouldBeCalledBeforeOrMethod() {
        node.or(NodeAttribute.TEXT, "test");
    }
}