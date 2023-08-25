package com.uprisingscallscreen.theme.flashscreen.callertheme.categoryui.linearCategory.kpopCategory;

import static com.pesonal.adsdk.AppManage.ADMOB_I;
import static com.pesonal.adsdk.AppManage.FACEBOOK_I;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.os.Parcelable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.pesonal.adsdk.AppManage;
import com.uprisingscallscreen.theme.flashscreen.R;
import com.uprisingscallscreen.theme.flashscreen.callertheme.categoryui.CategoryShowVideoActivity;
import com.uprisingscallscreen.theme.flashscreen.callertheme.categoryui.Images;
import com.bumptech.glide.Glide;

import java.util.ArrayList;

import pl.droidsonroids.gif.GifImageView;

public class LinearKpopVideoAdapter extends RecyclerView.Adapter<LinearKpopVideoAdapter.ViewHolder> {
    public static final String TAG = "LinearKpopVideoAdapter";
    private Context context;
    private ArrayList<Images> arrayList;


    public LinearKpopVideoAdapter(Context context, ArrayList<Images> arrayList) {
        this.context = context;
        this.arrayList = arrayList;
    }

    @NonNull
    @Override
    public LinearKpopVideoAdapter.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.parse_item_linear_video, parent, false);
        AppManage.getInstance(context).loadInterstitialAd((Activity) context, ADMOB_I[0], FACEBOOK_I[0]);
        return new LinearKpopVideoAdapter.ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull LinearKpopVideoAdapter.ViewHolder holder, int position) {
        Glide.with(context).load(arrayList.get(position).getUrl()).into(holder.imageView);
//        Picasso.get().load(arrayList.get(position).getUrl()).into(holder.imageView);
        final Images temp = arrayList.get(position);

        holder.imageView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                AppManage.getInstance(context).showInterstitialAd(context, new AppManage.MyCallback() {
                    public void callbackCall() {
                        Intent intent = new Intent(context, CategoryShowVideoActivity.class);
                        ArrayList<? extends Parcelable> parcelableList = new ArrayList<>(arrayList);
                        intent.putParcelableArrayListExtra("imageUrl", parcelableList);
                        intent.putExtra("position", position);
                        intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                        context.startActivity(intent);
                    }
                }, AppManage.ADMOB,AppManage.app_mainClickCntSwAd);



            }
        });
    }

    @Override
    public int getItemCount() {
        return arrayList.size()> 5 ? 5 : arrayList.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        GifImageView imageView;
        TextView tvApply;

        public ViewHolder(View itemView) {
            super(itemView);
            imageView = itemView.findViewById(R.id.imageView);
            tvApply = itemView.findViewById(R.id.tv_apply);
        }


    }
}
