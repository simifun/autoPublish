package fun.qsong.autopublish.img;

import android.Manifest;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v4.app.ActivityCompat;
import android.support.v4.content.ContextCompat;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.text.TextUtils;
import android.util.Log;
import android.view.View;
import android.view.inputmethod.InputMethodManager;
import android.widget.Button;
import android.widget.EditText;

import com.zhihu.matisse.Matisse;
import com.zhihu.matisse.MimeType;
import com.zhihu.matisse.engine.impl.GlideEngine;

import java.util.ArrayList;
import java.util.List;

import fun.qsong.autopublish.R;
import fun.qsong.autopublish.base.BaseActivity;
import fun.qsong.autopublish.entity.MyItit;
import fun.qsong.autopublish.view.EditDialog;
import fun.qsong.utils.util.T;

import static fun.qsong.autopublish.constants.ActivityReqConstants.REQUEST_OPEN_IMGPICKER;
import static fun.qsong.autopublish.constants.ActivityReqConstants.REQUEST_PERMISSION;

public class ImgActivity extends BaseActivity<IImgView, ImgPresenter> implements IImgView, View.OnClickListener {
    static final int TYPE_GIF = 100;
    static final int TYPE_IMG = 101;
    ImgAdapter imgAdapter;
    RecyclerView rcvImg;
    Button imgPickerBtn;
    Button imgUploadBtn;
    Button gifpickerBtn;
    private String type = "组图";
    List<Uri> mSelected = new ArrayList<>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_img);
        imgPickerBtn = findViewById(R.id.btn_picker);
        gifpickerBtn = findViewById(R.id.btn_gif);
        imgUploadBtn = findViewById(R.id.btn_upload);
        rcvImg = findViewById(R.id.rcv_img);

        rcvImg.setLayoutManager(new GridLayoutManager(this, 2));
        //设置监听事件
        imgPickerBtn.setOnClickListener(this);
        gifpickerBtn.setOnClickListener(this);
        imgUploadBtn.setOnClickListener(this);
    }

    @Override
    protected void onResume() {
        super.onResume();
        if (imgAdapter == null) {
            imgAdapter = new ImgAdapter(this);
            imgAdapter.bindToRecyclerView(rcvImg);
            imgAdapter.setEmptyView(R.layout.layout_empty_img, rcvImg);
        }
    }

    private void publishImg() {
        if (getUploadList().isEmpty()) {
            T.showShort("先选择图片好不好？！");
            return;
        }
        final EditDialog editDialog = new EditDialog(getActivityContext());
        editDialog.setTitle("来一个响亮的标题吧！");
        editDialog.setYesOnclickListener("确定", new EditDialog.onYesOnclickListener() {
            @Override
            public void onYesClick(String inputStr) {
                if (TextUtils.isEmpty(inputStr)) {
                    T.showShort("文章标题不能为空！");
                }else if(TextUtils.isEmpty(getUploadList().get(0).getText())) {
                    T.showShort("请至少把第一个图片配点文字吧！");
                }else {
                    //让软键盘隐藏
                    InputMethodManager imm = (InputMethodManager) getActivityContext().getSystemService(Context.INPUT_METHOD_SERVICE);
                    imm.hideSoftInputFromWindow(editDialog.getCurrentFocus().getApplicationWindowToken(), 0);
                    editDialog.dismiss();
                    presenter.upload(getUploadList(), inputStr,type);
                }
            }
        });
        editDialog.setNoOnclickListener("取消", new EditDialog.onNoOnclickListener() {
            @Override
            public void onNoClick() {
                editDialog.dismiss();
            }
        });
        editDialog.show();
    }

    private void openPickerActivity(int type) {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M
                && ContextCompat.checkSelfPermission(this, Manifest.permission.READ_EXTERNAL_STORAGE) != PackageManager.PERMISSION_GRANTED) {
            ActivityCompat.requestPermissions(this, new String[]{Manifest.permission.READ_EXTERNAL_STORAGE},
                    REQUEST_PERMISSION);
            return;
        } else if(type == TYPE_IMG){
            Log.e(TAG, "openPickerActivity: "+TYPE_IMG);
            Matisse.from(this)
                    .choose(MimeType.allOf()) // 选择 mime 的类型
                    .countable(true)
                    .maxSelectable(15) // 图片选择的最多数量
                    .thumbnailScale(0.85f) // 缩略图的比例
                    .imageEngine(new GlideEngine()) // 使用的图片加载引擎
                    .forResult(REQUEST_OPEN_IMGPICKER); // 设置作为标记的请求码
        } else if(type == TYPE_GIF){
            Log.e(TAG, "openPickerActivity: "+TYPE_GIF);
            Matisse.from(this)
                    .choose(MimeType.of(MimeType.GIF)) // 选择 mime 的类型
                    .countable(true)
                    .maxSelectable(15) // 图片选择的最多数量
                    .thumbnailScale(0.85f) // 缩略图的比例
                    .imageEngine(new GlideEngine()) // 使用的图片加载引擎
                    .forResult(REQUEST_OPEN_IMGPICKER); // 设置作为标记的请求码
        }
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.btn_picker:
                type = "组图";
                openPickerActivity(TYPE_IMG);
                break;
            case R.id.btn_gif:
                type = "动图";
                openPickerActivity(TYPE_GIF);
                break;
            case R.id.btn_upload:
                publishImg();
                break;
        }
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        switch (requestCode) {
            case REQUEST_OPEN_IMGPICKER:
                if (data != null) {
                    mSelected = Matisse.obtainResult(data);
                    imgAdapter.setNewData(mSelected);
                    Log.e("Matisse-->", "mSelected: " + mSelected);
                }else{
                    T.showShort("未选择图片");
                }
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

    public List<MyItit> getUploadList() {
        int size = getmSelected().size();
        EditText editText;
        MyItit myItit;
        List<MyItit> myItits = new ArrayList<>();
        for (int i = 0; i < size; i++) {
            editText = (EditText) imgAdapter.getViewByPosition(i, R.id.tv_img);
            myItit = new MyItit(imgAdapter.getItem(i), editText.getText().toString(), i);
            myItits.add(myItit);
        }
        return myItits;
    }

    @Override
    protected ImgPresenter onCreatePresenter() {
        return new ImgPresenter();
    }
}
