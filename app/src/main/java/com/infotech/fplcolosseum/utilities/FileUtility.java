package com.infotech.fplcolosseum.utilities;

import android.annotation.SuppressLint;
import android.content.Context;
import android.net.Uri;
import android.os.Environment;
import android.os.ParcelFileDescriptor;
import android.widget.Toast;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Locale;

public class FileUtility {

    Context mContext;
    public FileUtility(Context context){
        this.mContext = context;
    }

    public void savePdfFileToUri(Uri uri) {
        try {
            // Get the file content as bytes (replace this with your actual PDF content)
            byte[] pdfContent = getYourPdfContent();

            @SuppressLint("Recycle") FileOutputStream outputStream = (FileOutputStream) mContext.getContentResolver().openOutputStream(uri);
            if (outputStream != null) {
                outputStream.write(pdfContent);
                outputStream.close();
//                Toast.makeText(this, "PDF file saved", Toast.LENGTH_SHORT).show();
            }
        } catch (IOException e) {
            e.printStackTrace();
//            Toast.makeText(this, "Failed to save PDF file", Toast.LENGTH_SHORT).show();
        }
    }

    private File createPdfFile() {
        // Replace this with your PDF creation logic
        // For example, you might be using a library to generate PDFs
        // Here, we're creating a simple text file with some content

        String timeStamp = new SimpleDateFormat("yyyyMMdd_HHmmss", Locale.getDefault()).format(new Date());
        String fileName = "PDF_" + timeStamp + ".txt";

        File pdfFile = new File(mContext.getExternalFilesDir(Environment.DIRECTORY_DOWNLOADS), fileName);

        try {
            FileOutputStream outputStream = new FileOutputStream(pdfFile);
            outputStream.write("This is a sample PDF file content.".getBytes());
            outputStream.close();
            return pdfFile;
        } catch (IOException e) {
            e.printStackTrace();
            return null;
        }
    }

    public void alterDocument(Uri uri) {
        try {
            ParcelFileDescriptor pfd = mContext.getContentResolver().
                    openFileDescriptor(uri, "w");
            FileOutputStream fileOutputStream =
                    new FileOutputStream(pfd.getFileDescriptor());
            fileOutputStream.write(("Overwritten at " + System.currentTimeMillis() +
                    "\n").getBytes());
            // Let the document provider know you're done by closing the stream.
            fileOutputStream.close();
            pfd.close();
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private byte[] getYourPdfContent() {
        // Replace this with your actual PDF content
        // Here, we're returning a sample byte array
        return "This is a sample PDF file content.".getBytes();
    }
}
