package useCase;

import org.jmock.Expectations;
import org.junit.Test;
import org.openqa.selenium.ScreenOrientation;

import static org.junit.Assert.assertEquals;

public class ScriptManagerTest extends ScriptTest {
    ScriptManager sm = new ScriptManager();

    @Test
    public void addCommandsTest() {
        sm.add(script_1);
        sm.add(script_2);

        assertEquals(sm.isExist(script_2), true);
    }

    @Test
    public void executeScriptTest() {
        sm.add(script_1);
        sm.add(script_2);

        context.checking(new Expectations() {{
            oneOf(mockDriver).launchApplication();
            oneOf(mockDriver).waitAndClickElement(xPath);
            ScreenOrientation screenOrientation = ScreenOrientation.LANDSCAPE;
            oneOf(mockDriver).rotate(screenOrientation);
        }});

        sm.execute(script_2);
    }
}
