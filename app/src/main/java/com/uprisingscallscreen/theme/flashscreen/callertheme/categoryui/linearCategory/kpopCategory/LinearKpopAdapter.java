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
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.pesonal.adsdk.AppManage;
import com.uprisingscallscreen.theme.flashscreen.R;
import com.uprisingscallscreen.theme.flashscreen.callertheme.categoryui.CategoryShowActivity;
import com.uprisingscallscreen.theme.flashscreen.callertheme.categoryui.Images;
import com.squareup.picasso.Picasso;

import java.util.ArrayList;

public class LinearKpopAdapter extends RecyclerView.Adapter<LinearKpopAdapter.ViewHolder> {
    public static final String TAG = "LinearKpopAdapter";
    private Context context;
    private ArrayList<Images> arrayList;


    public LinearKpopAdapter(Context context, ArrayList<Images> arrayList) {
        this.context = context;
        this.arrayList = arrayList;
    }

    @NonNull
    @Override
    public LinearKpopAdapter.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.parse_item_linear, parent, false);
        AppManage.getInstance(context).loadInterstitialAd((Activity) context, ADMOB_I[0], FACEBOOK_I[0]);
        return new LinearKpopAdapter.ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull LinearKpopAdapter.ViewHolder holder, int position) {
        Picasso.get().load(arrayList.get(position).getUrl()).into(holder.imageView);
        final Images temp = arrayList.get(position);

        holder.imageView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                AppManage.getInstance(context).showInterstitialAd(context, new AppManage.MyCallback() {
                    public void callbackCall() {
                        Intent intent = new Intent(context, CategoryShowActivity.class);
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
        ImageView imageView,correct;
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
