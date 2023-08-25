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
import com.uprisingscallscreen.theme.flashscreen.callertheme.categoryui.linearCategory.kpopCategory.ViewPagerAdapter;

import java.util.ArrayList;

public class CategoryShowActivity extends AppCompatActivity {
    private static final String TAG = "CategoryShowActivity";
    private ArrayList<Images> lstImages = new ArrayList<>();
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
        AppManage.getInstance(CategoryShowActivity.this).loadInterstitialAd(this, ADMOB_I[0], FACEBOOK_I[0]);
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


        ViewPagerAdapter adapter = new ViewPagerAdapter(this, lstImages);
        viewPager.setAdapter(adapter);
        viewPager.setCurrentItem(clickedPosition);

        back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                AppManage.getInstance(CategoryShowActivity.this).showBackPressAd(CategoryShowActivity.this, new AppManage.MyCallback() {
                    @Override
                    public void callbackCall() {
                        CategoryShowActivity.super.onBackPressed();
                    }
                });
            }
        });


    }

    @Override
    protected void onResume() {
        super.onResume();
    }


    @Override
    public void onBackPressed() {
        AppManage.getInstance(CategoryShowActivity.this).showBackPressAd(CategoryShowActivity.this, new AppManage.MyCallback() {
            @Override
            public void callbackCall() {
                CategoryShowActivity.super.onBackPressed();
            }
        });
    }
}