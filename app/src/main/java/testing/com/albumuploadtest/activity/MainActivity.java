package testing.com.albumuploadtest.activity;

import android.content.Intent;
import android.os.Bundle;

import testing.com.albumuploadtest.R;
import testing.com.albumuploadtest.application.ZSApplication;

public class MainActivity extends ParentActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        aq.id(R.id.main_small_album_button).clicked(this, "smallAlbumClicked");
        aq.id(R.id.main_large_album_button).clicked(this, "largeAlbumClicked");
    }

    public void smallAlbumClicked() {
        ZSApplication.getInstance().startAlbum(SMALL_ALBUM_NUM_OF_PICS);
        Intent intent = new Intent(MainActivity.this, FolderPickerActivity.class);
        startActivity(intent);
    }

    public void largeAlbumClicked() {
        ZSApplication.getInstance().startAlbum(LARGE_ALBUM_NUM_OF_PICS);
        Intent intent = new Intent(MainActivity.this, FolderPickerActivity.class);
        startActivity(intent);
    }

}
