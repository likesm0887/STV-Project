package integrationTest;

import adapter.CommandMapper;
import adapter.device.AppiumDriver;
import adapter.device.DeviceDriver;
import adapter.parser.TestDataParser;
import adapter.scriptGenerator.ICommandMapper;
import adapter.scriptGenerator.ScriptGenerator;
import entity.Config;
import entity.TestData;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import useCase.command.CommandFactory;

import java.io.IOException;

public class EditTaskEndToEndTest {
    private ScriptGenerator scriptGenerator;
    private DeviceDriver driver;

    @Before
    public void setUp() {
        Config config = new Config("Android Emulator", "emulator-5554", 9, 5000, "emulator-5554", "emulator-5554", false);
        driver = new AppiumDriver(config);
        driver.startService();
        driver.restartAppAndCleanData();

        TestDataParser testDataParser = new TestDataParser("./TestData/TestData.xlsx");

        try {
            testDataParser.parse();
        } catch (IOException e) {
            e.printStackTrace();
        }

        TestData testData = testDataParser.getTestData();
        CommandFactory commandFactory = new CommandFactory(driver);
        ICommandMapper commandMapper = new CommandMapper(testData, commandFactory);

        scriptGenerator = new ScriptGenerator(commandMapper);

    }

    @After
    public void tearDown() {
        scriptGenerator.writeScriptFile("script");
        driver.stopApp();
        driver.stopService();
    }


    private void ClickTaskListBy(String taskListName) {
        String script = String.format("TaskList\tClick\tfolder{%s}", taskListName);
        scriptGenerator.executeInstruction(script);
    }

    private void ClickQuickAddButtonByRowOf(int index) {
        String script = String.format("TaskList\tClick\tquick_add_btn{%d}", index);
        scriptGenerator.executeInstruction(script);
    }

    private void CreateNewTaskQuicklyBy(String taskName) {
        String script = String.format("TaskList\tTypeText{%s}\tquick_add_editText", taskName);
        scriptGenerator.executeInstruction(script);
        scriptGenerator.executeInstruction("TaskList\tClick\tquick_add_save_btn");
    }

    private void ClickTaskBy(String taskName) {
        String script = String.format("TaskList\tClick\ttask{%s}", taskName);
        scriptGenerator.executeInstruction(script);
    }

    private void ClickEditTaskButton() {
        scriptGenerator.executeInstruction("ViewTask\tClick\tedit_btn");
    }

    private void EnterTaskTitle(String taskTitle) {
        String script = String.format("EditTasks\tTypeText{%s}\ttitle_editText", taskTitle);

        scriptGenerator.executeInstruction(script);

    }

    private void EnterLocation(String location) {
        String script = String.format("EditTasks\tTypeText{%s}\tlocation_editText", location);

        scriptGenerator.executeInstruction(script);
    }

    private void EnterDescription(String description) {
        String script = String.format("EditTasks\tTypeText{%s}\tdescription_editText", description);
        scriptGenerator.executeInstruction(script);
    }

    private void SelectStatusDropDownBy(String status) {
        scriptGenerator.executeInstruction("EditTasks\tClick\tstatus_dropdown");
        String script = String.format("EditTasks\tClick\t%s", status);
        scriptGenerator.executeInstruction(script);
    }

    private void SelectPriorityDropDownBy(String status) {
        ScrollToElement("EditTasks", "Down", "priority_dropdown");
        scriptGenerator.executeInstruction("EditTasks\tClick\tpriority_dropdown");
        String script = String.format("EditTasks\tClick\t%s", status);
        scriptGenerator.executeInstruction(script);
    }

    private void SelectPrivacyDropDownBy(String status) {
        ScrollToElement("EditTasks", "Down", "privacy_dropdown");
        scriptGenerator.executeInstruction("EditTasks\tClick\tprivacy_dropdown");
        String script = String.format("EditTasks\tClick\t%s", status);
        scriptGenerator.executeInstruction(script);
    }

    private void SaveEditTask() {
        scriptGenerator.executeInstruction("EditTasks\tClick\tsave_task_btn");
    }



    // url xpath incorrect
    private void EnterTaskURL(String url) {
        String script = String.format("EditTasks\tTypeText{%s}\tURL_editText", url);
        scriptGenerator.executeInstruction(script);
    }

    private void ClickStartDate() {
        scriptGenerator.executeInstruction("EditTasks\tClick\tstart_date");
    }

    private void ClickStartTime() {
        scriptGenerator.executeInstruction("EditTasks\tClick\tstart_time");
    }

    private void ClickDueDate() {
        scriptGenerator.executeInstruction("EditTasks\tClick\tdue_date");
    }

    private void ClickDueTime() {
        scriptGenerator.executeInstruction("EditTasks\tClick\tdue_time");
    }

    private void ScrollToCalenderYear(int year) {
        scriptGenerator.executeInstruction("EditTasks\tClick\tcalendar_year_picker");

        String str_year = Integer.toString(year);

        String script = String.format("EditTasks\tClickCalenderYear{%s}\tcalendar_pick_year{%s}", str_year, str_year);
        scriptGenerator.executeInstruction(script);
    }

    // slide down or up
    private void ClickCalendarYear(int year) {
        scriptGenerator.executeInstruction("EditTasks\tClick\tcalendar_year_picker");

        String str_year = Integer.toString(year);

        String script = String.format("EditTasks\tClickCalenderYear{%s}\tcalendar_pick_year{%s}", "Down", str_year);
        scriptGenerator.executeInstruction(script);
    }

    private void EnterCalendarDay(int day) {

        String script = String.format("EditTasks\tClick\tcalendar_pick_date{%d}", day);
        scriptGenerator.executeInstruction(script);
    }

    // count the distance from origin month to target month

    private void EnterCalendarMonth(int month) {
        if (month < 1 || month > 12)
            throw new IllegalArgumentException("Month must in the middle of one and twelve");

        int currentMonth = 5;

        if (currentMonth == month)
            return;

        if (currentMonth > month) {
            int offset = currentMonth - month;
            for (int i = 0; i < offset; i++) {
                scriptGenerator.executeInstruction("EditTasks\tClick\tcalendar_previous_month");
            }

        } else if (currentMonth < month) {
            int offset = month - currentMonth;
            for (int i = 0; i < offset; i++) {
                scriptGenerator.executeInstruction("EditTasks\tClick\tcalendar_next_month");
            }
        }
    }


    private void SaveCalendar() {
        scriptGenerator.executeInstruction("EditTasks\tClick\tcalendar_ok_btn");
    }

    private void CancelCalendar() {

        scriptGenerator.executeInstruction("EditTasks\tClick\tcalendar_cancel_btn");

    }

    private void ClickCompletedDate() {
        ScrollToElement("EditTasks", "Down", "complete_date");
        scriptGenerator.executeInstruction("EditTasks\tClick\tcomplete_date");
    }

    private void ClickCompletedTime() {
        ScrollToElement("EditTasks", "Down", "complete_time");
        scriptGenerator.executeInstruction("EditTasks\tClick\tcomplete_time");
    }
    private void EnterCalendar(int year, int month, int date) {
//        ScrollToCalenderYear(year);
        ClickCalendarYear(year);
        EnterCalendarMonth(month);
        EnterCalendarDay(date);
        SaveCalendar();
    }

    //.......

    private void EnterTime(int choice, int hour, int minute) {
        EnterAMOrPM(choice);
        EnterTimeHour(hour);
        EnterTimeMinute(minute);
        SaveTime();
    }

    private void EnterAMOrPM(int choice) {
        if (choice == 0) {
            // am
            scriptGenerator.executeInstruction("EditTasks\tClick\ttime_AM_label");
        } else {
            //pm
            scriptGenerator.executeInstruction("EditTasks\tClick\ttime_PM_label");
        }

    }



    private void EnterTimeHour(int hour) {
        String script = String.format("EditTasks\tClick\ttime_clock_pick_hours{%s}", Integer.toString(hour));
        scriptGenerator.executeInstruction(script);
    }

    private void EnterTimeMinute(int minute) {
        String script = String.format("EditTasks\tClick\ttime_clock_pick_minutes{%s}", Integer.toString(minute));
        scriptGenerator.executeInstruction(script);

    }

    private void SaveTime() {
        scriptGenerator.executeInstruction("EditTasks\tClick\ttime_ok_btn");
    }
    private void ScrollAndClickAllDayCheckBox() {
        String scrollScript = "EditTasks\tScroll{DOWN}\tall_day_checkbox";
        scriptGenerator.executeInstruction(scrollScript);

        String clickAllDayScript = "EditTasks\tClick\tall_day_checkbox";
        scriptGenerator.executeInstruction(clickAllDayScript);
    }

    private void ScrollToElement(String activity, String direction, String element) {
        String script = String.format("%s\tScroll{%s}\t%s", activity, direction, element);
        System.out.println(script);
        scriptGenerator.executeInstruction(script);
    }

    private void ScrollToElement(String activity, String direction, String element, String parameter) {
        String script = String.format("%s\tScroll{%s}\t%s{%s}", activity, direction, element, parameter);
        scriptGenerator.executeInstruction(script);
    }

    private void Swipe() {
        scriptGenerator.executeInstruction("EditTasks\tSwipeElement\ttask{My tasks}");
    }
    // swipe
    // timer: draggable, xpath

    // year

    // url xpath has problem

    // privacy dropdown xpath: PULL

    @Test
    public void editTask() {

        ClickTaskListBy("My tasks");
        ClickQuickAddButtonByRowOf(0);
        CreateNewTaskQuicklyBy("task");
        ClickTaskBy("task");
        ClickEditTaskButton();
//
        EnterTaskTitle("BIBI");
        SelectStatusDropDownBy("status_options_cancelled");

        EnterLocation("TW");
        EnterDescription("TW is Good");

        ClickStartDate();
        EnterCalendar(2040, 1, 22);
        //todo need scroll
        ClickDueTime();
        EnterTime(0, 10, 05);

        ClickDueDate();
        EnterCalendar(2040, 4, 23);

        ScrollToElement("EditTasks", "DOWN", "time_zone_dropdown");

        ClickTimeZone();
        EnterTimeZone("DOWN", "(GMT+08:00) Taipei");

        ClickCompletedDate();
        EnterCalendar(2040, 8, 05);

        ClickCompletedTime();
        EnterTime(0, 10, 05);
        SelectPriorityDropDownBy("priority_options_low");
        SelectPrivacyDropDownBy("privacy_options_public");
        EnterTaskURL("1234");
        SaveEditTask();

        ClickAddCheckItem();
        CreateCheckItem("taskItem1", 0);
        ClickBackKey();
        ClickAddCheckItem();
        CreateCheckItem("taskItem2", 1);
        ClickBackKey();
        ClickAddCheckItem(); // todo need to enter
//        ClickBackKey();
        MoveUpCheckItem(1);
        MoveDownCheckItem(0);

    }

    private void ClickBackKey() {
        scriptGenerator.executeInstruction("PressBackKey");
    }

    private void MoveDownCheckItem(int index) {
        String script = String.format("ViewTask\tMoveDown\tcheck_list_drag_handle{%s}", index);
        scriptGenerator.executeInstruction(script);
    }

    private void MoveUpCheckItem(int index) {
        String script = String.format("ViewTask\tMoveUp\tcheck_list_drag_handle{%s}", index);
        scriptGenerator.executeInstruction(script);
    }

    private void ClickAddCheckItem() {
        scriptGenerator.executeInstruction("ViewTask\tClick\tcheck_list_add_item");
    }

    private void CreateCheckItem(String taskItem1, int index) {
        String script = String.format("ViewTask\tTypeText{%s}\tcheck_list_editText{%s}", taskItem1, index);
        scriptGenerator.executeInstruction(script);
    }

    private void ClickTimeZone() {
        scriptGenerator.executeInstruction("EditTasks\tClick\ttime_zone_dropdown");
    }

    private void EnterTimeZone(String direction, String timeZone) {
        String script = String.format("EditTasks\tScrollAndClickTimeZone{%s}\ttime_zone_options{%s}", direction, timeZone);
        scriptGenerator.executeInstruction(script);
    }


}

//        ClickStartTime();
//        EnterTime(0, 12, 30);
//        ClickDueTime();
//        EnterTime(0, 12, 30);
//        ClickCompletedTime();
