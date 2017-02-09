package testing.com.albumuploadtest.partials;

import android.content.Context;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.widget.ImageView;
import android.widget.LinearLayout;

import com.androidquery.AQuery;
import com.nostra13.universalimageloader.core.ImageLoader;
import com.nostra13.universalimageloader.core.assist.ImageSize;

import java.util.List;

import testing.com.albumuploadtest.R;
import testing.com.albumuploadtest.dto.PictureDto;

public class PicPage extends LinearLayout {

    private static final String PIC_ROW = "row";
    private static final String PIC_VIEW = "iview";

    private LinearLayout rootView;

    private Context context;
    private AQuery aq;

    private int numOfPics;
    private boolean isOddPage;

    public PicPage(Context context, int numOfPics, boolean isOddPage) {
        super(context);
        this.context = context;
        this.numOfPics = numOfPics;
        this.isOddPage = isOddPage;
        aq = new AQuery(this);
        initializeLayout();
    }

    private void initializeLayout() {
        LayoutInflater inflater = LayoutInflater.from(context);
        inflater.inflate(R.layout._pic_page, this, true);
        rootView = (LinearLayout)findViewById(R.id.pic_page_root_view);

        if (isOddPage) {
            rootView.setBackground(getResources().getDrawable(R.drawable.odd_page_background));
        } else {
            rootView.setBackground(getResources().getDrawable(R.drawable.even_page_background));
        }

        int numOfRows;
        int module = numOfPics % 2;
        if (module == 0) {
            numOfRows = numOfPics / 2;
        } else {
            numOfRows = (numOfPics / 2) + 1;
        }

        int numOfCell = 0;
        for (int i = 0; i < numOfRows; i++) {
            LinearLayout picRow = new LinearLayout(context);
            LinearLayout.LayoutParams lp = new LayoutParams(LayoutParams.MATCH_PARENT, 0, 1f);
            picRow.setLayoutParams(lp);
            picRow.setGravity(Gravity.CENTER);
            picRow.setOrientation(LinearLayout.HORIZONTAL);
            picRow.setTag(PIC_ROW + i);
            picRow.setPadding(10, 10, 10, 10);

            for (int j = 0; j < 2; j++) {
                LinearLayout picCell = new LinearLayout(context);
                LinearLayout.LayoutParams lpCell = new LayoutParams(0, LayoutParams.MATCH_PARENT, 1f);
                picCell.setLayoutParams(lpCell);
                picCell.setPadding(15, 15, 15, 15);
                picCell.setGravity(Gravity.CENTER);

                ImageView picView = new ImageView(context);
                picView.setTag(PIC_VIEW + numOfCell);
                picCell.addView(picView);
                picRow.addView(picCell);
                numOfCell++;
            }

            rootView.addView(picRow);
        }
    }

    public void setPictures(List<PictureDto> pictures) {
        for (int i=0; i < pictures.size(); i++) {
            ImageView picCell = (ImageView)findViewWithTag(PIC_VIEW + i);
            PictureDto picture = pictures.get(i);
            ImageSize imageSize = new ImageSize(100, 100);
            ImageLoader imageLoader = ImageLoader.getInstance();
            imageLoader.displayImage(picture.getUriPreview(), picCell, imageSize);
        }
    }

}
