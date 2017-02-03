package testing.com.albumuploadtest.partials;

import android.content.Context;
import android.util.AttributeSet;
import android.widget.RelativeLayout;

import com.androidquery.AQuery;
import com.nostra13.universalimageloader.core.ImageLoader;
import com.nostra13.universalimageloader.core.assist.ImageSize;

import testing.com.albumuploadtest.R;
import testing.com.albumuploadtest.dto.PictureDto;

public class TilePicture extends RelativeLayout {

    private AQuery aq;
    private PictureDto picture;
    private OnClickListener onClickListener;

    public TilePicture(Context context, AttributeSet attrs) {
        super(context, attrs);
        aq = new AQuery(this);
        picture = new PictureDto(0, "", "");
    }

    @Override
    protected void onFinishInflate() {
        super.onFinishInflate();

        if (!isInEditMode())
            aq.id(R.id.tile_picture_image).clicked(this, "tilePressed");
    }

    public void tilePressed() {
        if (onClickListener != null)
            onClickListener.onClick(this);
    }

    public void switchSelection() {
        setSelected(!picture.isSelected());
    }

    public void setSelected(boolean selected) {
        picture.setSelected(selected);
        updateSelection();
    }

    public void updateSelection() {
        showTick(picture.isSelected());
        showTransparency(picture.isSelected());
    }

    public void showTick(Boolean show) {
        if (show) {
            aq.id(R.id.tile_picture_check).visible();
        } else {
            aq.id(R.id.tile_picture_check).gone();
        }
    }

    public void showTransparency(Boolean show) {
        if (show) {
            aq.id(R.id.tile_picture_transparency).visible();
        } else {
            aq.id(R.id.tile_picture_transparency).gone();
        }
    }

    public PictureDto getPicture() {
        return picture;
    }

    public void setPicture(PictureDto picture) {
        this.picture = picture;
        ImageLoader imageLoader = ImageLoader.getInstance();
        ImageSize imageSize = new ImageSize(300, 200);
        imageLoader.displayImage(picture.getUriPreview(), aq.id(R.id.tile_picture_image).getImageView(), imageSize);
        aq.id(R.id.tile_picture_resolution).text(picture.getWidth() + " x " + picture.getHeight());
        updateSelection();
    }

    public void setOnClickListener(OnClickListener onClickListener) {
        this.onClickListener = onClickListener;
    }

}
