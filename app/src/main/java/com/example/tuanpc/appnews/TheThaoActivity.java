package com.example.tuanpc.appnews;

import android.app.Activity;
import android.content.Intent;
import android.os.AsyncTask;
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
import com.example.tuanpc.appnews.Controllers.CustomAdapterTypeStartUp;
import com.example.tuanpc.appnews.Controllers.CustomAdapterTypeTheThao;
import com.example.tuanpc.appnews.Models.TypeStartUp;
import com.example.tuanpc.appnews.Models.TypeTheThao;
import com.example.tuanpc.appnews.Views.ReadNewsScreen;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;

import java.io.IOException;
import java.util.ArrayList;

public class TheThaoActivity extends Activity {
    private String URL_RSS_THETHAO="https://vnexpress.net/rss/the-thao.rss";
    private TextView txtTongSoBaiViet;
    private ListView lvTheThao;
    private ArrayList<TypeTheThao>arrayListTheThao;
    private CustomAdapterTypeTheThao customAdapterTypeTheThao;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.layout_contain_listview);
        mappedView();

        ReadRSSTheThao(URL_RSS_THETHAO);

        getActionBar().setDisplayHomeAsUpEnabled(true);
        getActionBar().setHomeAsUpIndicator(R.drawable.iconback);
        getActionBar().setTitle("Thể thao");

        addEvenListenr();
    }
    private void addEvenListenr(){
        lvTheThao.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                Intent intent=new Intent(TheThaoActivity.this, ReadNewsScreen.class);
                intent.putExtra("contentLink", arrayListTheThao.get(i).getContent());
                intent.putExtra("titleBaiViet", arrayListTheThao.get(i).getTitle());
                startActivity(intent);
            }
        });
    }
    private void ReadRSSTheThao(String url){
        arrayListTheThao=new ArrayList<>();
        RequestQueue requestQueue= Volley.newRequestQueue(TheThaoActivity.this);
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

                        arrayListTheThao.add(new TypeTheThao(strTieuDe,strDuongDan,strLinkImage));
                    }catch (IndexOutOfBoundsException e){

                    }

                }
                createListViewTheThao();
                txtTongSoBaiViet.setText("Danh sách "+arrayListTheThao.size()+
                        " bài viết được cập nhật gần nhất và hơn nữa");

            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                Toast.makeText(TheThaoActivity.this, "Có lỗi xảy ra", Toast.LENGTH_SHORT).show();
            }
        });
        requestQueue.add(stringRequest);
    }
    private void createListViewTheThao(){
        customAdapterTypeTheThao=new CustomAdapterTypeTheThao(TheThaoActivity.this,
                R.layout.row_listview_main,arrayListTheThao);
        lvTheThao.setAdapter(customAdapterTypeTheThao);
    }
    private void mappedView(){
        lvTheThao=findViewById(R.id.lvShowBaiViet);
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
