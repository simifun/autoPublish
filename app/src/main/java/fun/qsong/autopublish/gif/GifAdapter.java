package fun.qsong.autopublish.gif;

import android.app.Activity;
import android.widget.CompoundButton;
import android.widget.ImageView;
import android.widget.Switch;

import com.bumptech.glide.Glide;
import com.bumptech.glide.load.engine.DiskCacheStrategy;
import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.BaseViewHolder;
import fun.qsong.autopublish.R;
import fun.qsong.autopublish.img.ImgListBean;

/**
 * Created by admin on 2018/12/18.
 */

public class GifAdapter extends BaseQuickAdapter<GifListBean.Gif, BaseViewHolder>{
    private Activity activity;
    public GifAdapter(Activity activity) {
        super(R.layout.item_gif);
        this.activity = activity;
    }

    @Override
    protected void convert(final BaseViewHolder helper, final GifListBean.Gif item) {
        helper.setText(R.id.tv_gif,item.getName());
        ImageView ivGif = helper.getView(R.id.iv_gif);
        Switch bannerSwitch = helper.getView(R.id.switch_banner);
        Glide.with(activity)
                .load(item.getImg_url())
                .asGif()
                .placeholder(R.mipmap.img_loading)
                .error(R.mipmap.img_broken)
                .diskCacheStrategy(DiskCacheStrategy.SOURCE)
                .crossFade()
                .into(ivGif);
        bannerSwitch.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                item.setBannser(isChecked);
            }
        });
    }
}
