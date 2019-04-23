package fun.qsong.autopublish.joke;

import android.widget.ImageView;

import fun.qsong.autopublish.base.IMvpView;

/**
 * Created by admin on 2019/4/23.
 */

public interface IJokeView extends IMvpView  {
    void refresh(JokeListBean jokeListBean);
    void refreshImg(String imgUrl, ImageView imageView);
}
