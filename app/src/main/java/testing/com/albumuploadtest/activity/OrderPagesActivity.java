package testing.com.albumuploadtest.activity;

import android.content.Intent;
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

public class OrderPagesActivity extends ParentActivity {

    private CoverDto cover;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_order_pages);

        if (getSupportActionBar() != null)
            getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        aq.id(R.id.order_pages_cover_image).clicked(this, "editCover");
        aq.id(R.id.order_pages_cover_title).clicked(this, "editCover");
        aq.id(R.id.order_pages_cover_placeholder).clicked(this, "editCover");

        CoverDto testCover = new CoverDto(ZSApplication.getInstance().getAlbum().getPictures().get(0));
        testCover.setTitle("Prueba");
        ZSApplication.getInstance().addCoverToAlbum(testCover);

        setupCover();
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case android.R.id.home:
                break;
        }
        return super.onOptionsItemSelected(item);
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        switch (requestCode) {
            case COVER_CREATION_REQUEST_CODE:
                setupCover();
                break;
        }
    }

    private void setupCover() {
        cover = ZSApplication.getInstance().getAlbumCover();
        if (cover != null) {
            aq.id(R.id.order_pages_cover_placeholder).invisible();
            String title = cover.getTitle();
            if (title != null && !TextUtils.isEmpty(title)) {
                aq.id(R.id.order_pages_cover_title).text(title);
            } else {
                aq.id(R.id.order_pages_cover_title).invisible();
            }
            PictureDto coverPicture = cover.getPicture();
            if (coverPicture != null) {
                ImageSize imageSize = new ImageSize(200, 150);
                ImageLoader imageLoader = ImageLoader.getInstance();
                imageLoader.displayImage(coverPicture.getUriPreview(), aq.id(R.id.order_pages_cover_image).getImageView(), imageSize);
            }
        }
    }

    public void editCover() {
        Intent intent = new Intent(OrderPagesActivity.this, CoverEditorActivity.class);
        startActivityForResult(intent, COVER_CREATION_REQUEST_CODE);
    }

}
