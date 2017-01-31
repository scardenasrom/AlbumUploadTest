package testing.com.albumuploadtest.activity;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;

import com.androidquery.AQuery;

public class ParentActivity extends AppCompatActivity {

    protected static final String EXTRA_NUM_OF_PICS = "extraNumOfPics";

    protected static final int SMALL_ALBUM_NUM_OF_PICS = 24;
    protected static final int LARGE_ALBUM_NUM_OF_PICS = 96;

    protected AQuery aq;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        aq = new AQuery(this);
    }

}
