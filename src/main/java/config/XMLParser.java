package config;


import java.io.File;

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
