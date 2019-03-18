package coverage;

import org.junit.Test;

import java.io.IOException;

import static org.junit.Assert.*;

public class CodeCovergeratorTest {

    @Test
    public void pullCodeCoverage() throws IOException, InterruptedException {
        CodeCovergerator codeCovergerator = new CodeCovergerator();
        codeCovergerator.PullCodeCoverage();
    }
}