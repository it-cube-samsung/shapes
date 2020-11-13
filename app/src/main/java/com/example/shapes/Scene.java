package com.example.shapes;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.DashPathEffect;
import android.graphics.Paint;
import android.graphics.Point;
import android.util.AttributeSet;
import android.util.Log;
import android.view.MotionEvent;
import android.view.View;

import androidx.annotation.Nullable;

public class Scene extends View {
    Point[] points = new Point[3];
    float density;
    int gridSize = 30;
    int gridWidth;
    int gridHeight;
    int countPoints;
    String mode = "draw";
    private String typeShape = "rect";

    Point corner = null;
    int width;
    int height;

    Point center = null;
    int radius;

    public Scene(Context context, @Nullable AttributeSet attrs) {
        super(context, attrs);
        density = getResources().getDisplayMetrics().density;
        gridSize *= density;
    }

//    public Scene(Context context) {
//        super(context);
//    }

    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);
        gridWidth = getWidth() / gridSize;
        gridHeight = getHeight() / gridSize;

        drawGrid(canvas);
        drawPoints(canvas);
        drawRect(canvas);
        drawCircle(canvas);
    }

    private void drawCircle(Canvas canvas) {
        if (center != null) {
            Paint paint = new Paint();
            paint.setStyle(Paint.Style.STROKE);
            paint.setColor(Color.BLACK);
            paint.setStrokeWidth(density);
            canvas.drawCircle(center.x, center.y, radius, paint);
        }
    }

    private void drawRect(Canvas canvas) {
        if (corner != null) {
            Paint paint = new Paint();
            paint.setStyle(Paint.Style.STROKE);
            paint.setColor(Color.BLACK);
            paint.setStrokeWidth(density);
            canvas.drawRect(corner.x, corner.y, corner.x + width, corner.y + height, paint);
        }
    }

    private void drawGrid(Canvas canvas) {
        Paint paint = new Paint();
        paint.setStyle(Paint.Style.STROKE);
        DashPathEffect effects = new DashPathEffect(new float[] { 3, 9}, 0);
        paint.setPathEffect(effects);

        for (int i = 0; i < gridWidth ; i++) {
            int x = i * gridSize;
            canvas.drawLine(x, 0, x, getHeight(), paint);
        }

        for (int i = 0; i < gridHeight ; i++) {
            int y = i * gridSize;
            int endX = getWidth();
            canvas.drawLine(0, y, endX, y, paint);
        }

    }

    private void drawPoints(Canvas canvas) {
        Paint paint = new Paint();
        paint.setColor(Color.RED);

        for (int i = 0; i < countPoints ; i++) {
            Point point = points[i];
            canvas.drawCircle(point.x, point.y, 10, paint);
        }
    }

    @Override
    public boolean onTouchEvent(MotionEvent event) {
        if (event.getAction() == MotionEvent.ACTION_DOWN) {
            onDownTouch((int)event.getX(), (int)event.getY());
        }
        return true;
    }

    private void onDownTouch(int x, int y) {
        if (this.mode.equals("draw")) {
            Log.i("onDownTouch", typeShape);
            switch (this.typeShape) {
                case "rect": addRectPoint(x, y); break;
                case "circle": addCirclePoint(x, y); break;
            }
        }
    }

    private void addCirclePoint(int x, int y) {
        points[countPoints] = new Point(x, y);
        countPoints += 1;
        if (countPoints >= 2) {
            int radius = points[1].x - points[0].x;
            createCircle(new Point(points[0]), radius);
            countPoints = 0;
        }
        invalidate();
    }

    private void createCircle(Point center, int radius) {
        this.center = center;
        this.radius = radius;
        invalidate();
    }

    private void addRectPoint(int x, int y) {
        points[countPoints] = new Point(x, y);
        countPoints += 1;
        if (countPoints >= 3) {
            int width = points[1].x - points[0].x;
            int height = points[2].y - points[0].y;
            createRect(new Point(points[0]), width, height);
            countPoints = 0;
        }
        invalidate();
    }

    private void createRect(Point corner, int width, int height) {
        this.corner = corner;
        this.width = width;
        this.height = height;
    }

    public void setMode(String mode) {
        this.mode = mode;
    }

    public void setTypeShape(String type) {
        this.typeShape = type;
        countPoints = 0;
        invalidate();
    }
}
