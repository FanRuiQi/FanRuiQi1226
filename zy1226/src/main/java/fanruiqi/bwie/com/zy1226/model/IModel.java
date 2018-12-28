package fanruiqi.bwie.com.zy1226.model;

import java.util.Map;

import fanruiqi.bwie.com.zy1226.callback.MyCallBack;

public interface IModel {
    void requestData(String url, Map<String,Integer> map, Class clazz, MyCallBack myCallBack);
}
