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
import com.example.tuanpc.appnews.Controllers.CustomAdapterTypeCongDong;
import com.example.tuanpc.appnews.Controllers.CustomAdapterTypeTamSu;
import com.example.tuanpc.appnews.Models.TypeCongDong;
import com.example.tuanpc.appnews.Models.TypeTamSu;
import com.example.tuanpc.appnews.Views.ReadNewsScreen;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;
import org.w3c.dom.Text;

import java.util.ArrayList;

public class TamSuActivity extends Activity {
    private String URL_RSS_TAMSU="https://vnexpress.net/rss/tam-su.rss";
    private ListView lvTamSu;
    private TextView txtTongSoBaiViet;
    private CustomAdapterTypeTamSu adapterTypeTamSu;
    private ArrayList<TypeTamSu>arrayListTamSu;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.layout_contain_listview);

        mappedView();

        ReadRSSTamSu(URL_RSS_TAMSU);

        getActionBar().setDisplayHomeAsUpEnabled(true);
        getActionBar().setHomeAsUpIndicator(R.drawable.iconback);
        getActionBar().setTitle("Tâm sự");

        addEvenListener();

    }
    private void addEvenListener(){
        lvTamSu.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                Intent intent=new Intent(TamSuActivity.this, ReadNewsScreen.class);
                intent.putExtra("contentLink", arrayListTamSu.get(i).getContent());
                intent.putExtra("titleBaiViet", arrayListTamSu.get(i).getTitle());
                startActivity(intent);
            }
        });
    }
    private void ReadRSSTamSu(String url){
        arrayListTamSu=new ArrayList<>();
        RequestQueue requestQueue= Volley.newRequestQueue(TamSuActivity.this);
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

                        arrayListTamSu.add(new TypeTamSu(strTieuDe,strDuongDan,strLinkImage));
                    }catch (IndexOutOfBoundsException e){

                    }

                }
                createListViewTamSu();
                txtTongSoBaiViet.setText("Danh sách "+arrayListTamSu.size()+
                        " bài viết được cập nhật gần nhất và hơn nữa");

            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                Toast.makeText(TamSuActivity.this, "Có lỗi xảy ra", Toast.LENGTH_SHORT).show();
            }
        });
        requestQueue.add(stringRequest);
    }
    private void createListViewTamSu(){
        adapterTypeTamSu=new CustomAdapterTypeTamSu(TamSuActivity.this,
                R.layout.row_listview_main,arrayListTamSu);
        lvTamSu.setAdapter(adapterTypeTamSu);
    }

    private void mappedView(){
        lvTamSu=findViewById(R.id.lvShowBaiViet);
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
