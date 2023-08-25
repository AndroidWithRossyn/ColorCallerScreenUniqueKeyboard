package com.uprisingscallscreen.theme.flashscreen.fragments;

import android.os.Bundle;

import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.uprisingscallscreen.theme.flashscreen.R;
import com.uprisingscallscreen.theme.flashscreen.callertheme.categoryui.linearCategory.kpopCategory.IconAdapter;
import com.uprisingscallscreen.theme.flashscreen.models.CallReceiveRejectCall;

import java.util.ArrayList;

public class IconFragment extends Fragment {
    RecyclerView icon_recycler;
    IconAdapter adapterCallingiconpreview;
    ArrayList<CallReceiveRejectCall> callReceiveRejectCalls = new ArrayList<>();



    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view= inflater.inflate(R.layout.fragment_icon, container, false);
        icon_recycler=view.findViewById(R.id.icon_recycler);

        this.callReceiveRejectCalls.add(new CallReceiveRejectCall(R.drawable.accept_button_one, R.drawable.decline_button_one));
        this.callReceiveRejectCalls.add(new CallReceiveRejectCall(R.drawable.accept_button_two, R.drawable.decline_button_two));
        this.callReceiveRejectCalls.add(new CallReceiveRejectCall(R.drawable.accept_button_three, R.drawable.decline_button_three));
        this.callReceiveRejectCalls.add(new CallReceiveRejectCall(R.drawable.accept_button_four, R.drawable.decline_button_four));
        this.callReceiveRejectCalls.add(new CallReceiveRejectCall(R.drawable.accept_button_five, R.drawable.decline_button_five));
        this.callReceiveRejectCalls.add(new CallReceiveRejectCall(R.drawable.accept_button_six, R.drawable.decline_button_six));
        this.callReceiveRejectCalls.add(new CallReceiveRejectCall(R.drawable.accept_button_seven, R.drawable.decline_button_seven));
        this.callReceiveRejectCalls.add(new CallReceiveRejectCall(R.drawable.accept_button_eight, R.drawable.decline_button_eight));
        this.callReceiveRejectCalls.add(new CallReceiveRejectCall(R.drawable.accept_button_nine, R.drawable.decline_button_nine));
        this.callReceiveRejectCalls.add(new CallReceiveRejectCall(R.drawable.accept_button_ten, R.drawable.decline_button_ten));
        this.callReceiveRejectCalls.add(new CallReceiveRejectCall(R.drawable.accept_button_eleven, R.drawable.decline_button_eleven));
        this.callReceiveRejectCalls.add(new CallReceiveRejectCall(R.drawable.accept_button_twelve, R.drawable.decline_button_twelve));
        this.callReceiveRejectCalls.add(new CallReceiveRejectCall(R.drawable.accept_button_thirteen, R.drawable.decline_button_thirteen));
        this.callReceiveRejectCalls.add(new CallReceiveRejectCall(R.drawable.accept_button_fourteen, R.drawable.decline_button_fourteen));
        this.callReceiveRejectCalls.add(new CallReceiveRejectCall(R.drawable.accept_button_fiftyteen, R.drawable.decline_button_fiftyteen));
        this.icon_recycler.setLayoutManager(new LinearLayoutManager(requireActivity()));
        IconAdapter adapter_calling_icon_preview = new IconAdapter(this.callReceiveRejectCalls, requireActivity());
        this.adapterCallingiconpreview = adapter_calling_icon_preview;
        this.icon_recycler.setAdapter(adapter_calling_icon_preview);
        return view;
    }
}