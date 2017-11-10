package com.example.prince.oto;

import android.app.Activity;
import android.graphics.Bitmap;
import android.os.Bundle;
import android.view.KeyEvent;
import android.view.Window;
import android.view.WindowManager;
import android.webkit.WebChromeClient;
import android.webkit.WebSettings;
import android.webkit.WebView;
import android.webkit.WebViewClient;
import android.widget.TextView;
import com.cjj.MaterialRefreshLayout;
import com.cjj.MaterialRefreshListener;

public class FullscreenActivity extends Activity {
    private WebView mWebView;
    private TextView mTitle;
    private MaterialRefreshLayout mMaterialRefreshLayout;
    
    @Override  
    protected void onCreate(Bundle savedInstanceState) {
      super.onCreate(savedInstanceState);
      requestWindowFeature(Window.FEATURE_NO_TITLE);
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN,
                WindowManager.LayoutParams.FLAG_FULLSCREEN);
        setContentView(R.layout.activity_fullscreen);
    
      initView();  
      initWebView();  
      initRefreshLayout();  
    }
    
    /** 
    * 初始化View 
    */  
    private void initView() {  
      mTitle = (TextView) findViewById(R.id.title);
      mWebView = (WebView) findViewById(R.id.webview);  
      mMaterialRefreshLayout = (MaterialRefreshLayout) findViewById(R.id.refresh);  
    }  
    
    /** 
    * 初始化WebView 
    */  
    private void initWebView() {  
      mWebView.getSettings().setJavaScriptEnabled(true);  
      mWebView.getSettings().setBlockNetworkImage(false);  
      mWebView.loadUrl("http://192.168.3.2/oto");
      mWebView.setWebViewClient(new MyWebviewClient());  
      mWebView.setWebChromeClient(new MyChromeClient());
      mWebView.getSettings().setLayoutAlgorithm(WebSettings.LayoutAlgorithm.NARROW_COLUMNS);
    }  
    /** 
    * 初始化RefreshLayout刷新 
    * 不解释，相关的请看文章头部的MaterialRefreshLayout的连接 
    */  
    private void initRefreshLayout() {  
      mMaterialRefreshLayout.setMaterialRefreshListener(new MaterialRefreshListener() {
        @Override  
        public void onRefresh(MaterialRefreshLayout materialRefreshLayout) {  
        //下拉刷新停止  
        mWebView.loadUrl(mWebView.getUrl());  
        mMaterialRefreshLayout.finishRefresh();  
        }  
      });  
    }  
    
    /** 
    * 重写MyWebviewClient方法 
    * 
    * shouldOverrideUrlLoading（） 拦截网页跳转，是之继续在WebView中进行跳转 
    * onPageStarted（） 开始加载的时候（显示进度条） 
    * onPageFinished（） 夹在结束的时候（隐藏进度条） 
    */  
    private class MyWebviewClient extends WebViewClient {
      @Override  
      public boolean shouldOverrideUrlLoading(WebView view, String url) {  
        view.loadUrl(url);  
        return false;  
      }  
    
      @Override  
      public void onPageStarted(WebView view, String url, Bitmap favicon) {  
        super.onPageStarted(view, url, favicon);
    
      }  
    
      @Override  
      public void onPageFinished(WebView view, String url) {  
        super.onPageFinished(view, url);
    
      }  
    }  
    /** 
    * 重写MyChromeClient方法 
    * 
    * onProgressChanged（） 设置动态进度条 
    * onReceivedTitle（） 设置WebView的头部标题 
    * onReceivedIcon（）  设置WebView的头部图标 
    */  
    private class MyChromeClient extends WebChromeClient {
      @Override  
      public void onProgressChanged(WebView view, int newProgress) {  
        super.onProgressChanged(view, newProgress);
    
      }  
    
      @Override  
      public void onReceivedTitle(WebView view, String title) {  
        super.onReceivedTitle(view, title);  
        mTitle.setText(title);  
      }  
    
      @Override  
      public void onReceivedIcon(WebView view, Bitmap icon) {
        super.onReceivedIcon(view, icon);  
      }  
    }  
    
    /** 
    * 实现WebView的回退栈 
    * 
    * @param keyCode 
    * @param event 
    * @return 
    */  
    @Override  
    public boolean onKeyDown(int keyCode, KeyEvent event) {
      if (keyCode== KeyEvent.KEYCODE_BACK && mWebView.canGoBack()){  
        mWebView.goBack();  
        return true;  
      }
      return super.onKeyDown(keyCode, event);  
    }  
}