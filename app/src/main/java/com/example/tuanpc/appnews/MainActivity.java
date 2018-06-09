package com.example.tuanpc.appnews;

import android.app.Activity;
import android.content.Intent;
import android.support.v4.widget.DrawerLayout;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.TabHost;
import android.widget.Toast;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.example.tuanpc.appnews.Controllers.CustomAdapterNewsGiaiTri;
import com.example.tuanpc.appnews.Controllers.CustomAdapterNewsTheGioi;
import com.example.tuanpc.appnews.Controllers.CustomAdapterNewsKinhDoanh;
import com.example.tuanpc.appnews.Models.NewsGiaiTri;
import com.example.tuanpc.appnews.Models.NewsTheGioi;
import com.example.tuanpc.appnews.Models.NewsKinhDoanh;
import com.example.tuanpc.appnews.Views.ReadNewsScreen;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;

import java.util.ArrayList;


public class MainActivity extends Activity{
    private TabHost tabHost;
    private DrawerLayout drawerLayout;
    private LinearLayout linearSwipe;

    private ArrayAdapter adapterPaperType;

    private ListView lvTheLoai,lvTheGioi,lvGiaiTri,lvKinhDoanh;

    private ArrayList<String>arrayListPaperType;
    private ArrayList<NewsTheGioi>arrayListNewsTheGioi;
    private ArrayList<NewsGiaiTri>arrayListNewsGiaiTri;
    private ArrayList<NewsKinhDoanh> arrayListNewsKinhDoanh;

    private String URL_RSS_THEGIOI="https://vnexpress.net/rss/the-gioi.rss";
    private String URL_RSS_GIAITRI="https://vnexpress.net/rss/giai-tri.rss";
    private String URL_RSS_KINHDOANH="https://vnexpress.net/rss/kinh-doanh.rss";

    private CustomAdapterNewsTheGioi adapterNewsTheGioi;
    private CustomAdapterNewsGiaiTri adapterNewsGiaiTri;
    private CustomAdapterNewsKinhDoanh adapterNewsTheThao;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        getActionBar().setDisplayHomeAsUpEnabled(true);
        getActionBar().setHomeAsUpIndicator(R.drawable.iconmenu);
        getActionBar().setTitle("Trang chủ");
        mappedView();
        initTabHost();
        createListViewTheLoai();
        ReadRSSTheGioi(URL_RSS_THEGIOI);
        ReadRSSGiaiTri(URL_RSS_GIAITRI);
        ReadRSSKinhDoanh(URL_RSS_KINHDOANH);
        addEventListener();



    }
    private void addEventListener(){
        lvTheGioi.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                Intent intent=new Intent(MainActivity.this, ReadNewsScreen.class);
                intent.putExtra("contentLink",arrayListNewsTheGioi.get(i).getContent());
                intent.putExtra("titleBaiViet",arrayListNewsTheGioi.get(i).getTitle());
                startActivity(intent);
            }
        });

        lvGiaiTri.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                Intent intent=new Intent(MainActivity.this, ReadNewsScreen.class);
                intent.putExtra("contentLink",arrayListNewsGiaiTri.get(i).getContent());
                intent.putExtra("titleBaiViet",arrayListNewsGiaiTri.get(i).getTitle());
                startActivity(intent);
            }
        });
        lvKinhDoanh.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                Intent intent=new Intent(MainActivity.this, ReadNewsScreen.class);
                intent.putExtra("contentLink", arrayListNewsKinhDoanh.get(i).getContent());
                intent.putExtra("titleBaiViet", arrayListNewsKinhDoanh.get(i).getTitle());
                startActivity(intent);
            }
        });

        lvTheLoai.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                switch (i){
                    case 0:
                        startActivity(new Intent(MainActivity.this,ThoiSuActivity.class));
                        break;
                    case 1:
                        startActivity(new Intent(MainActivity.this,TheGioiActivity.class));
                        break;
                    case 2:
                        startActivity(new Intent(MainActivity.this,KinhDoanhActivity.class));
                        break;
                    case 3:
                        startActivity(new Intent(MainActivity.this,StartupActivity.class));
                        break;
                    case 4:
                        startActivity(new Intent(MainActivity.this,GiaiTriActivity.class));
                        break;
                    case 5:
                        startActivity(new Intent(MainActivity.this,TheThaoActivity.class));
                        break;
                    case 6:
                        startActivity(new Intent(MainActivity.this,PhapLuatActivity.class));
                        break;
                    case 7:
                        startActivity(new Intent(MainActivity.this,GiaoDucActivity.class));
                        break;
                    case 8:
                        startActivity(new Intent(MainActivity.this,SucKhoeActivity.class));
                        break;
                    case 9:
                        startActivity(new Intent(MainActivity.this,GiaDinhActivity.class));
                        break;
                    case 10:
                        startActivity(new Intent(MainActivity.this,DuLichActivity.class));
                        break;
                    case 11:
                        startActivity(new Intent(MainActivity.this,KhoaHocActivity.class));
                        break;
                    case 12:
                        startActivity(new Intent(MainActivity.this,SoHoaActivity.class));
                        break;
                    case 13:
                        startActivity(new Intent(MainActivity.this,XeActivity.class));
                        break;
                    case 14:
                        startActivity(new Intent(MainActivity.this,CongDongActivity.class));
                        break;
                    case 15:
                        startActivity(new Intent(MainActivity.this,TamSuActivity.class));
                        break;
                    case 16:
                        startActivity(new Intent(MainActivity.this,CuoiActivity.class));
                        break;

                }
            }
        });
    }
    private void ReadRSSKinhDoanh(String url){
        arrayListNewsKinhDoanh =new ArrayList<>();
        RequestQueue requestQueue=Volley.newRequestQueue(MainActivity.this);
        StringRequest stringRequest=new StringRequest(Request.Method.GET, url, new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
                Document document= Jsoup.parse(response);
                Elements elements=document.select("item");
                arrayListNewsKinhDoanh.clear();
                for(Element items:elements){
                    try{
                        String strTieuDe=items.select("title").text();

                        String strDescription=items.select("description").text();

                        Document docImage=Jsoup.parse(strDescription);
                        String strLinkImage=docImage.select("img").get(0).attr("src");

                        String strDuongDan=items.select("guid").text();

                        arrayListNewsKinhDoanh.add(new NewsKinhDoanh(strTieuDe,strDuongDan,strLinkImage));
                    }catch (IndexOutOfBoundsException e){

                    }

                }
                createListViewNewsTheThao();
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                Toast.makeText(MainActivity.this, "Có lỗi xảy ra", Toast.LENGTH_SHORT).show();
            }
        });
        requestQueue.add(stringRequest);
    }
    private void ReadRSSGiaiTri(String url){
        arrayListNewsGiaiTri=new ArrayList<>();
        RequestQueue requestQueue=Volley.newRequestQueue(MainActivity.this);
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
                        arrayListNewsGiaiTri.add(new NewsGiaiTri(strTieuDe,strDuongDan,strLinkImage));
                    }catch (IndexOutOfBoundsException e){

                    }



                }
                createListViewNewsGiaiTri();
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                Toast.makeText(MainActivity.this, "Có lỗi xảy ra", Toast.LENGTH_SHORT).show();

            }
        });
        requestQueue.add(stringRequest);

    }
    private void ReadRSSTheGioi(String url){
        arrayListNewsTheGioi=new ArrayList<>();
        RequestQueue requestQueue= Volley.newRequestQueue(MainActivity.this);
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

                        arrayListNewsTheGioi.add(new NewsTheGioi(strTieuDe,strDuongDan,strLinkImage));
                    }catch (IndexOutOfBoundsException e){

                    }

                }
                createListViewNewsTheGioi();

            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                Toast.makeText(MainActivity.this, "Có lỗi xảy ra", Toast.LENGTH_SHORT).show();
            }
        });
        requestQueue.add(stringRequest);
    }
    private void createListViewNewsTheThao(){
        adapterNewsTheThao=new CustomAdapterNewsKinhDoanh(MainActivity.this,
                R.layout.row_listview_main,
                arrayListNewsKinhDoanh);
        lvKinhDoanh.setAdapter(adapterNewsTheThao);
        
    }
    private void createListViewNewsGiaiTri(){
        adapterNewsGiaiTri=new CustomAdapterNewsGiaiTri(MainActivity.this,
                R.layout.row_listview_main,
                arrayListNewsGiaiTri);
        lvGiaiTri.setAdapter(adapterNewsGiaiTri);
    }
    private void createListViewNewsTheGioi(){
        adapterNewsTheGioi=new CustomAdapterNewsTheGioi(MainActivity.this,
                R.layout.row_listview_main,
                arrayListNewsTheGioi);
        lvTheGioi.setAdapter(adapterNewsTheGioi);
    }
    private void initTabHost(){
        mappedView();
        tabHost.setup();
        //Tab 1
        TabHost.TabSpec spec = tabHost.newTabSpec("Tab One");
        spec.setContent(R.id.tab1);
        spec.setIndicator("Thế giới");
        tabHost.addTab(spec);
        //Tab 2
        spec = tabHost.newTabSpec("Tab Two");
        spec.setContent(R.id.tab2);
        spec.setIndicator("Giải trí");
        tabHost.addTab(spec);
        //Tab 3
        spec = tabHost.newTabSpec("Tab Three");
        spec.setContent(R.id.tab3);
        spec.setIndicator("Kinh doanh");
        tabHost.addTab(spec);
    }
    private void createListViewTheLoai(){
        arrayListPaperType=new ArrayList<>();
        arrayListPaperType.add("Thời sự");
        arrayListPaperType.add("Thế giới");
        arrayListPaperType.add("Kinh doanh");
        arrayListPaperType.add("Startup");
        arrayListPaperType.add("Giải trí");
        arrayListPaperType.add("Thể thao");
        arrayListPaperType.add("Pháp luật");
        arrayListPaperType.add("Giáo dục");
        arrayListPaperType.add("Sức khỏe");
        arrayListPaperType.add("Gia đình");
        arrayListPaperType.add("Du lịch");
        arrayListPaperType.add("Khoa học");
        arrayListPaperType.add("Số hóa");
        arrayListPaperType.add("Xe");
        arrayListPaperType.add("Cộng đồng");
        arrayListPaperType.add("Tâm sự");
        arrayListPaperType.add("Cười");
        adapterPaperType=new ArrayAdapter
                (MainActivity.this,android.R.layout.simple_list_item_1,arrayListPaperType);
        lvTheLoai.setAdapter(adapterPaperType);


    }
    private void mappedView(){
        tabHost=findViewById(R.id.tabHost);
        drawerLayout=findViewById(R.id.drawerLayout);
        linearSwipe=findViewById(R.id.linearSwipe);
        lvTheLoai=findViewById(R.id.lvTheLoai);
        lvTheGioi=findViewById(R.id.lvTheGioi);
        lvGiaiTri=findViewById(R.id.lvGiaiTri);
        lvKinhDoanh=findViewById(R.id.lvKinhDoanh);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.constructor_menu,menu);
        return super.onCreateOptionsMenu(menu);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        Animation animation= AnimationUtils.loadAnimation(MainActivity.this,R.anim.animation_enter);

        switch (item.getItemId()){
            case android.R.id.home:
                if (!drawerLayout.isDrawerOpen(linearSwipe)){
                    drawerLayout.openDrawer(linearSwipe);


                }else{
                    drawerLayout.closeDrawer(linearSwipe);


                }
                break;
            case R.id.menuTheThao:
                startActivity(new Intent(MainActivity.this,TheThaoActivity.class));
                break;

            case R.id.menuGiaDinh:
                startActivity(new Intent(MainActivity.this,GiaDinhActivity.class));
                break;
            case R.id.menuGiaoDuc:
                startActivity(new Intent(MainActivity.this,GiaoDucActivity.class));
                break;

        }
        return super.onOptionsItemSelected(item);
    }


}
