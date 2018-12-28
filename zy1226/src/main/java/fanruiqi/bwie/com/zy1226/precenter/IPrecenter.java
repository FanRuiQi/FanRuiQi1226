package fanruiqi.bwie.com.zy1226.precenter;

import java.util.Map;

import fanruiqi.bwie.com.zy1226.callback.MyCallBack;

public interface IPrecenter {
    void requestData(String url, Map<String, Integer> map, Class clazz);
}
