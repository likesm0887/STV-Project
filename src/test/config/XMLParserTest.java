package config;

import org.junit.Before;
import org.junit.Test;

import static org.hamcrest.CoreMatchers.equalTo;
import static org.junit.Assert.*;

public class XMLParserTest {


    @Test
    public void f() {
        String fileName = "";
        XMLParser xmlParser = new XMLParser(fileName);
//        xmlParser.readFile();

//        System.out.println(xmlParser.getXMLSource());
        assertThat(0, equalTo(1));
    }

}