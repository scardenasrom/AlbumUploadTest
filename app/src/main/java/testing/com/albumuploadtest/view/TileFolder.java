package testing.com.albumuploadtest.view;

import android.content.Context;
import android.util.AttributeSet;
import android.widget.RelativeLayout;

import com.androidquery.AQuery;

import testing.com.albumuploadtest.R;
import testing.com.albumuploadtest.dto.FolderDto;

public class TileFolder extends RelativeLayout {

    private AQuery aq;
    private FolderDto folder;

    public TileFolder(Context context, AttributeSet attrs) {
        super(context, attrs);

        aq = new AQuery(this);
    }

    public void setFolder(FolderDto folder) {
        this.folder = folder;

        aq.id(R.id.tile_folder_name).text(folder.getName());
        aq.id(R.id.tile_folder_pic_count).text("" + folder.getNumPictures());
        //TODO Thumbnail
    }

}
