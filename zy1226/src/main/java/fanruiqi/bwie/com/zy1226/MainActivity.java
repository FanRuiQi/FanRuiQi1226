package fanruiqi.bwie.com.zy1226;

import android.animation.Animator;
import android.animation.AnimatorListenerAdapter;
import android.animation.ObjectAnimator;
import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.DividerItemDecoration;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.ImageView;
import android.widget.Toast;

import com.google.gson.Gson;

import org.greenrobot.eventbus.EventBus;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import fanruiqi.bwie.com.zy1226.adapter.MyAdapter;
import fanruiqi.bwie.com.zy1226.bean.ResponseBean;
import fanruiqi.bwie.com.zy1226.bean.ShopBean;
import fanruiqi.bwie.com.zy1226.precenter.IPrecenterImpl;
import fanruiqi.bwie.com.zy1226.util.OkUtils;
import fanruiqi.bwie.com.zy1226.view.IView;

public class MainActivity extends AppCompatActivity implements IView{

    @BindView(R.id.main_rv)
    RecyclerView mRecyclerView;

    @BindView(R.id.main_img)
    ImageView mImageView;

    IPrecenterImpl iPrecenter;

    private int page=1;
    private boolean OR=true;
    List<ResponseBean.DataBean> data1;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        iPrecenter = new IPrecenterImpl(this);
        ButterKnife.bind(this);
        //EventBus.getDefault().register(this);
        initData();
    }

    @OnClick(R.id.main_img)
    public void setImgOnClick(){   //图片点击切换

        if (OR){  //切换线性
            OR=false;

            mRecyclerView.addItemDecoration(new DividerItemDecoration(MainActivity.this,DividerItemDecoration.VERTICAL));
            mRecyclerView.setLayoutManager(new LinearLayoutManager(MainActivity.this,LinearLayoutManager.VERTICAL,false));
        }else {   //切换网格
            OR=true;

            mRecyclerView.addItemDecoration(new DividerItemDecoration(MainActivity.this,DividerItemDecoration.VERTICAL));
            mRecyclerView.addItemDecoration(new DividerItemDecoration(MainActivity.this,DividerItemDecoration.HORIZONTAL));
            mRecyclerView.setLayoutManager(new GridLayoutManager(MainActivity.this,2));

        }

        MyAdapter myAdapter = new MyAdapter(MainActivity.this,OR);
        myAdapter.setDatas(data1);
        mRecyclerView.setAdapter(myAdapter);
        myAdapter.setOnItemClickListener(new MyAdapter.OnItemClickListener() {  //重新配置点击事件
            @Override
            public void onItemClick(String url, String title, String price) {  //点击事件
                List<String> list = new ArrayList<>();
                list.add(title);
                list.add(price);
                ShopBean shopBean = new ShopBean(title, price);
                EventBus.getDefault().postSticky(shopBean);   //粘性事件传值

                Intent intent = new Intent(MainActivity.this, ThridActivity.class);
                startActivity(intent);
            }
        });
    }

    private void initData() {

        Map<String, Integer> map = new HashMap<>(); //请求数据
        iPrecenter.requestData(Apis.URL_PHONE_LIST,map,ResponseBean.class);

    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        iPrecenter.onDetach();
    }

    @Override
    public void showData(Object data) {   //返回数据
        ResponseBean responseBean = (ResponseBean) data;
        data1 = responseBean.getData();

        MyAdapter myAdapter = new MyAdapter(MainActivity.this,OR);
        myAdapter.setDatas(data1);
        mRecyclerView.setAdapter(myAdapter);
        mRecyclerView.addItemDecoration(new DividerItemDecoration(MainActivity.this,DividerItemDecoration.VERTICAL));
        mRecyclerView.addItemDecoration(new DividerItemDecoration(MainActivity.this,DividerItemDecoration.HORIZONTAL));
        mRecyclerView.setLayoutManager(new GridLayoutManager(MainActivity.this,2)); //默认网格布局

        myAdapter.setOnItemClickListener(new MyAdapter.OnItemClickListener() {
            @Override
            public void onItemClick(String url, String title, String price) {  //点击事件
                List<String> list = new ArrayList<>();
                list.add(title);
                list.add(price);
                ShopBean shopBean = new ShopBean(title, price);
                EventBus.getDefault().postSticky(shopBean);   //粘性事件传值

                Intent intent = new Intent(MainActivity.this, ThridActivity.class);
                startActivity(intent);
            }
        });
    }
}
