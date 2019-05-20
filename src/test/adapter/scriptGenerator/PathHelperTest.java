package adapter.scriptGenerator;

import org.junit.Before;
import org.junit.Test;

import static org.hamcrest.CoreMatchers.equalTo;
import static org.junit.Assert.assertThat;

public class PathHelperTest {

    protected PathHelper pathHelper;

    @Before
    public void setUp() {
        pathHelper = new PathHelper();
    }


    @Test
    public void formatFileNameGivenUnformatName() {
        String result = pathHelper.formatFileName("test");
        assertThat(result, equalTo("test.txt"));
        result = pathHelper.formatFileName("test.txt");
        assertThat(result, equalTo("test.txt"));
    }

    @Test
    public void formatPathGivenFileName() {
        String path = pathHelper.formatPath("test");
        assertThat(path, equalTo("./script/test.txt"));
        path = pathHelper.formatPath("test.txt");
        assertThat(path, equalTo("./script/test.txt"));
    }
}