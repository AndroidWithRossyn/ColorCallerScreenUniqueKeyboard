package com.uprisingscallscreen.theme.flashscreen.callertheme.categoryui.linearCategory.kpopCategory;

import static com.pesonal.adsdk.AppManage.ADMOB_I;
import static com.pesonal.adsdk.AppManage.ADMOB_N;
import static com.pesonal.adsdk.AppManage.FACEBOOK_I;
import static com.pesonal.adsdk.AppManage.FACEBOOK_N;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;

import androidx.recyclerview.widget.RecyclerView;

import com.pesonal.adsdk.AppManage;
import com.uprisingscallscreen.theme.flashscreen.R;
import com.uprisingscallscreen.theme.flashscreen.callertheme.categoryui.PreviewActivity;
import com.uprisingscallscreen.theme.flashscreen.models.CallReceiveRejectCall;

import java.util.ArrayList;

public class IconAdapter extends RecyclerView.Adapter<IconAdapter.ViewHolder> {
    ArrayList<CallReceiveRejectCall> callReceiveRejectCalls;
    Context context;


    public IconAdapter(ArrayList<CallReceiveRejectCall> arrayList, Context context2) {
        this.callReceiveRejectCalls = arrayList;
        this.context = context2;

    }


    public ViewHolder onCreateViewHolder(ViewGroup viewGroup, int i) {
        return new ViewHolder(LayoutInflater.from(this.context).inflate(R.layout.icon_layout_design, viewGroup, false));
    }

    public void onBindViewHolder(ViewHolder holder, int position) {
        final CallReceiveRejectCall item = callReceiveRejectCalls.get(position);
        AppManage.getInstance(context).loadInterstitialAd((Activity) context, ADMOB_I[0], FACEBOOK_I[0]);
        holder.reccive_call.setImageResource(item.getReceive());
        holder.reject_call.setImageResource(item.getReject());

        if (position != 0){
            if (position%5 == 0){
                holder.adsView.setVisibility(View.VISIBLE);
                AppManage.getInstance((Activity) context).showNative((ViewGroup) holder.adsView, ADMOB_N[0], FACEBOOK_N[0]);
                // Show ads
            } else {
                holder.adsView.setVisibility(View.GONE);
                // hide ads
            }
        }
        holder.reccive_call.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                SharedPreferences sharedPreferences = context.getSharedPreferences("MyPrefs", Context.MODE_PRIVATE);
                SharedPreferences.Editor editor = sharedPreferences.edit();
                editor.putInt("receiveIcon", item.getReceive());
                editor.apply();
                SharedPreferences anotherSharedPreferences = context.getSharedPreferences("AnotherPrefs", Context.MODE_PRIVATE);
                SharedPreferences.Editor anotherEditor = anotherSharedPreferences.edit();
                anotherEditor.putInt("receiveIcon", item.getReceive());
                anotherEditor.apply();
            }
        });

        holder.reject_call.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                SharedPreferences sharedPreferences = context.getSharedPreferences("MyPrefs", Context.MODE_PRIVATE);
                SharedPreferences.Editor editor = sharedPreferences.edit();
                editor.putInt("rejectIcon", item.getReject());
                editor.apply();
                SharedPreferences anotherSharedPreferences = context.getSharedPreferences("AnotherPrefs", Context.MODE_PRIVATE);
                SharedPreferences.Editor anotherEditor = anotherSharedPreferences.edit();

                anotherEditor.putInt("rejectIcon", item.getReject());
                anotherEditor.apply();
            }
        });

        holder.rl_icon.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                AppManage.getInstance((Activity) context).showInterstitialAd((Activity) context, new AppManage.MyCallback() {
                    public void callbackCall() {
                        SharedPreferences anotherSharedPreferences = context.getSharedPreferences("AnotherPrefs", Context.MODE_PRIVATE);
                        SharedPreferences.Editor anotherEditor = anotherSharedPreferences.edit();
                        anotherEditor.putInt("rejectIcon", item.getReject());
                        anotherEditor.putInt("receiveIcon", item.getReceive());
                        anotherEditor.apply();
                        Intent intent = new Intent(context, PreviewActivity.class);
                        context.startActivity(intent);
                    }
                }, AppManage.ADMOB,AppManage.app_mainClickCntSwAd);
            }
        });
    }


    public int getItemCount() {
        return this.callReceiveRejectCalls.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        LinearLayout rl_icon, adsView;
        ImageView reccive_call;
        ImageView reject_call;

        public ViewHolder(View view) {
            super(view);
            this.reccive_call = (ImageView) view.findViewById(R.id.reccive_call);
            this.reject_call = (ImageView) view.findViewById(R.id.reject_call);
            this.rl_icon = (LinearLayout) view.findViewById(R.id.liner_main);
            this.adsView = (LinearLayout) view.findViewById(R.id.adsView);
        }
    }
}
