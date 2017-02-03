package testing.com.albumuploadtest.activity;

import android.content.SharedPreferences;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;

import com.androidquery.AQuery;
import com.google.gson.Gson;

public class ParentActivity extends AppCompatActivity {

    protected static final String PREFS_NAME = "testPrefs";
    protected static final String PREFS_DONT_SHOW_ORDER_TUTORIAL = "dontShowOrderTutorial";

    protected static final String EXTRA_FOLDER_STRING = "extraFolderString";

    protected static final int COVER_CREATION_REQUEST_CODE = 100;
    protected static final int COVER_IMAGE_REQUEST_CODE = 200;

    protected static final int SMALL_ALBUM_NUM_OF_PICS = 8;
    protected static final int LARGE_ALBUM_NUM_OF_PICS = 24;

    protected AQuery aq;
    protected Gson gson;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        aq = new AQuery(this);
        gson = new Gson();
    }

    protected boolean getOrderPicsTutorialPref() {
        SharedPreferences sharedPreferences = getSharedPreferences(PREFS_NAME, MODE_PRIVATE);
        return sharedPreferences.getBoolean(PREFS_DONT_SHOW_ORDER_TUTORIAL, false);
    }

    protected void setOrderPicsTutorialPref(boolean dontShow) {
        getSharedPreferences(PREFS_NAME, MODE_PRIVATE).edit().putBoolean(PREFS_DONT_SHOW_ORDER_TUTORIAL, dontShow).apply();
    }

}
