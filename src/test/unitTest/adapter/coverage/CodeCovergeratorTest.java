package adapter.coverage;

import adapter.ConfigReader;
import org.junit.Test;

import java.io.IOException;

public class CodeCovergeratorTest {

    @Test
    public void pullCodeCoverage() throws IOException, InterruptedException {
        ConfigReader configReader = new ConfigReader();
        CodeCovergerator codeCovergerator = new CodeCovergerator();
        codeCovergerator.PullCodeCoverage(configReader.getConfig());
    }
}