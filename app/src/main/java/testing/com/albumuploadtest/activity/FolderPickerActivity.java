package testing.com.albumuploadtest.activity;

import android.content.Context;
import android.content.Intent;
import android.graphics.drawable.AnimationDrawable;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.design.widget.Snackbar;
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

import java.util.LinkedList;
import java.util.List;

import testing.com.albumuploadtest.R;
import testing.com.albumuploadtest.application.ZSApplication;
import testing.com.albumuploadtest.dto.AlbumDto;
import testing.com.albumuploadtest.dto.FolderDto;
import testing.com.albumuploadtest.partials.TileFolder;
import testing.com.albumuploadtest.utils.PicturesUtils;

public class FolderPickerActivity extends ParentActivity {

    private GridView foldersGridView;
    private List<FolderDto> folders;
    private TileAdapter foldersAdapter;
    private TextView picCountTextView;

    private AnimationDrawable gradientAnimation;

    private boolean exiting = false;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_folder_picker);

        if (getSupportActionBar() != null)
            getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        setupFloatingButtonAnimation();
        loadFolders();

        foldersGridView = (GridView) findViewById(R.id.folder_picker_grid_view);
        foldersAdapter = new TileAdapter(FolderPickerActivity.this);
        foldersGridView.setAdapter(foldersAdapter);

        aq.id(R.id.folder_picker_confirm_button).clicked(this, "confirmSelection");
    }

    @Override
    protected void onResume() {
        super.onResume();
        updatePicsCount();
        updateFolderBadges();
        if (gradientAnimation != null && !gradientAnimation.isRunning())
            gradientAnimation.start();
    }

    @Override
    protected void onPause() {
        super.onPause();
        if (gradientAnimation != null && gradientAnimation.isRunning())
            gradientAnimation.stop();
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
                handleExit();
                break;
        }
        return super.onOptionsItemSelected(item);
    }

    @Override
    public void onBackPressed() {
        handleExit();
    }

    private void setupFloatingButtonAnimation() {
        gradientAnimation = (AnimationDrawable)aq.id(R.id.folder_picker_confirm_button).getButton().getBackground();
        gradientAnimation.setEnterFadeDuration(1000);
        gradientAnimation.setExitFadeDuration(500);
        gradientAnimation.start();
    }

    private void updatePicsCount() {
        if (picCountTextView != null)
            picCountTextView.setText(ZSApplication.getInstance().getAlbumCurrentNumberOfPics() + "/" + ZSApplication.getInstance().getAlbumTotalNumOfPics());
        if (ZSApplication.getInstance().getAlbumCurrentNumberOfPics() == ZSApplication.getInstance().getAlbumTotalNumOfPics()) {
            aq.id(R.id.folder_picker_dummy_button).visible();
            aq.id(R.id.folder_picker_dummy_button).getButton().setAnimation(AnimationUtils.loadAnimation(FolderPickerActivity.this, R.anim.fade_in));
            aq.id(R.id.folder_picker_confirm_button).visible();
            aq.id(R.id.folder_picker_confirm_button).getButton().setAnimation(AnimationUtils.loadAnimation(FolderPickerActivity.this, R.anim.fade_in));
        } else {
            aq.id(R.id.folder_picker_dummy_button).getButton().setAnimation(AnimationUtils.loadAnimation(FolderPickerActivity.this, R.anim.fade_out));
            aq.id(R.id.folder_picker_dummy_button).gone();
            aq.id(R.id.folder_picker_confirm_button).getButton().setAnimation(AnimationUtils.loadAnimation(FolderPickerActivity.this, R.anim.fade_out));
            aq.id(R.id.folder_picker_confirm_button).gone();
        }
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
            folder.setNumOfSelectedPics(PicturesUtils.getNumOfSelectedPicturesInFolder(folder));
        }
    }

    private void updateFolderBadges() {
        if (folders != null && !folders.isEmpty()) {
            for (FolderDto folder: folders) {
                folder.setNumOfSelectedPics(PicturesUtils.getNumOfSelectedPicturesInFolder(folder));
            }
        }
        foldersAdapter.notifyDataSetChanged();
    }

    public void openFolder(FolderDto folder) {
        Intent intent = new Intent(FolderPickerActivity.this, PicturePickerActivity.class);
        intent.putExtra(EXTRA_FOLDER_STRING, gson.toJson(folder, FolderDto.class));
        startActivity(intent);
    }

    private void handleExit() {
        if (!exiting) {
            AlbumDto album = ZSApplication.getInstance().getAlbum();
            if (album != null && album.getPictures().size() > 0) {
                exiting = true;
                Snackbar confirmExitSnackbar = Snackbar.make(foldersGridView, "Pulse atrás otra vez para salir descartar el álbum", Snackbar.LENGTH_LONG);
                confirmExitSnackbar.addCallback(new Snackbar.Callback(){
                    @Override
                    public void onDismissed(Snackbar transientBottomBar, int event) {
                        exiting = false;
                    }
                });
                confirmExitSnackbar.show();
            } else {
                ZSApplication.getInstance().clearAlbum();
                finish();
            }
        } else {
            finish();
        }
    }

    public void confirmSelection() {
        Intent intent = new Intent(FolderPickerActivity.this, OrderPicturesActivity.class);
        startActivity(intent);
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
