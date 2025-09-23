package com.infotech.fplcolosseum.utilities;

import android.content.Context;
import android.graphics.Color;
import android.view.Gravity;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.ProgressBar;
import android.widget.TextView;

import androidx.appcompat.app.AlertDialog;

public class ProgressDialogHelper {

    private AlertDialog progressDialog;
    private final Context context;

    public ProgressDialogHelper(Context context) {
        this.context = context;
    }

    /**
     * Show indeterminate progress dialog - USE THIS FOR MOST CASES
     */
    public void showProgressDialog(String title, String message) {
        if (progressDialog != null && progressDialog.isShowing()) {
            return;
        }

        AlertDialog.Builder builder = new AlertDialog.Builder(context);

        // Create custom layout for progress dialog
        LinearLayout layout = new LinearLayout(context);
        layout.setOrientation(LinearLayout.HORIZONTAL);
        layout.setPadding(50, 50, 50, 50);
        layout.setGravity(Gravity.START);

        // Add progress bar
        ProgressBar progressBar = new ProgressBar(context);
        progressBar.setIndeterminate(true);
        LinearLayout.LayoutParams progressParams = new LinearLayout.LayoutParams(
                LinearLayout.LayoutParams.WRAP_CONTENT,
                LinearLayout.LayoutParams.WRAP_CONTENT
        );
        progressParams.gravity = Gravity.CENTER_VERTICAL;
        layout.addView(progressBar, progressParams);

        // Add message text
        if (message != null && !message.isEmpty()) {
            TextView textView = new TextView(context);
            textView.setText(message);
            textView.setTextSize(16);
            textView.setTextColor(Color.BLACK);
            LinearLayout.LayoutParams textParams = new LinearLayout.LayoutParams(
                    LinearLayout.LayoutParams.WRAP_CONTENT,
                    LinearLayout.LayoutParams.WRAP_CONTENT
            );
            textParams.gravity = Gravity.CENTER_VERTICAL; // center in line
            textParams.leftMargin = 30; // some spacing between progress & text
            textView.setLayoutParams(textParams);
            layout.addView(textView);
        }

        builder.setView(layout);
        if (title != null && !title.isEmpty()) {
            builder.setTitle(title);
        }

        builder.setCancelable(false);
        progressDialog = builder.create();
        progressDialog.show();
    }

    /**
     * Show determinate progress dialog with progress bar
     */
    public void showProgressDialog(String title, String message, int maxProgress) {
        if (progressDialog != null && progressDialog.isShowing()) {
            return;
        }

        AlertDialog.Builder builder = new AlertDialog.Builder(context);

        // Create custom layout
        LinearLayout layout = new LinearLayout(context);
        layout.setOrientation(LinearLayout.VERTICAL);
        layout.setPadding(50, 50, 50, 50);

        // Add message text
        if (message != null && !message.isEmpty()) {
            TextView textView = new TextView(context);
            textView.setText(message);
            textView.setTextSize(16);
            textView.setTextColor(Color.BLACK);
            textView.setPadding(0, 0, 0, 20);
            layout.addView(textView);
        }

        // Add horizontal progress bar
        ProgressBar progressBar = new ProgressBar(context, null, android.R.attr.progressBarStyleHorizontal);
        progressBar.setMax(maxProgress);
        progressBar.setProgress(0);
        progressBar.setId(View.generateViewId());
        layout.addView(progressBar);

        builder.setView(layout);
        if (title != null && !title.isEmpty()) {
            builder.setTitle(title);
        }

        builder.setCancelable(false);
        progressDialog = builder.create();
        progressDialog.show();
    }

    /**
     * Update progress for determinate progress dialog
     */
    public void updateProgress(int progress) {
        if (progressDialog != null && progressDialog.isShowing()) {
            ProgressBar progressBar = progressDialog.findViewById(android.R.id.progress);
            if (progressBar == null) {
                // Find by generated ID if not found by standard ID
                LinearLayout layout = (LinearLayout) progressDialog.findViewById(android.R.id.content);
                if (layout != null) {
                    for (int i = 0; i < layout.getChildCount(); i++) {
                        View child = layout.getChildAt(i);
                        if (child instanceof ProgressBar) {
                            progressBar = (ProgressBar) child;
                            break;
                        }
                    }
                }
            }
            if (progressBar != null) {
                progressBar.setProgress(progress);
            }
        }
    }

    /**
     * Update message text
     */
    public void updateMessage(String message) {
        if (progressDialog != null && progressDialog.isShowing()) {
            // Find TextView in dialog
            LinearLayout layout = (LinearLayout) ((AlertDialog) progressDialog).findViewById(android.R.id.content);
            if (layout != null) {
                for (int i = 0; i < layout.getChildCount(); i++) {
                    View child = layout.getChildAt(i);
                    if (child instanceof TextView) {
                        ((TextView) child).setText(message);
                        break;
                    }
                }
            }
        }
    }

    /**
     * Dismiss progress dialog
     */
    public void dismissProgressDialog() {
        if (progressDialog != null && progressDialog.isShowing()) {
            progressDialog.dismiss();
            progressDialog = null;
        }
    }

    /**
     * Check if dialog is showing
     */
    public boolean isShowing() {
        return progressDialog != null && progressDialog.isShowing();
    }
}