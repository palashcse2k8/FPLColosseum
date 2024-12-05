package com.infotech.fplcolosseum.utilities;

import android.app.Activity;
import android.content.Intent;
import android.graphics.Bitmap;
import android.net.Uri;
import android.os.Environment;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;

import androidx.core.content.FileProvider;

import com.google.android.material.bottomsheet.BottomSheetDialog;
import com.infotech.fplcolosseum.R;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;

public class ImageUtil {

    public static Bitmap getBitmapFromView(View view) {
        Bitmap bitmap = Bitmap.createBitmap(view.getWidth(), view.getHeight(), Bitmap.Config.ARGB_8888);
        view.draw(new android.graphics.Canvas(bitmap));
        return bitmap;
    }

    public static File saveBitmapToFile(Activity activity, Bitmap bitmap) throws IOException {
        File imageFolder = new File(activity.getExternalFilesDir(Environment.DIRECTORY_PICTURES), "SharedImages");
        if (!imageFolder.exists()) {
            imageFolder.mkdirs();
        }
        File imageFile = new File(imageFolder, "shared_image.png");
        FileOutputStream outputStream = new FileOutputStream(imageFile);
        bitmap.compress(Bitmap.CompressFormat.JPEG, 100, outputStream);
        outputStream.flush();
        outputStream.close();
        return imageFile;
    }

    public static void shareImage(Activity activity, File imageFile) {
        Uri imageUri = FileProvider.getUriForFile(
                activity,
                activity.getApplicationContext().getPackageName() + ".provider",
                imageFile
        );

        Intent shareIntent = new Intent(Intent.ACTION_SEND);
        shareIntent.setType("image/*");
        shareIntent.putExtra(Intent.EXTRA_STREAM, imageUri);
        shareIntent.putExtra(Intent.EXTRA_TEXT, "Check out this image!");
        shareIntent.addFlags(Intent.FLAG_GRANT_READ_URI_PERMISSION);

        // Add preview details for compatible apps (e.g., Google Photos)
        shareIntent.putExtra(Intent.EXTRA_TITLE, "Image Preview");
        shareIntent.putExtra(Intent.EXTRA_SUBJECT, "Preview before sharing");

        // Ensure WhatsApp and Messenger are included
        activity.startActivity(Intent.createChooser(shareIntent, "Share Image Using"));
    }

    public static void shareImageWithBottomSheetPreview(Activity activity, File imageFile) {
        Uri imageUri = FileProvider.getUriForFile(
                activity,
                activity.getApplicationContext().getPackageName() + ".provider",
                imageFile
        );

        // Create bottom sheet dialog
        BottomSheetDialog bottomSheetDialog = new BottomSheetDialog(activity);
        View bottomSheetView = activity.getLayoutInflater().inflate(R.layout.bottom_sheet_image_preview, null);

        ImageView previewImageView = bottomSheetView.findViewById(R.id.previewImageView);
        Button shareButton = bottomSheetView.findViewById(R.id.shareButton);

        previewImageView.setImageURI(imageUri);

        shareButton.setOnClickListener(v -> {
            bottomSheetDialog.dismiss();

            Intent shareIntent = new Intent(Intent.ACTION_SEND);
            shareIntent.setType("image/*");
            shareIntent.putExtra(Intent.EXTRA_STREAM, imageUri);
            shareIntent.addFlags(Intent.FLAG_GRANT_READ_URI_PERMISSION);

            Intent chooserIntent = Intent.createChooser(shareIntent, "Share Image");
            chooserIntent.putExtra(Intent.EXTRA_TITLE, "Image Preview");
            chooserIntent.putExtra(Intent.EXTRA_STREAM, imageUri);
            chooserIntent.putExtra(Intent.EXTRA_INITIAL_INTENTS, imageUri);
            activity.startActivity(chooserIntent);
        });

        bottomSheetDialog.setContentView(bottomSheetView);
        bottomSheetDialog.show();
    }
}
