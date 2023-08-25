package com.uprisingscallscreen.theme.flashscreen.ui;

import static com.pesonal.adsdk.AppManage.ADMOB_I;
import static com.pesonal.adsdk.AppManage.ADMOB_N;
import static com.pesonal.adsdk.AppManage.FACEBOOK_I;
import static com.pesonal.adsdk.AppManage.FACEBOOK_N;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.graphics.Color;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.view.View;
import android.view.ViewGroup;
import android.view.WindowManager;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.pesonal.adsdk.AppManage;
import com.uprisingscallscreen.theme.flashscreen.MainActivity;
import com.uprisingscallscreen.theme.flashscreen.R;

public class PrivacyPolicyActivity extends AppCompatActivity {
    TextView accept,privacy_link;
    LinearLayout adsView0;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_privacy_policy);
        getWindow().addFlags(WindowManager.LayoutParams.FLAG_DRAWS_SYSTEM_BAR_BACKGROUNDS);
        getWindow().setStatusBarColor(Color.TRANSPARENT);
        Drawable background = getResources().getDrawable(R.drawable.status_gradient);
        getWindow().setBackgroundDrawable(background);
        adsView0 = findViewById(R.id.adsView0);
        AppManage.getInstance(PrivacyPolicyActivity.this).loadInterstitialAd(this, ADMOB_I[0], FACEBOOK_I[0]);
        privacy_link = findViewById(R.id.privacy_link);
        AppManage.getInstance(PrivacyPolicyActivity.this).showNativeBanner((ViewGroup) adsView0, ADMOB_N[0], FACEBOOK_N[0]);
        accept = findViewById(R.id.accept);
        accept.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                AppManage.getInstance(PrivacyPolicyActivity.this).showInterstitialAd(PrivacyPolicyActivity.this, new AppManage.MyCallback() {
                    public void callbackCall() {
                        startActivity(new Intent(PrivacyPolicyActivity.this, MainActivity.class));
                    }
                },AppManage.ADMOB,AppManage.app_mainClickCntSwAd);


            }
        });
        privacy_link.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(PrivacyPolicyActivity.this, PrivacyActivity.class));
            }
        });
    }

}