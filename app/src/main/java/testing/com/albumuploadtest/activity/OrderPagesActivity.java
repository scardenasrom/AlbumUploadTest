package testing.com.albumuploadtest.activity;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.text.TextUtils;
import android.view.Gravity;
import android.view.MenuItem;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;

import com.nostra13.universalimageloader.core.ImageLoader;
import com.nostra13.universalimageloader.core.assist.ImageSize;

import java.util.List;

import testing.com.albumuploadtest.R;
import testing.com.albumuploadtest.application.ZSApplication;
import testing.com.albumuploadtest.dto.CoverDto;
import testing.com.albumuploadtest.dto.PictureDto;
import testing.com.albumuploadtest.partials.PicPage;

public class OrderPagesActivity extends ParentActivity {

    private CoverDto cover;

    private LinearLayout pagesContainer;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_order_pages);

        if (getSupportActionBar() != null)
            getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        pagesContainer = (LinearLayout)findViewById(R.id.order_pages_content);
        setupPages();

        aq.id(R.id.order_pages_cover_image).clicked(this, "editCover");
        aq.id(R.id.order_pages_cover_title).clicked(this, "editCover");
        aq.id(R.id.order_pages_cover_placeholder).clicked(this, "editCover");
    }

    @Override
    protected void onResume() {
        super.onResume();
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
                aq.id(R.id.order_pages_cover_title).visible();
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

    private void setupPages() {
        List<PictureDto> albumPictures = ZSApplication.getInstance().getAlbum().getPictures();
//        int numOfPics = albumPictures.size();
        setupAlbumPages(albumPictures);
//        switch (numOfPics) {
//            case SMALL_ALBUM_NUM_OF_PICS:
//                setupSmallAlbumPages(albumPictures);
//                break;
//            case LARGE_ALBUM_NUM_OF_PICS:
//                break;
//        }
    }

    private void setupAlbumPages(List<PictureDto> pictures) {
        int picsPerPage;
        switch (pictures.size()) {
            case SMALL_ALBUM_NUM_OF_PICS:
                picsPerPage = SMALL_ALBUM_PICS_PER_PAGE;
                break;
            case LARGE_ALBUM_NUM_OF_PICS:
            default:
                picsPerPage = LARGE_ALBUM_PICS_PER_PAGE;
                break;
        }
        int numOfPages = pictures.size() / picsPerPage;
        for (int i = 0; i < numOfPages; i = i+2) {
            LinearLayout pagesRow = new LinearLayout(OrderPagesActivity.this);
            LinearLayout.LayoutParams lp = new LinearLayout.LayoutParams(LinearLayout.LayoutParams.MATCH_PARENT, LinearLayout.LayoutParams.WRAP_CONTENT);
            pagesRow.setLayoutParams(lp);
            pagesRow.setGravity(Gravity.CENTER);
            pagesRow.setPadding(30, 30, 30, 30);

            PicPage firstPicPage = new PicPage(OrderPagesActivity.this, picsPerPage, (i%2!=0));
            PicPage secondPicPage = new PicPage(OrderPagesActivity.this, picsPerPage, ((i+1)%2!=0));

            List<PictureDto> picturesForFirstPage = pictures.subList(i * picsPerPage, (i+1) * picsPerPage);
            List<PictureDto> picturesForSecondPage = pictures.subList((i+1) * picsPerPage, (i+2) * picsPerPage);

            firstPicPage.setPictures(picturesForFirstPage);
            secondPicPage.setPictures(picturesForSecondPage);

            pagesRow.addView(firstPicPage);
            pagesRow.addView(secondPicPage);
            pagesContainer.addView(pagesRow);
        }
    }

//    private void setupSmallAlbumPages(List<PictureDto> pictures) {
////        int numOfPages = (pictures.size() / SMALL_ALBUM_PICS_PER_PAGE)+1;
//        int numOfPages = pictures.size() / SMALL_ALBUM_PICS_PER_PAGE;
////        if (numOfPages % 2 == 0) {
//            for (int i = 0; i < numOfPages; i = i+2) {
//                LinearLayout pagesRow = new LinearLayout(OrderPagesActivity.this);
//                LinearLayout.LayoutParams lp = new LinearLayout.LayoutParams(LinearLayout.LayoutParams.MATCH_PARENT, LinearLayout.LayoutParams.WRAP_CONTENT);
//                pagesRow.setLayoutParams(lp);
//                pagesRow.setGravity(Gravity.CENTER);
//                pagesRow.setPadding(30, 30, 30, 30);
//
//                PicPage firstPicPage = new PicPage(OrderPagesActivity.this, SMALL_ALBUM_PICS_PER_PAGE, (i%2!=0));
//                PicPage secondPicPage = new PicPage(OrderPagesActivity.this, SMALL_ALBUM_PICS_PER_PAGE, ((i+1)%2!=0));
//
//                List<PictureDto> picturesForFirstPage = pictures.subList(i * SMALL_ALBUM_PICS_PER_PAGE, (i+1) * SMALL_ALBUM_PICS_PER_PAGE);
//                List<PictureDto> picturesForSecondPage = pictures.subList((i+1) * SMALL_ALBUM_PICS_PER_PAGE, (i+2) * SMALL_ALBUM_PICS_PER_PAGE);
//
//                firstPicPage.setPictures(picturesForFirstPage);
//                secondPicPage.setPictures(picturesForSecondPage);
//
//                pagesRow.addView(firstPicPage);
//                pagesRow.addView(secondPicPage);
//                pagesContainer.addView(pagesRow);
//            }
////        }
//    }

}
