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
import com.example.tuanpc.appnews.Controllers.CustomAdapterNewsKinhDoanh;
import com.example.tuanpc.appnews.Models.NewsKinhDoanh;
import com.example.tuanpc.appnews.Views.ReadNewsScreen;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;

import java.util.ArrayList;

public class KinhDoanhActivity extends Activity {
    private String URL_RSS_KINHDOANH="https://vnexpress.net/rss/kinh-doanh.rss";
    private TextView txtTongSoBaiVietKinhDoanh;
    private ListView lvTypeKinhDoanh;
    private CustomAdapterNewsKinhDoanh adapterKinhDoanh;
    private ArrayList<NewsKinhDoanh>arrayListKinhDoanh;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.layout_contain_listview);

        mappedView();

        ReadRSSKinhDoanh(URL_RSS_KINHDOANH);
        getActionBar().setDisplayHomeAsUpEnabled(true);
        getActionBar().setHomeAsUpIndicator(R.drawable.iconback);
        getActionBar().setTitle("Kinh Doanh");
        addEventListener();
    }
    private void addEventListener(){
        lvTypeKinhDoanh.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                Intent intent=new Intent(KinhDoanhActivity.this, ReadNewsScreen.class);
                intent.putExtra("contentLink", arrayListKinhDoanh.get(i).getContent());
                intent.putExtra("titleBaiViet", arrayListKinhDoanh.get(i).getTitle());
                startActivity(intent);
            }
        });
    }
    private void ReadRSSKinhDoanh(String url){
        arrayListKinhDoanh=new ArrayList<>();
        RequestQueue requestQueue= Volley.newRequestQueue(KinhDoanhActivity.this);
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

                        arrayListKinhDoanh.add(new NewsKinhDoanh(strTieuDe,strDuongDan,strLinkImage));
                    }catch (IndexOutOfBoundsException e){

                    }

                }
                createListViewKinhDoanh();
                txtTongSoBaiVietKinhDoanh.setText("Danh sách "+arrayListKinhDoanh.size()+
                        " bài viết được cập nhật gần nhất và hơn nữa");

            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                Toast.makeText(KinhDoanhActivity.this, "Có lỗi xảy ra", Toast.LENGTH_SHORT).show();
            }
        });
        requestQueue.add(stringRequest);
    }
    private void createListViewKinhDoanh(){
        adapterKinhDoanh=new CustomAdapterNewsKinhDoanh(KinhDoanhActivity.this,
                R.layout.row_listview_main,arrayListKinhDoanh);
        lvTypeKinhDoanh.setAdapter(adapterKinhDoanh);
    }
    private void mappedView(){
        txtTongSoBaiVietKinhDoanh=findViewById(R.id.txtTongSoBaiViet);
        lvTypeKinhDoanh=findViewById(R.id.lvShowBaiViet);

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
