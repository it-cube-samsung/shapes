package com.example.shapes;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.util.Log;
import android.widget.RadioGroup;
import android.widget.RadioGroup.OnCheckedChangeListener;

public class MainActivity extends AppCompatActivity {
    RadioGroup shapes;
    Scene scene;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        scene = findViewById(R.id.scene);
        shapes = findViewById(R.id.shapeGroup);
        shapes.setOnCheckedChangeListener(new OnCheckedChangeListener(){
            @Override
            public void onCheckedChanged(RadioGroup group, int checkedId) {
                switch (checkedId) {
                    case R.id.rect: scene.setTypeShape("rect"); break;
                    case R.id.circle: scene.setTypeShape("circle"); break;
                }
            }
        });
    }
}