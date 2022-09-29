package by.murzo.activity_worker.activity;

import com.uber.cadence.activity.ActivityMethod;

public interface ConvertingFileActivity {
    @ActivityMethod(scheduleToCloseTimeoutSeconds = 20, taskList = "task-list")
    String convertCsvToXlsx(String oldFilename, String newKeyFileName);
}
