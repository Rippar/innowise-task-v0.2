package by.murzo.activity_worker.activity.impl;

import by.murzo.activity_worker.activity.ConvertingFileActivity;
import com.aspose.cells.FileFormatType;
import com.aspose.cells.LoadOptions;
import com.aspose.cells.SaveFormat;
import com.aspose.cells.Workbook;
import com.uber.cadence.workflow.Workflow;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

@Component
public class ConvertingFileActivityImpl implements ConvertingFileActivity {

    @Value("${file.storage_folder}")
    private String downloadFolder;

    @Override
    public String convertCsvToXlsx(String oldFilename, String newKeyFileName) {
        LoadOptions loadOptions = new LoadOptions(FileFormatType.CSV);
        Workbook workbook;
        String newFileName;
        try {
            workbook = new Workbook(downloadFolder + oldFilename, loadOptions);
            newFileName = newKeyFileName + ".xlsx";
            workbook.save(downloadFolder + newFileName, SaveFormat.XLSX);
        } catch (Exception e) {
            throw Workflow.wrap(e);
        }

        return newFileName;

    }


}
