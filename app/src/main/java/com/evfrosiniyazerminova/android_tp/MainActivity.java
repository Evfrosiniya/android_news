package com.evfrosiniyazerminova.android_tp;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.support.v4.content.LocalBroadcastManager;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.format.DateFormat;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.Date;

import ru.mail.weather.lib.News;
import ru.mail.weather.lib.NewsLoader;
import ru.mail.weather.lib.Storage;
import ru.mail.weather.lib.Topics;

public class MainActivity extends AppCompatActivity {

    Button settingsButton, refreshButton, updateButton;
    TextView titleTextView, textTextView, dateTextView;
    Storage mStorage;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        settingsButton = (Button) findViewById(R.id.settingsButton);
        refreshButton = (Button) findViewById(R.id.refreshButton);
        updateButton = (Button) findViewById(R.id.updateButton);

        titleTextView = (TextView) findViewById(R.id.newsTitle);
        textTextView = (TextView) findViewById(R.id.newsText);
        dateTextView = (TextView) findViewById(R.id.newsDate);

        settingsButton.setOnClickListener(onSettingsClick);
        refreshButton.setOnClickListener(onRefreshClick);

        mStorage = Storage.getInstance(MainActivity.this);

        if (mStorage.loadCurrentTopic() == "" || mStorage.loadCurrentTopic() == null)
            mStorage.saveCurrentTopic(Topics.AUTO);



    }

    private final View.OnClickListener onSettingsClick = new View.OnClickListener() {
        @Override
        public void onClick(View view) {
            final Intent intent = new Intent(MainActivity.this, SettingsActivity.class);
            startActivity(intent);
        }
    };

    private final View.OnClickListener onRefreshClick = new View.OnClickListener() {
        @Override
        public void onClick(View view) {
            Intent intent = new Intent(MainActivity.this, NewsIntentService.class);
            intent.setAction(NewsIntentService.NEWS_LOAD_ACTION);
            startService(intent);
        }
    };

    @Override
    protected void onStart() {
        super.onStart();
        final IntentFilter filter = new IntentFilter();
        filter.addAction(NewsIntentService.NEWS_CHANGED_ACTION);
        filter.addAction(NewsIntentService.NEWS_ERROR_ACTION);
        LocalBroadcastManager.getInstance(MainActivity.this).registerReceiver(receiver, filter);


        News news = mStorage.getLastSavedNews();
        if (news != null) {
            titleTextView.setText(news.getTitle());
            textTextView.setText(news.getBody());
            dateTextView.setText(DateFormat.format("MM/dd/yyyy HH:mm:ss", new Date(news.getDate())).toString());
        }
    }

    private BroadcastReceiver receiver = new BroadcastReceiver() {
        @Override
        public void onReceive(final Context context, final Intent intent) {
            if (intent.getAction().equals(NewsIntentService.NEWS_CHANGED_ACTION)) {
                News news = mStorage.getLastSavedNews();
                titleTextView.setText(news.getTitle());
                textTextView.setText(news.getBody());
                dateTextView.setText(DateFormat.format("MM/dd/yyyy HH:mm:ss", new Date(news.getDate())).toString());

            }
            if (intent.getAction().equals(NewsIntentService.NEWS_ERROR_ACTION)) {
                textTextView.setText("ERROR");
            }
        }
    };
}
