package com.uprisingscallscreen.theme.flashscreen.ui;

import static com.pesonal.adsdk.AppManage.ADMOB_I;
import static com.pesonal.adsdk.AppManage.FACEBOOK_I;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.graphics.Color;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.view.View;
import android.view.WindowManager;
import android.widget.TextView;

import com.pesonal.adsdk.AppManage;
import com.uprisingscallscreen.theme.flashscreen.MainActivity;
import com.uprisingscallscreen.theme.flashscreen.R;

public class PrivacyActivity extends AppCompatActivity {
    TextView accept;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_privacy_link);
        getWindow().addFlags(WindowManager.LayoutParams.FLAG_DRAWS_SYSTEM_BAR_BACKGROUNDS);
        getWindow().setStatusBarColor(Color.TRANSPARENT);
        Drawable background = getResources().getDrawable(R.drawable.status_gradient);
        getWindow().setBackgroundDrawable(background);
        AppManage.getInstance(PrivacyActivity.this).loadInterstitialAd(this, ADMOB_I[0], FACEBOOK_I[0]);
        accept = findViewById(R.id.accept);
        accept.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                AppManage.getInstance(PrivacyActivity.this).showInterstitialAd(PrivacyActivity.this, new AppManage.MyCallback() {
                    public void callbackCall() {
                        startActivity(new Intent(PrivacyActivity.this, MainActivity.class));
                    }
                }, AppManage.ADMOB,AppManage.app_mainClickCntSwAd);

            }
        });
    }

    @Override
    public void onBackPressed() {
        super.onBackPressed();
    }
}