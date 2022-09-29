package by.murzo.workflow_launcher.config;

import com.uber.cadence.client.WorkflowClient;
import com.uber.cadence.client.WorkflowClientOptions;
import com.uber.cadence.serviceclient.ClientOptions;
import com.uber.cadence.serviceclient.WorkflowServiceTChannel;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class LauncherConfig {

    @Value("${cadence.domain}")
    private String domain;

    @Bean
    public WorkflowClient workflowClient() {
        return WorkflowClient.newInstance(
                new WorkflowServiceTChannel(ClientOptions.defaultInstance()),
                WorkflowClientOptions
                        .newBuilder()
                        .setDomain("test-domain")
                        .build()
        );
    }
}
