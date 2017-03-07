package com.evfrosiniyazerminova.android_tp;

import android.content.SharedPreferences;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.RadioGroup;

import ru.mail.weather.lib.Storage;
import ru.mail.weather.lib.Topics;

public class SettingsActivity extends AppCompatActivity {

    Storage mStorage;
    public static final String auto = "auto";
    public static final String it = "it";
    public static final String health = "health";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_settings);

        mStorage = Storage.getInstance(SettingsActivity.this);

        RadioGroup radio = (RadioGroup) findViewById(R.id.categoriesRadioGroup);

        switch (mStorage.loadCurrentTopic()) {
            case auto:
                radio.check(R.id.category1);
                break;
            case it:
                radio.check(R.id.category2);
                break;
            case health:
                radio.check(R.id.category3);
                break;
        }

        radio.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(RadioGroup group, int checkedId) {
                switch(checkedId) {
                    case R.id.category1:
                        mStorage.saveCurrentTopic(Topics.AUTO);
                        break;
                    case R.id.category2:
                        mStorage.saveCurrentTopic(Topics.IT);
                        break;
                    case R.id.category3:
                        mStorage.saveCurrentTopic(Topics.HEALTH);
                        break;


                    default:
                        break;
                }
            }
        });

    }

}
