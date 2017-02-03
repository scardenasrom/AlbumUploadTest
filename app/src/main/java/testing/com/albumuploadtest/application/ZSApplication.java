package testing.com.albumuploadtest.application;

import android.app.Application;
import android.content.Context;

import com.nostra13.universalimageloader.core.DisplayImageOptions;
import com.nostra13.universalimageloader.core.ImageLoader;
import com.nostra13.universalimageloader.core.ImageLoaderConfiguration;

import testing.com.albumuploadtest.R;
import testing.com.albumuploadtest.dto.AlbumDto;
import testing.com.albumuploadtest.dto.PictureDto;

public class ZSApplication extends Application {

    private static ZSApplication instance;

    private AlbumDto album;

    public ZSApplication() {
        instance = this;
    }

    public static ZSApplication getInstance() {
        return instance;
    }

    public static Context getContext() {
        return instance;
    }

    @Override
    public void onCreate() {
        super.onCreate();

        DisplayImageOptions options = new DisplayImageOptions.Builder()
                .considerExifParams(true)
                .cacheInMemory(true)
                .resetViewBeforeLoading(true)
                .showImageOnLoading(R.drawable.ic_placeholder)
                .build();
        ImageLoaderConfiguration config = new ImageLoaderConfiguration.Builder(this)
                .defaultDisplayImageOptions(options)
                .build();
        ImageLoader.getInstance().init(config);
    }

    public AlbumDto getAlbum() {
        return album;
    }

    public void startAlbum(int totalNumOfPics) {
        album = new AlbumDto(totalNumOfPics);
    }

    public void addPictureToAlbum(PictureDto picture) {
        album.addNewPicture(picture);
    }

    public void removePictureFromAlbum(PictureDto picture) {
        album.removePicture(picture);
    }

    public void clearAlbum() {
        album = null;
    }

    public int getAlbumCurrentNumberOfPics() {
        return album.getPictures().size();
    }

    public int getAlbumTotalNumOfPics() {
        return album.getTotalNumOfPics();
    }

    public void swapPictures(PictureDto pic1, PictureDto pic2) {
        if (pic1 != null && pic2 != null)
            album.swapPictures(pic1, pic2);
    }

}
