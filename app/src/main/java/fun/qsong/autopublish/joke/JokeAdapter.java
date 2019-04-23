package fun.qsong.autopublish.joke;

import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.BaseViewHolder;

import fun.qsong.autopublish.R;

/**
 * Created by admin on 2019/4/23.
 */

public class JokeAdapter extends BaseQuickAdapter<JokeListBean.Joke,BaseViewHolder> {

    public JokeAdapter() {
        super(R.layout.item_joke);
    }

    @Override
    protected void convert(BaseViewHolder helper, JokeListBean.Joke item) {
        helper.setText(R.id.tv_joke,item.getContent());
    }


}
