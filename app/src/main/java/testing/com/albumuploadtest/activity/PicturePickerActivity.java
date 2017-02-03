package testing.com.albumuploadtest.activity;

import android.content.Context;
import android.content.Intent;
import android.graphics.drawable.AnimationDrawable;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.view.MenuItemCompat;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.AnimationUtils;
import android.widget.BaseAdapter;
import android.widget.GridView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import java.util.List;

import testing.com.albumuploadtest.R;
import testing.com.albumuploadtest.application.ZSApplication;
import testing.com.albumuploadtest.dto.FolderDto;
import testing.com.albumuploadtest.dto.PictureDto;
import testing.com.albumuploadtest.partials.TilePicture;
import testing.com.albumuploadtest.utils.PicturesUtils;

public class PicturePickerActivity extends ParentActivity {

    private GridView picturesGridView;
    private TextView picCountTextView;

    private FolderDto folder;
    private List<PictureDto> pictures;
    private TileAdapter picturesAdapter;

    private AnimationDrawable gradientAnimation;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_picture_picker);

        if (getSupportActionBar() != null)
            getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        folder = gson.fromJson(getIntent().getStringExtra(EXTRA_FOLDER_STRING), FolderDto.class);

        picturesGridView = (GridView)findViewById(R.id.picture_picker_grid_view);
        picturesAdapter = new TileAdapter(PicturePickerActivity.this);

        setupFloatingButtonAnimation();
        loadPictures();

        aq.id(R.id.picture_picker_confirm_button).clicked(this, "confirmSelection");
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater menuInflater = getMenuInflater();
        menuInflater.inflate(R.menu.menu_folder_picker, menu);

        MenuItem menuItem = menu.findItem(R.id.pics_count_item);
        MenuItemCompat.setActionView(menuItem, R.layout._pics_count);
        RelativeLayout rootView = (RelativeLayout)MenuItemCompat.getActionView(menuItem);
        picCountTextView = (TextView)rootView.findViewById(R.id.view_pics_count);
        updatePicsCount();

        return super.onCreateOptionsMenu(menu);
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

    private void setupFloatingButtonAnimation() {
        gradientAnimation = (AnimationDrawable)aq.id(R.id.picture_picker_confirm_button).getButton().getBackground();
        gradientAnimation.setEnterFadeDuration(1000);
        gradientAnimation.setExitFadeDuration(500);
        gradientAnimation.start();
    }

    private void updatePicsCount() {
        if (picCountTextView != null)
            picCountTextView.setText(ZSApplication.getInstance().getAlbumCurrentNumberOfPics() + "/" + ZSApplication.getInstance().getAlbumTotalNumOfPics());
        if (ZSApplication.getInstance().getAlbumCurrentNumberOfPics() == ZSApplication.getInstance().getAlbumTotalNumOfPics()) {
            aq.id(R.id.picture_picker_dummy_button).visible();
            aq.id(R.id.picture_picker_dummy_button).getButton().setAnimation(AnimationUtils.loadAnimation(PicturePickerActivity.this, R.anim.fade_in));
            aq.id(R.id.picture_picker_confirm_button).visible();
            aq.id(R.id.picture_picker_confirm_button).getButton().setAnimation(AnimationUtils.loadAnimation(PicturePickerActivity.this, R.anim.fade_in));
        } else {
            aq.id(R.id.picture_picker_dummy_button).getButton().setAnimation(AnimationUtils.loadAnimation(PicturePickerActivity.this, R.anim.fade_out));
            aq.id(R.id.picture_picker_dummy_button).gone();
            aq.id(R.id.picture_picker_confirm_button).getButton().setAnimation(AnimationUtils.loadAnimation(PicturePickerActivity.this, R.anim.fade_out));
            aq.id(R.id.picture_picker_confirm_button).gone();
        }
    }

    private void loadPictures() {
        pictures = PicturesUtils.getPicturesList(PicturePickerActivity.this, folder);
        List<PictureDto> albumPictures = ZSApplication.getInstance().getAlbum().getPictures();
        for (PictureDto currentAlbumPicture: albumPictures) {
            for (PictureDto currentPicture: pictures) {
                if (currentAlbumPicture.getId().equals(currentPicture.getId()))
                    currentPicture.setSelected(true);
            }
        }
        if (getSupportActionBar() != null)
            getSupportActionBar().setTitle(folder.getName());
        picturesGridView.setAdapter(picturesAdapter);
    }

    private void selectPicture(PictureDto picture) {
        picture.setFolderName(folder.getName());
        if (!picture.isSelected()) {
            ZSApplication.getInstance().removePictureFromAlbum(picture);
        } else {
            ZSApplication.getInstance().addPictureToAlbum(picture);
        }
        updatePicsCount();
    }

    public void confirmSelection() {
        Intent intent = new Intent(PicturePickerActivity.this, OrderPicturesActivity.class);
        startActivity(intent);
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
                    if (ZSApplication.getInstance().getAlbumCurrentNumberOfPics() != ZSApplication.getInstance().getAlbumTotalNumOfPics()) {
                        tileView.switchSelection();
                        selectPicture(tileView.getPicture());
                    } else {
                        if (tileView.getPicture().isSelected()) {
                            tileView.switchSelection();
                            selectPicture(tileView.getPicture());
                        }
                    }
                }
            });

            return tileView;
        }

        public void refresh() {
            notifyDataSetChanged();
        }
    }

}
