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
        Config config = new Config("Android Emulator", "emulator-5554", 8, 5000, "emulator-5554", "emulator-5554");
        driver = new AppiumDriver(config);
        driver.startService();

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
        scriptGenerator.writeScriptFile();
        driver.stopService();
    }





    private void ClickTaskListBy(String taskListName) {
        String script = String.format("TaskList\tClick\ttask{%s}", taskListName);
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
        scriptGenerator.executeInstruction("EditTasks\tClick\tpriority_dropdown");
        String script = String.format("EditTasks\tClick\t%s", status);
        scriptGenerator.executeInstruction(script);
    }

    private void SelectPrivacyDropDownBy(String status) {
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


    // slide down or up
    private void EnterCalendarYear(int year) {
        scriptGenerator.executeInstruction("EditTasks\tClick\tcalendar_year_picker");

        String str_year = Integer.toString(year);

        String script = String.format("EditTasks\tClick\tcalendar_pick_year{%s}", str_year);
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

        int currentMonth = 4;

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
        scriptGenerator.executeInstruction("EditTasks\tClick\tcomplete_date");
    }

    private void ClickCompletedTime() {
        scriptGenerator.executeInstruction("EditTasks\tClick\tcomplete_time");
    }

    private void EnterCalendar(int year, int month, int date) {
        EnterCalendarYear(year);
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

        EnterTaskTitle("BIBI");
        SelectStatusDropDownBy("status_options_cancelled");

        EnterLocation("TW");
        EnterDescription("TW is Good");

        ClickStartDate();
        EnterCalendar(2020, 1, 22);
        //todo need scroll
        ClickDueTime();
        EnterTime(0, 10, 05);


        ClickDueDate();
        EnterCalendar(2019, 4, 23);

//        SelectPriorityDropDownBy("priority_options_none");

        SaveEditTask();
    }


}

//        ClickStartTime();
//        EnterTime(0, 12, 30);
//        ClickDueTime();
//        EnterTime(0, 12, 30);
//        ClickCompletedTime();
