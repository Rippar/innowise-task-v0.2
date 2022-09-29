package by.murzo.activity_worker.activity;

import com.uber.cadence.activity.ActivityMethod;

public interface CreatingNewNameActivity {
    @ActivityMethod(scheduleToCloseTimeoutSeconds = 20, taskList = "task-list")
    String createNewFileName(String oldFileName);
}
