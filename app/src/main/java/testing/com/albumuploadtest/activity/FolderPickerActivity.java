package testing.com.albumuploadtest.activity;

import android.content.Context;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.GridView;

import java.util.LinkedList;
import java.util.List;

import testing.com.albumuploadtest.R;
import testing.com.albumuploadtest.dto.FolderDto;
import testing.com.albumuploadtest.utils.PicturesUtils;
import testing.com.albumuploadtest.view.TileFolder;

public class FolderPickerActivity extends ParentActivity {

    private GridView foldersGridView;
    private List<FolderDto> folders;
    private TileAdapter foldersAdapter;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_folder_picker);

        loadFolders();

        foldersGridView = (GridView) findViewById(R.id.folder_picker_grid_view);
        foldersAdapter = new TileAdapter(FolderPickerActivity.this);
        foldersGridView.setAdapter(foldersAdapter);
    }

    private void loadFolders() {
        folders = new LinkedList<>();
        List<FolderDto> auxList = PicturesUtils.getFoldersList(FolderPickerActivity.this);
        for (FolderDto album: auxList) {
            if (!album.getName().equals("AlbumUploadTest")) {
                folders.add(album);
            }
        }
        for (FolderDto folder: folders) {
            Integer count = PicturesUtils.getNumPicturesInFolder(FolderPickerActivity.this, folder);
            folder.setNumPictures(count);
        }
    }

    public void openFolder(FolderDto folderDto) {

    }

    private class TileAdapter extends BaseAdapter {

        private Context context;

        private TileAdapter(Context context) {
            this.context = context;
        }

        @Override
        public int getCount() {
            return folders.size();
        }

        @Override
        public Object getItem(int i) {
            return null;
        }

        @Override
        public long getItemId(int i) {
            return 0;
        }

        @Override
        public View getView(int i, View view, ViewGroup viewGroup) {
            final TileFolder tileFolder;
            if (view == null) {
                tileFolder = (TileFolder)View.inflate(context, R.layout._tile_folder, null);
            } else {
                tileFolder = (TileFolder)view;
            }

            final FolderDto folder = folders.get(i);
            tileFolder.setFolder(folder);
            tileFolder.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    openFolder(folder);
                }
            });
            return tileFolder;
        }

        public void refresh() {
            notifyDataSetChanged();
        }

    }

}
