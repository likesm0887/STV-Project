package adapter.scriptGenerator;

public class PathHelper {

    public String formatFileName(String fileName) {
        if (fileName.contains(".txt"))
            return fileName;
        else
            return fileName + ".txt";
    }

    public String formatPath(String fileName) {
        return "./script/" + formatFileName(fileName);
    }
}
