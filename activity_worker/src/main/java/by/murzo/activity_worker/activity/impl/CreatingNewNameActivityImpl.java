package by.murzo.activity_worker.activity.impl;

import by.murzo.activity_worker.activity.CreatingNewNameActivity;
import org.springframework.stereotype.Component;

import java.text.SimpleDateFormat;
import java.util.Date;

@Component
public class CreatingNewNameActivityImpl implements CreatingNewNameActivity {
    @Override
    public String createNewFileName(String oldFileName) {

        String[] fileNameParts = oldFileName.split("\\.");
        SimpleDateFormat myFormat = new SimpleDateFormat("yyyy-MM-dd");
        String currentDate = myFormat.format(new Date());

        return new StringBuilder()
                .append(fileNameParts[0])
                .append(currentDate)
                .append("Dmitry")
                .append(fileNameParts[1])
                .toString();

    }
}
