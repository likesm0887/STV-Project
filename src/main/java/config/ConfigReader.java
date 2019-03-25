package config;

import com.sun.javadoc.Doc;
import org.dom4j.Document;
import org.dom4j.DocumentException;
import org.dom4j.Element;
import org.dom4j.io.SAXReader;

import java.io.File;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

public class ConfigReader {
    final String CONFIG_PATH = "configuration/configuration.xml";
    File configFile;

    public Config getConfig()
    {
        readConfig();
        Config config = new Config();
        config.setDevicesName(configMap.get("DeviceName"));
        config.setAndroidVersion(Integer.parseInt(configMap.get("AndroidVersion")));
        config.setAppiumPort(Integer.parseInt(configMap.get("AppiumPort")));
        config.setSerialNumber(configMap.get("SerialNumber"));
        return config;
    }

    private Map<String, String> configMap = new LinkedHashMap<>();

    private void readConfig() {
        initializeFile();

        parseConfigFile();
    }

    void initializeFile() {
        this.configFile = createFileByPath(CONFIG_PATH);
    }

    File createFileByPath(String path) {
        return new File(path);
    }

    void parseConfigFile() {
        List<Element> childElement = createRootElement().elements();

        for (int i = 0; i < childElement.size(); i++) {
            Element element = childElement.get(i);

            insertConfigPair(element.getName(), element.getText());
        }
    }

    Element createRootElement() {
        Document document = createDocument();

        return (Element) document.getRootElement().clone();
    }

    void insertConfigPair(String tag, String content) {
        configMap.put(tag, content);
    }

    Document createDocument() {
        try {
            return new SAXReader().read(this.configFile);
        } catch (DocumentException e) {
            e.printStackTrace();
        }
        return null;
    }

//
//    private void readConfig() {
//        File pathConfigFile;
//        Element element, rootElement = null;
//        final String CONFIG_DIR_PATH = "configuration/";
//        File folderConfig = new File(CONFIG_DIR_PATH);
//        String[] listConfig = folderConfig.list();
//
//        int cntConfigList = 0;
//        while (cntConfigList < listConfig.length) {
//            String CONFIG_FILE_PATH = listConfig[cntConfigList];
//            pathConfigFile = new File(CONFIG_DIR_PATH + CONFIG_FILE_PATH);
//
//
//            try {
//                Document document = (new SAXReader()).read(pathConfigFile);
//                rootElement = (Element) document.getRootElement().clone();
//            } catch (DocumentException e) {
//                e.printStackTrace();
//            }
//
//
//
//            for (int i = 0; i < rootElement.elements().size(); i++) {
//                element = (Element) rootElement.elements().get(i);
//                if (element.elements().size() > 0){ // does element contain sub elements
//                    // put elements, include category one and innerElements
//                    configMap.put(element.getName(), "\n"); // The element is a category element by recognizing "\n"
//                    for (int j = 0; j < element.elements().size(); j++) {
//                        Element innerElement = (Element) element.elements().get(j);
//                        configMap.put(innerElement.getName(), innerElement.getText());
//                    }
//                } else {
//                    configMap.put(element.getName(), element.getText());
//                }
//            }
//            cntConfigList++;
//        }
//    }
}
