package testing.com.albumuploadtest.activity;

import android.os.Bundle;
import android.support.annotation.Nullable;

import testing.com.albumuploadtest.R;

public class PicturePickerActivity extends ParentActivity {

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_picture_picker);
    }
}
