package com.infotech.fplcolosseum.utilities;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileWriter;
import java.io.IOException;

public class JsonUtil {

    public static void saveResponseToJsonFile(String response, String filePath) {
        try {
            FileWriter fileWriter = new FileWriter(filePath);
            fileWriter.write(response);
            fileWriter.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public static String readJsonFromFile(String filePath) {
        File file = new File(filePath);
        if (file.exists()) {
            try {
                FileInputStream inputStream = new FileInputStream(file);
                byte[] buffer = new byte[(int) file.length()];
                int bytesRead = inputStream.read(buffer);
                inputStream.close();

                if (bytesRead > 0) {
                    return new String(buffer, "UTF-8");
                } else {
                    // Handle the case where the file is empty
                    return null;
                }
            } catch (IOException e) {
                e.printStackTrace();
                // Handle the exception if needed
                return null;
            }
        }
        return null;
    }
}