package fanruiqi.bwie.com.zy1226.adapter;

import android.content.Context;
import android.net.Uri;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.facebook.drawee.view.SimpleDraweeView;

import java.util.ArrayList;
import java.util.List;
import java.util.regex.Pattern;

import butterknife.BindView;
import butterknife.ButterKnife;
import fanruiqi.bwie.com.zy1226.R;
import fanruiqi.bwie.com.zy1226.bean.ResponseBean;

public class MyAdapter extends RecyclerView.Adapter<RecyclerView.ViewHolder>{

    private Context mContext;
    private List<ResponseBean.DataBean> list;
    private Boolean OR=true;

    public MyAdapter(Context context,Boolean or) {
        mContext = context;
        OR=or;
        list=new ArrayList<>();
    }

    public void setDatas(List<ResponseBean.DataBean> datas) {
        list.addAll(datas);
        notifyDataSetChanged();
    }


    @NonNull
    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        if (OR){
            View view = View.inflate(parent.getContext(), R.layout.item, null);
            ViewHolder holder = new ViewHolder(view);
            return holder;
        }else {
            View view1 = View.inflate(parent.getContext(), R.layout.lineitem, null);
            LineViewHolder lineViewholder = new LineViewHolder(view1);
            return lineViewholder;
        }
    }

    @Override
    public void onBindViewHolder(@NonNull RecyclerView.ViewHolder holder, int position) {

        if (OR){
            ViewHolder gridHolder = (ViewHolder) holder;

            final int gridPosision = position;

            gridHolder.mTextView.setText(list.get(position).getTitle());
            gridHolder.mTextView02.setText(list.get(position).getPrice());

            String str="";
            int length = list.get(position).getImages().length();
            for (int j=0;j<length;j++){
                if (list.get(position).getImages().substring(j,j+1).equals("|")){
                    str= list.get(position).getImages().substring(j + 1, length).trim();
                }
            }

            String images = list.get(position).getImages();  //截取字符串两种方式
            Pattern pattern = Pattern.compile("\\|");
            String[] img = pattern.split(images);


            Uri uri = Uri.parse(img[0]);
            gridHolder.mImageView.setImageURI(uri);

            final String finalStr = str;
            holder.itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    mOnItemClickListener.onItemClick(finalStr,list.get(gridPosision).getTitle(),list.get(gridPosision).getPrice());
                }
            });
        }else {
            LineViewHolder lineViewHolder = (LineViewHolder) holder;

            final int gridPosision = position;

            lineViewHolder.mTextView.setText(list.get(position).getTitle());
            lineViewHolder.mTextView02.setText(list.get(position).getPrice());

            String str="";
            int length = list.get(position).getImages().length();
            for (int j=0;j<length;j++){
                if (list.get(position).getImages().substring(j,j+1).equals("|")){
                    str= list.get(position).getImages().substring(j + 1, length).trim();
                }
            }

            String images = list.get(position).getImages();  //截取字符串两种方式
            Pattern pattern = Pattern.compile("\\|");
            String[] img = pattern.split(images);


            Uri uri = Uri.parse(img[0]);
            lineViewHolder.mImageView.setImageURI(uri);

            final String finalStr = str;
            holder.itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    mOnItemClickListener.onItemClick(finalStr,list.get(gridPosision).getTitle(),list.get(gridPosision).getPrice());
                }
            });
        }
    }


    public interface OnItemClickListener{
        void onItemClick(String url, String title, String price);
    }

    OnItemClickListener mOnItemClickListener;

    public void setOnItemClickListener(OnItemClickListener onItemClickListener){
        mOnItemClickListener=onItemClickListener;
    }


    @Override
    public int getItemCount() {
        return list.size();
    }




    public class ViewHolder extends RecyclerView.ViewHolder {
        @BindView(R.id.item_img)
        SimpleDraweeView mImageView;
        TextView mTextView;
        TextView mTextView02;
        public ViewHolder(View itemView) {
            super(itemView);

            ButterKnife.bind(this,itemView);
            //mImageView = itemView.findViewById(R.id.item_img);
            mTextView = itemView.findViewById(R.id.item_t1);
            mTextView02 = itemView.findViewById(R.id.item_t2);
        }
    }
    public class LineViewHolder extends RecyclerView.ViewHolder {
        @BindView(R.id.item_img)
        SimpleDraweeView mImageView;
        TextView mTextView;
        TextView mTextView02;
        public LineViewHolder(View itemView) {
            super(itemView);

            ButterKnife.bind(this,itemView);
            //mImageView = itemView.findViewById(R.id.item_img);
            mTextView = itemView.findViewById(R.id.item_t1);
            mTextView02 = itemView.findViewById(R.id.item_t2);
        }
    }
}
