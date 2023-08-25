package com.uprisingscallscreen.theme.flashscreen.callertheme.OS.fragment;

import android.graphics.Color;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.fragment.app.Fragment;

import com.uprisingscallscreen.theme.flashscreen.callertheme.OS.ActivityPerM;
import com.uprisingscallscreen.theme.flashscreen.R;


public class FragmentPer extends Fragment implements View.OnClickListener {
    private static final String ARG_POSITION = "position";
    private int position;

    public static FragmentPer newInstance(int i) {
        FragmentPer fragmentPer = new FragmentPer();
        Bundle bundle = new Bundle();
        bundle.putInt(ARG_POSITION, i);
        fragmentPer.setArguments(bundle);
        return fragmentPer;
    }

    @Override
    public void onCreate(Bundle bundle) {
        super.onCreate(bundle);
        if (getArguments() != null) {
            this.position = getArguments().getInt(ARG_POSITION);
        }
    }

    @Override
    public View onCreateView(LayoutInflater layoutInflater, ViewGroup viewGroup, Bundle bundle) {
        return layoutInflater.inflate(R.layout.fragment_per, viewGroup, false);
    }

    @Override
    public void onViewCreated(View view, Bundle bundle) {
        super.onViewCreated(view, bundle);
        ImageView imageView = (ImageView) view.findViewById(R.id.im_title);
        TextView textView = (TextView) view.findViewById(R.id.tv_title);
        TextView textView2 = (TextView) view.findViewById(R.id.tv_content);
        TextView textView3 = (TextView) view.findViewById(R.id.tv_action);
        textView3.setOnClickListener(this);
        int i = this.position;
        if (i == 0) {
            imageView.setImageResource(R.drawable.default_dialer_perm);
            imageView.clearColorFilter();
            textView.setText(getString(R.string.default_dialer));
            textView2.setText(getString(R.string.title_default));
            textView3.setText(getString(R.string.go_to_grant));
        } else if (i == 1) {
            imageView.setImageResource(R.drawable.ic_call_pad);
            imageView.clearColorFilter();
            textView.setText(getString(R.string.per_call_log));
            textView2.setText(getString(R.string.per_call_log_c));
            textView3.setText(getString(R.string.grant));
        } else if (i != 2) {
            imageView.setImageResource(R.drawable.storage_perm);
            imageView.clearColorFilter();
            textView.setText(getString(R.string.per_sd));
            textView2.setText(getString(R.string.per_sd_c));
            textView3.setText(getString(R.string.grant));
        } else {
            imageView.setImageResource(R.drawable.ic_contact);
            imageView.setColorFilter(Color.parseColor("#616161"));
            textView.setText(getString(R.string.per_contact));
            textView2.setText(getString(R.string.per_contact_c));
            textView3.setText(getString(R.string.grant));
        }
    }

    @Override
    public void onClick(View view) {
        ActivityPerM activityPerM = (ActivityPerM) getActivity();
        if (activityPerM != null) {
            activityPerM.action(this.position);
        }
    }
}
