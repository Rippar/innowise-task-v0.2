package by.murzo.activity_worker.workflow.impl;

import by.murzo.activity_worker.activity.ConvertingFileActivity;
import by.murzo.activity_worker.activity.CreatingNewNameActivity;
import by.murzo.activity_worker.activity.DownloadingFromCloudActivity;
import by.murzo.activity_worker.activity.UploadingToCLoudActivity;
import by.murzo.activity_worker.workflow.FileProcessingWorkflow;
import com.uber.cadence.workflow.Workflow;
import org.slf4j.Logger;


public class FileProcessingWorkflowImpl implements FileProcessingWorkflow {

    private final ConvertingFileActivity convertingFileActivity;
    private final CreatingNewNameActivity creatingNewNameActivity;
    private final DownloadingFromCloudActivity downloadingFromCloudActivity;
    private final UploadingToCLoudActivity uploadingToCLoudActivity;

    private static Logger logger = Workflow.getLogger(FileProcessingWorkflowImpl.class);

    public FileProcessingWorkflowImpl() {
        this.convertingFileActivity = Workflow.newActivityStub(ConvertingFileActivity.class);
        this.creatingNewNameActivity = Workflow.newActivityStub(CreatingNewNameActivity.class);
        this.downloadingFromCloudActivity = Workflow.newActivityStub(DownloadingFromCloudActivity.class);
        this.uploadingToCLoudActivity = Workflow.newActivityStub(UploadingToCLoudActivity.class);
    }

    @Override
    public void processFile(String filename) throws Exception {

        //todo delete
        logger.info("enter in process file????????????????????/");
        //todo exception???
        downloadingFromCloudActivity.downloadFromCloud(filename);

        String newKeyFileName = creatingNewNameActivity.createNewFileName(filename);

        String newFileName = convertingFileActivity.convertCsvToXlsx(filename, newKeyFileName);

        uploadingToCLoudActivity.uploadToCloud(newFileName);
        logger.info("all activities finished");

    }
}
