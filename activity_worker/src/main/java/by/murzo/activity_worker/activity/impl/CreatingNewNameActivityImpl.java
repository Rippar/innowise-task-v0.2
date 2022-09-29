package by.murzo.activity_worker.activity.impl;

import by.murzo.activity_worker.activity.CreatingNewNameActivity;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import java.text.SimpleDateFormat;
import java.util.Date;

@Component
public class CreatingNewNameActivityImpl implements CreatingNewNameActivity {

    @Value("${file.date_format_pattern}")
    private String dateFormatPattern;

    @Value("${file.author_name}")
    private String authorName;

    @Override
    public String createNewFileName(String oldFileName) {

        String[] fileNameParts = oldFileName.split("\\.");
        SimpleDateFormat myFormat = new SimpleDateFormat(dateFormatPattern);
        String currentDate = myFormat.format(new Date());

        return new StringBuilder()
                .append(fileNameParts[0])
                .append(currentDate)
                .append(authorName)
                .append(fileNameParts[1])
                .toString();

    }
}
