package org.dmfs.tasks.utils.tasks;

import android.app.Activity;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.support.test.rule.ActivityTestRule;

import org.dmfs.tasks.TaskListActivity;
import org.junit.Test;

public class TaskListActivityTest {
    private ActivityTestRule<TaskListActivity> activityTestRule;
    private boolean testStop = false;

    @Test
    public void testInitPrint() throws InterruptedException {
        activityTestRule = new ActivityTestRule<TaskListActivity>(TaskListActivity.class);
        activityTestRule.launchActivity(null);
        IntentFilter intentFilter = new IntentFilter("test");
        BroadcastReceiver brocastReceiver = new BroadcastReceiver() {
            @Override
            public void onReceive(Context context, Intent intent) {
                testStop = true;
            }
        };
        Activity activity = activityTestRule.getActivity();
        activity.registerReceiver(brocastReceiver, intentFilter);
        while (!activityTestRule.getActivity().isFinishing() && !testStop) {
        }
        activity.unregisterReceiver(brocastReceiver);
    }
}
