package com.uprisingscallscreen.theme.flashscreen.callertheme.ui;

import static com.pesonal.adsdk.AppManage.ADMOB_I;
import static com.pesonal.adsdk.AppManage.FACEBOOK_I;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.os.Bundle;
import android.util.Log;
import android.view.ViewGroup;
import android.widget.AbsListView;
import android.widget.LinearLayout;

import com.pesonal.adsdk.AppManage;
import com.uprisingscallscreen.theme.flashscreen.R;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

public class PopularActivity extends AppCompatActivity {
    private ArrayList<Hit> list;
    private RecyclerView popularRecycler;
    private GridLayoutManager manager;
    private RecyclerAdapter adapter;
    private boolean isScrolling = false;
    private int currentItems, scrolledOutItems, totalItems;
    private static final String URL = new Config().getAPI_URL();
    private int lastPage;
    LinearLayout adsView0;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_popular);
        adsView0=findViewById(R.id.adsView0);
        AppManage.getInstance(PopularActivity.this).loadInterstitialAd(this, ADMOB_I[0], FACEBOOK_I[0]);
        AppManage.getInstance(PopularActivity.this).showNativeBanner((ViewGroup) adsView0);
        list = new ArrayList<>();
        adapter = new RecyclerAdapter(PopularActivity.this,R.layout.grid_layout,R.layout.activity_popular,list);
        manager = new GridLayoutManager(PopularActivity.this,GridLayoutManager.chooseSize(2,2,0));
        popularRecycler = findViewById(R.id.popular_recycler);
        popularRecycler.setLayoutManager(manager);
        popularRecycler.setAdapter(adapter);
        fetchData(1);
        lastPage = 2;

        popularRecycler.addOnScrollListener(new RecyclerView.OnScrollListener() {
            @Override
            public void onScrollStateChanged(@NonNull RecyclerView recyclerView, int newState) {
                super.onScrollStateChanged(recyclerView, newState);
                if (newState == AbsListView.OnScrollListener.SCROLL_STATE_TOUCH_SCROLL){
                    isScrolling = true;
                }
            }

            @Override
            public void onScrolled(@NonNull RecyclerView recyclerView, int dx, int dy) {
                super.onScrolled(recyclerView, dx, dy);
                currentItems = manager.getChildCount();
                totalItems = manager.getItemCount();
                scrolledOutItems = manager.findFirstVisibleItemPosition();

                if (isScrolling && currentItems + scrolledOutItems == totalItems){
                    isScrolling = false;
                    fetchData(lastPage);
                    lastPage++;
                }
            }
        });
    }

    private void fetchData(int page) {
        StringRequest request = new StringRequest(URL + "&q=wallpaper&page=" + page, new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
                Log.d("CODE",response);
                GsonBuilder gsonBuilder = new GsonBuilder();
                Gson gson = gsonBuilder.create();
                ResultFormat result = gson.fromJson(response, ResultFormat.class);
                List<Hit> hit = result.getHits();
                for (int i = 0;i < hit.size(); i++) {
                    list.add(new Hit(hit.get(i).getLargeImageURL(), hit.get(i).getId(), hit.get(i).getTags(), hit.get(i).getWebformatURL(), hit.get(i).getPreviewURL()));
                }
                adapter.notifyDataSetChanged();

            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {}
        });

        RequestQueue queue = Volley.newRequestQueue(Objects.requireNonNull(PopularActivity.this));
        queue.add(request);
    }

    @Override
    public void onBackPressed() {
        AppManage.getInstance(PopularActivity.this).showCustomAppOpenAd(PopularActivity.this, new AppManage.MyCallback() {
            @Override
            public void callbackCall() {
                PopularActivity.super.onBackPressed();
            }
        });
    }
}