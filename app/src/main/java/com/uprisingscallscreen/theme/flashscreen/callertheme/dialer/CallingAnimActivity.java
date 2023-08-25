package com.uprisingscallscreen.theme.flashscreen.callertheme.dialer;


import androidx.appcompat.app.AppCompatActivity;

import android.animation.AnimatorSet;
import android.animation.ObjectAnimator;
import android.annotation.SuppressLint;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.content.SharedPreferences;
import android.content.res.ColorStateList;
import android.media.Ringtone;
import android.media.RingtoneManager;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.os.Handler;
import android.telecom.Call;
import android.util.Log;
import android.view.MotionEvent;
import android.view.View;
import android.view.WindowManager;
import android.view.animation.LinearInterpolator;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.uprisingscallscreen.theme.flashscreen.R;

import com.uprisingscallscreen.theme.flashscreen.callertheme.dialer.helpers.CallListHelper;
import com.uprisingscallscreen.theme.flashscreen.callertheme.dialer.helpers.CallManager;
import com.uprisingscallscreen.theme.flashscreen.callertheme.dialer.helpers.ContactsHelper;
import com.uprisingscallscreen.theme.flashscreen.callertheme.dialer.helpers.NotificationHelper;
import com.bumptech.glide.Glide;
import com.google.android.material.bottomsheet.BottomSheetBehavior;
import com.google.android.material.bottomsheet.BottomSheetDialog;
import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.squareup.picasso.Picasso;

import java.util.Locale;
import java.util.concurrent.TimeUnit;

import pl.droidsonroids.gif.GifImageView;

public class CallingAnimActivity extends AppCompatActivity {

    FloatingActionButton endCallBtn;
    private Ringtone defaultDialerRingtone;
    @SuppressLint("StaticFieldLeak")
    public static Button muteBtn, keypadBtn, speakerBtn, holdBtn, recordBtn, addCallBtn, mergeCallBtn;
    //    public static ImageView imageview_normal;
    ImageView callAnswerBtn, callRejectBtn;
    private static final int PERMISSION_REQUEST_CODE = 1;


    Button btn0, btn01, btn02, btn03, btn04, btn05, btn06, btn07, btn08, btn09, btnStar, btnHash;

    BottomSheetDialog keypadDialog;
    String keypadDialogTextViewText = "";

    @SuppressLint("StaticFieldLeak")
    public static TextView callerNameTV, callerPhoneNumberTV, callDurationTV, callingStatusTV;
    private boolean isCallActive;
    private long callStartTime;
    private Handler handler;
    private Runnable updateCallDurationRunnable;

    @SuppressLint("StaticFieldLeak")
    public static TextView incomingCallerPhoneNumberTV, incomingCallerNameTV, ringingStatusTV;

    RelativeLayout inProgressCallRLView, incomingRLView;

    public static boolean isMuted, isSpeakerOn, isCallOnHold, isRecordingCall;

    public static String PHONE_NUMBER, CALLER_NAME;
    ImageView default_image_theme;
    GifImageView gif_default_image_theme;

    public static String muteBtnName = "Mute", speakerBtnName = "Speaker On";
    TextView reply, incomingreply;
    LinearLayout leftArrowContainer, rightArrowContainer;
    private boolean isRecording = false;


    @SuppressLint({"UseCompatTextViewDrawableApis", "SetTextI18n"})
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_calling_anim);
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN,
                WindowManager.LayoutParams.FLAG_FULLSCREEN);
        default_image_theme = findViewById(R.id.default_image_theme);
        gif_default_image_theme = findViewById(R.id.gif_default_image_theme);
        reply = findViewById(R.id.reply);
        incomingreply = findViewById(R.id.incomingreply);


        // Load the image into ImageView using your preferred image loading library

        initializeValues();
        addLockScreenFlags();

        Log.d("Priya", "TOTAL_NUMBER_OF_CALLS: " + CallManager.NUMBER_OF_CALLS);
        Log.d("Priya", "TOTAL_NUMBER_OF_CALL_OBJECT: " + CallListHelper.callList.size());
//        ______________________________________________________________________________
        setButtonsDisabled();
//        ______________________________________________________________________________
        BroadcastReceiver broadcastReceiver = new BroadcastReceiver() {

            @SuppressLint("SetTextI18n")
            @Override
            public void onReceive(Context arg0, Intent intent) {

                String action = intent.getAction();

                if (action.equals("call_ended")) {
                    finishAndRemoveTask();
                } else if (action.equals("call_answered")) {

                    inProgressCallRLView.setVisibility(View.VISIBLE);
                    incomingRLView.setVisibility(View.GONE);

                    if (CallListHelper.callList.get(CallManager.NUMBER_OF_CALLS - 1).getDetails().hasProperty(Call.Details.PROPERTY_CONFERENCE)) {
                        PHONE_NUMBER = "Conference";
                        CALLER_NAME = "Conference";
                    } else {
                        PHONE_NUMBER = CallListHelper.callList.get(CallManager.NUMBER_OF_CALLS - 1).getDetails().getHandle().getSchemeSpecificPart();
                        CALLER_NAME = ContactsHelper.getContactNameByPhoneNumber(PHONE_NUMBER, CallingAnimActivity.this);
                    }

                    callerPhoneNumberTV.setText(PHONE_NUMBER);
                    reply.setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View view) {
                            String phoneNumber = PHONE_NUMBER; // Replace with the actual phone number

                            Uri smsUri = Uri.parse("smsto:" + phoneNumber);
                            Intent smsIntent = new Intent(Intent.ACTION_SENDTO, smsUri);
                            smsIntent.putExtra("sms_body", "Can you Call back later?");
                            startActivity(smsIntent);
                        }
                    });

                    callerNameTV.setText(CALLER_NAME);

                    Log.d("Priya", PHONE_NUMBER + "  " + CALLER_NAME);
//                    ------------------------------------------------------------------------------

                    if (isMuted) {
                        muteBtnName = "Unmute";
                    } else {
                        muteBtnName = "Mute";
                    }

                    if (isSpeakerOn) {
                        speakerBtnName = "Speaker Off";
                    } else {
                        speakerBtnName = "Speaker On";
                    }
//                    ------------------------------------------------------------------------------
                    if (isSpeakerOn) {
                        speakerBtn.setCompoundDrawableTintList(ColorStateList.valueOf(getColor(R.color.feature_on_color)));
                        speakerBtn.setTextColor(getColor(R.color.feature_on_color));
                        NotificationHelper.createIngoingCallNotification(CallingAnimActivity.this, CallListHelper.callList.get(CallManager.NUMBER_OF_CALLS - 1), "01:12:00", speakerBtnName, muteBtnName);
                    }
//                    ------------------------------------------------------------------------------

                    if (!isMuted) {
                        muteBtn.setEnabled(true);
                        muteBtn.setClickable(true);
                        muteBtn.setCompoundDrawableTintList(ColorStateList.valueOf(getColor(R.color.my_theme)));
                        muteBtn.setTextColor(getColor(R.color.my_theme));
                        muteBtn.setText("Mute");
                        NotificationHelper.createIngoingCallNotification(CallingAnimActivity.this, CallListHelper.callList.get(CallManager.NUMBER_OF_CALLS - 1), "01:12:00", speakerBtnName, muteBtnName);
                    } else {
                        muteBtn.setEnabled(true);
                        muteBtn.setClickable(true);
                        muteBtn.setCompoundDrawableTintList(ColorStateList.valueOf(getColor(R.color.feature_on_color)));
                        muteBtn.setTextColor(getColor(R.color.feature_on_color));
                        muteBtn.setText("Unmute");
                        NotificationHelper.createIngoingCallNotification(CallingAnimActivity.this, CallListHelper.callList.get(CallManager.NUMBER_OF_CALLS - 1), "01:12:00", speakerBtnName, muteBtnName);
                    }


                    if (!isRecordingCall) {
                        recordBtn.setEnabled(true);
                        recordBtn.setClickable(true);
                        recordBtn.setCompoundDrawableTintList(ColorStateList.valueOf(getColor(R.color.my_theme)));
                        recordBtn.setTextColor(getColor(R.color.my_theme));
                    }

                    callingStatusTV.setText("Call in progress...");
                    callingStatusTV.setTextColor(getColor(R.color.green));
                }
            }
        };

        IntentFilter filter = new IntentFilter();
        filter.addAction("call_ended");
        filter.addAction("call_answered");

        registerReceiver(broadcastReceiver, filter);


        endCallBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                CallManager.hangUpCall(CallListHelper.callList.get(CallManager.NUMBER_OF_CALLS - 1));
                handler.removeCallbacks(updateCallDurationRunnable);
                isCallActive = false;
            }
        });


        holdBtn.setOnClickListener(v -> {
            if (isCallOnHold) {
                CallManager.unholdCall(CallListHelper.callList.get(CallManager.NUMBER_OF_CALLS - 1));
                holdBtn.setCompoundDrawableTintList(ColorStateList.valueOf(getColor(R.color.my_theme)));
                holdBtn.setTextColor(getColor(R.color.my_theme));
                isCallOnHold = false;
            } else {
                CallManager.holdCall(CallListHelper.callList.get(CallManager.NUMBER_OF_CALLS - 1));
                holdBtn.setCompoundDrawableTintList(ColorStateList.valueOf(getColor(R.color.feature_on_color)));
                holdBtn.setTextColor(getColor(R.color.feature_on_color));
                isCallOnHold = true;
            }
        });

        muteBtn.setOnClickListener(v -> {
            if (isMuted) {
                CallManager.muteCall(false);
                muteBtn.setCompoundDrawableTintList(ColorStateList.valueOf(getColor(R.color.my_theme)));
                muteBtn.setTextColor(getColor(R.color.my_theme));
                muteBtn.setText("Mute");
                isMuted = false;

                NotificationHelper.createIngoingCallNotification(CallingAnimActivity.this, CallListHelper.callList.get(CallManager.NUMBER_OF_CALLS - 1), "01:12:00", speakerBtnName, "Mute");
            } else {
                CallManager.muteCall(true);
                muteBtn.setCompoundDrawableTintList(ColorStateList.valueOf(getColor(R.color.feature_on_color)));
                muteBtn.setTextColor(getColor(R.color.feature_on_color));
                muteBtn.setText("Unmute");
                isMuted = true;

                NotificationHelper.createIngoingCallNotification(CallingAnimActivity.this, CallListHelper.callList.get(CallManager.NUMBER_OF_CALLS - 1), "01:12:00", speakerBtnName, "Unmute");
            }
        });

        speakerBtn.setOnClickListener(v -> {
            if (isSpeakerOn) {
                CallManager.speakerCall(false);
                speakerBtn.setCompoundDrawableTintList(ColorStateList.valueOf(getColor(R.color.my_theme)));
                speakerBtn.setTextColor(getColor(R.color.my_theme));
                isSpeakerOn = false;

                NotificationHelper.createIngoingCallNotification(CallingAnimActivity.this, CallListHelper.callList.get(CallManager.NUMBER_OF_CALLS - 1), "01:12:00", "Speaker On", muteBtnName);
            } else {
                CallManager.speakerCall(true);
                speakerBtn.setCompoundDrawableTintList(ColorStateList.valueOf(getColor(R.color.feature_on_color)));
                speakerBtn.setTextColor(getColor(R.color.feature_on_color));
                isSpeakerOn = true;

                NotificationHelper.createIngoingCallNotification(CallingAnimActivity.this, CallListHelper.callList.get(CallManager.NUMBER_OF_CALLS - 1), "01:12:00", "Speaker Off", muteBtnName);
            }
        });

        keypadBtn.setOnClickListener(v -> {

            keypadDialog = new BottomSheetDialog(CallingAnimActivity.this);
            keypadDialog.setContentView(R.layout.in_progress_call_dialpad);
            keypadDialog.setCanceledOnTouchOutside(true);
            keypadDialog.getBehavior().setState(BottomSheetBehavior.STATE_EXPANDED);

            ImageButton keypadCancelBtn = keypadDialog.findViewById(R.id.keypadCancelBtn);
            assert keypadCancelBtn != null;
            keypadCancelBtn.setOnClickListener(v1 -> keypadDialog.cancel());

            FloatingActionButton endCallBottomSheet = keypadDialog.findViewById(R.id.endCallBtnBottomSheet);
            assert endCallBottomSheet != null;
            endCallBottomSheet.setOnClickListener(v1 -> CallManager.hangUpCall(CallListHelper.callList.get(CallManager.NUMBER_OF_CALLS - 1)));

            initBottomSheetBtnsAndPlayDTMFtones(CallListHelper.callList.get(CallManager.NUMBER_OF_CALLS - 1), keypadDialog, keypadDialog.findViewById(R.id.keypadDialogTextView));

            keypadDialog.show();
        });

        addCallBtn.setOnClickListener(v -> startActivity(new Intent(this, DialerActivity.class)));

        mergeCallBtn.setOnClickListener(v -> {


            CallListHelper.callList.get(CallManager.NUMBER_OF_CALLS - 3).conference(CallListHelper.callList.get(CallManager.NUMBER_OF_CALLS - 1));
            CallListHelper.callList.get(CallManager.NUMBER_OF_CALLS - 1).mergeConference();


        });



    }


    @SuppressLint("UseCompatTextViewDrawableApis")
    @Override
    protected void onResume() {
        super.onResume();

        if (CallListHelper.callList.size() >= 2) {

            mergeCallBtn.setVisibility(View.VISIBLE);

            holdBtn.setEnabled(false);
            holdBtn.setClickable(false);
            holdBtn.setCompoundDrawableTintList(ColorStateList.valueOf(getColor(R.color.light_grey)));
            holdBtn.setTextColor(getColor(R.color.light_grey));

            addCallBtn.setEnabled(false);
            addCallBtn.setClickable(false);
            addCallBtn.setCompoundDrawableTintList(ColorStateList.valueOf(getColor(R.color.light_grey)));
            addCallBtn.setTextColor(getColor(R.color.light_grey));
        } else {

            holdBtn.setEnabled(true);
            holdBtn.setClickable(true);
            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.Q) {
                if (CallListHelper.callList.get(CallManager.NUMBER_OF_CALLS - 1).getState() == Call.STATE_HOLDING) {
                    holdBtn.setCompoundDrawableTintList(ColorStateList.valueOf(getColor(R.color.feature_on_color)));
                    holdBtn.setTextColor(getColor(R.color.feature_on_color));
                } else {
                    holdBtn.setCompoundDrawableTintList(ColorStateList.valueOf(getColor(R.color.my_theme)));
                    holdBtn.setTextColor(getColor(R.color.my_theme));
                }
            } else if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.S) {
                if (CallListHelper.callList.get(CallManager.NUMBER_OF_CALLS - 1).getState() == Call.STATE_HOLDING) {
                    holdBtn.setCompoundDrawableTintList(ColorStateList.valueOf(getColor(R.color.feature_on_color)));
                    holdBtn.setTextColor(getColor(R.color.feature_on_color));
                } else {
                    holdBtn.setCompoundDrawableTintList(ColorStateList.valueOf(getColor(R.color.my_theme)));
                    holdBtn.setTextColor(getColor(R.color.my_theme));
                }
            } else if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.N) {
                if (CallListHelper.callList.get(CallManager.NUMBER_OF_CALLS - 1).getDetails().getState() == Call.STATE_HOLDING) {
                    holdBtn.setCompoundDrawableTintList(ColorStateList.valueOf(getColor(R.color.feature_on_color)));
                    holdBtn.setTextColor(getColor(R.color.feature_on_color));
                } else {
                    holdBtn.setCompoundDrawableTintList(ColorStateList.valueOf(getColor(R.color.my_theme)));
                    holdBtn.setTextColor(getColor(R.color.my_theme));
                }
            } else if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
                if (CallListHelper.callList.get(CallManager.NUMBER_OF_CALLS - 1).getDetails().getState() == Call.STATE_HOLDING) {
                    holdBtn.setCompoundDrawableTintList(ColorStateList.valueOf(getColor(R.color.feature_on_color)));
                    holdBtn.setTextColor(getColor(R.color.feature_on_color));
                } else {
                    holdBtn.setCompoundDrawableTintList(ColorStateList.valueOf(getColor(R.color.my_theme)));
                    holdBtn.setTextColor(getColor(R.color.my_theme));
                }
            }


            addCallBtn.setEnabled(true);
            addCallBtn.setClickable(true);
            addCallBtn.setCompoundDrawableTintList(ColorStateList.valueOf(getColor(R.color.my_theme)));
            addCallBtn.setTextColor(getColor(R.color.my_theme));
        }


        int call_state = CallManager.HP_CALL_STATE;

        if (call_state == Call.STATE_CONNECTING || call_state == Call.STATE_DIALING) {

            inProgressCallRLView.setVisibility(View.VISIBLE);
            incomingRLView.setVisibility(View.GONE);


            if (CallManager.NUMBER_OF_CALLS > 0 && CallListHelper.callList.size() > 0) {

                PHONE_NUMBER = CallListHelper.callList.get(CallManager.NUMBER_OF_CALLS - 1).getDetails().getHandle().getSchemeSpecificPart();
                CALLER_NAME = ContactsHelper.getContactNameByPhoneNumber(PHONE_NUMBER, this);

                callerPhoneNumberTV.setText(PHONE_NUMBER);
                callerNameTV.setText(CALLER_NAME);

//                NotificationHelper.createOutgoingNotification(this, CallListHelper.callList.get(CallManager.NUMBER_OF_CALLS - 1));
            }
        } else if (call_state == Call.STATE_ACTIVE || call_state == Call.STATE_HOLDING) {

            Intent broadCastIntent = new Intent("call_answered");
            sendBroadcast(broadCastIntent);
        } else if (call_state == Call.STATE_RINGING) {
            Uri defaultDialerRingtoneUri = RingtoneManager.getDefaultUri(RingtoneManager.TYPE_RINGTONE);
            defaultDialerRingtone = RingtoneManager.getRingtone(getApplicationContext(), defaultDialerRingtoneUri);
            defaultDialerRingtone.play();
            inProgressCallRLView.setVisibility(View.GONE);
            incomingRLView.setVisibility(View.VISIBLE);
            incomingreply.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    String phoneNumber = PHONE_NUMBER; // Replace with the actual phone number

                    Uri smsUri = Uri.parse("smsto:" + phoneNumber);
                    Intent smsIntent = new Intent(Intent.ACTION_SENDTO, smsUri);
                    smsIntent.putExtra("sms_body", "Can you Call back later?");
                    startActivity(smsIntent);
                }
            });

            default_image_theme.setVisibility(View.VISIBLE);
            loadImage();

            if (CallManager.NUMBER_OF_CALLS > 0 && CallListHelper.callList.size() > 0) {

                PHONE_NUMBER = CallListHelper.callList.get(CallManager.NUMBER_OF_CALLS - 1).getDetails().getHandle().getSchemeSpecificPart();
                CALLER_NAME = ContactsHelper.getContactNameByPhoneNumber(PHONE_NUMBER, this);

                incomingCallerPhoneNumberTV.setText(PHONE_NUMBER);
                incomingCallerNameTV.setText(CALLER_NAME);

                NotificationHelper.createIncomingNotification(this, CallListHelper.callList.get(CallManager.NUMBER_OF_CALLS - 1));
            }
        } else {
        }
    }

    private void loadImage() {
        SharedPreferences imageSharedPreferences = getSharedPreferences("image_theme", Context.MODE_PRIVATE);
        SharedPreferences gifSharedPreferences = getSharedPreferences("gif_theme", Context.MODE_PRIVATE);

        String imageUrl = imageSharedPreferences.getString("image_url1", null);
        String gifUrl = gifSharedPreferences.getString("image_url1", null);

        if (imageUrl != null && gifUrl != null) {
            long imageTimestamp = imageSharedPreferences.getLong("timestamp", 0);
            long gifTimestamp = gifSharedPreferences.getLong("timestamp", 0);

            if (imageTimestamp > gifTimestamp) {
                default_image_theme.setVisibility(View.VISIBLE);
                gif_default_image_theme.setVisibility(View.GONE);
                Picasso.get().load(imageUrl).into(default_image_theme);
            } else {
                default_image_theme.setVisibility(View.GONE);
                gif_default_image_theme.setVisibility(View.VISIBLE);
                Glide.with(CallingAnimActivity.this).load(gifUrl).into(gif_default_image_theme);
            }
        } else if (imageUrl != null) {
            default_image_theme.setVisibility(View.VISIBLE);
            gif_default_image_theme.setVisibility(View.GONE);
            Picasso.get().load(imageUrl).into(default_image_theme);
        } else if (gifUrl != null) {
            default_image_theme.setVisibility(View.GONE);
            gif_default_image_theme.setVisibility(View.VISIBLE);
            Glide.with(CallingAnimActivity.this).load(gifUrl).into(gif_default_image_theme);
        } else {
            default_image_theme.setVisibility(View.GONE);
            gif_default_image_theme.setVisibility(View.GONE);
        }
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();

        if (keypadDialog != null) {
            if (keypadDialog.isShowing()) {
                keypadDialog.dismiss();
            }
        }
    }

    private void initializeValues() {
        endCallBtn = findViewById(R.id.endCallBtn);

        muteBtn = findViewById(R.id.muteBtn);
        keypadBtn = findViewById(R.id.keyPadBtn);
        speakerBtn = findViewById(R.id.speakerBtn);
        holdBtn = findViewById(R.id.holdBtn);
        recordBtn = findViewById(R.id.recordBtn);
        addCallBtn = findViewById(R.id.addCallBtn);
        mergeCallBtn = findViewById(R.id.mergeCallBtn);

        callAnswerBtn = findViewById(R.id.answerCallBtn);
        callRejectBtn = findViewById(R.id.rejectCallBtn);
        SharedPreferences sharedPreferences = getSharedPreferences("AnotherActivityPrefs", Context.MODE_PRIVATE);

// Retrieve the stored values for receiveIcon and rejectIcon from SharedPreferences
        int receiveIcon = sharedPreferences.getInt("receiveIcon", R.drawable.accept_button_one);
        int rejectIcon = sharedPreferences.getInt("rejectIcon", R.drawable.decline_button_one);

// Set the stored icons as images for the ImageView elements
        callAnswerBtn.setImageResource(receiveIcon);
        callRejectBtn.setImageResource(rejectIcon);


        leftArrowContainer = findViewById(R.id.left_arrow_container);
        rightArrowContainer = findViewById(R.id.right_arrow_container);
// Create the animations for the left arrows
        ObjectAnimator leftArrow1Animator = ObjectAnimator.ofFloat(leftArrowContainer.getChildAt(0), "translationX", -40f);
        ObjectAnimator leftArrow2Animator = ObjectAnimator.ofFloat(leftArrowContainer.getChildAt(1), "translationX", -40f);


// Create the animations for the right arrows
        ObjectAnimator rightArrow1Animator = ObjectAnimator.ofFloat(rightArrowContainer.getChildAt(0), "translationX", 40f);
        ObjectAnimator rightArrow2Animator = ObjectAnimator.ofFloat(rightArrowContainer.getChildAt(1), "translationX", 40f);


        AnimatorSet leftArrowAnimatorSet = new AnimatorSet();
        leftArrowAnimatorSet.playSequentially(
                leftArrow1Animator,
                leftArrow2Animator

        );
        leftArrowAnimatorSet.setDuration(500);
        leftArrowAnimatorSet.setStartDelay(200); // Adjust the start delay as needed

// Create an AnimatorSet for the right arrows
        AnimatorSet rightArrowAnimatorSet = new AnimatorSet();
        rightArrowAnimatorSet.playSequentially(
                rightArrow1Animator,
                rightArrow2Animator

        );
        rightArrowAnimatorSet.setDuration(500);
        rightArrowAnimatorSet.setStartDelay(200);


        callAnswerBtn.setOnTouchListener(new SwipeTouchListener(leftArrowContainer, rightArrowContainer, callAnswerBtn));
        callRejectBtn.setOnTouchListener(new SwipeTouchListener(leftArrowContainer, rightArrowContainer, callRejectBtn));


        callerNameTV = findViewById(R.id.callerName);
        callerPhoneNumberTV = findViewById(R.id.callerPhoneNumber);
        callDurationTV = findViewById(R.id.callingDuration);
        isCallActive = false;
        callStartTime = 0;
        handler = new Handler();
        updateCallDurationRunnable = new Runnable() {
            @Override
            public void run() {
                if (isCallActive) {
                    long elapsedTime = System.currentTimeMillis() - callStartTime;
                    long minutes = TimeUnit.MILLISECONDS.toMinutes(elapsedTime);
                    long seconds = TimeUnit.MILLISECONDS.toSeconds(elapsedTime) % 60;

                    String durationText = String.format(Locale.getDefault(), "%02d:%02d", minutes, seconds);

                    callDurationTV.setText(durationText);

                    // Schedule the next update after 1 second
                    handler.postDelayed(this, 1000);
                }
            }
        };
//        callStartTime = System.currentTimeMillis();


        callingStatusTV = findViewById(R.id.callingStatus);

        inProgressCallRLView = findViewById(R.id.inProgressCallRLView);
        incomingRLView = findViewById(R.id.incomingRLView);

        incomingCallerPhoneNumberTV = findViewById(R.id.incomingCallerPhoneNumberTV);
        incomingCallerNameTV = findViewById(R.id.incomingCallerNameTV);
        ringingStatusTV = findViewById(R.id.ringingStatus);
    }

    class SwipeTouchListener implements View.OnTouchListener {
        private LinearLayout leftArrowContainer;
        private LinearLayout rightArrowContainer;
        private ImageView leftArrow1;
        private ImageView leftArrow2;

        private ImageView rightArrow1;
        private ImageView rightArrow2;
        private static final int SWIPE_THRESHOLD = 100;
        private static final int SWIPE_VELOCITY_THRESHOLD = 100;

        private boolean isSwiping = false;
        private float initialX;
        private ImageView buttonView;
        private ObjectAnimator fadeOutAnimator;
        private ObjectAnimator fadeInAnimator;

        SwipeTouchListener(LinearLayout leftArrowContainer, LinearLayout rightArrowContainer, ImageView buttonView) {
            this.leftArrowContainer = leftArrowContainer;
            this.rightArrowContainer = rightArrowContainer;
            this.buttonView = buttonView;

            leftArrow1 = leftArrowContainer.findViewById(R.id.left_arrow_1);
            leftArrow2 = leftArrowContainer.findViewById(R.id.left_arrow_2);
            rightArrow1 = rightArrowContainer.findViewById(R.id.right_arrow_1);
            rightArrow2 = rightArrowContainer.findViewById(R.id.right_arrow_2);

            fadeOutAnimator = ObjectAnimator.ofFloat(buttonView, "alpha", 1f, 0.5f);
            fadeOutAnimator.setDuration(200);
            fadeOutAnimator.setInterpolator(new LinearInterpolator());

            fadeInAnimator = ObjectAnimator.ofFloat(buttonView, "alpha", 0.5f, 1f);
            fadeInAnimator.setDuration(200);
            fadeInAnimator.setInterpolator(new LinearInterpolator());
        }

        @Override
        public boolean onTouch(View v, MotionEvent event) {
            switch (event.getAction()) {
                case MotionEvent.ACTION_DOWN:
                    isSwiping = false;
                    initialX = event.getX();
                    showArrows();
                    fadeOutAnimator.cancel();
                    fadeInAnimator.cancel();
                    buttonView.setAlpha(1f);
                    return true;
                case MotionEvent.ACTION_MOVE:
                    if (!isSwiping && Math.abs(event.getX() - initialX) > 100) {
                        isSwiping = true;
                    }
                    if (isSwiping) {
                        buttonView.setTranslationX(event.getX() - initialX);
                        fadeOutAnimator.start();
                    }
                    return true;
                case MotionEvent.ACTION_UP:
                    hideArrows();
                    if (isSwiping) {
                        float deltaX = event.getX() - initialX;
                        if (deltaX > 0) {
                            // Swiped right
                            // Handle receive call action here
                            CallManager.answerCall(CallListHelper.callList.get(CallManager.NUMBER_OF_CALLS - 1));
                            callStartTime = System.currentTimeMillis();
                            isCallActive = true;
                            if (defaultDialerRingtone != null) {
                                defaultDialerRingtone.stop();
                            }
                            // Start updating the call duration
                            handler.postDelayed(updateCallDurationRunnable, 1000);

                        } else {
                            // Swiped left
                            // Handle end call action here

                            CallManager.hangUpCall(CallListHelper.callList.get(CallManager.NUMBER_OF_CALLS - 1));
                            if (defaultDialerRingtone != null) {
                                defaultDialerRingtone.stop();
                            }


                        }
                    }
                    buttonView.setTranslationX(0f);
                    fadeInAnimator.start();
                    return true;
            }
            return false;
        }


        private void showArrows() {
            leftArrow1.setVisibility(View.VISIBLE);
            leftArrow1.setTranslationX(-50);
            leftArrow1.setAlpha(0f);
            leftArrow1.animate().translationXBy(50).alpha(1f).setDuration(200).start();

            leftArrow2.setVisibility(View.VISIBLE);
            leftArrow2.setTranslationX(-50);
            leftArrow2.setAlpha(0f);
            leftArrow2.animate().translationXBy(50).alpha(1f).setStartDelay(100).setDuration(200).start();


            rightArrow1.setVisibility(View.VISIBLE);
            rightArrow1.setTranslationX(50);
            rightArrow1.setAlpha(0f);
            rightArrow1.animate().translationXBy(-50).alpha(1f).setDuration(200).start();

            rightArrow2.setVisibility(View.VISIBLE);
            rightArrow2.setTranslationX(50);
            rightArrow2.setAlpha(0f);
            rightArrow2.animate().translationXBy(-50).alpha(1f).setStartDelay(100).setDuration(200).start();


        }


        private void hideArrows() {
            leftArrow1.animate().translationXBy(50).alpha(0f).setDuration(200).start();
            leftArrow2.animate().translationXBy(50).alpha(0f).setDuration(200).start();
            rightArrow1.animate().translationXBy(-50).alpha(0f).setDuration(200).start();
            rightArrow2.animate().translationXBy(-50).alpha(0f).setDuration(200).start();
        }
    }


    @SuppressLint("SetTextI18n")
    private void initBottomSheetBtnsAndPlayDTMFtones(Call call, BottomSheetDialog keypadDialog, TextView keypadDialogTextView) {

        btn0 = keypadDialog.findViewById(R.id.btn0);
        btn01 = keypadDialog.findViewById(R.id.btn01);
        btn02 = keypadDialog.findViewById(R.id.btn02);
        btn03 = keypadDialog.findViewById(R.id.btn03);
        btn04 = keypadDialog.findViewById(R.id.btn04);
        btn05 = keypadDialog.findViewById(R.id.btn05);
        btn06 = keypadDialog.findViewById(R.id.btn06);
        btn07 = keypadDialog.findViewById(R.id.btn07);
        btn08 = keypadDialog.findViewById(R.id.btn08);
        btn09 = keypadDialog.findViewById(R.id.btn09);
        btnStar = keypadDialog.findViewById(R.id.btnStar);
        btnHash = keypadDialog.findViewById(R.id.btnHash);

        btn0.setOnClickListener(v -> {
            CallManager.playDtmfTone(call, '0');
            keypadDialogTextViewText = keypadDialogTextView.getText().toString();
            keypadDialogTextView.setText(keypadDialogTextViewText + "0");
        });
        btn01.setOnClickListener(v -> {
            CallManager.playDtmfTone(call, '1');
            keypadDialogTextViewText = keypadDialogTextView.getText().toString();
            keypadDialogTextView.setText(keypadDialogTextViewText + "1");
        });
        btn02.setOnClickListener(v -> {
            CallManager.playDtmfTone(call, '2');
            keypadDialogTextViewText = keypadDialogTextView.getText().toString();
            keypadDialogTextView.setText(keypadDialogTextViewText + "2");
        });
        btn03.setOnClickListener(v -> {
            CallManager.playDtmfTone(call, '3');
            keypadDialogTextViewText = keypadDialogTextView.getText().toString();
            keypadDialogTextView.setText(keypadDialogTextViewText + "3");
        });
        btn04.setOnClickListener(v -> {
            CallManager.playDtmfTone(call, '4');
            keypadDialogTextViewText = keypadDialogTextView.getText().toString();
            keypadDialogTextView.setText(keypadDialogTextViewText + "4");
        });
        btn05.setOnClickListener(v -> {
            CallManager.playDtmfTone(call, '5');
            keypadDialogTextViewText = keypadDialogTextView.getText().toString();
            keypadDialogTextView.setText(keypadDialogTextViewText + "5");
        });
        btn06.setOnClickListener(v -> {
            CallManager.playDtmfTone(call, '6');
            keypadDialogTextViewText = keypadDialogTextView.getText().toString();
            keypadDialogTextView.setText(keypadDialogTextViewText + "6");
        });
        btn07.setOnClickListener(v -> {
            CallManager.playDtmfTone(call, '7');
            keypadDialogTextViewText = keypadDialogTextView.getText().toString();
            keypadDialogTextView.setText(keypadDialogTextViewText + "7");
        });
        btn08.setOnClickListener(v -> {
            CallManager.playDtmfTone(call, '8');
            keypadDialogTextViewText = keypadDialogTextView.getText().toString();
            keypadDialogTextView.setText(keypadDialogTextViewText + "8");
        });
        btn09.setOnClickListener(v -> {
            CallManager.playDtmfTone(call, '9');
            keypadDialogTextViewText = keypadDialogTextView.getText().toString();
            keypadDialogTextView.setText(keypadDialogTextViewText + "9");
        });
        btnStar.setOnClickListener(v -> {
            CallManager.playDtmfTone(call, '*');
            keypadDialogTextViewText = keypadDialogTextView.getText().toString();
            keypadDialogTextView.setText(keypadDialogTextViewText + "*");
        });
        btnHash.setOnClickListener(v -> {
            CallManager.playDtmfTone(call, '#');
            keypadDialogTextViewText = keypadDialogTextView.getText().toString();
            keypadDialogTextView.setText(keypadDialogTextViewText + "#");
        });
    }

    @SuppressLint("UseCompatTextViewDrawableApis")
    private void setButtonsDisabled() {
        muteBtn.setEnabled(false);
        muteBtn.setClickable(false);
        muteBtn.setCompoundDrawableTintList(ColorStateList.valueOf(getColor(R.color.light_grey)));
        muteBtn.setTextColor(getColor(R.color.light_grey));

        holdBtn.setEnabled(false);
        holdBtn.setClickable(false);
        holdBtn.setCompoundDrawableTintList(ColorStateList.valueOf(getColor(R.color.light_grey)));
        holdBtn.setTextColor(getColor(R.color.light_grey));

        recordBtn.setEnabled(false);
        recordBtn.setClickable(false);
        recordBtn.setCompoundDrawableTintList(ColorStateList.valueOf(getColor(R.color.light_grey)));
        recordBtn.setTextColor(getColor(R.color.light_grey));
      /*  recordBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                // Check if the required permissions are granted
                if (checkPermissions()) {
                    // Enable call recording functionality
                    startCallRecording();
                } else {
                    // Request the required permissions
                    requestPermissions();
                }
            }
        });*/

// Method to check if the required permissions are granted


        addCallBtn.setEnabled(false);
        addCallBtn.setClickable(false);
        addCallBtn.setCompoundDrawableTintList(ColorStateList.valueOf(getColor(R.color.light_grey)));
        addCallBtn.setTextColor(getColor(R.color.light_grey));
    }
  /*  private boolean checkPermissions() {
        int recordAudioPermission = ContextCompat.checkSelfPermission(this, Manifest.permission.RECORD_AUDIO);
        int writeExternalStoragePermission = ContextCompat.checkSelfPermission(this, Manifest.permission.WRITE_EXTERNAL_STORAGE);
        return recordAudioPermission == PackageManager.PERMISSION_GRANTED && writeExternalStoragePermission == PackageManager.PERMISSION_GRANTED;
    }
    private void requestPermissions() {
        String[] permissions = {Manifest.permission.RECORD_AUDIO, Manifest.permission.WRITE_EXTERNAL_STORAGE};
        ActivityCompat.requestPermissions(this, permissions, PERMISSION_REQUEST_CODE);
    }*/

    // Handle the permission request result
  /*  @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
        if (requestCode == PERMISSION_REQUEST_CODE) {
            boolean allPermissionsGranted = true;
            for (int grantResult : grantResults) {
                if (grantResult != PackageManager.PERMISSION_GRANTED) {
                    allPermissionsGranted = false;
                    break;
                }
            }

            if (allPermissionsGranted) {
                // Permissions granted, enable call recording functionality
                startCallRecording();
            } else {
                // Permissions not granted, display a message to the user or handle the situation accordingly
                Toast.makeText(this, "Call recording permissions not granted", Toast.LENGTH_SHORT).show();
            }
        }
    }

    // Method to start the call recording
    private void startCallRecording() {
        if (isRecording) {
            Toast.makeText(this, "Call recording is already in progress", Toast.LENGTH_SHORT).show();
            return;
        }

        // Create a MediaRecorder instance
        MediaRecorder mediaRecorder = new MediaRecorder();

        try {
            // Set the audio source to capture the voice call
            mediaRecorder.setAudioSource(MediaRecorder.AudioSource.VOICE_CALL);

            // Set the output format and file path for the recorded audio
            mediaRecorder.setOutputFormat(MediaRecorder.OutputFormat.MPEG_4);
            String filePath = getExternalFilesDir(null).getAbsolutePath() + "/call_recording.mp4";
            mediaRecorder.setOutputFile(filePath);

            // Set the audio encoder
            mediaRecorder.setAudioEncoder(MediaRecorder.AudioEncoder.AAC);

            // Prepare and start the recording
            mediaRecorder.prepare();
            mediaRecorder.start();

            // Update the recording status
            isRecording = true;

            // Display a toast message to indicate that call recording has started
            Toast.makeText(this, "Call recording started", Toast.LENGTH_SHORT).show();
        } catch (IOException e) {
            // Handle any errors that occur during media recorder setup or recording
            e.printStackTrace();

            // Display an error message to the user
            Toast.makeText(this, "Failed to start call recording", Toast.LENGTH_SHORT).show();
        }
    }*/

    private void addLockScreenFlags() {
        setShowWhenLocked(true);
        setTurnScreenOn(true);

        getWindow().addFlags(
                WindowManager.LayoutParams.FLAG_KEEP_SCREEN_ON |
                        WindowManager.LayoutParams.FLAG_DISMISS_KEYGUARD |
                        WindowManager.LayoutParams.FLAG_SHOW_WHEN_LOCKED |
                        WindowManager.LayoutParams.FLAG_TURN_SCREEN_ON |
                        WindowManager.LayoutParams.FLAG_HARDWARE_ACCELERATED
        );
    }
}