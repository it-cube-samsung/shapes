package com.example.shapes;

import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Point;

import org.json.JSONException;
import org.json.JSONObject;

public class Rect extends Shape {
    public final static String JSON_CORNER_X = "cornerX";
    public final static String JSON_CORNER_Y = "cornerY";
    public final static String JSON_WIDTH = "width";
    public final static String JSON_HEIGHT = "height";
    Point corner;
    int width;
    int height;

    public Rect(String color, Point corner, int width, int height) {
        super(color);
        this.corner = corner;
        this.width = width;
        this.height = height;
    }

    public Rect(JSONObject jsonObject) throws JSONException {
        this(
                jsonObject.getString(JSON_COLOR),
                new Point(jsonObject.getInt(JSON_CORNER_X), jsonObject.getInt(JSON_CORNER_Y)),
                jsonObject.getInt(JSON_WIDTH),
                jsonObject.getInt(JSON_HEIGHT)
        );
    }

    @Override
    void draw(Canvas canvas, Paint paint) {
        paint.setColor(getColorInt());
        canvas.drawRect(corner.x, corner.y, corner.x + width, corner.y + height, paint);
    }

    @Override
    JSONObject convertToJSON() throws JSONException {
        JSONObject jsonObject = super.convertToJSON();
        jsonObject.put(JSON_CORNER_X, corner.x);
        jsonObject.put(JSON_CORNER_Y, corner.y);
        jsonObject.put(JSON_WIDTH, width);
        jsonObject.put(JSON_HEIGHT, height);

        return jsonObject;
    }
}
