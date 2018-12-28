package fanruiqi.bwie.com.zy1226.util;

import java.io.IOException;
import java.util.HashMap;
import java.util.Map;
import java.util.concurrent.TimeUnit;

import okhttp3.MediaType;
import okhttp3.OkHttpClient;
import okhttp3.RequestBody;
import okhttp3.ResponseBody;
import okhttp3.logging.HttpLoggingInterceptor;
import retrofit2.Retrofit;
import retrofit2.adapter.rxjava.RxJavaCallAdapterFactory;
import rx.Observer;
import rx.android.schedulers.AndroidSchedulers;
import rx.schedulers.Schedulers;

public class RetrofitManager<T> {

    private static RetrofitManager mRetrofitManager;
    private final BaseApis mBaseApis;
    private String BASE_URL="http://www.zhaoapi.cn";
    public RetrofitManager() {
        HttpLoggingInterceptor interceptor = new HttpLoggingInterceptor();
        interceptor.setLevel(HttpLoggingInterceptor.Level.BODY);

        OkHttpClient client = new OkHttpClient.Builder()
                .connectTimeout(5000, TimeUnit.MILLISECONDS)
                .readTimeout(5000,TimeUnit.MILLISECONDS)
                .writeTimeout(5000,TimeUnit.MILLISECONDS)
                .addInterceptor(interceptor)
                .build();

        Retrofit retrofit = new Retrofit.Builder()
                .addCallAdapterFactory(RxJavaCallAdapterFactory.create())
                .baseUrl(BASE_URL)
                .client(client)
                .build();

         mBaseApis = retrofit.create(BaseApis.class);

    }

    public static synchronized RetrofitManager getInstance(){
        if (mRetrofitManager==null){
            mRetrofitManager = new RetrofitManager();
        }
        return mRetrofitManager;
    }

    public Map<String, RequestBody> generateRequestBody(Map<String, String> requestDataMap) {
        Map<String, RequestBody> requestBodyMap = new HashMap<>();
        for (String key : requestDataMap.keySet()) {
            RequestBody requestBody = RequestBody.create(MediaType.parse("multipart/form-data"),
                    requestDataMap.get(key) == null ? "" : requestDataMap.get(key));
            requestBodyMap.put(key, requestBody);
        }
        return requestBodyMap;
    }

    //GET
    public RetrofitManager get(String url){
        mBaseApis.get(url)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(observer);
        return mRetrofitManager;
    }

    //表单POST
    public RetrofitManager postFormBody(String url, Map<String, RequestBody> map) {
        if (map == null) {
            map = new HashMap<>();
        }

        mBaseApis.postFormBody(url, map)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(observer);
        return mRetrofitManager;
    }


    //POST
    public RetrofitManager post(String url, Map<String, String> map) {
        if (map == null) {
            map = new HashMap<>();
        }
        mBaseApis.post(url, map)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(observer);
        return mRetrofitManager;
    }

    private Observer observer = new Observer<ResponseBody>() {
        @Override
        public void onNext(ResponseBody responseBody) {
            try {
                String data = responseBody.string();
                if (listener != null) {
                    listener.onSuccess(data);
                }
            } catch (IOException e) {
                e.printStackTrace();
                if (listener != null) {
                    listener.onFail(e.getMessage());
                }
            }
        }

        @Override
        public void onError(Throwable e) {
            if (listener != null) {
                listener.onFail(e.getMessage());
            }
        }

        @Override
        public void onCompleted() {

        }
    };

    private HttpListener listener;

    public void result(HttpListener listener) {
        this.listener = listener;
    }

    public interface HttpListener {
        void onSuccess(String data);

        void onFail(String error);
    }

}
