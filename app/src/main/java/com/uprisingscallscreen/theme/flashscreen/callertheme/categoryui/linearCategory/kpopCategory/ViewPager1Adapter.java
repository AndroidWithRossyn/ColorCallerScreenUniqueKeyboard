package com.uprisingscallscreen.theme.flashscreen.callertheme.categoryui.linearCategory.kpopCategory;

import static com.pesonal.adsdk.AppManage.ADMOB_I;
import static com.pesonal.adsdk.AppManage.FACEBOOK_I;

import android.animation.AnimatorSet;
import android.animation.ObjectAnimator;
import android.app.Activity;
import android.app.Dialog;
import android.content.Context;
import android.content.Intent;
import android.os.Parcelable;
import android.util.Base64;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.LinearInterpolator;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.viewpager.widget.PagerAdapter;

import com.pesonal.adsdk.AppManage;
import com.uprisingscallscreen.theme.flashscreen.R;
import com.uprisingscallscreen.theme.flashscreen.callertheme.categoryui.CategoryShowActivity;
import com.uprisingscallscreen.theme.flashscreen.callertheme.categoryui.Images;
import com.uprisingscallscreen.theme.flashscreen.callertheme.categoryui.linearCategory.Theme_GifActivity_Calling_Theme_Preview;
import com.uprisingscallscreen.theme.flashscreen.utils.GifDrawableUtil;
import com.uprisingscallscreen.theme.flashscreen.utils.SharedPreferenceManager;
import com.bumptech.glide.Glide;
import com.bumptech.glide.load.DataSource;
import com.bumptech.glide.load.engine.GlideException;
import com.bumptech.glide.request.RequestListener;
import com.bumptech.glide.request.target.Target;


import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.atomic.AtomicInteger;

import pl.droidsonroids.gif.GifDrawable;
import pl.droidsonroids.gif.GifImageView;

public class ViewPager1Adapter extends PagerAdapter {
    private static final String TAG = "ViewPager1Adapter";
    private Context context;
    private List<Images> lstImages;
    private LayoutInflater layoutInflater;
    GradientProgressBar progressBar;
    Dialog dialog;
    private SharedPreferenceManager preferenceManager;
    LinearLayout leftArrowContainer, rightArrowContainer;

    public ViewPager1Adapter(Context context, List<Images> lstImages) {
        this.context = context;
        this.lstImages = lstImages;
        this.layoutInflater = LayoutInflater.from(context);
        preferenceManager = new SharedPreferenceManager(context);
    }

    @Override
    public int getCount() {
        return lstImages.size();
    }

    @Override
    public boolean isViewFromObject(@NonNull View view, @NonNull Object object) {
        return view.equals(object);
    }

    @NonNull
    @Override
    public Object instantiateItem(@NonNull ViewGroup container, int position) {
        View view = layoutInflater.inflate(R.layout.item_page_video, container, false);
        GifImageView gifImageView = view.findViewById(R.id.gifimage_view);
        AppManage.getInstance(context).loadInterstitialAd((Activity) context, ADMOB_I[0], FACEBOOK_I[0]);
        TextView tv_apply = view.findViewById(R.id.tv_apply);
        ImageView receive = view.findViewById(R.id.receive);
        ImageView end = view.findViewById(R.id.end);
        leftArrowContainer = view.findViewById(R.id.left_arrow_container);
        rightArrowContainer = view.findViewById(R.id.right_arrow_container);
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


        receive.setOnTouchListener(new SwipeTouchListener(leftArrowContainer, rightArrowContainer, receive));
        end.setOnTouchListener(new SwipeTouchListener(leftArrowContainer, rightArrowContainer, end));

        String imageUrl = lstImages.get(position).getUrl();

        Glide.with(context)
                .as(File.class)
                .load(imageUrl)
                .addListener(new RequestListener<File>() {
                    @Override
                    public boolean onLoadFailed(@Nullable GlideException e, Object model, Target<File> target, boolean isFirstResource) {
                        // Handle GIF load failure
                        return false;
                    }

                    @Override
                    public boolean onResourceReady(File resource, Object model, Target<File> target, DataSource dataSource, boolean isFirstResource) {
                        try {
                            GifDrawable gifDrawable = new GifDrawable(resource);
                            saveImageToSharedPreferences(gifDrawable);
                            gifImageView.setImageDrawable(gifDrawable);
                            gifDrawable.start(); // Start the GIF animation
                        } catch (IOException e) {
                            // Handle GIF loading error
                        }
                        return false;
                    }
                })
                .submit();
        tv_apply.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                AppManage.getInstance(context).showInterstitialAd(context, new AppManage.MyCallback() {
                    public void callbackCall() {
                        boolean isDownloaded = preferenceManager.getImageUrl().equals(imageUrl);
                        if (isDownloaded) {
                            // Image is already downloaded, directly go to Theme_Activity_Calling_Theme_Preview
                            Intent intent = new Intent(context, Theme_GifActivity_Calling_Theme_Preview.class);
                            intent.putExtra("image_url", imageUrl);
                            context.startActivity(intent);
                        } else {
                            // Image is not downloaded, show the download dialog
                            downloadDialog(imageUrl);
                        }
                    }
                }, AppManage.ADMOB,AppManage.app_mainClickCntSwAd);
            }
        });

        container.addView(view);
        return view;
    }

    private void downloadDialog(final String imageUrl) {
        dialog = new Dialog(context);
        dialog.requestWindowFeature(1);
        dialog.setContentView(R.layout.custom_downloading_dialog);
        dialog.setCancelable(true);
        dialog.getWindow().setBackgroundDrawableResource(17170445);
        TextView downloading = dialog.findViewById(R.id.txt3);
        downloading.setText("Downloading...");
//        progressBar.setVisibility(View.VISIBLE);
        progressBar = dialog.findViewById(R.id.my_progressBar);
        progressBar.setProgress(0);
        progressBar.setMax(100);
        new Thread(new Runnable() {
            @Override
            public void run() {
                // Perform the image download here

                // Simulate download progress
                final AtomicInteger progress = new AtomicInteger(0);
                while (progress.get() <= 100) {
                    try {
                        Thread.sleep(500);
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    }

                    // Update the progress bar on the UI thread
                    progressBar.post(new Runnable() {
                        @Override
                        public void run() {
                            progressBar.setProgress(progress.get());
                            progressBar.invalidate();
                        }
                    });

                    // Increment the progress
                    progress.addAndGet(10);
                }

                // Store the image URL in SharedPreferences
                preferenceManager.setImageUrl(imageUrl);

                // Update the UI after the download is complete
                progressBar.post(new Runnable() {
                    @Override
                    public void run() {


                        progressBar.setVisibility(View.GONE); // Hide the progress bar

                        // Dismiss the dialog
                        dialog.dismiss();
                        Toast.makeText(context, "Downloaded Successfully", Toast.LENGTH_SHORT).show();

                        // Start the next activity and pass the image URL as an extra
                        Intent intent = new Intent(context, Theme_GifActivity_Calling_Theme_Preview.class);
                        intent.putExtra("image_url", imageUrl);
                        context.startActivity(intent);
                    }
                });
            }
        }).start();
        dialog.show();
    }


    private void saveImageToSharedPreferences(GifDrawable gifDrawable) {
        ByteArrayOutputStream baos = new ByteArrayOutputStream();

        try {
            GifDrawableUtil.writeToStream(gifDrawable, baos);
            byte[] gifBytes = baos.toByteArray();
            String encodedGif = Base64.encodeToString(gifBytes, Base64.DEFAULT);

            preferenceManager.setImageUrl(encodedGif);
        } catch (IOException e) {
            e.printStackTrace();
        }

        if (dialog != null && dialog.isShowing()) {
            dialog.dismiss();
        }
    }


    @Override
    public void destroyItem(@NonNull ViewGroup container, int position, @NonNull Object object) {
        container.removeView((View) object);
    }
    class SwipeTouchListener implements View.OnTouchListener {
        private LinearLayout leftArrowContainer;
        private LinearLayout rightArrowContainer;
        private ImageView leftArrow1;
        private ImageView leftArrow2;

        private ImageView rightArrow1;
        private ImageView rightArrow2;

        private boolean isSwiping = false;
        private float initialX;
        private ImageView buttonView;
        private ObjectAnimator fadeOutAnimator;
        private ObjectAnimator fadeInAnimator;

        SwipeTouchListener(LinearLayout leftArrowContainer, LinearLayout rightArrowContainer, ImageView buttonView) {
            this.leftArrowContainer = leftArrowContainer;
            this.rightArrowContainer = rightArrowContainer;
            this.buttonView = buttonView;

            leftArrow1 = leftArrowContainer.findViewById(R.id.left_arrow_1);
            leftArrow2 = leftArrowContainer.findViewById(R.id.left_arrow_2);
            rightArrow1 = rightArrowContainer.findViewById(R.id.right_arrow_1);
            rightArrow2 = rightArrowContainer.findViewById(R.id.right_arrow_2);

            fadeOutAnimator = ObjectAnimator.ofFloat(buttonView, "alpha", 1f, 0.5f);
            fadeOutAnimator.setDuration(200);
            fadeOutAnimator.setInterpolator(new LinearInterpolator());

            fadeInAnimator = ObjectAnimator.ofFloat(buttonView, "alpha", 0.5f, 1f);
            fadeInAnimator.setDuration(200);
            fadeInAnimator.setInterpolator(new LinearInterpolator());
        }

        @Override
        public boolean onTouch(View v, MotionEvent event) {
            switch (event.getAction()) {
                case MotionEvent.ACTION_DOWN:
                    isSwiping = false;
                    initialX = event.getX();
                    showArrows();
                    fadeOutAnimator.cancel();
                    fadeInAnimator.cancel();
                    buttonView.setAlpha(1f);
                    return true;
                case MotionEvent.ACTION_MOVE:
                    if (!isSwiping && Math.abs(event.getX() - initialX) > 100) {
                        isSwiping = true;
                    }
                    if (isSwiping) {
                        buttonView.setTranslationX(event.getX() - initialX);
                        fadeOutAnimator.start();
                    }
                    return true;
                case MotionEvent.ACTION_UP:
                    hideArrows();
                    if (isSwiping) {
                        float deltaX = event.getX() - initialX;
                        if (deltaX > 0) {
                            // Swiped right
                            // Handle receive call action here
                        } else {
                            // Swiped left
                            // Handle end call action here
                        }
                    }
                    buttonView.setTranslationX(0f);
                    fadeInAnimator.start();
                    return true;
            }
            return false;
        }

        private void showArrows() {
            leftArrow1.setVisibility(View.VISIBLE);
            leftArrow1.setTranslationX(-50);
            leftArrow1.setAlpha(0f);
            leftArrow1.animate().translationXBy(50).alpha(1f).setDuration(200).start();

            leftArrow2.setVisibility(View.VISIBLE);
            leftArrow2.setTranslationX(-50);
            leftArrow2.setAlpha(0f);
            leftArrow2.animate().translationXBy(50).alpha(1f).setStartDelay(100).setDuration(200).start();


            rightArrow1.setVisibility(View.VISIBLE);
            rightArrow1.setTranslationX(50);
            rightArrow1.setAlpha(0f);
            rightArrow1.animate().translationXBy(-50).alpha(1f).setDuration(200).start();

            rightArrow2.setVisibility(View.VISIBLE);
            rightArrow2.setTranslationX(50);
            rightArrow2.setAlpha(0f);
            rightArrow2.animate().translationXBy(-50).alpha(1f).setStartDelay(100).setDuration(200).start();


        }


        private void hideArrows() {
            leftArrow1.animate().translationXBy(50).alpha(0f).setDuration(200).start();
            leftArrow2.animate().translationXBy(50).alpha(0f).setDuration(200).start();
            rightArrow1.animate().translationXBy(-50).alpha(0f).setDuration(200).start();
            rightArrow2.animate().translationXBy(-50).alpha(0f).setDuration(200).start();
        }
    }
}