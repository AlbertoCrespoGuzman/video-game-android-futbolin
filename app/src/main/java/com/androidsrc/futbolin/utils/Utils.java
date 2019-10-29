package com.androidsrc.futbolin.utils;

import android.animation.ObjectAnimator;
import android.content.Context;
import android.content.res.Resources;
import android.graphics.PorterDuff;
import android.graphics.drawable.Drawable;
import android.graphics.drawable.LayerDrawable;
import android.os.Bundle;
import android.util.DisplayMetrics;
import android.util.Log;
import android.view.animation.DecelerateInterpolator;
import android.widget.ProgressBar;

import com.androidsrc.futbolin.R;

import java.util.Locale;

import androidx.core.content.ContextCompat;

public class Utils {
    public static Context setLanguage(Context context, String language_code){
        Resources res = context.getResources();
// Change locale settings in the app.
        DisplayMetrics dm = res.getDisplayMetrics();
        android.content.res.Configuration conf = res.getConfiguration();
        if(language_code != null ){
            Log.e("Utils","set Language  Bundle NOT null = " + language_code);
            conf.setLocale(new Locale(language_code)); // API 17+ only.
        }else{
            conf.setLocale(new Locale("es")); // API 17+ only.
            Log.e("Utils","set Language NUll --> forcing ES");
        }

// Use conf.locale = new Locale(...) if targeting lower versions
        res.updateConfiguration(conf, dm);
        return context;
    }
    public static Context setLanguage(Context context, Bundle language_code){
        Resources res = context.getResources();
// Change locale settings in the app.
        DisplayMetrics dm = res.getDisplayMetrics();
        android.content.res.Configuration conf = res.getConfiguration();
        if(language_code != null && language_code.getString("language") != null && language_code.getString("language").length() == 2){
            Log.e("Utils","set Language  Bundle NOT null = " + language_code.getString("language"));
            conf.setLocale(new Locale(language_code.getString("language").toLowerCase())); // API 17+ only.
        }else{
            conf.setLocale(new Locale("es")); // API 17+ only.
            Log.e("Utils","set Language NUll --> forcing ES");
        }

// Use conf.locale = new Locale(...) if targeting lower versions
        res.updateConfiguration(conf, dm);
        return context;
    }

    public static ProgressBar setStyleForEnergyProgressBar(ProgressBar progressBar, int progress, Context context){


        progressBar.setProgress(0);
        if( progress <= 40){
            LayerDrawable progressBarDrawable = (LayerDrawable) progressBar.getProgressDrawable();
            Drawable backgroundDrawable = progressBarDrawable.getDrawable(0);
            Drawable progressDrawable = progressBarDrawable.getDrawable(1);

            backgroundDrawable.setColorFilter(ContextCompat.getColor(context, R.color.gray_progress_bar), PorterDuff.Mode.SRC_IN);
            progressDrawable.setColorFilter(ContextCompat.getColor(context, android.R.color.holo_red_light), PorterDuff.Mode.SRC_IN);

            if(progressBarDrawable.getNumberOfLayers() > 2){
                Drawable progressDrawable2 = progressBarDrawable.getDrawable(2);
                progressDrawable2.setColorFilter(ContextCompat.getColor(context, android.R.color.holo_red_light), PorterDuff.Mode.SRC_IN);
            }
        }else if(progress < 90){
            LayerDrawable progressBarDrawable = (LayerDrawable) progressBar.getProgressDrawable();
            Drawable backgroundDrawable = progressBarDrawable.getDrawable(0);
            Drawable progressDrawable = progressBarDrawable.getDrawable(1);

            backgroundDrawable.setColorFilter(ContextCompat.getColor(context, R.color.gray_progress_bar), PorterDuff.Mode.SRC_IN);
            progressDrawable.setColorFilter(ContextCompat.getColor(context, R.color.trophy_first_color), PorterDuff.Mode.SRC_IN);

            if(progressBarDrawable.getNumberOfLayers() > 2){
                Drawable progressDrawable2 = progressBarDrawable.getDrawable(2);
                progressDrawable2.setColorFilter(ContextCompat.getColor(context, R.color.trophy_first_color), PorterDuff.Mode.SRC_IN);
            }
        }else{
            LayerDrawable progressBarDrawable = (LayerDrawable) progressBar.getProgressDrawable();
            Drawable backgroundDrawable = progressBarDrawable.getDrawable(0);
            Drawable progressDrawable = progressBarDrawable.getDrawable(1);

            backgroundDrawable.setColorFilter(ContextCompat.getColor(context, R.color.gray_progress_bar), PorterDuff.Mode.SRC_IN);
            progressDrawable.setColorFilter(ContextCompat.getColor(context, android.R.color.holo_green_dark), PorterDuff.Mode.SRC_IN);

            if(progressBarDrawable.getNumberOfLayers() > 2){
                Drawable progressDrawable2 = progressBarDrawable.getDrawable(2);
                progressDrawable2.setColorFilter(ContextCompat.getColor(context, android.R.color.holo_green_dark), PorterDuff.Mode.SRC_IN);
            }
        }
        ObjectAnimator animation = ObjectAnimator.ofInt(progressBar, "progress", progress);
        animation.setDuration(1000); // 0.5 second
        animation.setInterpolator(new DecelerateInterpolator());
        animation.start();


        return progressBar;
    }
}
