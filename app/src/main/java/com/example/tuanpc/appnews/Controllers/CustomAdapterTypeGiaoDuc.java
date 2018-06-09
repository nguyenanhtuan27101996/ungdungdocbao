package com.example.tuanpc.appnews.Controllers;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.tuanpc.appnews.Models.TypeGiaoDuc;
import com.example.tuanpc.appnews.Models.TypePhapLuat;
import com.example.tuanpc.appnews.R;
import com.squareup.picasso.Picasso;

import org.w3c.dom.Text;

import java.lang.reflect.Type;
import java.util.ArrayList;

/**
 * Created by tuanpc on 9/24/2017.
 */

public class CustomAdapterTypeGiaoDuc extends BaseAdapter {
    private Context context;
    private int layout;
    private ArrayList<TypeGiaoDuc>arrayListTypeGiaoDuc;

    public CustomAdapterTypeGiaoDuc(Context context, int layout, ArrayList<TypeGiaoDuc> arrayListTypeGiaoDuc) {
        this.context = context;
        this.layout = layout;
        this.arrayListTypeGiaoDuc = arrayListTypeGiaoDuc;
    }

    @Override
    public int getCount() {
        return arrayListTypeGiaoDuc.size();
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
        ImageView imgNews;
        TextView txtTieuDeNews;

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
            viewHolder.imgNews=view.findViewById(R.id.imgNews);
            viewHolder.txtTieuDeNews=view.findViewById(R.id.txtTieuDeNews);

            view.setTag(viewHolder);
        }else{
            viewHolder= (ViewHolder) view.getTag();
            TypeGiaoDuc objGiaoDuc=arrayListTypeGiaoDuc.get(i);

            viewHolder.txtTieuDeNews.setText(objGiaoDuc.getTitle());
            Picasso.with(context).load(objGiaoDuc.getImage()).into(viewHolder.imgNews);
        }
        return view;
    }
}
