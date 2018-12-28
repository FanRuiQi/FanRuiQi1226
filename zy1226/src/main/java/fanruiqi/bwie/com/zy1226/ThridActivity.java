package fanruiqi.bwie.com.zy1226;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentPagerAdapter;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.RadioGroup;

import butterknife.BindView;
import butterknife.ButterKnife;
import fanruiqi.bwie.com.zy1226.fragment.Afrag;
import fanruiqi.bwie.com.zy1226.fragment.Bfrag;
import fanruiqi.bwie.com.zy1226.fragment.Cfrag;

public class ThridActivity extends AppCompatActivity{

    @BindView(R.id.third_group)
    RadioGroup mRadioGroup;

    @BindView(R.id.third_pager)
    ViewPager mViewPager;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_thrid);

        ButterKnife.bind(this);

        mViewPager.setAdapter(new FragmentPagerAdapter(getSupportFragmentManager()) {
            @Override
            public Fragment getItem(int position) {
                Fragment fragment=null;
                switch (position){
                    case 0:
                        fragment = new Afrag();
                        break;
                    case 1:
                        fragment = new Bfrag();
                        break;
                    case 2:
                        fragment = new Cfrag();
                        break;

                }
                return fragment;
            }

            @Override
            public int getCount() {
                return 3;
            }
        });

        mRadioGroup.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(RadioGroup radioGroup, int i) {
                switch (i){
                    case R.id.r1:
                        mViewPager.setCurrentItem(0);
                        break;
                    case R.id.r2:
                        mViewPager.setCurrentItem(1);
                        break;
                    case R.id.r3:
                        mViewPager.setCurrentItem(2);
                        break;
                }
            }
        });
    }
}
