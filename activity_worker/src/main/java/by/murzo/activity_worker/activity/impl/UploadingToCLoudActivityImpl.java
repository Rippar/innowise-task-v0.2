package by.murzo.activity_worker.activity.impl;

import by.murzo.activity_worker.activity.UploadingToCLoudActivity;
import com.amazonaws.auth.profile.ProfileCredentialsProvider;
import com.amazonaws.services.s3.AmazonS3;
import com.amazonaws.services.s3.AmazonS3ClientBuilder;
import com.amazonaws.services.s3.model.ObjectMetadata;
import com.amazonaws.services.s3.model.PutObjectRequest;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import java.io.File;

@Component
public class UploadingToCLoudActivityImpl implements UploadingToCLoudActivity {

    @Value("${cloud.upload_bucket_name}")
    private String bucket_name;

    @Value("${cloud.upload_storage_name}")
    private String storage_name;

    @Value("${cloud.upload_region_name}")
    private String region_name;

    @Value("${cloud.upload_content_type}")
    private String content_type;

    @Override
    public void uploadToCloud(String filename) {
        AmazonS3 s3Client = AmazonS3ClientBuilder.standard()
                .withCredentials(new ProfileCredentialsProvider())
                .withEndpointConfiguration(
                        new AmazonS3ClientBuilder.EndpointConfiguration(storage_name, region_name)
                )
                .build();

        PutObjectRequest request = new PutObjectRequest(bucket_name, filename, new File("processed_files/"+filename));
        ObjectMetadata metadata = new ObjectMetadata();
        metadata.setContentType(content_type);
        request.setMetadata(metadata);
        s3Client.putObject(request);

    }
}
