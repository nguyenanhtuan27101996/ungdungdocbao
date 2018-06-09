package com.example.tuanpc.appnews;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.example.tuanpc.appnews.Controllers.CustomAdapterTypeGiaDinh;
import com.example.tuanpc.appnews.Controllers.CustomAdapterTypeSucKhoe;
import com.example.tuanpc.appnews.Models.TypeGiaDinh;
import com.example.tuanpc.appnews.Models.TypeSucKhoe;
import com.example.tuanpc.appnews.Views.ReadNewsScreen;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;

import java.util.ArrayList;

public class GiaDinhActivity extends Activity {
    private String URL_RSS_GIADINH="https://vnexpress.net/rss/gia-dinh.rss";
    private ListView lvGiaDinh;
    private TextView txtTongSoBaiViet;
    private CustomAdapterTypeGiaDinh adapterTypeGiaDinh;
    private ArrayList<TypeGiaDinh>arrayListGiaDinh;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.layout_contain_listview);
        mappedView();
        ReadRSSGiaDinh(URL_RSS_GIADINH);

        getActionBar().setDisplayHomeAsUpEnabled(true);
        getActionBar().setHomeAsUpIndicator(R.drawable.iconback);
        getActionBar().setTitle("Gia đình");

        addEvenListener();
    }
    private void addEvenListener(){
        lvGiaDinh.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                Intent intent=new Intent(GiaDinhActivity.this, ReadNewsScreen.class);
                intent.putExtra("contentLink", arrayListGiaDinh.get(i).getContent());
                intent.putExtra("titleBaiViet", arrayListGiaDinh.get(i).getTitle());
                startActivity(intent);
            }
        });
    }
    private void ReadRSSGiaDinh(String url){
        arrayListGiaDinh=new ArrayList<>();
        RequestQueue requestQueue= Volley.newRequestQueue(GiaDinhActivity.this);
        StringRequest stringRequest=new StringRequest(Request.Method.GET, url, new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
                Document document= Jsoup.parse(response);
                Elements elements=document.select("item");

                for(Element items:elements){
                    try{
                        String strTieuDe=items.select("title").text();

                        String strDescription=items.select("description").text();

                        Document docImage=Jsoup.parse(strDescription);
                        String strLinkImage=docImage.select("img").get(0).attr("src");

                        String strDuongDan=items.select("guid").text();

                        arrayListGiaDinh.add(new TypeGiaDinh(strTieuDe,strDuongDan,strLinkImage));
                    }catch (IndexOutOfBoundsException e){

                    }

                }
                createListViewGiaDinh();
                txtTongSoBaiViet.setText("Danh sách "+arrayListGiaDinh.size()+
                        " bài viết được cập nhật gần nhất và hơn nữa");

            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                Toast.makeText(GiaDinhActivity.this, "Có lỗi xảy ra", Toast.LENGTH_SHORT).show();
            }
        });
        requestQueue.add(stringRequest);
    }
    private void createListViewGiaDinh(){
        adapterTypeGiaDinh=new CustomAdapterTypeGiaDinh(GiaDinhActivity.this,
                R.layout.row_listview_main,arrayListGiaDinh);
        lvGiaDinh.setAdapter(adapterTypeGiaDinh);
    }

    private void mappedView(){
        lvGiaDinh=findViewById(R.id.lvShowBaiViet);
        txtTongSoBaiViet=findViewById(R.id.txtTongSoBaiViet);

    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()){
            case android.R.id.home:
                finish();
                break;
        }
        return super.onOptionsItemSelected(item);
    }
}
