package fanruiqi.bwie.com.zy1226.fragment;

import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import android.widget.Toast;

import org.greenrobot.eventbus.EventBus;
import org.greenrobot.eventbus.Subscribe;
import org.greenrobot.eventbus.ThreadMode;

import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import fanruiqi.bwie.com.zy1226.R;
import fanruiqi.bwie.com.zy1226.bean.ShopBean;

public class Afrag extends Fragment{
    @BindView(R.id.af_text1)
    TextView mTextView1;

    @BindView(R.id.af_text2)
    TextView mTextView2;
    private String mTitle;
    private String mPrice;

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EventBus.getDefault().register(this);
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = View.inflate(getActivity(), R.layout.afrag, null);

        ButterKnife.bind(this,view);
        mTextView1.setText(mTitle+"");
        mTextView2.setText(mPrice+"");


        mTextView1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                ShopBean shopBean = new ShopBean(mTitle, "");
                EventBus.getDefault().postSticky(shopBean);
            }
        });
        mTextView2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                ShopBean shopBean = new ShopBean(null, mPrice);
                EventBus.getDefault().postSticky(shopBean);
            }
        });
        return view;
    }

    /*@OnClick(R.id.af_text1)
    public void onTitleClick(){
        EventBus.getDefault().postSticky(mTitle);
        Toast.makeText(getActivity(),"ff",Toast.LENGTH_SHORT).show();

    }*/

    @Subscribe(threadMode = ThreadMode.MAIN,sticky = true)
    public void onMessageEvent(ShopBean shopBean) {

         mTitle = shopBean.getTitle();
         mPrice = shopBean.getPrice();

    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        EventBus.getDefault().unregister(this);
    }
}
