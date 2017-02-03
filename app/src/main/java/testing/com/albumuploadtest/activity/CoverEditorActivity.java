package testing.com.albumuploadtest.activity;

import android.content.Intent;
import android.graphics.drawable.AnimationDrawable;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.text.TextUtils;
import android.view.MenuItem;

import com.nostra13.universalimageloader.core.ImageLoader;
import com.nostra13.universalimageloader.core.assist.ImageSize;

import testing.com.albumuploadtest.R;
import testing.com.albumuploadtest.application.ZSApplication;
import testing.com.albumuploadtest.dto.CoverDto;
import testing.com.albumuploadtest.dto.PictureDto;

public class CoverEditorActivity extends ParentActivity {

    private CoverDto cover;

    private AnimationDrawable gradientAnimation;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_cover_editor);

        if (getSupportActionBar() != null)
            getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        setupFloatingButtonAnimation();
        loadCover();

        aq.id(R.id.cover_editor_placeholder).clicked(this, "pickCover");
        aq.id(R.id.cover_editor_image).clicked(this, "pickCover");
    }

    @Override
    protected void onResume() {
        super.onResume();
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
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case android.R.id.home:
                finish();
                break;
        }
        return super.onOptionsItemSelected(item);
    }

    private void loadCover() {
        cover = ZSApplication.getInstance().getAlbumCover();
        if (cover != null) {
            aq.id(R.id.cover_editor_placeholder).gone();
            PictureDto coverPicture = cover.getPicture();
            if (coverPicture != null) {
                ImageSize imageSize = new ImageSize(300, 300);
                ImageLoader imageLoader = ImageLoader.getInstance();
                imageLoader.displayImage(coverPicture.getUriPreview(), aq.id(R.id.cover_editor_image).getImageView(), imageSize);
            }
            String title = cover.getTitle();
            if (title != null && !TextUtils.isEmpty(title)) {
                aq.id(R.id.cover_editor_title).text(title);
                aq.id(R.id.cover_editor_title).getEditText().setSelection(title.length());
            }
            aq.id(R.id.cover_editor_disabled_button).gone();
        } /*else {
            //TODO Album has no cover
        }*/
    }

    public void pickCover() {
        Intent intent = new Intent(CoverEditorActivity.this, CoverFolderPickerActivity.class);
        intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
        startActivity(intent);
    }

    private void setupFloatingButtonAnimation() {
        gradientAnimation = (AnimationDrawable)aq.id(R.id.cover_editor_confirm_button).getButton().getBackground();
        gradientAnimation.setEnterFadeDuration(1000);
        gradientAnimation.setExitFadeDuration(500);
        gradientAnimation.start();
    }

}
