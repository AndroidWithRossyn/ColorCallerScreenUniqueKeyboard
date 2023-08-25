package com.uprisingscallscreen.theme.flashscreen.utils;
import android.graphics.Bitmap;
import android.graphics.Canvas;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.OutputStream;
import java.util.ArrayList;
import java.util.List;

import pl.droidsonroids.gif.GifDrawable;

public class GifDrawableUtil {
    public static void writeToStream(GifDrawable gifDrawable, OutputStream outputStream) throws IOException {
        List<Bitmap> frameBitmaps = gifDrawableToBitmaps(gifDrawable);
        for (Bitmap frameBitmap : frameBitmaps) {
            byte[] frameData = bitmapToByteArray(frameBitmap);
            outputStream.write(frameData);
            frameBitmap.recycle();
        }
    }

    private static List<Bitmap> gifDrawableToBitmaps(GifDrawable gifDrawable) {
        List<Bitmap> frameBitmaps = new ArrayList<>();
        int width = gifDrawable.getIntrinsicWidth();
        int height = gifDrawable.getIntrinsicHeight();

        Bitmap bitmap = Bitmap.createBitmap(width, height, Bitmap.Config.ARGB_8888);
        Canvas canvas = new Canvas(bitmap);
        gifDrawable.setBounds(0, 0, width, height);

        int frameCount = gifDrawable.getNumberOfFrames();
        for (int i = 0; i < frameCount; i++) {
            gifDrawable.seekToFrame(i);
            gifDrawable.draw(canvas);
            frameBitmaps.add(bitmap.copy(Bitmap.Config.ARGB_8888, true));
        }

        return frameBitmaps;
    }

    private static byte[] bitmapToByteArray(Bitmap bitmap) {
        ByteArrayOutputStream outputStream = new ByteArrayOutputStream();
        bitmap.compress(Bitmap.CompressFormat.PNG, 100, outputStream);
        return outputStream.toByteArray();
    }
}
