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
import com.example.tuanpc.appnews.Controllers.CustomAdapterTypeThoiSu;
import com.example.tuanpc.appnews.Models.TypeThoiSu;
import com.example.tuanpc.appnews.Views.ReadNewsScreen;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;

import java.util.ArrayList;

public class ThoiSuActivity extends Activity {
    private TextView txtTongSoBaiViet;
    private ListView lvThoiSu;
    private String URL_RSS_THOISU="https://vnexpress.net/rss/thoi-su.rss";

    private ArrayList<TypeThoiSu>arrayListThoiSu;
    private CustomAdapterTypeThoiSu adapterTypeThoiSu;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.layout_contain_listview);
        mappedView();
        getActionBar().setDisplayHomeAsUpEnabled(true);
        getActionBar().setHomeAsUpIndicator(R.drawable.iconback);
        getActionBar().setTitle("Thời sự");

        ReadRSSThoiSu(URL_RSS_THOISU);
        addEventListener();
    }
    private void addEventListener(){
        lvThoiSu.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                Intent intent=new Intent(ThoiSuActivity.this, ReadNewsScreen.class);
                intent.putExtra("contentLink",arrayListThoiSu.get(i).getContent());
                intent.putExtra("titleBaiViet",arrayListThoiSu.get(i).getTitle());
                startActivity(intent);
            }
        });
    }
    private void ReadRSSThoiSu(String url){
        arrayListThoiSu=new ArrayList<>();
        RequestQueue requestQueue= Volley.newRequestQueue(ThoiSuActivity.this);
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

                        arrayListThoiSu.add(new TypeThoiSu(strTieuDe,strDuongDan,strLinkImage));
                    }catch (IndexOutOfBoundsException e){

                    }

                }
                createListViewTypeThoiSu();
                txtTongSoBaiViet.setText("Danh sách "+arrayListThoiSu.size()+" bài viết" +
                        " được cập nhật gần nhất và hơn nữa");
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                Toast.makeText(ThoiSuActivity.this, "Có lỗi xảy ra", Toast.LENGTH_SHORT).show();

            }
        });
        requestQueue.add(stringRequest);
    }
    private void createListViewTypeThoiSu(){
        adapterTypeThoiSu=new CustomAdapterTypeThoiSu(ThoiSuActivity.this,
                R.layout.row_listview_main
        ,arrayListThoiSu);
        lvThoiSu.setAdapter(adapterTypeThoiSu);
    }
    private void mappedView(){
        lvThoiSu=findViewById(R.id.lvShowBaiViet);
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
