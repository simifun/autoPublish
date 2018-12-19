package fun.qsong.autopublish.gif;

import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;

import fun.qsong.autopublish.R;
import fun.qsong.autopublish.base.BaseActivity;



public class GifActivity extends BaseActivity {

//    WebView webView;
    GifAdapter gifAdapter;
    RecyclerView rcvGif;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_gif);
//        webView = findViewById(R.id.web_gif);
        rcvGif = findViewById(R.id.rcv_gif);

        gifAdapter = new GifAdapter(this);
        gifAdapter.bindToRecyclerView(rcvGif);
        rcvGif.setLayoutManager(new LinearLayoutManager(this));


        GifListBean gifListBean = (GifListBean)getIntent().getSerializableExtra("gifListBeans");
//        webView.loadUrl(gifListBean.data.get(0).getGif_url());
        gifAdapter.setNewData(gifListBean.data);

    }


}
