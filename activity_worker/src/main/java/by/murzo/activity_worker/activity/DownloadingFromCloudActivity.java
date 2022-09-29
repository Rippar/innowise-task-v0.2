package by.murzo.activity_worker.activity;

import com.uber.cadence.activity.ActivityMethod;


public interface DownloadingFromCloudActivity {
    @ActivityMethod(scheduleToCloseTimeoutSeconds = 20, taskList = "task-list")
    void downloadFromCloud(String filename);

}
