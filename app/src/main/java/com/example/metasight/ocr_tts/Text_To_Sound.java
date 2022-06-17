package com.example.metasight.ocr_tts;

import android.annotation.SuppressLint;
import android.app.DownloadManager;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.os.Environment;
import android.view.View;
import android.webkit.CookieManager;
import android.webkit.DownloadListener;
import android.webkit.URLUtil;
import android.webkit.WebView;
import android.webkit.WebViewClient;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.swiperefreshlayout.widget.SwipeRefreshLayout;

import com.example.metasight.R;

public class Text_To_Sound extends AppCompatActivity {

    Toolbar actionbar;
    WebView webView;
    SwipeRefreshLayout refreshLayout;
    final private String url = "https://freetts.com";

    @SuppressLint("SetJavaScriptEnabled")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_text_to_sound);
        actionbar = findViewById(R.id.sound_toolbar);
        refreshLayout = findViewById(R.id.sound_refresher);
        webView = findViewById(R.id.tts_web);
        webView.setWebViewClient(new WebViewClient());
        webView.canGoBack();
        webView.loadUrl(url);
        webView.getSettings().setJavaScriptEnabled(true);
        actionbar.setTitle(R.string.text_to_sound);
        actionbar.setTitleTextColor(getResources().getColor(R.color.text));
        actionbar.setTitleTextColor(getResources().getColor(R.color.text));
        actionbar.setPadding(10, 5, 0, 5);
        actionbar.setNavigationIcon(R.drawable.arrow_back);
        //Set Toolbar
        setSupportActionBar(actionbar);
        actionbar.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });
        refreshLayout.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh() {
                LoadWeb(url);
            }
        });
        //handle downloading
        webView.setDownloadListener(new DownloadListener() {
            @Override
            public void onDownloadStart(String url, String userAgent,
                                        String contentDisposition, String mimeType,
                                        long contentLength) {
                DownloadManager.Request request = new DownloadManager.Request(
                        Uri.parse(url));
                request.setMimeType(mimeType);
                String cookies = CookieManager.getInstance().getCookie(url);
                request.addRequestHeader("cookie", cookies);
                request.addRequestHeader("User-Agent", userAgent);
                request.setDescription("Downloading File...");
                request.setTitle(URLUtil.guessFileName(url, contentDisposition, mimeType));
                request.allowScanningByMediaScanner();
                request.setNotificationVisibility(DownloadManager.Request.VISIBILITY_VISIBLE_NOTIFY_COMPLETED);
                if (Build.VERSION.SDK_INT > 28) {
                    request.setDestinationInExternalPublicDir(
                            Environment.DIRECTORY_AUDIOBOOKS, URLUtil.guessFileName(
                                    url, contentDisposition, mimeType));
                } else {
                    request.setDestinationInExternalPublicDir(
                            Environment.DIRECTORY_DOWNLOADS, URLUtil.guessFileName(
                                    url, contentDisposition, mimeType));
                }

                DownloadManager dm = (DownloadManager) getSystemService(DOWNLOAD_SERVICE);
                dm.enqueue(request);
                Toast.makeText(getApplicationContext(), "Downloading File", Toast.LENGTH_LONG).show();
            }
        });

    }

    public void LoadWeb(String url) {
        webView.getSettings().setAppCacheEnabled(true);
        webView.loadUrl(url);
        refreshLayout.setRefreshing(true);
        webView.setWebViewClient(new WebViewClient() {
            public void onPageFinished(WebView view, String url) {
                refreshLayout.setRefreshing(false);
            }

        });
    }
}
