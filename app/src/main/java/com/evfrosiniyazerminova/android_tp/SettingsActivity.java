package com.evfrosiniyazerminova.android_tp;

import android.content.SharedPreferences;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.RadioGroup;

public class SettingsActivity extends AppCompatActivity {

    public static final String APP_PREFERENCES = "mysettings";

    private SharedPreferences mSettings;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_settings);

        RadioGroup radio = (RadioGroup) findViewById(R.id.categoriesRadioGroup);

        radio.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(RadioGroup group, int checkedId) {
                switch(checkedId) {
                    case R.id.category1:
                        saveText("1");
                        break;
                    case R.id.category2:
                        saveText("2");
                        break;
                    case R.id.category3:
                        saveText("3");
                        break;


                    default:
                        break;
                }
            }
        });


        mSettings = getSharedPreferences(APP_PREFERENCES, MODE_PRIVATE);
        if(mSettings.contains("category")) {
            String category = mSettings.getString("category", "");
            switch (category) {
                case "1":
                    radio.check(R.id.category1);
                    break;
                case "2":
                    radio.check(R.id.category2);
                    break;
                case "3":
                    radio.check(R.id.category3);
                    break;
            }
        } else {
            radio.check(R.id.category1);
        }
    }


    void saveText(String text) {
        mSettings = getSharedPreferences(APP_PREFERENCES, MODE_PRIVATE);
        SharedPreferences.Editor editor = mSettings.edit();
        editor.putString("category", text);
        editor.apply();
    }
}
