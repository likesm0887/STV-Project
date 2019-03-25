package config;


import org.w3c.dom.Document;
import org.xml.sax.SAXException;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;
import java.io.File;
import java.io.IOException;

public class XMLParser {
    File xmlFile = new File("configuration/configuration.xml");


    public XMLParser(String fileName) {
        System.out.println(xmlFile);

//
//        try {
//            DocumentBuilderFactory dbFactory = DocumentBuilderFactory.newInstance();
//            DocumentBuilder dBuilder = null;
//            dBuilder = dbFactory.newDocumentBuilder();
//            Document doc = dBuilder.parse(xmlFile);
//            doc.getDocumentElement().normalize();
//            System.out.println("Root element :" + doc.getDocumentElement().getNodeName());
//
//        } catch (Exception e) {
//            e.printStackTrace();
//        }

        //optional, but recommended
        //read this - http://stackoverflow.com/questions/13786607/normalization-in-dom-parsing-with-java-how-does-it-work


    }
}
