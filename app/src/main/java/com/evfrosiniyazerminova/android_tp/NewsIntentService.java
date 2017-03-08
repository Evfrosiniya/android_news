package com.evfrosiniyazerminova.android_tp;

import android.app.IntentService;
import android.content.Intent;
import android.support.v4.content.LocalBroadcastManager;
import android.widget.Toast;

import java.io.IOException;

import ru.mail.weather.lib.News;
import ru.mail.weather.lib.NewsLoader;
import ru.mail.weather.lib.Storage;


public class NewsIntentService extends IntentService {

    public static final String NEWS_ERROR_ACTION = "NEWS_ERROR_ACTION";
    public static final String NEWS_LOAD_ACTION = "NEWS_LOAD_ACTION";
    public static final String NEWS_CHANGED_ACTION = "NEWS_CHANGED_ACTION";

    public NewsIntentService() {
        super("NewsIntentService");
    }


    @Override
    protected void onHandleIntent(Intent intent) {
        LocalBroadcastManager broadcastManager = LocalBroadcastManager.getInstance(this);
        if (intent != null & NEWS_LOAD_ACTION.equals(intent.getAction())) {

            try {
                NewsLoader newsLoader = new NewsLoader();
                News news = newsLoader.loadNews(Storage.getInstance(this).loadCurrentTopic());
                if (news != null) {
                    Storage.getInstance(this).saveNews(news);

                    broadcastManager.sendBroadcast(new Intent(NEWS_CHANGED_ACTION));
                } else {
                    broadcastManager.sendBroadcast(new Intent(NEWS_ERROR_ACTION));
                }

            } catch(IOException e) {
                broadcastManager.sendBroadcast(new Intent(NEWS_ERROR_ACTION));
                e.printStackTrace();
            }

        }
    }
}
