package by.murzo.workflow_worker.workflow;

import com.uber.cadence.workflow.WorkflowMethod;

public interface FileProcessingWorkflow {
    @WorkflowMethod(taskList = "task-list", executionStartToCloseTimeoutSeconds = 30)
    void processFile(String filename);
}
