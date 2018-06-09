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
import com.example.tuanpc.appnews.Controllers.CustomAdapterTypeCuoi;
import com.example.tuanpc.appnews.Controllers.CustomAdapterTypeTamSu;
import com.example.tuanpc.appnews.Models.TypeCuoi;
import com.example.tuanpc.appnews.Models.TypeTamSu;
import com.example.tuanpc.appnews.Views.ReadNewsScreen;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;
import org.w3c.dom.Text;

import java.util.ArrayList;

public class CuoiActivity extends Activity {
    private String URL_RSS_CUOI="https://vnexpress.net/rss/cuoi.rss";
    private ListView lvCuoi;
    private TextView txtTongSoBaiViet;
    private CustomAdapterTypeCuoi adapterTypeCuoi;
    private ArrayList<TypeCuoi>arrayListCuoi;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.layout_contain_listview);
        mappedView();

        ReadRSSCuoi(URL_RSS_CUOI);
        getActionBar().setDisplayHomeAsUpEnabled(true);
        getActionBar().setHomeAsUpIndicator(R.drawable.iconback);
        getActionBar().setTitle("Cười");
        addEvenListener();
    }

    private void addEvenListener(){
        lvCuoi.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                Intent intent=new Intent(CuoiActivity.this, ReadNewsScreen.class);
                intent.putExtra("contentLink", arrayListCuoi.get(i).getContent());
                intent.putExtra("titleBaiViet", arrayListCuoi.get(i).getTitle());
                startActivity(intent);
            }
        });
    }
    private void ReadRSSCuoi(String url){
        arrayListCuoi=new ArrayList<>();
        RequestQueue requestQueue= Volley.newRequestQueue(CuoiActivity.this);
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

                        arrayListCuoi.add(new TypeCuoi(strTieuDe,strDuongDan,strLinkImage));
                    }catch (IndexOutOfBoundsException e){

                    }

                }
                createListViewCuoi();
                txtTongSoBaiViet.setText("Danh sách "+arrayListCuoi.size()+
                        " bài viết được cập nhật gần nhất và hơn nữa");

            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                Toast.makeText(CuoiActivity.this, "Có lỗi xảy ra", Toast.LENGTH_SHORT).show();
            }
        });
        requestQueue.add(stringRequest);
    }
    private void createListViewCuoi(){
        adapterTypeCuoi=new CustomAdapterTypeCuoi(CuoiActivity.this,
                R.layout.row_listview_main,arrayListCuoi);
        lvCuoi.setAdapter(adapterTypeCuoi);
    }

    private void mappedView(){
        lvCuoi=findViewById(R.id.lvShowBaiViet);
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
