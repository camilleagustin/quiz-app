package com.example.quizapp.adapters;

import android.content.Context;
import android.graphics.Color;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;

import com.example.quizapp.R;

public class AvatarAdapter extends BaseAdapter {

    private Context context;
    private LayoutInflater inflater;
    private int[] images;
    private int selectedPosition = -1;

    public AvatarAdapter(Context context, int[] images) {
        this.context = context;
        this.images = images;
    }

    @Override

    public int getCount() {
        return images.length;
    }

    @Override
    public Object getItem(int position) {
        return null;
    }

    @Override
    public long getItemId(int position) {
        return 0;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        if (inflater == null) {
            inflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        }
        if (convertView == null) {
            convertView = inflater.inflate(R.layout.item_avatar, null);
        }

        if (position == selectedPosition) {
            convertView.setBackgroundResource(R.drawable.rounded_image);
        } else {
            convertView.setBackgroundColor(Color.TRANSPARENT);
        }

        ImageView imgAvatar = convertView.findViewById(R.id.imgAvatar);
        imgAvatar.setImageResource(images[position]);

        return convertView;
    }

    public void setSelectedPosition(int position) {
        selectedPosition = position;
    }
}
