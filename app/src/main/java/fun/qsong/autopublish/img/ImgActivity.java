package fun.qsong.autopublish.img;

import android.Manifest;
import android.content.Intent;
import android.content.pm.ActivityInfo;
import android.content.pm.PackageManager;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v4.app.ActivityCompat;
import android.support.v4.content.ContextCompat;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

import com.zhihu.matisse.Matisse;
import com.zhihu.matisse.MimeType;
import com.zhihu.matisse.engine.impl.GlideEngine;

import java.util.ArrayList;
import java.util.List;

import fun.qsong.autopublish.R;
import fun.qsong.autopublish.base.BaseActivity;
import fun.qsong.autopublish.main.MainPresenter;
import fun.qsong.utils.util.T;

import static fun.qsong.autopublish.constants.ActivityReqConstants.REQUEST_OPEN_IMGPICKER;
import static fun.qsong.autopublish.constants.ActivityReqConstants.REQUEST_PERMISSION;

public class ImgActivity extends BaseActivity<IImgView,ImgPresenter> implements IImgView,View.OnClickListener {
    ImgAdapter imgAdapter;
    RecyclerView rcvImg;
    Button imgPickerBtn;
    Button imgUploadBtn;
    List<Uri> mSelected;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_img);
        imgPickerBtn = findViewById(R.id.btn_picker);
        imgUploadBtn = findViewById(R.id.btn_upload);
        rcvImg = findViewById(R.id.rcv_img);

        rcvImg.setLayoutManager(new GridLayoutManager(this,2));
        //设置监听事件
        imgPickerBtn.setOnClickListener(this);
        imgUploadBtn.setOnClickListener(this);
    }

    @Override
    protected void onResume() {
        super.onResume();
        if(imgAdapter == null){
            imgAdapter = new ImgAdapter(this);
            imgAdapter.bindToRecyclerView(rcvImg);
        }
    }

    private void openPickerActivity(){
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M
                && ContextCompat.checkSelfPermission(this, Manifest.permission.READ_EXTERNAL_STORAGE) != PackageManager.PERMISSION_GRANTED) {
            ActivityCompat.requestPermissions(this, new String[]{Manifest.permission.READ_EXTERNAL_STORAGE},
                    REQUEST_PERMISSION);
            return;
        }else{
            Matisse.from(this)
                    .choose(MimeType.allOf()) // 选择 mime 的类型
                    .countable(true)
                    .maxSelectable(9) // 图片选择的最多数量
                    .thumbnailScale(0.85f) // 缩略图的比例
                    .imageEngine(new GlideEngine()) // 使用的图片加载引擎
                    .forResult(REQUEST_OPEN_IMGPICKER); // 设置作为标记的请求码
        }
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()){
            case R.id.btn_picker:
                openPickerActivity();
                break;
            case R.id.btn_upload:
                presenter.upload(getUploadList());
                break;
        }
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        switch (requestCode){
            case REQUEST_OPEN_IMGPICKER:
                mSelected = Matisse.obtainResult(data);
                imgAdapter.setNewData(mSelected);
                Log.e("Matisse-->", "mSelected: " + mSelected);
        }
    }

    @Override
    public void onRequestPermissionsResult(final int requestCode, @NonNull final String[] permissions, @NonNull final int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
        if (requestCode == REQUEST_PERMISSION) {
            if (grantResults.length > 0 && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                Matisse.from(this)
                        .choose(MimeType.allOf()) // 选择 mime 的类型
                        .countable(true)
                        .maxSelectable(9) // 图片选择的最多数量
                        .thumbnailScale(0.85f) // 缩略图的比例
                        .imageEngine(new GlideEngine()) // 使用的图片加载引擎
                        .forResult(REQUEST_OPEN_IMGPICKER); // 设置作为标记的请求码
            } else {
                T.showShort("你拒绝了访问权限申请！这就不能选择本地图片了笨蛋！");
            }
        }
    }

    public List<Uri> getmSelected() {
        return mSelected;
    }

    public void setmSelected(List<Uri> mSelected) {
        this.mSelected = mSelected;
    }

    public List<MyItit> getUploadList(){
        int size = imgAdapter.getItemCount();
        EditText editText;
        MyItit myItit;
        List<MyItit> myItits = new ArrayList<>();
        for(int i=0;i<size;i++){
            editText = (EditText) imgAdapter.getViewByPosition(i,R.id.tv_img);
            myItit = new MyItit(imgAdapter.getItem(i),editText.getText().toString(),i);
            myItits.add(myItit);
        }
        return myItits;
    }

    @Override
    protected ImgPresenter onCreatePresenter() {
        return new ImgPresenter();
    }
}
