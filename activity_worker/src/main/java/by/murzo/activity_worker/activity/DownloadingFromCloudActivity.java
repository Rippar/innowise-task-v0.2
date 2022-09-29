package by.murzo.activity_worker.activity;

import com.uber.cadence.activity.ActivityMethod;

import java.io.IOException;

public interface DownloadingFromCloudActivity {
    @ActivityMethod(scheduleToCloseTimeoutSeconds = 20, taskList = "task-list")
    void downloadFromCloud(String filename) throws IOException;

}
