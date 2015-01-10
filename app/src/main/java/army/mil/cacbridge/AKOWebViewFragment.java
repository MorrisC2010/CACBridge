package army.mil.cacbridge;
import android.content.res.AssetManager;
import android.net.http.SslError;
import android.os.AsyncTask;
import android.os.Build;
import android.os.Bundle;
import android.app.Fragment;
import android.os.Environment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.webkit.CookieManager;
import android.webkit.SslErrorHandler;
import android.webkit.WebSettings;
import android.webkit.WebView;
import android.widget.TextView;
import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.InputStream;
import java.io.PrintWriter;
import army.mil.cacbridge.http.Api;
import army.mil.cacbridge.http.AuthenticationParameters;
import army.mil.cacbridge.util.IOUtil;

/**
 * Created by Cody on 11/27/2014.
 */
    public class AKOWebViewFragment extends Fragment {

        public AKOWebViewFragment(){}

    public static WebView mWebView;
    public boolean forceClaim = true;

        @Override

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
            mWebView.loadUrl("file:///android_asset/http/ako.html");

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


    /*public void DetectUSB() {

        UsbManager manager = (UsbManager) getSystemService(Context.USB_SERVICE);

        HashMap<String, UsbDevice> deviceList = manager.getDeviceList();
        Iterator<UsbDevice> deviceIterator = deviceList.values().iterator();

        while (deviceIterator.hasNext()) {

            PingUSB();
        }


        BroadcastReceiver mUsbReceiver = new BroadcastReceiver() {

            public void onReceive(Context context, Intent intent) {
                String action = intent.getAction();

                if (UsbManager.ACTION_USB_DEVICE_DETACHED.equals(action)) {
                    UsbDevice device = (UsbDevice) intent.getParcelableExtra(UsbManager.EXTRA_DEVICE);
                    if (device != null) {
                        UsbManager manager = (UsbManager) getSystemService(Context.USB_SERVICE);

                        HashMap<String, UsbDevice> deviceList = manager.getDeviceList();
                        // call your method that cleans up and closes communication with the device
                        UsbDeviceConnection connection = manager.openDevice(device);
                        connection.close();
                        finish();
                    }
                }
            }


        };
    }

    public void PingUSB() {

        UsbManager manager = (UsbManager) getSystemService(Context.USB_SERVICE);

        HashMap<String, UsbDevice> deviceList = manager.getDeviceList();
        Iterator<UsbDevice> deviceIterator = deviceList.values().iterator();

        UsbDevice device = deviceIterator.next();
        UsbInterface intf = device.getInterface(1);
        UsbEndpoint endpoint = intf.getEndpoint(1);
        UsbDeviceConnection connection = manager.openDevice(device);
        connection.claimInterface(intf, forceClaim);
    }
*/


}