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
    public Map<String, String> getConfigMap() {
        return configMap;
    }

    public Config getConfig() {

        readConfig();
        Config config = new Config(
                configMap.get("DeviceName"),
                configMap.get("SerialNumber"),
                Integer.parseInt(configMap.get("AndroidVersion")),
                Integer.parseInt(configMap.get("AppiumPort")));
        return config;
    }

    private Map<String, String> configMap = new LinkedHashMap<>();

    private void readConfig() {
        File pathConfigFile;
        Element element, rootElement = null;
        final String CONFIG_DIR_PATH = "configuration/";
        File folderConfig = new File(CONFIG_DIR_PATH);
        String[] listConfig = folderConfig.list();

        int cntConfigList = 0;
        while (cntConfigList < listConfig.length) {
            String CONFIG_FILE_PATH = listConfig[cntConfigList];
            pathConfigFile = new File(CONFIG_DIR_PATH + CONFIG_FILE_PATH);


            try {
                Document document = (new SAXReader()).read(pathConfigFile);
                rootElement = (Element) document.getRootElement().clone();
            } catch (DocumentException e) {
                e.printStackTrace();
            }



            for (int i = 0; i < rootElement.elements().size(); i++) {
                element = (Element) rootElement.elements().get(i);
                if (element.elements().size() > 0){ // does element contain sub elements
                    // put elements, include category one and innerElements
                    configMap.put(element.getName(), "\n"); // The element is a category element by recognizing "\n"
                    for (int j = 0; j < element.elements().size(); j++) {
                        Element innerElement = (Element) element.elements().get(j);
                        configMap.put(innerElement.getName(), innerElement.getText());
                    }
                } else {
                    configMap.put(element.getName(), element.getText());
                }
            }
            cntConfigList++;
        }
    }
}
