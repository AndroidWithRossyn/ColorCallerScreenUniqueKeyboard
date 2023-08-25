package com.uprisingscallscreen.theme.flashscreen.callertheme.dialer.services;


import android.annotation.SuppressLint;
import android.content.Intent;
import android.content.res.ColorStateList;
import android.os.Build;
import android.os.Bundle;
import android.telecom.Call;
import android.telecom.InCallService;
import android.util.Log;
import android.view.View;
import android.widget.Toast;

import com.uprisingscallscreen.theme.flashscreen.R;
import com.uprisingscallscreen.theme.flashscreen.callertheme.dialer.CallingAnimActivity;
import com.uprisingscallscreen.theme.flashscreen.callertheme.dialer.helpers.CallListHelper;
import com.uprisingscallscreen.theme.flashscreen.callertheme.dialer.helpers.CallManager;
import com.uprisingscallscreen.theme.flashscreen.callertheme.dialer.helpers.NotificationHelper;

public class CallServiceAnim extends InCallService {

    int call_state;
    String speakerBtnName, muteBtnName;

    @SuppressLint({"SetTextI18n", "UseCompatTextViewDrawableApis"})
    @Override
    public void onCallAdded(Call call) {
        super.onCallAdded(call);
        Log.d("Priya", "onCallAdded: Service");
        Log.d("Priya", "onCallAdded: Call Details: " + call.getDetails().toString());

        Bundle callExtras = call.getDetails().getExtras();
        if (callExtras != null && callExtras.containsKey(String.valueOf(Call.Details.PROPERTY_CONFERENCE))) {
            // Conference Call handling code

            CallListHelper.callList.clear();
            CallingAnimActivity.callerNameTV.setText("Conference Call");
            CallingAnimActivity.callerPhoneNumberTV.setText("");

            CallingAnimActivity.addCallBtn.setEnabled(true);
            CallingAnimActivity.addCallBtn.setClickable(true);
            CallingAnimActivity.addCallBtn.setCompoundDrawableTintList(ColorStateList.valueOf(getColor(R.color.my_theme)));
            CallingAnimActivity.addCallBtn.setTextColor(getColor(R.color.my_theme));

            CallingAnimActivity.holdBtn.setEnabled(true);
            CallingAnimActivity.holdBtn.setClickable(true);
            CallingAnimActivity.holdBtn.setCompoundDrawableTintList(ColorStateList.valueOf(getColor(R.color.my_theme)));
            CallingAnimActivity.holdBtn.setTextColor(getColor(R.color.my_theme));

            CallingAnimActivity.mergeCallBtn.setVisibility(View.GONE);
            String speakerBtnName, muteBtnName;

            if (CallingAnimActivity.isSpeakerOn) {
                speakerBtnName = "Speaker Off";
            } else {
                speakerBtnName = "Speaker On";
            }

            if (CallingAnimActivity.isMuted) {
                muteBtnName = "Unmute";
            } else {
                muteBtnName = "Mute";
            }

            NotificationHelper.createIngoingCallNotification(this, call, "12:00:4", speakerBtnName, muteBtnName);
        }

        CallListHelper.callList.add(call);
        CallManager.inCallService = this;
        CallManager.NUMBER_OF_CALLS = CallListHelper.callList.size();

        Log.d("Priya", "onCallAdded: NUM " + CallManager.NUMBER_OF_CALLS);

        call.registerCallback(CallManager.callback);

        int callState = 0;


        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.Q) {
            callState = call.getState();
        } else {
            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.S) {
                callState = call.getDetails().getState();
            } else {
                if (android.os.Build.VERSION.SDK_INT >= android.os.Build.VERSION_CODES.N) {
                    callState = call.getState();
                }
            }
        }
        CallManager.HP_CALL_STATE = callState;
        if (callState == Call.STATE_RINGING) {
            Intent intent = new Intent(this, CallingAnimActivity.class);
            intent.setFlags(Intent.FLAG_ACTIVITY_BROUGHT_TO_FRONT | Intent.FLAG_ACTIVITY_NEW_TASK);
            startActivity(intent);

            Toast.makeText(this, "Incoming call from " + call.getDetails().getHandle().getSchemeSpecificPart(), Toast.LENGTH_SHORT).show();
        } else if (callState == Call.STATE_CONNECTING || callState == Call.STATE_DIALING) {
            Intent intent = new Intent(this, CallingAnimActivity.class);
            intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
            startActivity(intent);

            Toast.makeText(this, "Dialing to " + call.getDetails().getHandle().getSchemeSpecificPart(), Toast.LENGTH_SHORT).show();
        }
    }


    @Override
    public void onCallRemoved(Call call) {
        Toast.makeText(this, "Call ended", Toast.LENGTH_SHORT).show();

        if (CallListHelper.callList.size() > 0) {

            NotificationHelper.createIngoingCallNotification(this, CallListHelper.callList.get(CallManager.NUMBER_OF_CALLS - 1), "00:12:45", CallingAnimActivity.speakerBtnName, CallingAnimActivity.muteBtnName);
            CallManager.HP_CALL_STATE = Call.STATE_ACTIVE;

            Log.d("Priya", "onCallRemoved:........ " + call.getDetails().getDisconnectCause().toString());
        } else {
            Log.d("Priya", "onCallRemoved:________ " + call.getDetails().getDisconnectCause().toString());
        }
    }
}