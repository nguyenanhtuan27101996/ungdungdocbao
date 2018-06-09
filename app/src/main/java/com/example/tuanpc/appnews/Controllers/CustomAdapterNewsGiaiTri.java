package com.example.tuanpc.appnews.Controllers;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.tuanpc.appnews.Models.NewsGiaiTri;
import com.example.tuanpc.appnews.R;
import com.squareup.picasso.Picasso;

import java.util.ArrayList;

/**
 * Created by tuanpc on 9/23/2017.
 */

public class CustomAdapterNewsGiaiTri extends BaseAdapter {
    private Context context;
    private int layout;
    private ArrayList<NewsGiaiTri>arrayListNewsGiaiTri;

    public CustomAdapterNewsGiaiTri(Context context, int layout, ArrayList<NewsGiaiTri> arrayListNewsGiaiTri) {
        this.context = context;
        this.layout = layout;
        this.arrayListNewsGiaiTri = arrayListNewsGiaiTri;
    }

    @Override
    public int getCount() {
        return arrayListNewsGiaiTri.size();
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
        ImageView imgNewsGiaiTri;
        TextView txtNewsGiaiTri;
    }
    @Override
    public View getView(int i, View view, ViewGroup viewGroup) {
        ViewHolder viewHolder;
        if (view == null) {
            LayoutInflater inflater= (LayoutInflater)
                    context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
            view=inflater.inflate(layout,null);

            viewHolder=new ViewHolder();
            //anh xa

            viewHolder.imgNewsGiaiTri=view.findViewById(R.id.imgNews);
            viewHolder.txtNewsGiaiTri=view.findViewById(R.id.txtTieuDeNews);

            view.setTag(viewHolder);
        }else{
            viewHolder= (ViewHolder) view.getTag();
        }

        NewsGiaiTri objNewsGiaiTri=arrayListNewsGiaiTri.get(i);
        viewHolder.txtNewsGiaiTri.setText(objNewsGiaiTri.getTitle());
        Picasso.with(context).load(objNewsGiaiTri.getImage()).into(viewHolder.imgNewsGiaiTri);

        return view;
    }
}
