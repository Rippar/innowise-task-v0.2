package by.murzo.activity_worker.activity.impl;

import by.murzo.activity_worker.activity.UploadingToCLoudActivity;
import com.amazonaws.services.s3.AmazonS3;
import com.amazonaws.services.s3.model.ObjectMetadata;
import com.amazonaws.services.s3.model.PutObjectRequest;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import java.io.File;

@Component
public class UploadingToCLoudActivityImpl implements UploadingToCLoudActivity {

    @Value("${cloud.bucket_name}")
    private String bucket_name;

    @Value("${cloud.content_type}")
    private String content_type;

    @Value("${file.storage_folder}")
    private String downloadFolder;

    private final AmazonS3 s3Client;

    @Autowired
    public UploadingToCLoudActivityImpl(AmazonS3 s3Client) {
        this.s3Client = s3Client;
    }

    @Override
    public void uploadToCloud(String filename) {

        PutObjectRequest request = new PutObjectRequest(bucket_name, filename, new File(downloadFolder + filename));
        ObjectMetadata metadata = new ObjectMetadata();
        metadata.setContentType(content_type);
        request.setMetadata(metadata);
        s3Client.putObject(request);

    }
}
