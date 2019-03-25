package activity;

import org.junit.Test;

import static org.hamcrest.CoreMatchers.equalTo;
import static org.junit.Assert.*;

public class ActivityTest {

    @Test
    public void fetchAddNewTaskXPathFromXPathData() {
        Activity activity = createActivity();

        String xPath = activity.fetchXPath(MainActivity.ADD_NEW_TASK_BUTTON);
        assertThat(xPath,
                equalTo("//*[@class='android.widget.ImageButton']"));
    }

    public Activity createActivity() {
        return new Activity(null);
    }

    @Test
    public void fetchTaskLabelXPathFromXPathData() {
        Activity activity = createActivity();

        String xPath = activity.fetchXPath(MainActivity.TASK_LABEL);
        assertThat(xPath,
                equalTo("//*[@class='android.view.View' and @resource-id='org.dmfs.tasks:id/colorbar2']"));
    }

    @Test
    public void fetchTabBarXPathFromXPathData() {
        Activity activity = createActivity();

        String xPath = activity.fetchXPath(MainActivity.TAB_BAR);
        assertThat(xPath,
                equalTo("//android.widget.HorizontalScrollView[@resource-id='org.dmfs.tasks:id/tabs']/android.widget.LinearLayout/*"));
    }

    @Test
    public void fetchQuickAddTaskButtonXPathFromXPathData() {
        Activity activity = createActivity();

        String xPath = activity.fetchXPath(MainActivity.QUICK_ADD_TASK_BUTTON);
        assertThat(xPath,
                equalTo("//*[@class='android.widget.ImageView' and @resource-id='org.dmfs.tasks:id/quick_add_task']"));
    }

    @Test
    public void fetchQuickAddTextFieldXPathFromXPathData() {
        Activity activity = createActivity();

        String xPath = activity.fetchXPath(MainActivity.QUICK_ADD_TASK_TEXTFIELD);
        assertThat(xPath,
                equalTo("//*[@class='android.widget.EditText' and @resource-id='android:id/input']"));
    }

    @Test
    public void fetchQuickAddSaveButtonXPathFromXPathData() {
        Activity activity = createActivity();

        String xPath = activity.fetchXPath(MainActivity.QUICK_ADD_TASK_SAVE_BUTTON);
        assertThat(xPath,
                equalTo("//*[@class='android.widget.TextView' and @resource-id='android:id/button1']"));
    }
}