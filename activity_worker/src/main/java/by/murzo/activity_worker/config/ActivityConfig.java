package by.murzo.activity_worker.config;

import by.murzo.activity_worker.activity.ConvertingFileActivity;
import by.murzo.activity_worker.activity.CreatingNewNameActivity;
import by.murzo.activity_worker.activity.DownloadingFromCloudActivity;
import by.murzo.activity_worker.activity.UploadingToCLoudActivity;
import com.amazonaws.auth.profile.ProfileCredentialsProvider;
import com.amazonaws.services.s3.AmazonS3;
import com.amazonaws.services.s3.AmazonS3ClientBuilder;
import com.uber.cadence.DomainAlreadyExistsError;
import com.uber.cadence.RegisterDomainRequest;
import com.uber.cadence.client.WorkflowClient;
import com.uber.cadence.client.WorkflowClientOptions;
import com.uber.cadence.serviceclient.ClientOptions;
import com.uber.cadence.serviceclient.IWorkflowService;
import com.uber.cadence.serviceclient.WorkflowServiceTChannel;
import com.uber.cadence.worker.Worker;
import com.uber.cadence.worker.WorkerFactory;
import lombok.extern.slf4j.Slf4j;
import org.apache.thrift.TException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.CommandLineRunner;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
@Slf4j
public class ActivityConfig {

    private static final Logger LOGGER = LoggerFactory.getLogger(ActivityConfig.class);

    @Value("${cadence.domain}")
    private String domain;

    @Value("${cadence.task-list}")
    private String taskList;

    @Value("${cloud.storage_name}")
    private String storageName;

    @Value("${cloud.region_name}")
    private String regionName;

    @Value("${cadence.retention_period}")
    private int retentionPeriodInDays;

    @Bean
    public AmazonS3 s3Client() {
        return AmazonS3ClientBuilder.standard()
                .withCredentials(new ProfileCredentialsProvider())
                .withEndpointConfiguration(
                        new AmazonS3ClientBuilder.EndpointConfiguration(storageName, regionName)
                )
                .build();
    }

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
        } catch (DomainAlreadyExistsError e) {
            LOGGER.info("Domain already registered");
        } catch (TException ignored) {

        }
    }


    private void registerActivity(String domain, String taskList, Object... activityInstances) {
        WorkflowClient workflowClient =
                WorkflowClient.newInstance(
                        new WorkflowServiceTChannel(ClientOptions.defaultInstance()),
                        WorkflowClientOptions.newBuilder().setDomain(domain).build());

        WorkerFactory factory = WorkerFactory.newInstance(workflowClient);
        Worker worker = factory.newWorker(taskList);

        worker.registerActivitiesImplementations(activityInstances);
        factory.start();

    }

}
