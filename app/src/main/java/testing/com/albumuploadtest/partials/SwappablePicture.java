package testing.com.albumuploadtest.partials;

import android.content.Context;
import android.view.LayoutInflater;
import android.widget.RelativeLayout;

import com.androidquery.AQuery;
import com.nostra13.universalimageloader.core.ImageLoader;
import com.nostra13.universalimageloader.core.assist.ImageSize;

import testing.com.albumuploadtest.R;
import testing.com.albumuploadtest.dto.PictureDto;

public class SwappablePicture extends RelativeLayout {

    private Context context;
    private AQuery aq;
    private PictureDto picture;
    private boolean selected = false;

    public SwappablePicture(Context context, PictureDto picture) {
        super(context);
        initialize(context, picture);
    }

    public void initialize(Context context, PictureDto picture) {
        LayoutInflater layoutInflater = LayoutInflater.from(context);
        layoutInflater.inflate(R.layout._swappable_picture, this, true);
        this.context = context;
        aq = new AQuery(this);
        setPicture(picture);
    }

    public PictureDto getPicture() {
        return picture;
    }

    public void setPicture(PictureDto picture) {
        this.picture = picture;
        ImageSize imageSize = new ImageSize(100, 100);
        ImageLoader imageLoader = ImageLoader.getInstance();
        imageLoader.displayImage(picture.getUriPreview(), aq.id(R.id.swappable_picture_image_view).getImageView(), imageSize);
    }

    public boolean isSelected() {
        return selected;
    }

    public void setSelected(boolean selected) {
        this.selected = selected;
        if (selected) {
            aq.id(R.id.swappable_picture_root_view).backgroundColor(context.getResources().getColor(R.color.primary));
        } else {
            aq.id(R.id.swappable_picture_root_view).backgroundColor(context.getResources().getColor(R.color.grey));
        }
    }

}
