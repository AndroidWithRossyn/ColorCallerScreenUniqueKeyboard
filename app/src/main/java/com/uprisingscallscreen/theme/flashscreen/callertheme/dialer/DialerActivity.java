package com.uprisingscallscreen.theme.flashscreen.callertheme.dialer;

import static com.pesonal.adsdk.AppManage.ADMOB_I;
import static com.pesonal.adsdk.AppManage.FACEBOOK_I;

import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;

import android.Manifest;
import android.annotation.SuppressLint;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.graphics.Color;
import android.graphics.drawable.Drawable;
import android.net.Uri;
import android.os.Bundle;
import android.telecom.TelecomManager;
import android.view.View;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Toast;

import com.pesonal.adsdk.AppManage;
import com.uprisingscallscreen.theme.flashscreen.R;


public class DialerActivity extends AppCompatActivity {
    Button callBtn;
    EditText phoneNumber;
    ImageView back_btn;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_dialer);
        back_btn = findViewById(R.id.back_btn);
        getWindow().addFlags(WindowManager.LayoutParams.FLAG_DRAWS_SYSTEM_BAR_BACKGROUNDS);
        getWindow().setStatusBarColor(Color.TRANSPARENT);
        Drawable background = getResources().getDrawable(R.drawable.status_gradient);
        getWindow().setBackgroundDrawable(background);
        AppManage.getInstance(DialerActivity.this).loadInterstitialAd(this, ADMOB_I[0], FACEBOOK_I[0]);
        back_btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                AppManage.getInstance(DialerActivity.this).showBackPressAd(DialerActivity.this, new AppManage.MyCallback() {
                    @Override
                    public void callbackCall() {
                        DialerActivity.super.onBackPressed();
                    }
                });
            }
        });
        initVariables();

        callBtn.setOnClickListener(v -> {
            String inputNumber = phoneNumber.getText().toString();

            if (!inputNumber.isEmpty()) {

                @SuppressLint("ServiceCast") TelecomManager telecomManager = (TelecomManager) getSystemService(Context.TELECOM_SERVICE);
                Uri uri = Uri.fromParts("tel", inputNumber, null);
                Bundle extras = new Bundle();
                extras.putBoolean(TelecomManager.EXTRA_START_CALL_WITH_SPEAKERPHONE, false);

                if (ActivityCompat.checkSelfPermission(this, Manifest.permission.CALL_PHONE) == PackageManager.PERMISSION_GRANTED) {

                    if (telecomManager.getDefaultDialerPackage().equals(getPackageName())) {
                        telecomManager.placeCall(uri, extras);
                    } else {
                        Uri phoneNumber = Uri.parse("tel:" + inputNumber);
                        Intent callIntent = new Intent(Intent.ACTION_CALL, phoneNumber);
                        callIntent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                        startActivity(callIntent);
                    }
                } else {
                    Toast.makeText(this, "Please allow permission", Toast.LENGTH_SHORT).show();
                }
            }
        });


    }


    private void initVariables() {
        callBtn = findViewById(R.id.callBtn);
        phoneNumber = findViewById(R.id.inputNumberET);
    }

    @Override
    protected void onResume() {
        super.onResume();
    }

    @Override
    public void onBackPressed() {
        AppManage.getInstance(DialerActivity.this).showBackPressAd(DialerActivity.this, new AppManage.MyCallback() {
            @Override
            public void callbackCall() {
                DialerActivity.super.onBackPressed();
            }
        });
    }
}