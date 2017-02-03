package testing.com.albumuploadtest.activity;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.GridView;

import java.util.LinkedList;
import java.util.List;

import testing.com.albumuploadtest.R;
import testing.com.albumuploadtest.dto.FolderDto;
import testing.com.albumuploadtest.partials.TileFolder;
import testing.com.albumuploadtest.utils.PicturesUtils;

public class CoverFolderPickerActivity extends ParentActivity {

    private GridView foldersGridView;
    private List<FolderDto> folders;
    private TileAdapter foldersAdapter;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_cover_folder_picker);

        if (getSupportActionBar() != null)
            getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        loadFolders();

        foldersGridView = (GridView) findViewById(R.id.cover_folder_picker_grid_view);
        foldersAdapter = new TileAdapter(CoverFolderPickerActivity.this);
        foldersGridView.setAdapter(foldersAdapter);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case android.R.id.home:
                finish();
                break;
        }
        return super.onOptionsItemSelected(item);
    }

    private void loadFolders() {
        folders = new LinkedList<>();
        List<FolderDto> auxList = PicturesUtils.getFoldersList(CoverFolderPickerActivity.this);
        for (FolderDto album: auxList) {
            if (!album.getName().equals("AlbumUploadTest")) {
                folders.add(album);
            }
        }
        for (FolderDto folder: folders) {
            Integer count = PicturesUtils.getNumPicturesInFolder(CoverFolderPickerActivity.this, folder);
            folder.setNumPictures(count);
        }
    }

    private void openFolder(FolderDto folder) {
        Intent intent = new Intent(CoverFolderPickerActivity.this, CoverPicturePickerActivity.class);
        intent.putExtra(EXTRA_FOLDER_STRING, gson.toJson(folder, FolderDto.class));
        startActivityForResult(intent, COVER_IMAGE_REQUEST_CODE);
    }

    private class TileAdapter extends BaseAdapter {
        private Context mContext;

        public TileAdapter(Context c) {
            mContext = c;
        }

        public int getCount() {
            return folders.size();
        }

        public Object getItem(int position) {
            return null;
        }

        public long getItemId(int position) {
            return 0;
        }

        public View getView(int position, View convertView, ViewGroup parent) {
            final TileFolder tileView;
            if (convertView == null) {
                tileView = (TileFolder)View.inflate(mContext, R.layout._tile_folder, null);
            } else {
                tileView = (TileFolder)convertView;
            }

            final FolderDto folder = folders.get(position);
            tileView.setFolder(folder);
            tileView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    openFolder(folder);
                }
            });

            return tileView;
        }

        public void refresh() {
            notifyDataSetChanged();
        }
    }

}
