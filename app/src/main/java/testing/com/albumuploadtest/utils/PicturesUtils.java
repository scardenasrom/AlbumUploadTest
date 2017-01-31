package testing.com.albumuploadtest.utils;

import android.content.Context;
import android.database.Cursor;
import android.net.Uri;
import android.provider.MediaStore;
import android.util.Log;

import java.util.LinkedList;
import java.util.List;

import testing.com.albumuploadtest.dto.FolderDto;

public class PicturesUtils {

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
        Cursor cursor = getPicturesInAlbumCursor(context, folder);
        if (cursor.moveToFirst()) {
            res = cursor.getCount();
        }

        cursor.close();

        return res;
    }

    public static Cursor getPicturesInAlbumCursor(Context context, FolderDto folder) {
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
