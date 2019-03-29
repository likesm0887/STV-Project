package coverage;

import config.ConfigReader;
import org.junit.Test;

import java.io.IOException;

import static org.junit.Assert.*;

public class CodeCovergeratorTest {

    @Test
    public void pullCodeCoverage() throws IOException, InterruptedException {
        ConfigReader configReader = new ConfigReader();
        CodeCovergerator codeCovergerator = new CodeCovergerator();
        codeCovergerator.PullCodeCoverage(configReader.getConfig());
    }
}