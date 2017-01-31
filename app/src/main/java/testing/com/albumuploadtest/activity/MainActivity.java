package testing.com.albumuploadtest.activity;

import android.content.Intent;
import android.os.Bundle;

import testing.com.albumuploadtest.R;

public class MainActivity extends ParentActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        aq.id(R.id.main_small_album_button).clicked(this, "smallAlbumClicked");
        aq.id(R.id.main_large_album_button).clicked(this, "largeAlbumClicked");
    }

    public void smallAlbumClicked() {
        Intent intent = new Intent(MainActivity.this, FolderPickerActivity.class);
        intent.putExtra(EXTRA_NUM_OF_PICS, SMALL_ALBUM_NUM_OF_PICS);
        startActivity(intent);
    }

    public void largeAlbumClicked() {
        Intent intent = new Intent(MainActivity.this, FolderPickerActivity.class);
        intent.putExtra(EXTRA_NUM_OF_PICS, LARGE_ALBUM_NUM_OF_PICS);
        startActivity(intent);
    }

}
