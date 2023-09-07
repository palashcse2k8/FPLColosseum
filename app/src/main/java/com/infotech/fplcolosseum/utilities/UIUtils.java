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
//package com.infotech.fplcolosseum.utilities;
//
//import android.content.Context;
//import android.content.res.Resources;
//import android.graphics.Rect;
//import android.widget.ProgressBar;
//import android.widget.Toast;
//
//import androidx.annotation.StringRes;
//
//import com.jpardogo.android.googleprogressbar.library.ChromeFloatingCirclesDrawable;
//
//import bd.com.sonalibank.sw.core.Core;
//import bd.com.sonalibank.sw.R;
//import es.dmoral.toasty.Toasty;
//
//
//public class UIUtils {
//
//    private UIUtils() {}
//
//    public static void toast(String msg) {
//        toast(msg, ToastLevel.INFO);
//    }
//
//    public static void toast(@StringRes int msg) {
//        toast(Core.getApp().getString(msg), ToastLevel.INFO);
//    }
//
//    public static void toast(String msg, ToastLevel level) {
//        switch (level) {
//
//            case SUCCESS:
//                Toasty.success(Core.getApp(), msg, Toast.LENGTH_LONG).show();
//                break;
//            case WARNING:
//                Toasty.warning(Core.getApp(), msg, Toast.LENGTH_LONG).show();
//                break;
//            case INFO:
//                Toasty.info(Core.getApp(), msg, Toast.LENGTH_LONG).show();
//                break;
//            case ERROR:
//                Toasty.error(Core.getApp(), msg, Toast.LENGTH_LONG).show();
//                break;
//        }
//    }
//
//    public static void toast(@StringRes int msg, ToastLevel level) {
//        toast(Core.getApp().getString(msg), level);
//    }
//
//    public static int getScreenWidth() {
//        return Resources.getSystem().getDisplayMetrics().widthPixels;
//    }
//
//    public static int getScreenHeight() {
//        return Resources.getSystem().getDisplayMetrics().heightPixels;
//    }
//
//    public static void setupProgressbar(ProgressBar pbLoading, Context context) {
//
////        sprogressbar.setImage(R.mipmap.logo_round_tp);
////        sprogressbar.drawCenterline(true);
////        sprogressbar.drawStartline(true);
////        sprogressbar.setProgress(50.0);
////        sprogressbar.setIndeterminate(true);
//        /**Dynamically*/
//        Rect bounds = pbLoading.getIndeterminateDrawable().getBounds();
//        pbLoading.setIndeterminateDrawable(new ChromeFloatingCirclesDrawable.Builder(context)
//                .colors(getProgressDrawableColors())
//                .build());
//        pbLoading.getIndeterminateDrawable().setBounds(bounds);
//
//    }
//
//    private static int[] getProgressDrawableColors() {
//        int[] colors = new int[4];
//        colors[0] = Core.getApp().getResources().getColor(R.color.sonali_beguni);
//        colors[1] = Core.getApp().getResources().getColor(R.color.yellow);
//        colors[2] = Core.getApp().getResources().getColor(R.color.golden);
//        colors[3] = Core.getApp().getResources().getColor(R.color.dark_blue);
//        return colors;
//    }
//
//}
