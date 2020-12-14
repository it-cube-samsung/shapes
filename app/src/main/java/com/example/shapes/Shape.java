package com.example.shapes;

import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;

import org.json.JSONException;
import org.json.JSONObject;

abstract public class Shape {
    public final static String JSON_CLASS_NAME = "className";
    public final static String JSON_COLOR = "color";
    String type = "shape";
    String color;


    Shape() {
        this("#000000");
    }

    Shape(String color) {
        this.color = color;
    }

    int getColorInt() {
        return Color.parseColor(this.color);
    }

    abstract void draw(Canvas canvas, Paint paint);

    JSONObject convertToJSON() throws JSONException {
        JSONObject jsonObject = new JSONObject();

        jsonObject.put(JSON_CLASS_NAME, this.getClass().getName());
        jsonObject.put(JSON_COLOR, this.color);

        return jsonObject;
    }
}
