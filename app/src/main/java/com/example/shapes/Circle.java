package com.example.shapes;

import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Point;

import org.json.JSONException;
import org.json.JSONObject;

public class Circle extends Shape {

    public static final String JSON_CENTER_X = "centerX";
    public static final String JSON_CENTER_Y = "centerY";
    public static final String JSON_RADIUS = "radius";
    Point center;
    float radius;

    public Circle(String color, Point center, float radius) {
        super(color);
        this.center = center;
        this.radius = radius;
    }

    public Circle(JSONObject jsonObject) throws JSONException {
        this(
            jsonObject.getString(JSON_COLOR),
            new Point(jsonObject.getInt(JSON_CENTER_X), jsonObject.getInt(JSON_CENTER_Y)),
            (float)jsonObject.getDouble(JSON_RADIUS)
        );
    }

    @Override
    void draw(Canvas canvas, Paint paint) {
        paint.setColor(getColorInt());
        canvas.drawCircle(center.x, center.y, radius, paint);
    }

    @Override
    JSONObject convertToJSON() throws JSONException {
        JSONObject jsonObject = super.convertToJSON();
        jsonObject.put(JSON_CENTER_X, center.x);
        jsonObject.put(JSON_CENTER_Y, center.y);
        jsonObject.put(JSON_RADIUS, radius);

        return jsonObject;
    }
}
