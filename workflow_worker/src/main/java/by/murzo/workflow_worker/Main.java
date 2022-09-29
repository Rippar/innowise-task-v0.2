package by.murzo.workflow_worker;

import by.murzo.workflow_worker.workflow.impl.FileProcessingWorkflowImpl;
import com.uber.cadence.client.WorkflowClient;
import com.uber.cadence.client.WorkflowClientOptions;
import com.uber.cadence.serviceclient.ClientOptions;
import com.uber.cadence.serviceclient.WorkflowServiceTChannel;
import com.uber.cadence.worker.Worker;
import com.uber.cadence.worker.WorkerFactory;


public class Main {

    private static final String DOMAIN = "test-domain";
    private static final String TASK_LIST = "task-list";

    public static void main(String[] args) {
        WorkflowClient workflowClient =
                WorkflowClient.newInstance(new WorkflowServiceTChannel(ClientOptions.defaultInstance()),
                WorkflowClientOptions.newBuilder().setDomain(DOMAIN).build());

        WorkerFactory factory = WorkerFactory.newInstance(workflowClient);
        Worker worker = factory.newWorker(TASK_LIST);
        worker.registerWorkflowImplementationTypes(FileProcessingWorkflowImpl.class);
        factory.start();

    }
}
