package army.mil.cacbridge;

import android.app.Fragment;
import android.net.http.SslError;
import android.os.Build;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.webkit.CookieManager;
import android.webkit.SslErrorHandler;
import android.webkit.WebSettings;
import android.webkit.WebView;

/**
 * Created by Cody on 12/4/2014.
 */
public class MyPayWebViewFragment extends Fragment {


    public MyPayWebViewFragment(){}

    public static WebView mWebView;
    public boolean forceClaim = true;


    public View onCreateView(LayoutInflater inflater, ViewGroup container,

                             Bundle savedInstanceState) {

        View rootView = inflater.inflate(R.layout.webviewfragment, container, false);
        mWebView = (WebView) rootView.findViewById(R.id.WebViewFragment);

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
        mWebView.loadUrl("file:///android_asset/http/mypay.html");

        return rootView;

    }

    public static void BackPressed() {
        if (mWebView.canGoBack()) {
            mWebView.goBack();
        }
    }
    public static void ForwardPressed() {
        if (mWebView.canGoForward()) {
            mWebView.goForward();
        }
    }
}
