package by.murzo.activity_worker.workflow;

import com.uber.cadence.workflow.WorkflowMethod;

public interface FileProcessingWorkflow {
    @WorkflowMethod(taskList = "task-list", executionStartToCloseTimeoutSeconds = 30)
    void processFile(String filename) throws Exception;
}
