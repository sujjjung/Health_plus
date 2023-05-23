package com.example.djsu;

import android.content.Context;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;

import com.squareup.picasso.Picasso;

import java.util.ArrayList;

public class ImageAdapter extends BaseAdapter {
    private Context context;
    private ArrayList<String> imageUrls;

    public ImageAdapter(Context context, ArrayList<String> imageUrls) {
        this.context = context;
        this.imageUrls = imageUrls;
    }

    @Override
    public int getCount() {
        return imageUrls.size();
    }

    @Override
    public Object getItem(int position) {
        return imageUrls.get(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        ImageView imageView;

        if (convertView == null) {
            imageView = new ImageView(context);
            imageView.setLayoutParams(new ViewGroup.LayoutParams(250, 250)); // 이미지 뷰의 크기를 조정합니다.
            imageView.setScaleType(ImageView.ScaleType.CENTER_CROP);
        } else {
            imageView = (ImageView) convertView;
        }

        // 이미지 로딩을 위해 Picasso 라이브러리를 사용합니다. 필요하다면 해당 라이브러리를 추가해야 합니다.
        Picasso.get()
                .load(imageUrls.get(position))
                .placeholder(R.drawable.loading) // 이미지가 로드되기 전에 보여줄 임시 이미지
                .error(R.drawable.error) // 이미지 로드 실패 시 보여줄 이미지
                .into(imageView);

        return imageView;
    }
}
