package entity.xPath;

import org.junit.Before;
import org.junit.Test;

import javax.xml.soap.Node;

import static org.junit.Assert.*;

public class XPathBuilderTest {
    private XPathBuilder builder;

    @Before
    public void setUp() throws Exception {
        builder = new XPathBuilder();
    }

    @Test
    public void appendOneNode() {
        builder.append("identifier");

        assertEquals("//identifier", builder.build());
    }

    @Test
    public void appendOneNodeWithPredicate() {
        builder.append("identifier")
                .which(NodeAttribute.TEXT, "test");

        assertEquals("//identifier[@text='test']", builder.build());
    }

    @Test
    public void appendTwoNode() {
        builder.append("identifier1")
                .append("identifier2");

        assertEquals("//identifier1//identifier2", builder.build());
    }

    @Test (expected = IllegalStateException.class)
    public void operateWithoutAppend() {
        builder.which(NodeAttribute.TEXT, "test");
    }
}