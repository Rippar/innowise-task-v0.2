package by.murzo.workflow_launcher.service.impl;

import by.murzo.activity_worker.workflow.FileProcessingWorkflow;
import by.murzo.workflow_launcher.service.WorkflowLauncher;
import com.uber.cadence.client.WorkflowClient;
import lombok.RequiredArgsConstructor;
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
        System.out.println("Enter up the launch zone");
        FileProcessingWorkflow fileProcessingWorkflow = workflowClient.newWorkflowStub(FileProcessingWorkflow.class);
        try {
            System.out.println("Enter in launch zone");
            fileProcessingWorkflow.processFile(filename);
        } catch (Exception e) {
            //todo
            throw new RuntimeException(e);
        }
    }
}
