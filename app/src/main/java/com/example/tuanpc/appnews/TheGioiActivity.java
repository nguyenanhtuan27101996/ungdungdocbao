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
import com.example.tuanpc.appnews.Controllers.CustomAdapterNewsTheGioi;
import com.example.tuanpc.appnews.Models.NewsTheGioi;
import com.example.tuanpc.appnews.Views.ReadNewsScreen;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;

import java.util.ArrayList;

public class TheGioiActivity extends Activity {
    private String URL_RSS_THEGIOI="https://vnexpress.net/rss/the-gioi.rss";
    private TextView txtTongSoBaiVietTheGioi;
    private ListView lvTheGioi;
    private ArrayList<NewsTheGioi>arrayListTheGioi;
    private CustomAdapterNewsTheGioi adapterTheGioi;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.layout_contain_listview);
        mappedView();

        getActionBar().setDisplayHomeAsUpEnabled(true);
        getActionBar().setHomeAsUpIndicator(R.drawable.iconback);
        getActionBar().setTitle("Thế giới");
        ReadRSSTheGioi(URL_RSS_THEGIOI);

        addEventListener();
    }
    private void addEventListener(){
        lvTheGioi.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                Intent intent=new Intent(TheGioiActivity.this, ReadNewsScreen.class);
                intent.putExtra("contentLink",arrayListTheGioi.get(i).getContent());
                intent.putExtra("titleBaiViet",arrayListTheGioi.get(i).getTitle());
                startActivity(intent);
            }
        });
    }
    private void ReadRSSTheGioi(String url){
        arrayListTheGioi=new ArrayList<>();
        RequestQueue requestQueue= Volley.newRequestQueue(TheGioiActivity.this);
        StringRequest stringRequest=new StringRequest(Request.Method.GET, url,
                new Response.Listener<String>() {
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

                        arrayListTheGioi.add(new NewsTheGioi(strTieuDe,strDuongDan,strLinkImage));
                    }catch (IndexOutOfBoundsException e){

                    }

                }
                createListViewTheGioi();
                txtTongSoBaiVietTheGioi.setText("Danh sách "+arrayListTheGioi.size()+
                " bài viết được cập nhật gần nhất và hơn nữa");
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                Toast.makeText(TheGioiActivity.this, "Có lỗi xảy ra", Toast.LENGTH_SHORT).show();
            }
        });
        requestQueue.add(stringRequest);
    }

    private void createListViewTheGioi(){
        adapterTheGioi=new CustomAdapterNewsTheGioi(TheGioiActivity.this,
                R.layout.row_listview_main,
                arrayListTheGioi);
        lvTheGioi.setAdapter(adapterTheGioi);
    }
    private void mappedView(){
        txtTongSoBaiVietTheGioi=findViewById(R.id.txtTongSoBaiViet);
        lvTheGioi=findViewById(R.id.lvShowBaiViet);
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
