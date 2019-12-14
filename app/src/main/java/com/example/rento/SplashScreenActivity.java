package com.example.rento;

import androidx.appcompat.app.AppCompatActivity;
import gr.net.maroulis.library.EasySplashScreen;

import android.graphics.Color;
import android.os.Bundle;
import android.view.View;

public class SplashScreenActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        EasySplashScreen  config = new EasySplashScreen(SplashScreenActivity.this)
            .withFullScreen()
            .withTargetActivity(MainActivity.class)
                .withSplashTimeOut(3000)
                .withBackgroundColor(Color.parseColor("#899b8d"))
                .withBeforeLogoText("RentO")
                .withAfterLogoText("Your Rental Solution")
                .withLogo(R.mipmap.ic_launcher_round);

        config.getBeforeLogoTextView().setTextColor(Color.BLACK);
        config.getAfterLogoTextView().setTextColor(Color.BLACK);
        View easySplashScreen = config.create();
        setContentView(easySplashScreen);


    }
}
