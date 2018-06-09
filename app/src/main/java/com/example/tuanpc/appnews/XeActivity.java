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
import com.example.tuanpc.appnews.Controllers.CustomAdapterTypeKhoaHoc;
import com.example.tuanpc.appnews.Controllers.CustomAdapterTypeXe;
import com.example.tuanpc.appnews.Models.TypeKhoaHoc;
import com.example.tuanpc.appnews.Models.TypeXe;
import com.example.tuanpc.appnews.Views.ReadNewsScreen;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;

import java.util.ArrayList;

public class XeActivity extends Activity {
    private String URL_RSS_XE="https://vnexpress.net/rss/oto-xe-may.rss";
    private ListView lvXe;
    private TextView txtTongSoBaiViet;
    private CustomAdapterTypeXe adapterTypeXe;
    private ArrayList<TypeXe>arrayListXe;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.layout_contain_listview);

        mappedView();

        ReadRSSXe(URL_RSS_XE);

        getActionBar().setDisplayHomeAsUpEnabled(true);
        getActionBar().setHomeAsUpIndicator(R.drawable.iconback);
        getActionBar().setTitle("Xe");

        addEvenListener();
    }

    private void addEvenListener(){
        lvXe.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                Intent intent=new Intent(XeActivity.this, ReadNewsScreen.class);
                intent.putExtra("contentLink", arrayListXe.get(i).getContent());
                intent.putExtra("titleBaiViet", arrayListXe.get(i).getTitle());
                startActivity(intent);
            }
        });
    }
    private void ReadRSSXe(String url){
        arrayListXe=new ArrayList<>();
        RequestQueue requestQueue= Volley.newRequestQueue(XeActivity.this);
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

                        arrayListXe.add(new TypeXe(strTieuDe,strDuongDan,strLinkImage));
                    }catch (IndexOutOfBoundsException e){

                    }

                }
                createListViewXe();
                txtTongSoBaiViet.setText("Danh sách "+arrayListXe.size()+
                        " bài viết được cập nhật gần nhất và hơn nữa");

            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                Toast.makeText(XeActivity.this, "Có lỗi xảy ra", Toast.LENGTH_SHORT).show();
            }
        });
        requestQueue.add(stringRequest);
    }
    private void createListViewXe(){
        adapterTypeXe=new CustomAdapterTypeXe(XeActivity.this,
                R.layout.row_listview_main,arrayListXe);
        lvXe.setAdapter(adapterTypeXe);
    }

    private void mappedView(){
        lvXe=findViewById(R.id.lvShowBaiViet);
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
