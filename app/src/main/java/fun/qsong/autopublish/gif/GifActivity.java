package fun.qsong.autopublish.gif;

import android.os.Bundle;
import android.webkit.WebView;

import fun.qsong.autopublish.R;
import fun.qsong.autopublish.base.BaseActivity;

public class GifActivity extends BaseActivity {

    WebView webView;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_gif);
        webView = findViewById(R.id.web_gif);


        GifListBean gifListBean = (GifListBean)getIntent().getSerializableExtra("gifListBeans");
        webView.loadUrl(gifListBean.data.get(0).getImg_url());
    }


}
