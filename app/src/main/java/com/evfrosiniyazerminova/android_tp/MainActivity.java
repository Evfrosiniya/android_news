package com.evfrosiniyazerminova.android_tp;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;

import java.io.IOException;

import ru.mail.weather.lib.NewsLoader;
import ru.mail.weather.lib.Topics;

public class MainActivity extends AppCompatActivity {

    private final View.OnClickListener onSettingsClick = new View.OnClickListener() {
        @Override
        public void onClick(View view) {
            final Intent intent = new Intent(MainActivity.this, SettingsActivity.class);
            startActivity(intent);
        }
    };




    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        findViewById(R.id.settingsButton).setOnClickListener(onSettingsClick);

        try {
                NewsLoader newsLoader = new NewsLoader();
                newsLoader.loadNews(Topics.AUTO);
            }
        } catch(IOException) {
            ie.printStackTrace();
        }
    }
}
