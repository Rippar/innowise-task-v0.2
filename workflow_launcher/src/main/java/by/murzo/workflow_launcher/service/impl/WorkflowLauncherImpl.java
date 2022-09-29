package by.murzo.workflow_launcher.service.impl;


import by.murzo.workflow_launcher.service.WorkflowLauncher;
import by.murzo.workflow_worker.workflow.FileProcessingWorkflow;
import com.uber.cadence.client.WorkflowClient;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class WorkflowLauncherImpl implements WorkflowLauncher {

    private final WorkflowClient workflowClient;

    @Autowired
    public WorkflowLauncherImpl(WorkflowClient workflowClient) {
        this.workflowClient = workflowClient;
    }

    @Override
    public void launchWorkflow(String filename) {
        FileProcessingWorkflow fileProcessingWorkflow = workflowClient.newWorkflowStub(FileProcessingWorkflow.class);
        fileProcessingWorkflow.processFile(filename);

    }
}
