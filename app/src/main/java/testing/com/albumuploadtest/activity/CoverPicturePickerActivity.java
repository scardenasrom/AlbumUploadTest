package testing.com.albumuploadtest.activity;

import android.content.Context;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.GridView;

import java.util.List;

import testing.com.albumuploadtest.R;
import testing.com.albumuploadtest.application.ZSApplication;
import testing.com.albumuploadtest.dto.FolderDto;
import testing.com.albumuploadtest.dto.PictureDto;
import testing.com.albumuploadtest.partials.TilePicture;
import testing.com.albumuploadtest.utils.PicturesUtils;

public class CoverPicturePickerActivity extends ParentActivity {

    private GridView picturesGridView;

    private FolderDto folder;
    private List<PictureDto> pictures;
    private TileAdapter picturesAdapter;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_cover_picture_picker);

        if (getSupportActionBar() != null)
            getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        folder = gson.fromJson(getIntent().getStringExtra(EXTRA_FOLDER_STRING), FolderDto.class);

        picturesGridView = (GridView)findViewById(R.id.cover_picture_grid_view);
        picturesAdapter = new TileAdapter(CoverPicturePickerActivity.this);

        loadPictures();
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

    private void loadPictures() {
        pictures = PicturesUtils.getPicturesList(CoverPicturePickerActivity.this, folder);
        if (getSupportActionBar() != null)
            getSupportActionBar().setTitle(folder.getName());
        picturesGridView.setAdapter(picturesAdapter);
    }

    private class TileAdapter extends BaseAdapter {
        private Context mContext;

        public TileAdapter(Context c) {
            mContext = c;
        }

        public int getCount() {
            return pictures.size();
        }

        public Object getItem(int position) {
            return null;
        }

        public long getItemId(int position) {
            return 0;
        }

        public View getView(int position, View convertView, ViewGroup parent) {
            final TilePicture tileView;
            if (convertView == null) {
                tileView = (TilePicture)View.inflate(mContext, R.layout._tile_picture, null);
            } else {
                tileView = (TilePicture)convertView;
            }

            final PictureDto picture = pictures.get(position);
            tileView.setPicture(picture);
            tileView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    PictureDto coverPicture = tileView.getPicture();
                    //TODO
                }
            });

            return tileView;
        }

        public void refresh() {
            notifyDataSetChanged();
        }
    }

}
