package com.example.tuanpc.appnews.Controllers;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.tuanpc.appnews.Models.NewsTheGioi;
import com.example.tuanpc.appnews.R;
import com.squareup.picasso.Picasso;

import java.util.ArrayList;

/**
 * Created by tuanpc on 9/22/2017.
 */

public class CustomAdapterNewsTheGioi extends BaseAdapter {
    private Context context;
    private int layout;
    private ArrayList<NewsTheGioi>arrayListNewsTheGioi;

    public CustomAdapterNewsTheGioi(Context context, int layout, ArrayList<NewsTheGioi> arrayListNewsTheGioi) {
        this.context = context;
        this.layout = layout;
        this.arrayListNewsTheGioi = arrayListNewsTheGioi;
    }

    @Override
    public int getCount() {
        return arrayListNewsTheGioi.size();
    }

    @Override
    public Object getItem(int i) {
        return null;
    }

    @Override
    public long getItemId(int i) {
        return 0;
    }
    private class ViewHolder{
        ImageView imgNewsTheGioi;
        TextView txtTieuDeNewsTheGioi;
    }
    @Override
    public View getView(int i, View view, ViewGroup viewGroup) {
        ViewHolder viewHolder;
        if (view == null) {
            viewHolder=new ViewHolder();
            LayoutInflater inflater=
                    (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
            view=inflater.inflate(layout,null);

            //anh xa
            viewHolder.imgNewsTheGioi=view.findViewById(R.id.imgNews);
            viewHolder.txtTieuDeNewsTheGioi=view.findViewById(R.id.txtTieuDeNews);

            view.setTag(viewHolder);
        }else{
            viewHolder= (ViewHolder) view.getTag();
        }

        NewsTheGioi objNewsTheGioi=arrayListNewsTheGioi.get(i);
        viewHolder.txtTieuDeNewsTheGioi.setText(objNewsTheGioi.getTitle());
        Picasso.with(context).load(objNewsTheGioi.getImage()).into(viewHolder.imgNewsTheGioi);
        return view;
    }
}
