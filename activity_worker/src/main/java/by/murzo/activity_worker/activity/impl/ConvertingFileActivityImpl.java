package by.murzo.activity_worker.activity.impl;

import by.murzo.activity_worker.activity.ConvertingFileActivity;
import com.aspose.cells.FileFormatType;
import com.aspose.cells.LoadOptions;
import com.aspose.cells.SaveFormat;
import com.aspose.cells.Workbook;
import org.springframework.stereotype.Component;

@Component
public class ConvertingFileActivityImpl implements ConvertingFileActivity {
    @Override
    public String convertCsvToXlsx(String oldFilename, String newKeyFileName) throws Exception {
        LoadOptions loadOptions = new LoadOptions(FileFormatType.CSV);
        Workbook workbook = new Workbook("processed_files/"+oldFilename, loadOptions);
        String newFileName = newKeyFileName + ".xlsx";
        workbook.save("processed_files/"+newFileName, SaveFormat.XLSX);
        return newFileName;

    }


}
