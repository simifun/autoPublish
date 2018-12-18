package fun.qsong.autopublish.gif;

import android.support.annotation.Nullable;
import android.widget.ImageView;

import com.bumptech.glide.Glide;
import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.BaseViewHolder;

import java.util.List;

import fun.qsong.autopublish.R;

/**
 * Created by admin on 2018/12/18.
 */

public class GifAdaper extends BaseQuickAdapter<GifListBean.Gif, BaseViewHolder>{
    public GifAdaper() {
        super(R.layout.item_gif);
    }

    @Override
    protected void convert(BaseViewHolder helper, GifListBean.Gif item) {
        helper.setText(R.id.tv_gif,item.getName());
        ImageView ivGif = helper.getView(R.id.iv_gif);
        Glide.with(mContext)
                .load(item.getImg_url())
                .into(ivGif);
    }
}
