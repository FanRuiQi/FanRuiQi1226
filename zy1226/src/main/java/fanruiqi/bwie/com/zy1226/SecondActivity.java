package fanruiqi.bwie.com.zy1226;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;

public class SecondActivity extends AppCompatActivity{
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_second);

        Intent intent = getIntent();

        String img = intent.getStringExtra("img");
        String title = intent.getStringExtra("title");
        String price = intent.getStringExtra("price");

        ImageView imageView = findViewById(R.id.second_img);
        TextView textView = findViewById(R.id.second_t1);
        TextView textView1 = findViewById(R.id.second_t2);

        Glide.with(SecondActivity.this).load(img).into(imageView);

        textView.setText(title);
        textView1.setText(price);
    }
}
