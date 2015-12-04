package com.misrotostudio.www.myapplication;

import android.content.Intent;
import android.graphics.drawable.BitmapDrawable;
import android.graphics.drawable.Drawable;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ImageButton;

public class SigActivity extends AppCompatActivity {

    private ImageButton sig_button;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sig);

        sig_button = (ImageButton) findViewById(R.id.sig_button);

        sig_button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i = new Intent(SigActivity.this, DetailActivity.class);
                startActivity(i);
            }
        });
    }
}
