package by.murzo.activity_worker.activity;

import com.uber.cadence.activity.ActivityMethod;

public interface UploadingToCLoudActivity {
    @ActivityMethod(scheduleToCloseTimeoutSeconds = 20, taskList = "task-list")
    void uploadToCloud(String filename);
}
