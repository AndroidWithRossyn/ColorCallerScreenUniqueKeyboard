package com.uprisingscallscreen.theme.flashscreen.callertheme.OS;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.graphics.Paint;
import android.graphics.PorterDuff;
import android.graphics.PorterDuffXfermode;
import android.graphics.Rect;
import android.graphics.drawable.StateListDrawable;
import android.os.Build;
import android.telecom.TelecomManager;
import android.view.View;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;

import androidx.core.app.ActivityCompat;
import androidx.core.view.MotionEventCompat;


import com.uprisingscallscreen.theme.flashscreen.R;

import java.io.FileOutputStream;
import java.io.IOException;
import java.lang.reflect.Array;
import java.util.Calendar;


public class OtherUntil {
    public static boolean checkPer(Context context, String str) {
        return ActivityCompat.checkSelfPermission(context, str) == 0;
    }

    public static boolean checkPer(Activity activity) {
        TelecomManager telecomManager = (TelecomManager) activity.getSystemService("telecom");
        if (Build.VERSION.SDK_INT >= 29) {
            if ((telecomManager == null || activity.getPackageName().equals(telecomManager.getDefaultDialerPackage())) && checkPer(activity, "android.permission.READ_CONTACTS") && checkPer(activity, "android.permission.READ_EXTERNAL_STORAGE") && checkPer(activity, "android.permission.CALL_PHONE")) {
                return false;
            }
            activity.startActivity(new Intent(activity, ActivityPerM.class));
            return true;
        } else if ((telecomManager == null || activity.getPackageName().equals(telecomManager.getDefaultDialerPackage())) && checkPer(activity, "android.permission.READ_CONTACTS") && checkPer(activity, "android.permission.READ_EXTERNAL_STORAGE") && checkPer(activity, "android.permission.WRITE_EXTERNAL_STORAGE") && checkPer(activity, "android.permission.CALL_PHONE")) {
            return false;
        } else {
            activity.startActivity(new Intent(activity, ActivityPerM.class));
            return true;
        }
    }

    public static boolean canWriteInMediaStore(Context context) {
        if (Build.VERSION.SDK_INT < 29 && ActivityCompat.checkSelfPermission(context, "android.permission.WRITE_EXTERNAL_STORAGE") != 0) {
            return false;
        }
        return true;
    }

    public static StateListDrawable selNum(Context context) {
        StateListDrawable stateListDrawable = new StateListDrawable();
        stateListDrawable.setExitFadeDuration(400);
        stateListDrawable.addState(new int[]{16842919}, context.getDrawable(R.drawable.bg_num_phone_press));
        stateListDrawable.addState(new int[0], context.getDrawable(R.drawable.bg_num_phone_nomal));
        return stateListDrawable;
    }

    public static String getTime(int i) {
        int i2 = i / 60;
        int i3 = i % 60;
        StringBuilder sb = new StringBuilder();
        if (i2 < 10) {
            sb.append("0");
        }
        sb.append(i2);
        sb.append(":");
        if (i3 < 10) {
            sb.append("0");
        }
        sb.append(i3);
        return sb.toString();
    }

    public static Bitmap fastblur(Bitmap bitmap, float f, int i) {
        int[] iArr;
        int i2 = i;
        Bitmap createScaledBitmap = Bitmap.createScaledBitmap(bitmap, Math.round(((float) bitmap.getWidth()) * f), Math.round(((float) bitmap.getHeight()) * f), false);
        Bitmap copy = createScaledBitmap.copy(createScaledBitmap.getConfig(), true);
        if (i2 < 1) {
            return null;
        }
        int width = copy.getWidth();
        int height = copy.getHeight();
        int i3 = width * height;
        int[] iArr2 = new int[i3];
        copy.getPixels(iArr2, 0, width, 0, 0, width, height);
        int i4 = width - 1;
        int i5 = height - 1;
        int i6 = i2 + i2 + 1;
        int[] iArr3 = new int[i3];
        int[] iArr4 = new int[i3];
        int[] iArr5 = new int[i3];
        int[] iArr6 = new int[Math.max(width, height)];
        int i7 = (i6 + 1) >> 1;
        int i8 = i7 * i7;
        int i9 = i8 * 256;
        int[] iArr7 = new int[i9];
        for (int i10 = 0; i10 < i9; i10++) {
            iArr7[i10] = i10 / i8;
        }
        int[][] iArr8 = (int[][]) Array.newInstance(int.class, i6, 3);
        int i11 = i2 + 1;
        int i12 = 0;
        int i13 = 0;
        int i14 = 0;
        while (i12 < height) {
            int i15 = -i2;
            int i16 = 0;
            int i17 = 0;
            int i18 = 0;
            int i19 = 0;
            int i20 = 0;
            int i21 = 0;
            int i22 = 0;
            int i23 = 0;
            int i24 = 0;
            while (i15 <= i2) {
                int i25 = iArr2[i13 + Math.min(i4, Math.max(i15, 0))];
                int[] iArr9 = iArr8[i15 + i2];
                iArr9[0] = (i25 & 16711680) >> 16;
                iArr9[1] = (i25 & MotionEventCompat.ACTION_POINTER_INDEX_MASK) >> 8;
                iArr9[2] = i25 & 255;
                int abs = i11 - Math.abs(i15);
                i24 += iArr9[0] * abs;
                i16 += iArr9[1] * abs;
                i17 += iArr9[2] * abs;
                if (i15 > 0) {
                    i21 += iArr9[0];
                    i22 += iArr9[1];
                    i23 += iArr9[2];
                } else {
                    i18 += iArr9[0];
                    i19 += iArr9[1];
                    i20 += iArr9[2];
                }
                i15++;
                i5 = i5;
                iArr6 = iArr6;
            }
            int i26 = i2;
            int i27 = i24;
            int i28 = 0;
            while (i28 < width) {
                iArr3[i13] = iArr7[i27];
                iArr4[i13] = iArr7[i16];
                iArr5[i13] = iArr7[i17];
                int i29 = i27 - i18;
                int i30 = i16 - i19;
                int i31 = i17 - i20;
                int[] iArr10 = iArr8[((i26 - i2) + i6) % i6];
                int i32 = i18 - iArr10[0];
                int i33 = i19 - iArr10[1];
                int i34 = i20 - iArr10[2];
                if (i12 == 0) {
                    iArr = iArr7;
                    iArr6[i28] = Math.min(i28 + i2 + 1, i4);
                } else {
                    iArr = iArr7;
                }
                int i35 = iArr2[i14 + iArr6[i28]];
                iArr10[0] = (i35 & 16711680) >> 16;
                iArr10[1] = (i35 & MotionEventCompat.ACTION_POINTER_INDEX_MASK) >> 8;
                iArr10[2] = i35 & 255;
                int i36 = i21 + iArr10[0];
                int i37 = i22 + iArr10[1];
                int i38 = i23 + iArr10[2];
                i27 = i29 + i36;
                i16 = i30 + i37;
                i17 = i31 + i38;
                i26 = (i26 + 1) % i6;
                int[] iArr11 = iArr8[i26 % i6];
                i18 = i32 + iArr11[0];
                i19 = i33 + iArr11[1];
                i20 = i34 + iArr11[2];
                i21 = i36 - iArr11[0];
                i22 = i37 - iArr11[1];
                i23 = i38 - iArr11[2];
                i13++;
                i28++;
                iArr7 = iArr;
            }
            i14 += width;
            i12++;
            copy = copy;
            height = height;
            i5 = i5;
            iArr6 = iArr6;
        }
        int i39 = i5;
        int i40 = height;
        int i41 = 0;
        while (i41 < width) {
            int i42 = -i2;
            int i43 = i42 * width;
            int i44 = 0;
            int i45 = 0;
            int i46 = 0;
            int i47 = 0;
            int i48 = 0;
            int i49 = 0;
            int i50 = 0;
            int i51 = i42;
            int i52 = 0;
            int i53 = 0;
            while (i51 <= i2) {
                int max = Math.max(0, i43) + i41;
                int[] iArr12 = iArr8[i51 + i2];
                iArr12[0] = iArr3[max];
                iArr12[1] = iArr4[max];
                iArr12[2] = iArr5[max];
                int abs2 = i11 - Math.abs(i51);
                i52 += iArr3[max] * abs2;
                i53 += iArr4[max] * abs2;
                i44 += iArr5[max] * abs2;
                if (i51 > 0) {
                    i48 += iArr12[0];
                    i49 += iArr12[1];
                    i50 += iArr12[2];
                } else {
                    i45 += iArr12[0];
                    i46 += iArr12[1];
                    i47 += iArr12[2];
                }
                if (i51 < i39) {
                    i43 += width;
                }
                i51++;
                i39 = i39;
                width = width;
            }
            int i54 = i2;
            int i55 = i41;
            int i56 = i44;
            int i57 = i53;
            int i58 = 0;
            while (i58 < i40) {
                iArr2[i55] = (iArr2[i55] & -16777216) | (iArr7[i52] << 16) | (iArr7[i57] << 8) | iArr7[i56];
                int i59 = i52 - i45;
                int i60 = i57 - i46;
                int i61 = i56 - i47;
                int[] iArr13 = iArr8[((i54 - i2) + i6) % i6];
                int i62 = i45 - iArr13[0];
                int i63 = i46 - iArr13[1];
                int i64 = i47 - iArr13[2];
                if (i41 == 0) {
                    iArr6[i58] = Math.min(i58 + i11, i39) * width;
                }
                int i65 = iArr6[i58] + i41;
                iArr13[0] = iArr3[i65];
                iArr13[1] = iArr4[i65];
                iArr13[2] = iArr5[i65];
                int i66 = i48 + iArr13[0];
                int i67 = i49 + iArr13[1];
                int i68 = i50 + iArr13[2];
                i52 = i59 + i66;
                i57 = i60 + i67;
                i56 = i61 + i68;
                i54 = (i54 + 1) % i6;
                int[] iArr14 = iArr8[i54];
                i45 = i62 + iArr14[0];
                i46 = i63 + iArr14[1];
                i47 = i64 + iArr14[2];
                i48 = i66 - iArr14[0];
                i49 = i67 - iArr14[1];
                i50 = i68 - iArr14[2];
                i55 += width;
                i58++;
                i2 = i;
            }
            i41++;
            i2 = i;
            i39 = i39;
            i40 = i40;
            i6 = i6;
            iArr2 = iArr2;
            width = width;
        }
        copy.setPixels(iArr2, 0, width, 0, 0, width, i40);
        return copy;
    }

    public static void anim(View view, int i, boolean z) {
        anim(view, AnimationUtils.loadAnimation(view.getContext(), i), z);
    }

    private static void anim(final View view, Animation animation, final boolean z) {
        animation.setAnimationListener(new Animation.AnimationListener() {
            @Override
            public void onAnimationRepeat(Animation animation2) {
            }

            @Override
            public void onAnimationStart(Animation animation2) {
            }

            @Override
            public void onAnimationEnd(Animation animation2) {
                if (z) {
                    view.setVisibility(View.INVISIBLE);
                } else {
                    view.setVisibility(View.VISIBLE);
                }
            }
        });
        view.startAnimation(animation);
    }

    public static String longToTime(Context context, long j) {
        Calendar instance = Calendar.getInstance();
        Calendar instance2 = Calendar.getInstance();
        instance2.setTimeInMillis(j);
        if (instance2.get(1) != instance.get(1)) {
            return setNum(instance2.get(5)) + "/" + setNum(instance2.get(2) + 1) + "/" + instance2.get(1);
        } else if (instance2.get(6) == instance.get(6)) {
            return setNum(instance2.get(11)) + ":" + setNum(instance2.get(12));
        } else if (instance.get(6) - instance2.get(6) == 1) {
            return context.getString(R.string.yesterday);
        } else {
            if (instance.get(6) - instance2.get(6) < 7) {
                return dayToString(context, instance2.get(7));
            }
            return setNum(instance2.get(5)) + "/" + setNum(instance2.get(2) + 1) + "/" + instance2.get(1);
        }
    }

    private static String setNum(int i) {
        if (i < 10) {
            return "0" + i;
        }
        return "" + i;
    }

    private static String dayToString(Context context, int i) {
        switch (i) {
            case 2:
                return context.getResources().getString(R.string.monday);
            case 3:
                return context.getResources().getString(R.string.tuesday);
            case 4:
                return context.getResources().getString(R.string.wednesday);
            case 5:
                return context.getResources().getString(R.string.thursday);
            case 6:
                return context.getResources().getString(R.string.friday);
            case 7:
                return context.getResources().getString(R.string.saturday);
            default:
                return context.getResources().getString(R.string.sunday);
        }
    }


    public static Bitmap getCroppedBitmap(Bitmap bitmap) {
        Bitmap createBitmap = Bitmap.createBitmap(bitmap.getWidth(), bitmap.getHeight(), Bitmap.Config.ARGB_8888);
        Canvas canvas = new Canvas(createBitmap);
        Paint paint = new Paint();
        Rect rect = new Rect(0, 0, bitmap.getWidth(), bitmap.getHeight());
        paint.setAntiAlias(true);
        canvas.drawARGB(0, 0, 0, 0);
        paint.setColor(-12434878);
        canvas.drawCircle((float) (bitmap.getWidth() / 2), (float) (bitmap.getHeight() / 2), (float) (bitmap.getWidth() / 2), paint);
        paint.setXfermode(new PorterDuffXfermode(PorterDuff.Mode.SRC_IN));
        canvas.drawBitmap(bitmap, rect, rect, paint);
        return createBitmap;
    }


    public static void saveFile(String str, Bitmap bitmap) {
        Throwable th;
        Exception e;
        FileOutputStream fileOutputStream = null;
        if (bitmap != null) {
            try {
                FileOutputStream fileOutputStream2 = null;
                try {
                    try {
                        fileOutputStream = new FileOutputStream(str);
                    } catch (Exception e2) {
                        e = e2;
                    }
                } catch (Throwable th2) {
                    th = th2;
                }
                try {
                    bitmap.compress(Bitmap.CompressFormat.JPEG, 100, fileOutputStream);
                    fileOutputStream.close();
                    return;
                } catch (Exception e3) {
                    e = e3;
                    fileOutputStream2 = fileOutputStream;
                    e.printStackTrace();
                    if (fileOutputStream2 != null) {
                        fileOutputStream2.close();
                        return;
                    }
                    return;
                } catch (Throwable th3) {
                    th = th3;
                    fileOutputStream2 = fileOutputStream;
                    if (fileOutputStream2 != null) {
                        try {
                            fileOutputStream2.close();
                        } catch (IOException e4) {
                            e4.printStackTrace();
                        }
                    }
                }
            } catch (IOException e5) {
                e5.printStackTrace();
            }
        } else {
            return;
        }

    }
}
