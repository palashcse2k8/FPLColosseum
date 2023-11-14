///*
// * Created by Md Abdullah- Al- Asad,Assistant Programmer,Md Abdullah- Al- Asad,Assistant Programmer,Mobile Development Team, Business IT Division, Sonali Bank Limited, Bangladesh
// * Copyright 2018 Sonali Bank Limited. All rights reserved.
// * Licensed under the Apache License, Version 2.0 (the "License");
// * you may not use this file except in compliance with the License.
// * You may obtain a copy of the License at
// * http://www.apache.org/licenses/LICENSE-2.0
// * Unless required by applicable law or agreed to in writing, software
// * distributed under the License is distributed on an "AS IS" BASIS,
// * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
// * See the License for the specific language governing permissions and
// * limitations under the License.
// */
//
package com.infotech.fplcolosseum.utilities;

import android.content.Context;
import android.content.res.Resources;
import android.graphics.Rect;
import android.widget.ProgressBar;
import android.widget.Toast;

import androidx.annotation.StringRes;

import com.infotech.fplcolosseum.FPLCApplication;

import es.dmoral.toasty.Toasty;

public class UIUtils {

    private UIUtils() {}
    public static void toast(Context context, String msg, ToastLevel level) {
        switch (level) {

            case SUCCESS:
                Toasty.success(context, msg, Toast.LENGTH_LONG).show();
                break;
            case WARNING:
                Toasty.warning(context, msg, Toast.LENGTH_LONG).show();
                break;
            case INFO:
                Toasty.info(context, msg, Toast.LENGTH_LONG).show();
                break;
            case ERROR:
                Toasty.error(context, msg, Toast.LENGTH_LONG).show();
                break;
        }
    }

    public static void toast(@StringRes int msg, ToastLevel level) {
        toast(msg, level);
    }

    public static int getScreenWidth() {
        return Resources.getSystem().getDisplayMetrics().widthPixels;
    }

    public static int getScreenHeight() {
        return Resources.getSystem().getDisplayMetrics().heightPixels;
    }
}
