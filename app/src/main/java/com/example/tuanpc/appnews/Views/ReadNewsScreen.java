package com.example.tuanpc.appnews.Views;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.MenuItem;
import android.webkit.WebSettings;
import android.webkit.WebView;
import android.webkit.WebViewClient;

import com.example.tuanpc.appnews.R;

public class ReadNewsScreen extends Activity {
    private WebView webviewNewsKinhDoanh;
    private String strTieuDeBaiViet="";
    private String strDuongDan="";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_read_news_screen);
        mappedView();
        nhanDuLieuIntent();
        getActionBar().setDisplayHomeAsUpEnabled(true);
        getActionBar().setHomeAsUpIndicator(R.drawable.iconback);
        getActionBar().setTitle(strTieuDeBaiViet);
        webviewNewsKinhDoanh.loadUrl(strDuongDan);
        webviewNewsKinhDoanh.setWebViewClient(new WebViewClient());
        WebSettings webSettings=webviewNewsKinhDoanh.getSettings();
        webSettings.setJavaScriptEnabled(true);
    }

    private void nhanDuLieuIntent(){
        Intent intent=getIntent();
        strDuongDan=intent.getStringExtra("contentLink");
        strTieuDeBaiViet=intent.getStringExtra("titleBaiViet");

    }
    private void mappedView(){
        webviewNewsKinhDoanh=findViewById(R.id.webviewNewsKinhDoanh);

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
