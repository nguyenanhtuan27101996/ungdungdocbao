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
import com.example.tuanpc.appnews.Controllers.CustomAdapterTypeGiaoDuc;
import com.example.tuanpc.appnews.Controllers.CustomAdapterTypePhapLuat;
import com.example.tuanpc.appnews.Models.TypeGiaoDuc;
import com.example.tuanpc.appnews.Models.TypePhapLuat;
import com.example.tuanpc.appnews.Views.ReadNewsScreen;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;

import java.util.ArrayList;

public class GiaoDucActivity extends Activity {
    private String URL_RSS_GIAODUC="https://vnexpress.net/rss/giao-duc.rss";
    private ListView lvGiaoDuc;
    private TextView txtTongSoBaiViet;
    private CustomAdapterTypeGiaoDuc adapterTypeGiaoDuc;
    private ArrayList<TypeGiaoDuc>arrayListGiaoDuc;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.layout_contain_listview);
        mappedView();

        ReadRSSGiaoDuc(URL_RSS_GIAODUC);

        getActionBar().setDisplayHomeAsUpEnabled(true);
        getActionBar().setHomeAsUpIndicator(R.drawable.iconback);
        getActionBar().setTitle("Giáo dục");

        addEvenListener();
    }
    private void addEvenListener(){
        lvGiaoDuc.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                Intent intent=new Intent(GiaoDucActivity.this, ReadNewsScreen.class);
                intent.putExtra("contentLink", arrayListGiaoDuc.get(i).getContent());
                intent.putExtra("titleBaiViet", arrayListGiaoDuc.get(i).getTitle());
                startActivity(intent);
            }
        });
    }
    private void ReadRSSGiaoDuc(String url){
        arrayListGiaoDuc=new ArrayList<>();
        RequestQueue requestQueue= Volley.newRequestQueue(GiaoDucActivity.this);
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

                        arrayListGiaoDuc.add(new TypeGiaoDuc(strTieuDe,strDuongDan,strLinkImage));
                    }catch (IndexOutOfBoundsException e){

                    }

                }
                createListViewGiaoDuc();
                txtTongSoBaiViet.setText("Danh sách "+arrayListGiaoDuc.size()+
                        " bài viết được cập nhật gần nhất và hơn nữa");

            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                Toast.makeText(GiaoDucActivity.this, "Có lỗi xảy ra", Toast.LENGTH_SHORT).show();
            }
        });
        requestQueue.add(stringRequest);
    }
    private void createListViewGiaoDuc(){
        adapterTypeGiaoDuc=new CustomAdapterTypeGiaoDuc(GiaoDucActivity.this,
                R.layout.row_listview_main,arrayListGiaoDuc);
        lvGiaoDuc.setAdapter(adapterTypeGiaoDuc);
    }
    private void mappedView(){
        lvGiaoDuc=findViewById(R.id.lvShowBaiViet);
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
