package fun.qsong.autopublish.img;

import android.util.Log;

import java.io.File;
import java.util.ArrayList;
import java.util.List;

import fun.qsong.autopublish.base.BasePresenter;
import fun.qsong.autopublish.entity.Itit;
import fun.qsong.autopublish.entity.MyItit;
import fun.qsong.autopublish.entity.ReSponseItit;
import fun.qsong.autopublish.retrofit.Query;
import fun.qsong.utils.util.FileUtils;
import fun.qsong.utils.util.GsonUtils;
import fun.qsong.utils.util.T;
import io.reactivex.functions.Consumer;
import okhttp3.MediaType;
import okhttp3.MultipartBody;
import okhttp3.RequestBody;

/**
 * Created by admin on 2018/12/17.
 */

public class ImgPresenter extends BasePresenter<IImgView> {
    @Override
    protected void init() {
        context = mIView.getActivityContext();
    }

    //上传文件到服务器
    public void upload(List<MyItit> myItits, final String title, final String type) {
        MultipartBody.Part[] parts = getMultipartBodyPart(myItits);
        MultipartBody.Part itits = getMultipartBodyItit(myItits);
//        List<Itit> itits = getItitList(myItits);
        Query.getInstance().uploadImgFile(parts,itits)
                .subscribe(new Consumer<ReSponseItit>() {
                    @Override
                    public void accept(ReSponseItit responseItit) throws Exception {
                        publish(responseItit,title,type);
                    }
                }, new Consumer<Throwable>() {
                    @Override
                    public void accept(Throwable throwable) throws Exception {
                        Log.e(TAG, "throwable: -->" + throwable);
                        T.showShort("请求失败！请检查网络！");
                    }
                });
    }

    //发布文章
    public void publish(ReSponseItit ReSponseItit,String title,String type) {
        if(ReSponseItit.getData().getItit() == null || ReSponseItit.getData().getItit().size() == 0){
            T.showShort("发布内容为空！请检查！");
            return;
        }
        String itits = "";
        try {
            itits = GsonUtils.toJsonString(ReSponseItit.getData().getItit());
        } catch (Exception e) {
            T.showShort("JSON转换出错！");
            e.printStackTrace();
        }
        Query.getInstance().postNewImgArticle(itits,title,type)
                .subscribe(new Consumer<Object>() {
                    @Override
                    public void accept(Object o) throws Exception {
                        Log.d(TAG, "Object: -->" + o);
                        T.showShort("发布成功！"+o.toString());
                    }
                }, new Consumer<Throwable>() {
                    @Override
                    public void accept(Throwable throwable) throws Exception {
                        Log.e(TAG, "throwable: -->" + throwable);
                        T.showShort("请求失败！请检查网络！");
                    }
                });
    }



    private MultipartBody.Part[] getMultipartBodyPart(List<MyItit> myItits){
        int n = myItits.size();
        if(n > 0){
            File file;
            RequestBody requestFile;
            MultipartBody.Part[] parts = new MultipartBody.Part[n];
            for(int i=0;i<n;i++){
                file = new File(FileUtils.getFilePathByUri(myItits.get(i).getImgUri()));
                requestFile = RequestBody.create(MediaType.parse("multipart/form-data"), file);
                parts[i] = MultipartBody.Part.createFormData("myfile", file.getName(), requestFile);
            }
            return parts;
        }
        return null;
    }

    private MultipartBody.Part getMultipartBodyItit(List<MyItit> myItits){
        int n = myItits.size();
        if(n > 0){
            MyItit myItit;
            Itit[] itits = new Itit[n];
            for(int i=0;i<n;i++){
                myItit = myItits.get(i);
                itits[i] = new Itit(myItit.getText(),myItit.getTag());
            }
            String test = null;
            try {
                test = GsonUtils.toJsonString(itits);
            } catch (Exception e) {
                T.showShort("JSON转换出错！");
                e.printStackTrace();
            }
            return  MultipartBody.Part.createFormData("itit", test);
        }
        return null;
    }

    private List<Itit> getItitList(List<MyItit> myItits){
        int n = myItits.size();
        if(n > 0){
            MyItit myItit;
            List<Itit> itits = new ArrayList<>();
            for(int i=0;i<n;i++){
                myItit = myItits.get(i);
                itits.add(new Itit(myItit.getText(),myItit.getTag()));
            }
            return itits;
        }
        return null;
    }

    private MultipartBody.Part toRequestBodyOfText (String keyStr, String value) {
        MultipartBody.Part body = MultipartBody.Part.createFormData(keyStr, value);
        return body;
    }

    private MultipartBody.Part toRequestBodyOfImage(String keyStr, File pFile){
        RequestBody requestBody = RequestBody.create(MediaType.parse("multipart/form-data"), pFile);
        MultipartBody.Part filedata = MultipartBody.Part.createFormData(keyStr, pFile.getName(), requestBody);
        return filedata;
    }
}
