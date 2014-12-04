package army.mil.cacbridge;

import android.app.Activity;
import android.net.http.SslError;
import android.os.Build;
import android.os.Bundle;
import android.webkit.CookieManager;
import android.webkit.SslErrorHandler;
import android.webkit.WebSettings;
import android.webkit.WebView;

import java.io.BufferedInputStream;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.net.HttpURLConnection;
import java.net.URL;

/**
 * Created by Cody on 11/27/2014.
 */
public class WebViewFragment extends Activity {

    private WebView mWebView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.webviewfragment);

        mWebView = (WebView) findViewById(R.id.WebViewFragment);

        // Enable Javascript
        mWebView.setWebViewClient(new WebViewFragmentClient() {
            @Override
            public void onReceivedSslError(WebView view, SslErrorHandler handler, SslError error){
                handler.proceed();
            }
        });
        CookieManager.getInstance().setAcceptCookie(true);
        WebSettings webSettings = mWebView.getSettings();
        webSettings.setJavaScriptEnabled(true);
        webSettings.setUseWideViewPort(false);
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.KITKAT) {
            WebView.setWebContentsDebuggingEnabled(true);
        }
        mWebView.loadUrl("https://login.us.army.mil/suite/login/fcc/akologin.fcc?TYPE=33554433&REALMOID=06-84df9b84-1402-1006-8b6f-832f13160000&GUID=&SMAUTHREASON=0&METHOD=GET&SMAGENTNAME=o8Rc1CnMzrYqxaNYlt7BewL7jx5IlO25mFAiVAkDqBHxcKazZQqKStqmDNolsEgk&TARGET=-SM-https%3a%2f%2flogin%2eus%2earmy%2emil%2fSmKBAuth%2fLoginEnrollmentForms%2fSmKBAuth%2efcc%3fsite%3dONE%26TYPE%3d33554432%26REALMOID%3d06--532666b4--7541--1005--8b6f--832f13160000%26GUID%3d%26SMAUTHREASON%3d0%26METHOD%3dGET%26SMAGENTNAME%3d--SM--XeHOmTYGdo5lT6ctArAivGV-%2faFYY6LMGw5RkYoLTW-%2bItmoZVbbnjqCLPEM8hKZ-%2ft%26TARGET%3d--SM--http-%3a-%2f-%2fwww-%2eus-%2earmy-%2emil-%2fsuite-%2fdesigner");
    }

    @Override
    public void onBackPressed() {
        if (mWebView.canGoBack()) {
            mWebView.goBack();
        } else {
            super.onBackPressed();
        }
    }


}
