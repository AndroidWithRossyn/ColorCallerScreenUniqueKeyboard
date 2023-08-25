package com.uprisingscallscreen.theme.flashscreen.callertheme.categoryui;

import static com.pesonal.adsdk.AppManage.ADMOB_I;
import static com.pesonal.adsdk.AppManage.ADMOB_N;
import static com.pesonal.adsdk.AppManage.FACEBOOK_I;
import static com.pesonal.adsdk.AppManage.FACEBOOK_N;

import androidx.appcompat.app.AppCompatActivity;

import android.animation.AnimatorSet;
import android.animation.ObjectAnimator;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.View;
import android.view.ViewGroup;
import android.view.WindowManager;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.pesonal.adsdk.AppManage;
import com.uprisingscallscreen.theme.flashscreen.MainActivity;
import com.uprisingscallscreen.theme.flashscreen.R;
import com.bumptech.glide.Glide;
import com.squareup.picasso.Picasso;

import pl.droidsonroids.gif.GifImageView;

public class PreviewActivity extends AppCompatActivity {
    TextView btn_set_theam;
    ImageView imageView, callAnswerBtn, callRejectBtn, back1;
    GifImageView gif_image_view_preview;
    LinearLayout leftArrowContainer, rightArrowContainer,adsView0;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_preview);
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN,
                WindowManager.LayoutParams.FLAG_FULLSCREEN);
        btn_set_theam = findViewById(R.id.btn_set_theam);
        adsView0=findViewById(R.id.adsView0);
        AppManage.getInstance(PreviewActivity.this).loadInterstitialAd(this, ADMOB_I[0], FACEBOOK_I[0]);
        AppManage.getInstance(PreviewActivity.this).showNativeBanner((ViewGroup) adsView0, ADMOB_N[0], FACEBOOK_N[0]);
        imageView = findViewById(R.id.image_view_preview);
        callAnswerBtn = findViewById(R.id.img_recive);
        callRejectBtn = findViewById(R.id.img_reject);
        back1 = findViewById(R.id.back1);
        gif_image_view_preview = findViewById(R.id.gif_image_view_preview);
        leftArrowContainer = findViewById(R.id.left_arrow_container);
        rightArrowContainer = findViewById(R.id.right_arrow_container);
// Create the animations for the left arrows
        ObjectAnimator leftArrow1Animator = ObjectAnimator.ofFloat(leftArrowContainer.getChildAt(0), "translationX", -40f);
        ObjectAnimator leftArrow2Animator = ObjectAnimator.ofFloat(leftArrowContainer.getChildAt(1), "translationX", -40f);


// Create the animations for the right arrows
        ObjectAnimator rightArrow1Animator = ObjectAnimator.ofFloat(rightArrowContainer.getChildAt(0), "translationX", 40f);
        ObjectAnimator rightArrow2Animator = ObjectAnimator.ofFloat(rightArrowContainer.getChildAt(1), "translationX", 40f);


        AnimatorSet leftArrowAnimatorSet = new AnimatorSet();
        leftArrowAnimatorSet.playSequentially(
                leftArrow1Animator,
                leftArrow2Animator

        );
        leftArrowAnimatorSet.setDuration(500);
        leftArrowAnimatorSet.setStartDelay(200); // Adjust the start delay as needed

// Create an AnimatorSet for the right arrows
        AnimatorSet rightArrowAnimatorSet = new AnimatorSet();
        rightArrowAnimatorSet.playSequentially(
                rightArrow1Animator,
                rightArrow2Animator

        );
        rightArrowAnimatorSet.setDuration(500);
        rightArrowAnimatorSet.setStartDelay(200);


        callAnswerBtn.setOnTouchListener(new Theme_Activity_Calling_Theme_Preview.SwipeTouchListener(leftArrowContainer, rightArrowContainer, callAnswerBtn));
        callRejectBtn.setOnTouchListener(new Theme_Activity_Calling_Theme_Preview.SwipeTouchListener(leftArrowContainer, rightArrowContainer, callRejectBtn));


        back1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(PreviewActivity.this, MainActivity.class));
            }
        });
        SharedPreferences sharedPreferences1 = getSharedPreferences("AnotherPrefs", Context.MODE_PRIVATE);

// Retrieve the stored values for receiveIcon and rejectIcon from SharedPreferences
        int receiveIcon = sharedPreferences1.getInt("receiveIcon", R.drawable.accept_button_one);
        int rejectIcon = sharedPreferences1.getInt("rejectIcon", R.drawable.decline_button_one);


        callAnswerBtn.setImageResource(receiveIcon);
        callRejectBtn.setImageResource(rejectIcon);
        SharedPreferences imageSharedPreferences = getSharedPreferences("image_theme", Context.MODE_PRIVATE);
        SharedPreferences gifSharedPreferences = getSharedPreferences("gif_theme", Context.MODE_PRIVATE);

        String imageUrl = imageSharedPreferences.getString("image_url1", null);
        String gifUrl = gifSharedPreferences.getString("image_url1", null);

        if (imageUrl != null && gifUrl != null) {
            long imageTimestamp = imageSharedPreferences.getLong("timestamp", 0);
            long gifTimestamp = gifSharedPreferences.getLong("timestamp", 0);

            if (imageTimestamp > gifTimestamp) {
                imageView.setVisibility(View.VISIBLE);
                gif_image_view_preview.setVisibility(View.GONE);
                Picasso.get().load(imageUrl).into(imageView);
            } else {
                imageView.setVisibility(View.GONE);
                gif_image_view_preview.setVisibility(View.VISIBLE);
                Glide.with(PreviewActivity.this).load(gifUrl).into(gif_image_view_preview);
            }
        } else if (imageUrl != null) {
            imageView.setVisibility(View.VISIBLE);
            gif_image_view_preview.setVisibility(View.GONE);
            Picasso.get().load(imageUrl).into(imageView);
        } else if (gifUrl != null) {
            imageView.setVisibility(View.GONE);
            gif_image_view_preview.setVisibility(View.VISIBLE);
            Glide.with(PreviewActivity.this).load(gifUrl).into(gif_image_view_preview);
        } else {
            imageView.setVisibility(View.GONE);
            gif_image_view_preview.setVisibility(View.GONE);
        }



        btn_set_theam.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                AppManage.getInstance(PreviewActivity.this).showInterstitialAd(PreviewActivity.this, new AppManage.MyCallback() {
                    public void callbackCall() {
                        SharedPreferences anotherActivitySharedPreferences = getSharedPreferences("AnotherActivityPrefs", Context.MODE_PRIVATE);
                        SharedPreferences.Editor anotherActivityEditor = anotherActivitySharedPreferences.edit();
                        anotherActivityEditor.putInt("receiveIcon", receiveIcon);
                        anotherActivityEditor.putInt("rejectIcon", rejectIcon);
                        anotherActivityEditor.apply();
                        Toast.makeText(PreviewActivity.this, "Set Successfully", Toast.LENGTH_SHORT).show();
                        startActivity(new Intent(PreviewActivity.this, MainActivity.class));
                    }
                }, AppManage.ADMOB,AppManage.app_mainClickCntSwAd);


            }
        });
    }
}