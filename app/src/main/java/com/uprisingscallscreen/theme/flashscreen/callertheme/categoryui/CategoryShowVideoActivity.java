package com.uprisingscallscreen.theme.flashscreen.callertheme.categoryui;

import static com.pesonal.adsdk.AppManage.ADMOB_I;
import static com.pesonal.adsdk.AppManage.FACEBOOK_I;

import androidx.appcompat.app.AppCompatActivity;
import androidx.viewpager.widget.ViewPager;

import android.os.Bundle;
import android.os.Parcelable;
import android.util.Log;
import android.view.View;
import android.view.WindowManager;
import android.widget.ImageView;

import com.pesonal.adsdk.AppManage;
import com.uprisingscallscreen.theme.flashscreen.R;
import com.uprisingscallscreen.theme.flashscreen.callertheme.categoryui.linearCategory.kpopCategory.ViewPager1Adapter;

import java.util.ArrayList;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.Future;

public class CategoryShowVideoActivity extends AppCompatActivity {
    private static final String TAG = "CategoryShowActivity";
    private ArrayList<Images> lstImages=new ArrayList<>();
    ImageView back;
    private int clickedPosition;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_category_show);
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN,
                WindowManager.LayoutParams.FLAG_FULLSCREEN);
        ViewPager viewPager = findViewById(R.id.view_pager);
        back = findViewById(R.id.back);
        AppManage.getInstance(CategoryShowVideoActivity.this).loadInterstitialAd(this, ADMOB_I[0], FACEBOOK_I[0]);
        ArrayList<Parcelable> parcelableArrayList = getIntent().getParcelableArrayListExtra("imageUrl");
        if (parcelableArrayList != null) {
            for (Parcelable parcelable : parcelableArrayList) {
                if (parcelable instanceof Images) {
                    Images image = (Images) parcelable;
                    lstImages.add(image);
                }
            }
        }
        clickedPosition = getIntent().getIntExtra("position", 0);
        Log.println(Log.ASSERT, TAG, "lstImages: " + lstImages.size());


        ViewPager1Adapter adapter = new ViewPager1Adapter(this, lstImages);
        viewPager.setAdapter(adapter);
        viewPager.setCurrentItem(clickedPosition);
        ExecutorService service = Executors.newSingleThreadExecutor();
        Future<?> future = service.submit(() -> {
            back.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    AppManage.getInstance(CategoryShowVideoActivity.this).showBackPressAd(CategoryShowVideoActivity.this, new AppManage.MyCallback() {
                        @Override
                        public void callbackCall() {
                            CategoryShowVideoActivity.super.onBackPressed();
                        }
                    });

                }
            });
            service.shutdown();
        });
       /* viewPager.addOnPageChangeListener(new ViewPager.OnPageChangeListener() {
            @Override
            public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {

            }

            @Override
            public void onPageSelected(int position) {
                if (position != 0) {
                    if (position % 5 == 0) {
                        Constants.hitCounter = 2;
                        AdUtils.showInterstitialAd(CategoryShowVideoActivity.this, state_load -> {

                        });
                        // Show ads
                    }
                }
            }

            @Override
            public void onPageScrollStateChanged(int state) {

            }
        });*/
    }
    @Override
    protected void onResume() {
        super.onResume();

    }



    @Override
    public void onBackPressed() {
        AppManage.getInstance(CategoryShowVideoActivity.this).showBackPressAd(CategoryShowVideoActivity.this, new AppManage.MyCallback() {
            @Override
            public void callbackCall() {
                CategoryShowVideoActivity.super.onBackPressed();
            }
        });
    }
}