package fanruiqi.bwie.com.zy1226.model;

import android.util.Log;

import com.google.gson.Gson;

import java.util.Map;

import fanruiqi.bwie.com.zy1226.bean.ResponseBean;
import fanruiqi.bwie.com.zy1226.callback.MyCallBack;
import fanruiqi.bwie.com.zy1226.util.OkUtils;
import fanruiqi.bwie.com.zy1226.util.RetrofitManager;
import okhttp3.RequestBody;

public class IModelImpl implements IModel {

    @Override
    public void requestData(String url, Map<String, Integer> map, final Class clazz, final MyCallBack myCallBack) {

        Map<String, RequestBody> map1 = RetrofitManager.getInstance().generateRequestBody(map);

       /* OkUtils.getInstance().doGet(url, new OkUtils.OnCallBack() {
            @Override
            public void onFail() {

            }

            @Override
            public void onResponse(String json) {

                Object o = new Gson().fromJson(json, clazz);
                myCallBack.setData(o);
            }
        });*/

        RetrofitManager.getInstance().post(url,map1).result(new RetrofitManager.HttpListener() {
            @Override
            public void onSuccess(String data) {
                try {
                    Object obj = new Gson().fromJson(data, ResponseBean.class);
                    if(myCallBack!=null){
                        myCallBack.setData(obj);
                    }
                }catch (Exception e){
                    Log.e("WS",""+e);
                }
            }

            @Override
            public void onFail(String error) {

            }
        });
    }
}
