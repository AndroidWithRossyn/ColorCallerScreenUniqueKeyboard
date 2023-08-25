package com.uprisingscallscreen.theme.flashscreen.callertheme.dialer.receivers;

import android.annotation.SuppressLint;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.res.ColorStateList;
import android.os.Bundle;

import com.uprisingscallscreen.theme.flashscreen.R;
import com.uprisingscallscreen.theme.flashscreen.callertheme.dialer.CallingAnimActivity;
import com.uprisingscallscreen.theme.flashscreen.callertheme.dialer.helpers.CallListHelper;
import com.uprisingscallscreen.theme.flashscreen.callertheme.dialer.helpers.CallManager;
import com.uprisingscallscreen.theme.flashscreen.callertheme.dialer.helpers.NotificationHelper;

public class ActionReceiver extends BroadcastReceiver {

    @SuppressLint({"UseCompatTextViewDrawableApis", "SetTextI18n"})
    @Override
    public void onReceive(Context context, Intent intent) {

        Bundle intentExtras = intent.getExtras();

        for (String k: intentExtras.keySet()) {

        }

        if (intentExtras.containsKey("pickUpCall")){

            String action = intent.getStringExtra("pickUpCall");

            if(action.equalsIgnoreCase("YES")){
                CallManager.answerCall(CallListHelper.callList.get(CallManager.NUMBER_OF_CALLS - 1));
            }
            else if(action.equalsIgnoreCase("NO")){
                CallManager.hangUpCall(CallListHelper.callList.get(CallManager.NUMBER_OF_CALLS - 1));
            }
        }

        if (intentExtras.containsKey("cancelCall")){

            String action = intent.getStringExtra("cancelCall");

            if(action.equalsIgnoreCase("YES"))
                CallManager.hangUpCall(CallListHelper.callList.get(CallManager.NUMBER_OF_CALLS - 1));
        }

        if (intentExtras.containsKey("endCall")){

            String action = intent.getStringExtra("endCall");

            if(action.equalsIgnoreCase("YES"))
                CallManager.hangUpCall(CallListHelper.callList.get(CallManager.NUMBER_OF_CALLS - 1));
        }


        if (intentExtras.containsKey("speakerCall")){

            String action = intent.getStringExtra("speakerCall");

            String muteBtnName;

            if (CallingAnimActivity.isMuted){
                muteBtnName = "Unmute";
            }
            else {
                muteBtnName = "Mute";
            }

            if(action.equalsIgnoreCase("YES")){

                if (CallingAnimActivity.isSpeakerOn){
                    CallManager.speakerCall(false);
                    CallingAnimActivity.speakerBtn.setCompoundDrawableTintList(ColorStateList.valueOf(context.getColor(R.color.my_theme)));
                    CallingAnimActivity.speakerBtn.setTextColor(context.getColor(R.color.my_theme));
                    CallingAnimActivity.isSpeakerOn = false;

                    NotificationHelper.createIngoingCallNotification(context, CallListHelper.callList.get(CallManager.NUMBER_OF_CALLS - 1), "01:12:00", "Speaker On", muteBtnName);
                }
                else{
                    CallManager.speakerCall(true);
                    CallingAnimActivity.speakerBtn.setCompoundDrawableTintList(ColorStateList.valueOf(context.getColor(R.color.feature_on_color)));
                    CallingAnimActivity.speakerBtn.setTextColor(context.getColor(R.color.feature_on_color));
                    CallingAnimActivity.isSpeakerOn = true;

                    NotificationHelper.createIngoingCallNotification(context, CallListHelper.callList.get(CallManager.NUMBER_OF_CALLS - 1), "01:12:00", "Speaker Off", muteBtnName);
                }
            }
        }


        if (intentExtras.containsKey("muteCall")){

            String action = intent.getStringExtra("muteCall");

            String speakerBtnName;

            if (CallingAnimActivity.isSpeakerOn){
                speakerBtnName = "Speaker Off";
            }
            else {
                speakerBtnName = "Speaker On";
            }

            if(action.equalsIgnoreCase("YES")){

                if (CallingAnimActivity.isMuted){
                    CallManager.muteCall(false);
                    CallingAnimActivity.muteBtn.setCompoundDrawableTintList(ColorStateList.valueOf(context.getColor(R.color.my_theme)));
                    CallingAnimActivity.muteBtn.setTextColor(context.getColor(R.color.my_theme));
                    CallingAnimActivity.muteBtn.setText("Mute");
                    CallingAnimActivity.isMuted = false;

                    NotificationHelper.createIngoingCallNotification(context, CallListHelper.callList.get(CallManager.NUMBER_OF_CALLS - 1), "01:12:00", speakerBtnName, "Mute");
                }
                else{
                    CallManager.muteCall(true);
                    CallingAnimActivity.muteBtn.setCompoundDrawableTintList(ColorStateList.valueOf(context.getColor(R.color.feature_on_color)));
                    CallingAnimActivity.muteBtn.setTextColor(context.getColor(R.color.feature_on_color));
                    CallingAnimActivity.muteBtn.setText("Unmute");
                    CallingAnimActivity.isMuted = true;

                    NotificationHelper.createIngoingCallNotification(context, CallListHelper.callList.get(CallManager.NUMBER_OF_CALLS - 1), "01:12:00", speakerBtnName, "Unmute");
                }
            }
        }
    }
}
