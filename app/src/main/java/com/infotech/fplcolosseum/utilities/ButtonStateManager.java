package com.infotech.fplcolosseum.utilities;

import android.content.Context;
import android.content.res.ColorStateList;
import androidx.core.content.ContextCompat;
import com.google.android.material.button.MaterialButton;
import com.infotech.fplcolosseum.R;

public class ButtonStateManager {

    public enum ButtonState {
        AVAILABLE, NOT_AVAILABLE, ACTIVE, PLAYED
    }

    public static void updateButtonState(Context context, MaterialButton button, ButtonState state) {
        switch (state) {
            case AVAILABLE:
                button.setEnabled(true);
                button.setTextColor(ContextCompat.getColor(context, R.color.button_text_available));
                button.setStrokeColor(ColorStateList.valueOf(ContextCompat.getColor(context, R.color.button_stroke_available)));
                button.setBackgroundTintList(ColorStateList.valueOf(ContextCompat.getColor(context, R.color.white)));
                break;
            case NOT_AVAILABLE:
                button.setEnabled(true);
                button.setTextColor(ContextCompat.getColor(context, R.color.black));
                button.setStrokeColor(ColorStateList.valueOf(ContextCompat.getColor(context, R.color.black)));
                button.setBackgroundTintList(ColorStateList.valueOf(ContextCompat.getColor(context, R.color.button_stroke_not_available)));
                break;
            case ACTIVE:
                button.setEnabled(true);
                button.setTextColor(ContextCompat.getColor(context, R.color.button_text_active));
                button.setBackgroundTintList(ColorStateList.valueOf(ContextCompat.getColor(context, R.color.button_background_active)));
                break;
            case PLAYED:
                button.setEnabled(true);
                button.setTextColor(ContextCompat.getColor(context, R.color.black));
                button.setStrokeColor(ColorStateList.valueOf(ContextCompat.getColor(context, R.color.black)));
                button.setBackgroundTintList(ColorStateList.valueOf(ContextCompat.getColor(context, R.color.button_stroke_not_available)));
                break;
        }

        // Store the state in the button's tag
        button.setTag(state);
    }

    public static ButtonState getButtonState(MaterialButton button) {
        Object state = button.getTag();
        return (state instanceof ButtonState) ? (ButtonState) state : ButtonState.NOT_AVAILABLE;
    }
}