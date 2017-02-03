package testing.com.albumuploadtest.partials;

import android.content.Context;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.widget.LinearLayout;

import com.androidquery.AQuery;

import testing.com.albumuploadtest.R;

public class PageOrderPic extends LinearLayout {

    private Context context;
    private AQuery aq;

    public PageOrderPic(Context context, String title) {
        super(context);
        initialize(context, title);
    }

    private void initialize(Context context, String title) {
        LayoutInflater inflater = LayoutInflater.from(context);
        inflater.inflate(R.layout._page_order_pics, this, true);
        this.context = context;
        aq = new AQuery(this);
        aq.id(R.id.page_order_pics_label).text(title);
    }

    public void addPictureToPage(SwappablePicture swappablePicture) {
        LinearLayout picturesLayout = (LinearLayout)aq.id(R.id.page_order_pics_layout).getView();
        LinearLayout linearLayout = new LinearLayout(context);
        LinearLayout.LayoutParams lp = new LayoutParams(0, LinearLayout.LayoutParams.WRAP_CONTENT, 1f);
        linearLayout.setLayoutParams(lp);
        linearLayout.setGravity(Gravity.CENTER);
        linearLayout.addView(swappablePicture);
        picturesLayout.addView(linearLayout);
    }

}
