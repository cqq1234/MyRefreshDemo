package com.qq.administrator.myrefreshdemo.ui.activities;

import android.annotation.SuppressLint;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.webkit.WebView;
import android.webkit.WebViewClient;

import com.qq.administrator.myrefreshdemo.R;
import com.qq.administrator.myrefreshdemo.utils.StatusBarUtil;
import com.scwang.smartrefresh.layout.SmartRefreshLayout;
import com.scwang.smartrefresh.layout.api.RefreshLayout;
import com.scwang.smartrefresh.layout.listener.OnRefreshListener;
import com.scwang.smartrefresh.layout.util.DensityUtil;

import java.util.Locale;

import butterknife.Bind;
import butterknife.ButterKnife;

/**
 * Created by 崔琦 on 2017/7/12 0012.
 * Describe : 网页-Github
 */
public class WebviewPracticeActivity extends AppCompatActivity {

    @Bind(R.id.webView)
    WebView webView;
    @Bind(R.id.smartLayout)
    SmartRefreshLayout smartLayout;
    @Bind(R.id.toolbar)
    Toolbar toolbar;

    @SuppressLint("SetJavaScriptEnabled")
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_practice_webview);
        ButterKnife.bind(this);
        initView();
    }

    private void initView(){
        toolbar.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                finish();
            }
        });
        smartLayout.setOnRefreshListener(new OnRefreshListener() {
            @Override
            public void onRefresh(RefreshLayout refreshlayout) {
                webView.loadUrl("https://github.com/scwang90/SmartRefreshLayout");
            }
        });
        smartLayout.autoRefresh();
        webView.getSettings().setJavaScriptEnabled(true);
        webView.setWebViewClient(new WebViewClient(){
            @Override
            public boolean shouldOverrideUrlLoading(WebView view, String url) {
                view.loadUrl(url);
                return true;
            }

            @Override
            public void onPageCommitVisible(WebView view, String url) {
                super.onPageCommitVisible(view, url);
                smartLayout.finishRefresh();
                view.loadUrl(String.format(Locale.CHINA,"javascript:document.body.style.paddingTop='%fpx'; void 0", DensityUtil.px2dp(webView.getPaddingTop())));
            }
        });
        //状态栏透明和间距处理
        StatusBarUtil.immersive(this);
        StatusBarUtil.setPaddingSmart(this, webView);
        StatusBarUtil.setPaddingSmart(this, toolbar);
        StatusBarUtil.setMargin(this, findViewById(R.id.header));
        StatusBarUtil.setPaddingSmart(this, findViewById(R.id.blurview));
    }
}
