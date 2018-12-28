package fanruiqi.bwie.com.zy1226.precenter;

import java.util.Map;

import fanruiqi.bwie.com.zy1226.callback.MyCallBack;
import fanruiqi.bwie.com.zy1226.model.IModelImpl;
import fanruiqi.bwie.com.zy1226.view.IView;

public class IPrecenterImpl implements IPrecenter{

    private IView mIView;
    IModelImpl iModel;

    public IPrecenterImpl(IView IView) {
        mIView = IView;
        iModel = new IModelImpl();
    }

    @Override
    public void requestData(String url, Map<String, Integer> map, Class clazz) {
        iModel.requestData(url, map, clazz, new MyCallBack() {
            @Override
            public void setData(Object data) {
                mIView.showData(data);
            }
        });
    }

    public void onDetach(){
        if (mIView!=null){
            mIView=null;
        }
        if (iModel!=null){
            iModel=null;
        }
    }
}
