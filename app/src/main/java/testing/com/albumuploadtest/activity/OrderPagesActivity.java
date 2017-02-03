package testing.com.albumuploadtest.activity;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.text.TextUtils;
import android.widget.Toast;

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

        aq.id(R.id.order_pages_cover_image).clicked(this, "editCover");
        aq.id(R.id.order_pages_cover_title).clicked(this, "editCover");
        aq.id(R.id.order_pages_cover_placeholder).clicked(this, "editCover");

        CoverDto testCover = new CoverDto(ZSApplication.getInstance().getAlbum().getPictures().get(0));
        testCover.setTitle("Prueba");
        ZSApplication.getInstance().addCoverToAlbum(testCover);

        setupCover();
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
        Toast.makeText(OrderPagesActivity.this, "Hi!", Toast.LENGTH_SHORT).show();
    }

}
