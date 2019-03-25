package entity.xPath;

import org.junit.Before;
import org.junit.Test;
//import org.omg.CORBA.PUBLIC_MEMBER;
//
//import static org.hamcrest.CoreMatchers.equalTo;
//import static org.junit.Assert.*;
//
//public class XPathBuilderTest {
//
//    protected XPathBuilder xPathBuilder;
//
//    @Before
//    public void setUp() {
//        xPathBuilder = new XPathBuilder();
//    }
//
//
//    @Test
//    public void buildXPathGivenClassAttribute() {
//        xPathBuilder.append("*")
//                .at(NodeAttribute.CLASS, "android.support.v7.app.ActionBar$Tab");
//
//        String result = "//*[@class='android.support.v7.app.ActionBar$Tab']";
//        assertThat(xPathBuilder.toString(), equalTo(result));
//    }
//
//    @Test
//    public void buildXPathGivenClassAndResourceIdAttribute() {
//        xPathBuilder.append("*")
//                .at(NodeAttribute.CLASS, "android.widget.ImageButton")
//                .and(NodeAttribute.RESOURCE_ID, "3");
//
//        String result = "//*[@class='android.widget.ImageButton' and @resource-id='3']";
//        assertThat(xPathBuilder.toString(), equalTo(result));
//    }
//
//    @Test
//    public void buildAddNewTaskButtonXPath() {
//        xPathBuilder.append("*")
//                .at(NodeAttribute.CLASS, "android.widget.ImageButton");
//
//        String result = "//*[@class='android.widget.ImageButton']";
//        assertThat(xPathBuilder.toString(), equalTo(result));
//    }
//
//    @Test
//    public void buildTaskLabelXPath() {
//        xPathBuilder.append("*")
//                .at(NodeAttribute.CLASS, "android.widget.TextView")
//                .and(NodeAttribute.RESOURCE_ID, "android:id/title");
//
//        String result = "//*[@class='android.widget.TextView' and @resource-id='android:id/title']";
//        assertThat(xPathBuilder.toString(), equalTo(result));
//    }
//
//    @Test
//    public void buildQuickAddTaskButtonXPath() {
//        xPathBuilder.append("*")
//                .at(NodeAttribute.CLASS, "android.widget.ImageView")
//                .and(NodeAttribute.RESOURCE_ID, "org.dmfs.tasks:id/quick_add_task");
//
//        String result = "//*[@class='android.widget.ImageView' and @resource-id='org.dmfs.tasks:id/quick_add_task']";
//        assertThat(xPathBuilder.toString(), equalTo(result));
//    }
//
//    @Test
//    public void buildQuickAddTextField() {
//
//        xPathBuilder.append("*")
//                .at(NodeAttribute.CLASS, "android.widget.EditText")
//                .and(NodeAttribute.RESOURCE_ID, "android:id/input");
//
//        String result = "//*[@class='android.widget.EditText' and @resource-id='android:id/input']";
//        assertThat(xPathBuilder.toString(), equalTo(result));
//    }
//
//    @Test
//    public void buildQuickAddSaveButton() {
//        xPathBuilder.append("*")
//                .at(NodeAttribute.CLASS, "android.widget.TextView")
//                .and(NodeAttribute.RESOURCE_ID, "android:id/button1");
//
//        String result = "//*[@class='android.widget.TextView' and @resource-id='android:id/button1']";
//        assertThat(xPathBuilder.toString(), equalTo(result));
//    }
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