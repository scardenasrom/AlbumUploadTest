package testing.com.albumuploadtest.activity;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.widget.GridView;

import java.util.List;

import testing.com.albumuploadtest.R;
import testing.com.albumuploadtest.view.FolderDto;

public class FolderPickerActivity extends AppCompatActivity {

    private GridView foldersGridView;
    private List<FolderDto> folders;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_folder_picker);

        foldersGridView = (GridView) findViewById(R.id.folder_picker_grid_view);
    }

}
