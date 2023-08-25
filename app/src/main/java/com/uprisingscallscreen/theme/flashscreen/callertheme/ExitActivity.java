package com.uprisingscallscreen.theme.flashscreen.callertheme;

import static com.pesonal.adsdk.AppManage.ADMOB_N;
import static com.pesonal.adsdk.AppManage.FACEBOOK_N;

import androidx.appcompat.app.AppCompatActivity;

import android.graphics.Color;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.view.View;
import android.view.ViewGroup;
import android.view.WindowManager;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.pesonal.adsdk.AppManage;
import com.uprisingscallscreen.theme.flashscreen.R;

public class ExitActivity extends AppCompatActivity {
    LinearLayout adsView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_exit);
        getWindow().addFlags(WindowManager.LayoutParams.FLAG_DRAWS_SYSTEM_BAR_BACKGROUNDS);
        getWindow().setStatusBarColor(Color.TRANSPARENT);
        Drawable background = getResources().getDrawable(R.drawable.status_gradient);
        getWindow().setBackgroundDrawable(background);
        TextView cancel_btn = findViewById(R.id.cancel_btn);
//        TextView rate_btn = findViewById(R.id.rate_btn);
        TextView yes_btn = findViewById(R.id.yes_btn);
        adsView = findViewById(R.id.adsView);
        AppManage.getInstance(ExitActivity.this).showNative((ViewGroup) adsView, ADMOB_N[0], FACEBOOK_N[0]);
        cancel_btn.setOnClickListener(v -> ExitActivity.super.onBackPressed());
       /* rate_btn.setOnClickListener(v -> {
            try {
                startActivity(new Intent(Intent.ACTION_VIEW,
                        Uri.parse("market://details?id=" + getPackageName())));
            } catch (android.content.ActivityNotFoundException e) {
                startActivity(new Intent(Intent.ACTION_VIEW,
                        Uri.parse("http://play.google.com/store/apps/details?id=" + getPackageName())));
            }
        });*/
        yes_btn.setOnClickListener(v -> {
            finishAffinity();
            System.exit(0);
        });
        cancel_btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                ExitActivity.super.onBackPressed();
            }
        });
    }
    @Override
    protected void onResume() {
        super.onResume();

    }
}