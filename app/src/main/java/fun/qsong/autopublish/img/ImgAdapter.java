package fun.qsong.autopublish.img;

import android.app.Activity;
import android.net.Uri;
import android.widget.ImageView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.load.engine.DiskCacheStrategy;
import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.BaseViewHolder;

import fun.qsong.autopublish.R;

/**
 * Created by admin on 2018/12/18.
 */

public class ImgAdapter extends BaseQuickAdapter<Uri, BaseViewHolder>{
    private Activity activity;

    public ImgAdapter(Activity activity) {
        super(R.layout.item_img);
        this.activity = activity;
    }


    @Override
    protected void convert(BaseViewHolder helper, Uri item) {
        ImageView ivImg = helper.getView(R.id.iv_img);
        Glide.with(activity)
                .load(item)
                .diskCacheStrategy(DiskCacheStrategy.SOURCE)
                .crossFade()
                .into(ivImg);
    }
}
