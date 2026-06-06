package com.moodtrip.app;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.LinearGradient;
import android.graphics.Paint;
import android.graphics.Path;
import android.graphics.RectF;
import android.graphics.Shader;
import android.view.View;

import java.util.ArrayList;
import java.util.List;

class MoodMapView extends View {
    private final Paint paint = new Paint(Paint.ANTI_ALIAS_FLAG);
    private List<TravelSpot> spots = new ArrayList<>();

    MoodMapView(Context context) {
        super(context);
        setMinimumHeight(dp(210));
    }

    void setSpots(List<TravelSpot> spots) {
        this.spots = spots;
        invalidate();
    }

    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);
        int width = getWidth();
        int height = getHeight();
        RectF bounds = new RectF(0, 0, width, height);

        paint.setShader(new LinearGradient(0, 0, width, height, Color.rgb(229, 240, 229),
                Color.rgb(238, 230, 214), Shader.TileMode.CLAMP));
        canvas.drawRoundRect(bounds, dp(18), dp(18), paint);
        paint.setShader(null);

        drawRoad(canvas, width, height, 0.18f, 0.78f, 0.88f, 0.22f);
        drawRoad(canvas, width, height, 0.08f, 0.35f, 0.92f, 0.55f);
        drawRiver(canvas, width, height);

        for (TravelSpot spot : spots) {
            drawPin(canvas, spot.mapX * width, spot.mapY * height, spot.containsTag("pedas"));
        }

        paint.setColor(Color.argb(150, 255, 255, 255));
        canvas.drawRoundRect(new RectF(dp(14), dp(14), dp(136), dp(44)), dp(14), dp(14), paint);
        paint.setColor(Color.rgb(31, 48, 45));
        paint.setTextSize(dp(12));
        paint.setFakeBoldText(true);
        canvas.drawText("Mood map MVP", dp(24), dp(34), paint);
        paint.setFakeBoldText(false);
    }

    private void drawRoad(Canvas canvas, int width, int height, float sx, float sy, float ex, float ey) {
        paint.setColor(Color.argb(180, 255, 255, 255));
        paint.setStrokeWidth(dp(10));
        paint.setStyle(Paint.Style.STROKE);
        paint.setStrokeCap(Paint.Cap.ROUND);
        canvas.drawLine(sx * width, sy * height, ex * width, ey * height, paint);
        paint.setColor(Color.argb(120, 187, 170, 145));
        paint.setStrokeWidth(dp(2));
        canvas.drawLine(sx * width, sy * height, ex * width, ey * height, paint);
        paint.setStyle(Paint.Style.FILL);
    }

    private void drawRiver(Canvas canvas, int width, int height) {
        Path path = new Path();
        path.moveTo(0, height * 0.58f);
        path.cubicTo(width * 0.22f, height * 0.48f, width * 0.38f, height * 0.80f, width * 0.60f, height * 0.64f);
        path.cubicTo(width * 0.78f, height * 0.52f, width * 0.86f, height * 0.66f, width, height * 0.58f);
        paint.setColor(Color.argb(115, 92, 152, 170));
        paint.setStrokeWidth(dp(18));
        paint.setStrokeCap(Paint.Cap.ROUND);
        paint.setStyle(Paint.Style.STROKE);
        canvas.drawPath(path, paint);
        paint.setStyle(Paint.Style.FILL);
    }

    private void drawPin(Canvas canvas, float x, float y, boolean accent) {
        paint.setColor(accent ? Color.rgb(230, 90, 61) : Color.rgb(18, 108, 98));
        canvas.drawCircle(x, y, dp(10), paint);
        paint.setColor(Color.WHITE);
        canvas.drawCircle(x, y, dp(4), paint);
    }

    private int dp(int value) {
        return (int) (value * getResources().getDisplayMetrics().density + 0.5f);
    }
}
