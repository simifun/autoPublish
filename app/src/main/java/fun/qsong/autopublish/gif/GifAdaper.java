package fun.qsong.autopublish.gif;

import android.support.annotation.Nullable;

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

    }
}
