package testing.com.albumuploadtest.utils;

import android.content.Context;
import android.content.Intent;
import android.database.Cursor;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.provider.MediaStore;
import android.util.Log;

import java.io.File;
import java.util.LinkedList;
import java.util.List;

import testing.com.albumuploadtest.application.ZSApplication;
import testing.com.albumuploadtest.dto.FolderDto;
import testing.com.albumuploadtest.dto.PictureDto;
import testing.com.albumuploadtest.dto.PictureResolutionDto;

public class PicturesUtils {

    //region Folder Operations
    public static List<FolderDto> getFoldersList(Context context) {
        List<FolderDto> res = new LinkedList<>();

        String[] projection = new String[]{
                MediaStore.Images.ImageColumns.BUCKET_ID,
                MediaStore.Images.ImageColumns.BUCKET_DISPLAY_NAME,
                MediaStore.Images.ImageColumns.DATA,
                MediaStore.Images.ImageColumns._ID
        };

        String groupBy = "1) GROUP BY 1,(2";
        String orderBy = "MAX(datetaken) DESC";

        // content:// style URI for the "primary" external storage volume
        Uri images = MediaStore.Images.Media.EXTERNAL_CONTENT_URI;

        Cursor cursor1 = null;

        cursor1 = context.getContentResolver().query(images,
                projection, // Which columns to return
                groupBy,       // Which rows to return (all rows)
                null,       // Selection arguments (none)
                orderBy        // Ordering
        );

        Log.i("ListingImages", " query count=" + cursor1.getCount());

        if (cursor1.moveToFirst()) {
            Integer bucketId;
            String bucket;
            String image;
            String thumbnail = "";
            Integer id;
            int bucketIdColumn = cursor1.getColumnIndex(MediaStore.Images.Media.BUCKET_ID);
            int bucketColumn = cursor1.getColumnIndex(MediaStore.Images.Media.BUCKET_DISPLAY_NAME);
            int dataColumn = cursor1.getColumnIndex(MediaStore.Images.Media.DATA);
            int idColumn = cursor1.getColumnIndex(MediaStore.Images.Media._ID);

            do {
                bucketId = cursor1.getInt(bucketIdColumn);
                bucket = cursor1.getString(bucketColumn);
                image = cursor1.getString(dataColumn);
                id = cursor1.getInt(idColumn);

                /* No necesito los thumbnails
                Cursor cursor2 = MediaStore.Images.Thumbnails.queryMiniThumbnail(ZSApplication.getContext().getContentResolver(), id, MediaStore.Images.Thumbnails.MINI_KIND, null);
                if(cursor2 != null && cursor2.getCount() > 0) {
                    cursor2.moveToFirst();
                    thumbnail = cursor2.getString(cursor2.getColumnIndex(MediaStore.Images.Thumbnails.DATA));
                }
                */
                //cursor2.close();
                res.add(new FolderDto(bucketId, bucket, image.substring(0, image.lastIndexOf("/")), 58, image, thumbnail));

            } while (cursor1.moveToNext());

        }

        cursor1.close();

        return res;
    }

    public static Integer getNumPicturesInFolder(Context context, FolderDto folder) {
        int res = 0;
        Cursor cursor = getPicturesInFolderCursor(context, folder);
        if (cursor.moveToFirst()) {
            res = cursor.getCount();
        }

        cursor.close();

        return res;
    }

    public static Integer getNumOfSelectedPicturesInFolder(FolderDto folder) {
        Integer i = 0;
        List<PictureDto> albumPictures = ZSApplication.getInstance().getAlbum().getPictures();
        for (PictureDto picture: albumPictures) {
            if (folder.getName().equals(picture.getFolderName()))
                i++;
        }
        return i;
    }
    //endregion

    //region Pictures Operations
    public static List<PictureDto> getPicturesList(Context context, FolderDto folder) {
        List<PictureDto> res = new LinkedList<>();

        Cursor cursor = getPicturesInFolderCursor(context, folder);

        if (cursor.moveToFirst()) {
            String image;
            String thumbnail;
            Integer id;
            int dataColumn = cursor.getColumnIndex(MediaStore.Images.Media.DATA);
            int idColumn = cursor.getColumnIndex(MediaStore.Images.Media._ID);

            do {
                thumbnail = "";
                image = cursor.getString(dataColumn);
                id = cursor.getInt(idColumn);
                File tempFile = new File(image);
                PictureResolutionDto tempFileResolution = getPictureResolution(tempFile);

                PictureDto picture = new PictureDto(id, image, thumbnail, tempFileResolution.getWidth(), tempFileResolution.getHeight());
                res.add(picture);
            } while (cursor.moveToNext());
        }

        cursor.close();
        return res;
    }

    public static PictureResolutionDto getPictureResolution(File file) {
        final BitmapFactory.Options options = new BitmapFactory.Options();
        options.inJustDecodeBounds = true;
        BitmapFactory.decodeFile(file.getPath(), options);
        return new PictureResolutionDto(options.outWidth, options.outHeight);
    }
    //endregion

    public static Cursor getPicturesInFolderCursor(Context context, FolderDto folder) {
        String[] projection = new String[]{
                MediaStore.Images.ImageColumns.BUCKET_DISPLAY_NAME,
                MediaStore.Images.ImageColumns.DATA,
                MediaStore.Images.ImageColumns._ID
        };

        String where = MediaStore.Images.ImageColumns.BUCKET_DISPLAY_NAME + " = '" + folder.getName() + "'";
        String orderBy = MediaStore.Images.Media.DATE_TAKEN + " DESC";

        Cursor cursor = null;

        Uri images = MediaStore.Images.Media.EXTERNAL_CONTENT_URI;
        cursor = context.getContentResolver().query(images,
                projection, // Which columns to return
                where,       // Which rows to return (all rows)
                null,       // Selection arguments (none)
                orderBy
        );

        return cursor;
    }

}
