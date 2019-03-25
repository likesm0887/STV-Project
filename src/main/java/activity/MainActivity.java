package activity;

public class MainActivity extends ActivityAttribute {

    public static final ActivityAttribute ADD_NEW_TASK_BUTTON =
            new ActivityAttribute("//*[@class='android.widget.ImageButton']");

    public static final ActivityAttribute TASK_LABEL =
            new ActivityAttribute("//*[@class='android.widget.TextView' and @resource-id='android:id/title']");

    public static final ActivityAttribute TAB_BAR =
            new ActivityAttribute("//android.widget.HorizontalScrollView[@resource-id='org.dmfs.tasks:id/tabs']/android.widget.LinearLayout/*");

    public static final ActivityAttribute QUICK_ADD_TASK_BUTTON =
            new ActivityAttribute("//*[@class='android.widget.ImageView' and @resource-id='org.dmfs.tasks:id/quick_add_task']");

    public static final ActivityAttribute QUICK_ADD_TASK_TEXTFIELD =
            new ActivityAttribute("//*[@class='android.widget.EditText' and @resource-id='android:id/input']");

    public static final ActivityAttribute QUICK_ADD_TASK_SAVE_BUTTON =
            new ActivityAttribute("//*[@class='android.widget.TextView' and @resource-id='android:id/button1']");

    public static final ActivityAttribute SETTING_BUTTON =
            new ActivityAttribute("//*[@class='android.support.v7.widget.LinearLayoutCompat']");

    public MainActivity(String xPath) {
        super(xPath);
    }

}
