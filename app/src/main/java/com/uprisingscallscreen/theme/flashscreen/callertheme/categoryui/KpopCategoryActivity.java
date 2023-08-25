package com.uprisingscallscreen.theme.flashscreen.callertheme.categoryui;

import static com.pesonal.adsdk.AppManage.ADMOB_I;
import static com.pesonal.adsdk.AppManage.ADMOB_N;
import static com.pesonal.adsdk.AppManage.FACEBOOK_I;
import static com.pesonal.adsdk.AppManage.FACEBOOK_N;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.graphics.Color;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.view.ViewGroup;
import android.view.WindowManager;
import android.widget.LinearLayout;

import com.pesonal.adsdk.AppManage;
import com.uprisingscallscreen.theme.flashscreen.R;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.remoteconfig.FirebaseRemoteConfig;
import com.google.firebase.remoteconfig.FirebaseRemoteConfigSettings;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;
import java.util.Timer;
import java.util.TimerTask;

public class KpopCategoryActivity extends AppCompatActivity/* implements RecyclerInterface*/ {

    private RecyclerView recyclerView;
    private static final String TAG = "KpopCategoryActivity";
    private Timer fetchTimer;
    LinearLayout adsView0;
    ImagesAdapter trendingAdapter = null;
    ImagesVideoAdapter livetrendingAdapter = null;
    ImagesAdapter kpopAdapter = null;
    ImagesVideoAdapter livekpopAdapter = null;
    ImagesAdapter neonAdapter = null;
    ImagesVideoAdapter liveneonAdapter = null;
    ImagesAdapter loveAdapter = null;
    ImagesVideoAdapter liveloveAdapter = null;
    ImagesAdapter callAdapter = null;
    ImagesVideoAdapter livecallAdapter = null;
    ImagesAdapter animeAdapter = null;
    ImagesVideoAdapter liveanimeAdapter = null;
    ImagesAdapter soccerAdapter = null;
    ImagesVideoAdapter livesoccerAdapter = null;
    ImagesAdapter cuteAdapter = null;
    ImagesVideoAdapter livecuteAdapter = null;
    ImagesAdapter modernAdapter = null;
    ImagesVideoAdapter livemodernAdapter = null;
    ImagesAdapter natureAdapter = null;
    ImagesVideoAdapter livenatureAdapter = null;
    ImagesAdapter animalAdapter = null;
    ImagesVideoAdapter liveanimalAdapter = null;
    ImagesAdapter christmasAdapter = null;
    ImagesVideoAdapter livechristmasAdapter = null;
    private ArrayList<Images> imagesList = new ArrayList<>();
    private ArrayList<Images> trendingList = new ArrayList<>();
    private ArrayList<Images> christmaslist = new ArrayList<>();
    private ArrayList<Images> animalsList = new ArrayList<>();
    private ArrayList<Images> natureList = new ArrayList<>();
    private ArrayList<Images> modernList = new ArrayList<>();
    private ArrayList<Images> cutefunnyList = new ArrayList<>();
    private ArrayList<Images> soccerList = new ArrayList<>();
    private ArrayList<Images> neonList = new ArrayList<>();
    private ArrayList<Images> loveList = new ArrayList<>();
    private ArrayList<Images> callofdutyList = new ArrayList<>();
    private ArrayList<Images> animeList = new ArrayList<>();
    private ArrayList<Images> imagesListLiveKpop = new ArrayList<>();
    private ArrayList<Images> imagesListLiveChristmas = new ArrayList<>();
    private ArrayList<Images> imagesListLiveAnimals = new ArrayList<>();
    private ArrayList<Images> imagesListLiveNature = new ArrayList<>();
    private ArrayList<Images> imagesListLiveModern = new ArrayList<>();
    private ArrayList<Images> imagesListLiveCute = new ArrayList<>();
    private ArrayList<Images> imagesListLiveSoccer = new ArrayList<>();
    private ArrayList<Images> imagesListLiveNeon = new ArrayList<>();
    private ArrayList<Images> imagesListLiveLove = new ArrayList<>();
    private ArrayList<Images> imagesListLiveCall = new ArrayList<>();
    private ArrayList<Images> imagesListLiveAnime = new ArrayList<>();
    private ArrayList<Images> imagesListLiveTrending = new ArrayList<>();


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_neon_category);
        getWindow().addFlags(WindowManager.LayoutParams.FLAG_DRAWS_SYSTEM_BAR_BACKGROUNDS);
        getWindow().setStatusBarColor(Color.TRANSPARENT);
        Drawable background = getResources().getDrawable(R.drawable.status_gradient);
        getWindow().setBackgroundDrawable(background);
        FirebaseRemoteConfigSettings configSettings = new FirebaseRemoteConfigSettings.Builder()
                .setMinimumFetchIntervalInSeconds(3600)
                .build();
        FirebaseRemoteConfig remoteConfig = FirebaseRemoteConfig.getInstance();
        remoteConfig.setConfigSettingsAsync(configSettings);

        Map<String, Object> defaultValues = new HashMap<>();
        defaultValues.put("categories_parameter", "new");
        recyclerView = findViewById(R.id.neon_recycler);
        adsView0 = findViewById(R.id.adsView0);
        AppManage.getInstance(KpopCategoryActivity.this).loadInterstitialAd(this, ADMOB_I[0], FACEBOOK_I[0]);
        AppManage.getInstance(KpopCategoryActivity.this).showNativeBanner((ViewGroup) adsView0);
        GridLayoutManager layoutManager = new GridLayoutManager(this, 2);
        recyclerView.setLayoutManager(layoutManager);
        recyclerView.setHasFixedSize(true);
        recyclerView.setNestedScrollingEnabled(false);



        fetchTimer = new Timer();
        fetchTimer.schedule(new TimerTask() {
            @Override
            public void run() {
                remoteConfig.fetchAndActivate()
                        .addOnCompleteListener(new OnCompleteListener<Boolean>() {
                            @Override
                            public void onComplete(@NonNull Task<Boolean> task) {
                                if (task.isSuccessful()) {
                                    String jsonConfig = remoteConfig.getString("categories_parameter");
                                    try {
                                        JSONObject jsonObject = new JSONObject(jsonConfig);
                                        JSONObject categoriesObject = jsonObject.getJSONObject("Categories");

                                        processCategory(categoriesObject, "Trending", trendingList);
                                        processCategory(categoriesObject, "LiveTrending", imagesListLiveTrending);
                                        processCategory(categoriesObject, "Christmas", christmaslist);
                                        processCategory(categoriesObject, "LiveChristmas", imagesListLiveChristmas);
                                        processCategory(categoriesObject, "Kpop", imagesList);
                                        processCategory(categoriesObject, "LiveKpop", imagesListLiveKpop);
                                        processCategory(categoriesObject, "Neon", neonList);
                                        processCategory(categoriesObject, "LiveNeon", imagesListLiveNeon);
                                        processCategory(categoriesObject, "Love", loveList);
                                        processCategory(categoriesObject, "LiveLove", imagesListLiveLove);
                                        processCategory(categoriesObject, "CallOfDuty", callofdutyList);
                                        processCategory(categoriesObject, "LiveCallOfDuty", imagesListLiveCall);
                                        processCategory(categoriesObject, "Anime", animeList);
                                        processCategory(categoriesObject, "LiveAnime", imagesListLiveAnime);
                                        processCategory(categoriesObject, "Soccer", soccerList);
                                        processCategory(categoriesObject, "LiveSoccer", imagesListLiveSoccer);
                                        processCategory(categoriesObject, "CuteAndFunny", cutefunnyList);
                                        processCategory(categoriesObject, "LiveCuteAndFunny", imagesListLiveCute);
                                        processCategory(categoriesObject, "Modern", modernList);
                                        processCategory(categoriesObject, "LiveModern", imagesListLiveModern);
                                        processCategory(categoriesObject, "Nature", natureList);
                                        processCategory(categoriesObject, "LiveNature", imagesListLiveNature);
                                        processCategory(categoriesObject, "Animal", animalsList);
                                        processCategory(categoriesObject, "LiveAnimal", imagesListLiveAnimals);
                                        // Add more categories as needed

                                        // Notify adapter of data changes
                                        ArrayList<Images> categoryIdentifier = getIntent().getParcelableArrayListExtra("categoryIdentifier");
                                        if (categoryIdentifier != null) {
                                            for (Images image : categoryIdentifier) {
                                                String category1 = image.getUrl();

                                                switch (category1) {
                                                    case "Images":
                                                        ImagesAdapter kpopAdapter = new ImagesAdapter(KpopCategoryActivity.this, imagesList);
                                                        recyclerView.setAdapter(kpopAdapter);
                                                        break;
                                                    case "Trending":
                                                        ImagesAdapter trendingAdapter = new ImagesAdapter(KpopCategoryActivity.this, trendingList);
                                                        recyclerView.setAdapter(trendingAdapter);
                                                        break;
                                                    case "Animals":
                                                        ImagesAdapter animalAdapter = new ImagesAdapter(KpopCategoryActivity.this, animalsList);
                                                        recyclerView.setAdapter(animalAdapter);
                                                        break;
                                                    case "Christmas":
                                                        ImagesAdapter christmasAdapter = new ImagesAdapter(KpopCategoryActivity.this, christmaslist);
                                                        recyclerView.setAdapter(christmasAdapter);
                                                        break;
                                                    case "Nature":
                                                        ImagesAdapter natureAdapter = new ImagesAdapter(KpopCategoryActivity.this, natureList);
                                                        recyclerView.setAdapter(natureAdapter);
                                                        break;
                                                    case "Modern":
                                                        ImagesAdapter modernAdapter = new ImagesAdapter(KpopCategoryActivity.this, modernList);
                                                        recyclerView.setAdapter(modernAdapter);
                                                        break;
                                                    case "CuteFunny":
                                                        ImagesAdapter cuteAdapter = new ImagesAdapter(KpopCategoryActivity.this, cutefunnyList);
                                                        recyclerView.setAdapter(cuteAdapter);
                                                        break;
                                                    case "Soccer":
                                                        ImagesAdapter soccerAdapter = new ImagesAdapter(KpopCategoryActivity.this, soccerList);
                                                        recyclerView.setAdapter(soccerAdapter);
                                                        break;
                                                    case "Neon":
                                                        ImagesAdapter neonAdapter = new ImagesAdapter(KpopCategoryActivity.this, neonList);
                                                        recyclerView.setAdapter(neonAdapter);
                                                        break;
                                                    case "Love":
                                                        ImagesAdapter loveAdapter = new ImagesAdapter(KpopCategoryActivity.this, loveList);
                                                        recyclerView.setAdapter(loveAdapter);
                                                        break;
                                                    case "CallOfDuty":
                                                        ImagesAdapter callAdapter = new ImagesAdapter(KpopCategoryActivity.this, callofdutyList);
                                                        recyclerView.setAdapter(callAdapter);
                                                        break;
                                                    case "Anime":
                                                        ImagesAdapter animeAdapter = new ImagesAdapter(KpopCategoryActivity.this, animeList);
                                                        recyclerView.setAdapter(animeAdapter);
                                                        break;
                                                    case "LiveTrending":
                                                        ImagesVideoAdapter livetrendingAdapter = new ImagesVideoAdapter(KpopCategoryActivity.this, imagesListLiveTrending);
                                                        recyclerView.setAdapter(livetrendingAdapter);

                                                        break;
                                                    case "LiveKpop":
                                                        ImagesVideoAdapter livekpopAdapter = new ImagesVideoAdapter(KpopCategoryActivity.this, imagesListLiveKpop);
                                                        recyclerView.setAdapter(livekpopAdapter);
                                                        break;
                                                    case "LiveChristmas":
                                                        ImagesVideoAdapter livechristmasAdapter = new ImagesVideoAdapter(KpopCategoryActivity.this, imagesListLiveChristmas);
                                                        recyclerView.setAdapter(livechristmasAdapter);
                                                        break;
                                                    case "LiveAnimal":
                                                        ImagesVideoAdapter liveanimalAdapter = new ImagesVideoAdapter(KpopCategoryActivity.this, imagesListLiveAnimals);
                                                        recyclerView.setAdapter(liveanimalAdapter);
                                                        break;
                                                    case "LiveNature":
                                                        ImagesVideoAdapter livenatureAdapter = new ImagesVideoAdapter(KpopCategoryActivity.this, imagesListLiveNature);
                                                        recyclerView.setAdapter(livenatureAdapter);
                                                        break;
                                                    case "LiveModern":
                                                        ImagesVideoAdapter livemodernAdapter = new ImagesVideoAdapter(KpopCategoryActivity.this, imagesListLiveModern);
                                                        recyclerView.setAdapter(livemodernAdapter);
                                                        break;
                                                    case "LiveCuteFunny":
                                                        ImagesVideoAdapter livecuteAdapter = new ImagesVideoAdapter(KpopCategoryActivity.this, imagesListLiveCute);
                                                        recyclerView.setAdapter(livecuteAdapter);
                                                        break;
                                                    case "LiveSoccer":
                                                        ImagesVideoAdapter livesoccerAdapter = new ImagesVideoAdapter(KpopCategoryActivity.this, imagesListLiveSoccer);
                                                        recyclerView.setAdapter(livesoccerAdapter);
                                                        break;
                                                    case "LiveNeon":
                                                        ImagesVideoAdapter liveneonAdapter = new ImagesVideoAdapter(KpopCategoryActivity.this, imagesListLiveNeon);
                                                        recyclerView.setAdapter(liveneonAdapter);
                                                        break;
                                                    case "LiveLove":
                                                        ImagesVideoAdapter liveloveAdapter = new ImagesVideoAdapter(KpopCategoryActivity.this, imagesListLiveLove);
                                                        recyclerView.setAdapter(liveloveAdapter);
                                                        break;
                                                    case "LiveCallOfDuty":
                                                        ImagesVideoAdapter livecallAdapter = new ImagesVideoAdapter(KpopCategoryActivity.this, imagesListLiveCall);
                                                        recyclerView.setAdapter(livecallAdapter);
                                                        break;
                                                    case "LiveAnime":
                                                        ImagesVideoAdapter liveanimeAdapter = new ImagesVideoAdapter(KpopCategoryActivity.this, imagesListLiveAnime);
                                                        recyclerView.setAdapter(liveanimeAdapter);
                                                        break;

                                                }
                                            }
                                        }
                                        runOnUiThread(new Runnable() {
                                            @Override
                                            public void run() {
                                                if (trendingAdapter != null) {
                                                    trendingAdapter.notifyDataSetChanged();
                                                }
                                                if (livetrendingAdapter != null) {
                                                    livetrendingAdapter.notifyDataSetChanged();
                                                }
                                                if (kpopAdapter != null) {
                                                    kpopAdapter.notifyDataSetChanged();
                                                }
                                                if (livekpopAdapter != null) {
                                                    livekpopAdapter.notifyDataSetChanged();
                                                } if (neonAdapter != null) {
                                                    neonAdapter.notifyDataSetChanged();
                                                }if (liveneonAdapter != null) {
                                                    liveneonAdapter.notifyDataSetChanged();
                                                }if (loveAdapter != null) {
                                                    loveAdapter.notifyDataSetChanged();
                                                }if (liveloveAdapter != null) {
                                                    liveloveAdapter.notifyDataSetChanged();
                                                }if (callAdapter != null) {
                                                    callAdapter.notifyDataSetChanged();
                                                }if (livecallAdapter != null) {
                                                    callAdapter.notifyDataSetChanged();
                                                }if (animeAdapter != null) {
                                                    animeAdapter.notifyDataSetChanged();
                                                }if (liveanimeAdapter != null) {
                                                    animeAdapter.notifyDataSetChanged();
                                                }if (soccerAdapter != null) {
                                                    soccerAdapter.notifyDataSetChanged();
                                                }if (livesoccerAdapter != null) {
                                                    livesoccerAdapter.notifyDataSetChanged();
                                                }if (cuteAdapter != null) {
                                                    cuteAdapter.notifyDataSetChanged();
                                                }if (livecuteAdapter != null) {
                                                    livecuteAdapter.notifyDataSetChanged();
                                                }if (modernAdapter != null) {
                                                    modernAdapter.notifyDataSetChanged();
                                                }if (livemodernAdapter != null) {
                                                    livemodernAdapter.notifyDataSetChanged();
                                                }if (natureAdapter != null) {
                                                    natureAdapter.notifyDataSetChanged();
                                                }if (livenatureAdapter != null) {
                                                    livenatureAdapter.notifyDataSetChanged();
                                                }if (animalAdapter != null) {
                                                    animalAdapter.notifyDataSetChanged();
                                                }if (liveanimalAdapter != null) {
                                                    liveanimalAdapter.notifyDataSetChanged();
                                                }if (christmasAdapter != null) {
                                                    christmasAdapter.notifyDataSetChanged();
                                                }if (livechristmasAdapter != null) {
                                                    livechristmasAdapter.notifyDataSetChanged();
                                                }
                                            }
                                        });
                                    } catch (JSONException e) {
                                        e.printStackTrace();
                                    }
                                } else {

                                }
                            }
                        });
            }
        }, 0, 1000);


    }


    @Override
    public void onBackPressed() {
        AppManage.getInstance(KpopCategoryActivity.this).showBackPressAd(KpopCategoryActivity.this, new AppManage.MyCallback() {
            @Override
            public void callbackCall() {
                KpopCategoryActivity.super.onBackPressed();
            }
        });
    }

    @Override
    protected void onResume() {
        super.onResume();
    }


    private void processCategory(JSONObject categoriesObject, String categoryName, ArrayList<Images> categoryList) throws JSONException {
        if (categoriesObject.has(categoryName)) {
            JSONObject categoryObject = categoriesObject.getJSONObject(categoryName);
            JSONArray urlsArray = categoryObject.getJSONArray("urls");
            categoryList.clear();
            for (int i = 0; i < urlsArray.length(); i++) {
                String url = urlsArray.getString(i);
                Images images = new Images();
                images.setUrl(url);
                categoryList.add(images);
            }
        }
    }


    @Override
    public void onStop() {
        super.onStop();
        if (fetchTimer != null) {
            fetchTimer.cancel();
        }
    }
}
