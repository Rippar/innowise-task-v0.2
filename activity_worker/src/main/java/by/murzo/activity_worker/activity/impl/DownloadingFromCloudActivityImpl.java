package by.murzo.activity_worker.activity.impl;

import by.murzo.activity_worker.activity.DownloadingFromCloudActivity;
import com.amazonaws.auth.profile.ProfileCredentialsProvider;
import com.amazonaws.services.s3.AmazonS3;
import com.amazonaws.services.s3.AmazonS3ClientBuilder;
import com.amazonaws.services.s3.model.GetObjectRequest;
import com.amazonaws.services.s3.model.S3Object;
import com.opencsv.CSVWriter;
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

//    @Value("${cloud.download_bucket_name}")
//    private String bucket_name;
//
//    @Value("${cloud.download_storage_name}")
//    private String storage_name;
//
//    @Value("${cloud.download_region_name}")
//    private String region_name;

    @Override
    public void downloadFromCloud(String filename) throws IOException {

        AmazonS3 s3Client = AmazonS3ClientBuilder.standard()
                .withCredentials(new ProfileCredentialsProvider())
                .withEndpointConfiguration(
                        new AmazonS3ClientBuilder.EndpointConfiguration("storage.yandexcloud.net", "ru-central1")
                )
                .build();

        S3Object fullObject = s3Client.getObject(new GetObjectRequest("innowise", filename));

        List<String[]> csvData = convertInputIntoCSVFormat(fullObject.getObjectContent());
        CSVWriter writer = new CSVWriter(new FileWriter("processed_files/"+fullObject.getKey()));
        writer.writeAll(csvData);
        writer.close();

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
