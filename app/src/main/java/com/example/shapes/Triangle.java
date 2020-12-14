package com.example.shapes;

import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Path;
import android.graphics.Point;

import org.json.JSONException;
import org.json.JSONObject;

public class Triangle extends Shape {
    public static final String JSON_AY = "aY";
    public static final String JSON_AX = "aX";
    public static final String JSON_BX = "bX";
    public static final String JSON_BY = "bY";
    public static final String JSON_CX = "cX";
    public static final String JSON_CY = "cY";

    Point a;
    Point b;
    Point c;

    public Triangle(String color, Point a, Point b, Point c) {
        super(color);
        this.a = a;
        this.b = b;
        this.c = c;
    }

    public Triangle(JSONObject jsonObject) throws JSONException {
        this(
            jsonObject.getString(JSON_COLOR),
            new Point(jsonObject.getInt(JSON_AX), jsonObject.getInt(JSON_AY)),
            new Point(jsonObject.getInt(JSON_BX), jsonObject.getInt(JSON_BY)),
            new Point(jsonObject.getInt(JSON_CX), jsonObject.getInt(JSON_CY))
        );
    }

    @Override
    void draw(Canvas canvas, Paint paint) {
        paint.setColor(getColorInt());

        Path path = new Path();
        path.moveTo(a.x, a.y);
        path.lineTo(b.x, b.y);
        path.lineTo(c.x, c.y);
        path.lineTo(a.x, a.y);

        canvas.drawPath(path, paint);
    }

    @Override
    JSONObject convertToJSON() throws JSONException {
        JSONObject jsonObject = super.convertToJSON();
        jsonObject.put(JSON_AX, a.x);
        jsonObject.put(JSON_AY, a.y);
        jsonObject.put(JSON_BX, b.x);
        jsonObject.put(JSON_BY, b.y);
        jsonObject.put(JSON_CX, c.x);
        jsonObject.put(JSON_CY, c.y);

        return jsonObject;
    }
}
