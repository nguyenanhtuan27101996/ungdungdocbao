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
import com.example.tuanpc.appnews.Controllers.CustomAdapterTypeSucKhoe;
import com.example.tuanpc.appnews.Models.TypeGiaoDuc;
import com.example.tuanpc.appnews.Models.TypeSucKhoe;
import com.example.tuanpc.appnews.Views.ReadNewsScreen;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;

import java.util.ArrayList;

public class SucKhoeActivity extends Activity {
    private String URL_RSS_SUCKHOE="https://vnexpress.net/rss/suc-khoe.rss";
    private ListView lvSucKhoe;
    private TextView txtTongSoBaiViet;
    private CustomAdapterTypeSucKhoe customAdapterTypeSucKhoe;
    private ArrayList<TypeSucKhoe>arrayListSucKhoe;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.layout_contain_listview);

        mappedView();
        ReadRSSSucKhoe(URL_RSS_SUCKHOE);

        getActionBar().setDisplayHomeAsUpEnabled(true);
        getActionBar().setHomeAsUpIndicator(R.drawable.iconback);
        getActionBar().setTitle("Sức khỏe");

        addEvenListener();
    }
    private void addEvenListener(){
        lvSucKhoe.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                Intent intent=new Intent(SucKhoeActivity.this, ReadNewsScreen.class);
                intent.putExtra("contentLink", arrayListSucKhoe.get(i).getContent());
                intent.putExtra("titleBaiViet", arrayListSucKhoe.get(i).getTitle());
                startActivity(intent);
            }
        });
    }
    private void ReadRSSSucKhoe(String url){
        arrayListSucKhoe=new ArrayList<>();
        RequestQueue requestQueue= Volley.newRequestQueue(SucKhoeActivity.this);
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

                        arrayListSucKhoe.add(new TypeSucKhoe(strTieuDe,strDuongDan,strLinkImage));
                    }catch (IndexOutOfBoundsException e){

                    }

                }
                createListViewSucKhoe();
                txtTongSoBaiViet.setText("Danh sách "+arrayListSucKhoe.size()+
                        " bài viết được cập nhật gần nhất và hơn nữa");

            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                Toast.makeText(SucKhoeActivity.this, "Có lỗi xảy ra", Toast.LENGTH_SHORT).show();
            }
        });
        requestQueue.add(stringRequest);
    }
    private void createListViewSucKhoe(){
        customAdapterTypeSucKhoe=new CustomAdapterTypeSucKhoe(SucKhoeActivity.this,
                R.layout.row_listview_main,arrayListSucKhoe);
        lvSucKhoe.setAdapter(customAdapterTypeSucKhoe);
    }

    private void mappedView(){
        lvSucKhoe=findViewById(R.id.lvShowBaiViet);
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
