package com.uprisingscallscreen.theme.flashscreen.callertheme.categoryui;

import static com.pesonal.adsdk.AppManage.ADMOB_I;
import static com.pesonal.adsdk.AppManage.FACEBOOK_I;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.os.Parcelable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.pesonal.adsdk.AppManage;
import com.uprisingscallscreen.theme.flashscreen.R;
import com.squareup.picasso.Picasso;

import java.util.ArrayList;

public class ImagesAdapter extends RecyclerView.Adapter<ImagesAdapter.ViewHolder> {
    public static final String TAG = "ImagesAdapter";
    private Context context;
    private ArrayList<Images> arrayList;


    public ImagesAdapter(Context context, ArrayList<Images> arrayList) {
        this.context = context;
        this.arrayList = arrayList;
    }

    @NonNull
    @Override
    public ImagesAdapter.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.parse_item, parent, false);
        AppManage.getInstance(context).loadInterstitialAd((Activity) context, ADMOB_I[0], FACEBOOK_I[0]);
        return new ViewHolder(view);
    }
    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        Picasso.get().load(arrayList.get(position).getUrl()).into(holder.imageView);
        final Images temp = arrayList.get(position);

        holder.imageView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Activity activity = getActivityFromView(view);
                if (activity != null) {
                    AppManage.getInstance(activity).showInterstitialAd(activity, new AppManage.MyCallback() {
                        public void callbackCall() {
                            Intent intent = new Intent(activity, CategoryShowActivity.class);
                            ArrayList<? extends Parcelable> parcelableList = new ArrayList<>(arrayList);
                            intent.putParcelableArrayListExtra("imageUrl", parcelableList);
                            intent.putExtra("position", position);
                            intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                            activity.startActivity(intent);
                        }
                    }, AppManage.ADMOB,AppManage.app_mainClickCntSwAd);
                }

            }
        });


    }
    private Activity getActivityFromView(View view) {
        Context context = view.getContext();
        if (context instanceof Activity) {
            return (Activity) context;
        }
        return null;
    }


    @Override
    public int getItemCount() {
        return arrayList.size();
    }

    public static class ViewHolder extends RecyclerView.ViewHolder {
        ImageView imageView;
        TextView tvApply;

        public ViewHolder(View itemView/*, RecyclerInterface recyclerInterface*/) {
            super(itemView);
            imageView = itemView.findViewById(R.id.imageView);
            tvApply = itemView.findViewById(R.id.tv_apply);
            int dimension = (int) itemView.getContext().getResources().getDimension(R.dimen.pa);
            int i = (itemView.getContext().getResources().getDisplayMetrics().widthPixels - (dimension * 3)) / 2;
            RelativeLayout.LayoutParams layoutParams = new RelativeLayout.LayoutParams(i, (i * 640) / 360);
            layoutParams.addRule(14);
            layoutParams.setMargins(0, dimension, 0, 0);
            itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                }
            });

        }

    }

}
