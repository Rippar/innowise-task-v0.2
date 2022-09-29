package by.murzo.activity_worker.config;

import by.murzo.activity_worker.activity.ConvertingFileActivity;
import by.murzo.activity_worker.activity.CreatingNewNameActivity;
import by.murzo.activity_worker.activity.DownloadingFromCloudActivity;
import by.murzo.activity_worker.activity.UploadingToCLoudActivity;
import com.uber.cadence.DomainAlreadyExistsError;
import com.uber.cadence.RegisterDomainRequest;
import com.uber.cadence.client.WorkflowClient;
import com.uber.cadence.client.WorkflowClientOptions;
import com.uber.cadence.serviceclient.ClientOptions;
import com.uber.cadence.serviceclient.IWorkflowService;
import com.uber.cadence.serviceclient.WorkflowServiceTChannel;
import com.uber.cadence.worker.Worker;
import com.uber.cadence.worker.WorkerFactory;
import org.apache.thrift.TException;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.CommandLineRunner;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class ActivityConfig {

    @Value("${cadence.domain}")
    private String domain;

    @Value("${cadence.task-list}")
    private String taskList;

    @Value("${cadence.retention_period_in_days}")
    private int retentionPeriodInDays;

    @Bean
    public CommandLineRunner commandLineRunner(ConvertingFileActivity convertingFileActivity,
                                               CreatingNewNameActivity creatingNewNameActivity,
                                               DownloadingFromCloudActivity downloadingFromCloudActivity,
                                               UploadingToCLoudActivity uploadingToCLoudActivity) {
        return args -> {
            registerDomain();
            registerActivity(domain, taskList, convertingFileActivity, creatingNewNameActivity,
                    downloadingFromCloudActivity, uploadingToCLoudActivity);
        };
    }

    private void registerDomain() {
        IWorkflowService service = new WorkflowServiceTChannel(ClientOptions.defaultInstance());
        RegisterDomainRequest request = new RegisterDomainRequest();
        request.setName(domain).setWorkflowExecutionRetentionPeriodInDays(retentionPeriodInDays);
        try {
            service.RegisterDomain(request);
        } catch (DomainAlreadyExistsError ignored) {
        } catch (TException e) {
            //todo
        }
    }

    private void registerActivity(String domain, String taskList, Object... activityInstances) {
        WorkflowClient workflowClient =
                WorkflowClient.newInstance(
                        new WorkflowServiceTChannel(ClientOptions.defaultInstance()),
                        WorkflowClientOptions.newBuilder().setDomain("test-domain").build());

        WorkerFactory factory = WorkerFactory.newInstance(workflowClient);
        Worker worker = factory.newWorker("task-list");

        worker.registerActivitiesImplementations(activityInstances);
        factory.start();


    }

}
