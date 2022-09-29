package by.murzo.workflow_worker.workflow.impl;

import by.murzo.activity_worker.activity.ConvertingFileActivity;
import by.murzo.activity_worker.activity.CreatingNewNameActivity;
import by.murzo.activity_worker.activity.DownloadingFromCloudActivity;
import by.murzo.activity_worker.activity.UploadingToCLoudActivity;
import by.murzo.workflow_worker.workflow.FileProcessingWorkflow;
import com.uber.cadence.workflow.Workflow;

public class FileProcessingWorkflowImpl implements FileProcessingWorkflow {

    private final ConvertingFileActivity convertingFileActivity;
    private final CreatingNewNameActivity creatingNewNameActivity;
    private final DownloadingFromCloudActivity downloadingFromCloudActivity;
    private final UploadingToCLoudActivity uploadingToCLoudActivity;

    public FileProcessingWorkflowImpl() {
        this.convertingFileActivity = Workflow.newActivityStub(ConvertingFileActivity.class);
        this.creatingNewNameActivity = Workflow.newActivityStub(CreatingNewNameActivity.class);
        this.downloadingFromCloudActivity = Workflow.newActivityStub(DownloadingFromCloudActivity.class);
        this.uploadingToCLoudActivity = Workflow.newActivityStub(UploadingToCLoudActivity.class);
    }

    @Override
    public void processFile(String filename) {

        downloadingFromCloudActivity.downloadFromCloud(filename);

        String newKeyFileName = creatingNewNameActivity.createNewFileName(filename);

        String newFileName = convertingFileActivity.convertCsvToXlsx(filename, newKeyFileName);

        uploadingToCLoudActivity.uploadToCloud(newFileName);

    }
}
