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
import com.example.tuanpc.appnews.Controllers.CustomAdapterTypeSoHoa;
import com.example.tuanpc.appnews.Models.TypeKhoaHoc;
import com.example.tuanpc.appnews.Models.TypeSoHoa;
import com.example.tuanpc.appnews.Views.ReadNewsScreen;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;

import java.util.ArrayList;

public class SoHoaActivity extends Activity {
    private String URL_RSS_SOHOA="https://vnexpress.net/rss/so-hoa.rss";
    private ListView lvSoHoa;
    private TextView txtTongSoBaiViet;
    private CustomAdapterTypeSoHoa adapterTypeSoHoa;
    private ArrayList<TypeSoHoa>arrayListSoHoa;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.layout_contain_listview);
        mappedView();
        ReadRSSSoHoa(URL_RSS_SOHOA);

        getActionBar().setDisplayHomeAsUpEnabled(true);
        getActionBar().setHomeAsUpIndicator(R.drawable.iconback);
        getActionBar().setTitle("Số hóa");

        addEvenListener();
    }

    private void addEvenListener(){
        lvSoHoa.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                Intent intent=new Intent(SoHoaActivity.this, ReadNewsScreen.class);
                intent.putExtra("contentLink", arrayListSoHoa.get(i).getContent());
                intent.putExtra("titleBaiViet", arrayListSoHoa.get(i).getTitle());
                startActivity(intent);
            }
        });
    }
    private void ReadRSSSoHoa(String url){
        arrayListSoHoa=new ArrayList<>();
        RequestQueue requestQueue= Volley.newRequestQueue(SoHoaActivity.this);
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

                        arrayListSoHoa.add(new TypeSoHoa(strTieuDe,strDuongDan,strLinkImage));
                    }catch (IndexOutOfBoundsException e){

                    }

                }
                createListViewSoHoa();
                txtTongSoBaiViet.setText("Danh sách "+arrayListSoHoa.size()+
                        " bài viết được cập nhật gần nhất và hơn nữa");

            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                Toast.makeText(SoHoaActivity.this, "Có lỗi xảy ra", Toast.LENGTH_SHORT).show();
            }
        });
        requestQueue.add(stringRequest);
    }
    private void createListViewSoHoa(){
        adapterTypeSoHoa=new CustomAdapterTypeSoHoa(SoHoaActivity.this,
                R.layout.row_listview_main,arrayListSoHoa);
        lvSoHoa.setAdapter(adapterTypeSoHoa);
    }

    private void mappedView(){
        lvSoHoa=findViewById(R.id.lvShowBaiViet);
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
