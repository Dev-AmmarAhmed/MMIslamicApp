package com.mmislamic.app;

import android.app.Activity;
import android.os.Bundle;
import android.view.WindowManager;
import android.webkit.JavascriptInterface;
import android.webkit.WebSettings;
import android.webkit.WebView;
import android.webkit.WebViewClient;
import android.widget.Toast;

public class MainActivity extends Activity {
    private WebView webView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        webView = new WebView(this);
        setContentView(webView);

        WebSettings webSettings = webView.getSettings();
        webSettings.setJavaScriptEnabled(true);
        
        // Offline Caching & Local Storage Enable
        webSettings.setDomStorageEnabled(true);
        webSettings.setDatabaseEnabled(true);
        webSettings.setCacheMode(WebSettings.LOAD_DEFAULT);

        webView.setWebViewClient(new WebViewClient());
        
        // JS Bridge for Screenshot Blocking
        webView.addJavascriptInterface(new JSBridge(), "AndroidBridge");

        // Yahan aapki Hosted App ka URL aayega (Update this link later if needed)
        webView.loadUrl("https://mm-islamic-erp.web.app"); 
    }

    @Override
    public void onBackPressed() {
        if (webView.canGoBack()) {
            webView.goBack();
        } else {
            super.onBackPressed();
        }
    }

    // Yeh class HTML/JS se commands receive karegi
    class JSBridge {
        @JavascriptInterface
        public void setScreenCaptureDisabled(boolean disable) {
            runOnUiThread(() -> {
                if (disable) {
                    getWindow().setFlags(WindowManager.LayoutParams.FLAG_SECURE, WindowManager.LayoutParams.FLAG_SECURE);
                    // Optional Toast for debugging
                    // Toast.makeText(MainActivity.this, "Security ON: Screenshots Blocked", Toast.LENGTH_SHORT).show();
                } else {
                    getWindow().clearFlags(WindowManager.LayoutParams.FLAG_SECURE);
                }
            });
        }
    }
}
