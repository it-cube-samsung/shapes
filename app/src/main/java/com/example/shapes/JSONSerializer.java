package com.example.shapes;

import android.content.Context;
import android.util.Log;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;
import org.json.JSONTokener;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.io.OutputStreamWriter;
import java.io.Writer;
import java.lang.reflect.Constructor;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class JSONSerializer {
    String fileName;
    Context context;
    Map<String, Class> map = new HashMap<>();
    {
        map.put("rect", Rect.class);
    }

    public JSONSerializer(String fileName, Context context) {
        this.fileName = fileName;
        this.context = context;
    }

    public void save(List<Shape> shapes) throws IOException, JSONException {
        JSONArray jsonArray = new JSONArray();

        for (Shape shape : shapes) {
            jsonArray.put(shape.convertToJSON());
        }

        Writer writer = null;
        try {
            OutputStream out = context.openFileOutput(fileName, context.MODE_PRIVATE);
            writer = new OutputStreamWriter(out);
            writer.write(jsonArray.toString());
        } finally {
            if (writer != null) {
                writer.close();
            }
        }
    }

    List<Shape> load() throws IOException, JSONException, Exception {
        List<Shape> list = new ArrayList<>();
        BufferedReader reader = null;
        try {
            InputStream in = context.openFileInput(fileName);
            reader = new BufferedReader(new InputStreamReader(in));
            StringBuilder jsonString = new StringBuilder();

            String line = null;
            while((line = reader.readLine()) != null) {
                jsonString.append(line);
            }
            JSONArray jsonArray = (JSONArray) new JSONTokener(jsonString.toString()).nextValue();

            for (int i = 0; i < jsonArray.length() ; i++) {
                JSONObject jsonObject = jsonArray.getJSONObject(i);
                String className = jsonObject.getString(Shape.JSON_CLASS_NAME);
                Class<?> c = Class.forName(className);
                Constructor<?> cons = c.getConstructor(JSONObject.class);
                list.add((Shape)cons.newInstance(jsonObject));
            }
        } finally {
            if (reader != null) {
                reader.close();
            }
        }
        return list;
    }
}
