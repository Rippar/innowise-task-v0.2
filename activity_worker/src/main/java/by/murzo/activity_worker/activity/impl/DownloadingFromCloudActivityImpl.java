package by.murzo.activity_worker.activity.impl;

import by.murzo.activity_worker.activity.DownloadingFromCloudActivity;
import com.amazonaws.services.s3.AmazonS3;
import com.amazonaws.services.s3.model.GetObjectRequest;
import com.amazonaws.services.s3.model.S3Object;
import com.opencsv.CSVWriter;
import com.uber.cadence.workflow.Workflow;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import java.io.BufferedReader;
import java.io.FileWriter;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.List;

@Component
public class DownloadingFromCloudActivityImpl implements DownloadingFromCloudActivity {

    @Value("${cloud.bucket_name}")
    private String bucketName;

    @Value("${file.storage_folder}")
    private String downloadFolder;

    private final AmazonS3 s3Client;

    @Autowired
    public DownloadingFromCloudActivityImpl(AmazonS3 s3Client) {
        this.s3Client = s3Client;
    }

    @Override
    public void downloadFromCloud(String filename) {

        S3Object fullObject = s3Client.getObject(new GetObjectRequest(bucketName, filename));

        List<String[]> csvData;
        try {
            csvData = convertInputIntoCSVFormat(fullObject.getObjectContent());
            CSVWriter writer = new CSVWriter(new FileWriter(downloadFolder + fullObject.getKey()));
            writer.writeAll(csvData);
            writer.close();
        } catch (IOException e) {
            throw Workflow.wrap(e);
        }


    }

    private List<String[]> convertInputIntoCSVFormat(InputStream input) throws IOException {

        BufferedReader reader = new BufferedReader(new InputStreamReader(input));
        List<String[]> csvData = new ArrayList<>();
        String line;

        while ((line = reader.readLine()) != null) {
            String[] temp = line.split(";");
            csvData.add(temp);
        }

        reader.close();
        return csvData;
    }
}
