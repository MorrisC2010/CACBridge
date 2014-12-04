package army.mil.cacbridge;

import android.app.Activity;
import android.app.AlertDialog;
import android.app.DownloadManager;
import android.app.ProgressDialog;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.IntentFilter;
import android.content.pm.PackageManager;
import android.content.pm.ResolveInfo;
import android.hardware.usb.UsbDevice;
import android.hardware.usb.UsbDeviceConnection;
import android.hardware.usb.UsbEndpoint;
import android.hardware.usb.UsbInterface;
import android.hardware.usb.UsbManager;
import android.net.Uri;
import android.os.AsyncTask;
import android.os.Build;
import android.os.Bundle;
import android.os.Environment;
import android.os.Handler;
import android.os.ParcelFileDescriptor;
import android.util.Log;
import android.view.Gravity;
import android.view.Menu;
import android.view.MenuItem;
import android.view.Window;
import android.view.WindowManager;
import android.widget.TextView;
import android.widget.Toast;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;

import libsuperuser.Shell;

public class StartupSplash extends Activity {

    public Byte[] bytes;
    public static int TIMEOUT = 4000;
    public boolean forceClaim = true;
    private DownloadManager downloadManager;
    private long downloadReference;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN,
                WindowManager.LayoutParams.FLAG_FULLSCREEN);
        setContentView(R.layout.activity_cacbridge);

        File file = new File(Environment.getExternalStorageDirectory() + "/Android/data/army.mil.cacbridge/files/Download/rel3_dodroot_1024_retired.p7b");
        if(file.exists()) {
            Log.e(DOWNLOAD_SERVICE, "FILES EXIST");
            SplashScreen();
        }

        else {
            Log.e(DOWNLOAD_SERVICE, "FILES DO NOT EXIST");
            IntentFilter filter = new IntentFilter(DownloadManager.ACTION_DOWNLOAD_COMPLETE);
            registerReceiver(downloadReceiver, filter);
            downloadManager = (DownloadManager) getSystemService(DOWNLOAD_SERVICE);
            Uri Download_Uri1 = Uri.parse("http://dodpki.c3pki.chamb.disa.mil/rel3_dodroot_2048.p7b");
            Uri Download_Uri2 = Uri.parse("http://dodpki.c3pki.chamb.disa.mil/dodeca.p7b");
            Uri Download_Uri3 = Uri.parse("http://dodpki.c3pki.chamb.disa.mil/dodeca2.p7b");
            Uri Download_Uri4 = Uri.parse("http://dodpki.c3pki.chamb.disa.mil/dodroot-med.p7b");
            Uri Download_Uri5 = Uri.parse("http://dodpki.c3pki.chamb.disa.mil/rel3_dodroot_1024_retired.p7b");
            DownloadManager.Request request1 = new DownloadManager.Request(Download_Uri1);
            DownloadManager.Request request2 = new DownloadManager.Request(Download_Uri2);
            DownloadManager.Request request3 = new DownloadManager.Request(Download_Uri3);
            DownloadManager.Request request4 = new DownloadManager.Request(Download_Uri4);
            DownloadManager.Request request5 = new DownloadManager.Request(Download_Uri5);
            request1.setAllowedNetworkTypes(DownloadManager.Request.NETWORK_WIFI | DownloadManager.Request.NETWORK_MOBILE);
            request1.setAllowedOverRoaming(false);
            request1.setDestinationInExternalFilesDir(this, Environment.DIRECTORY_DOWNLOADS, "rel3_dodroot_2048.p7b");

            request2.setAllowedNetworkTypes(DownloadManager.Request.NETWORK_WIFI | DownloadManager.Request.NETWORK_MOBILE);
            request2.setAllowedOverRoaming(false);
            request2.setDestinationInExternalFilesDir(this, Environment.DIRECTORY_DOWNLOADS, "dodeca.p7b");

            request3.setAllowedNetworkTypes(DownloadManager.Request.NETWORK_WIFI | DownloadManager.Request.NETWORK_MOBILE);
            request3.setAllowedOverRoaming(false);
            request3.setDestinationInExternalFilesDir(this, Environment.DIRECTORY_DOWNLOADS, "dodeca2.p7b");

            request4.setAllowedNetworkTypes(DownloadManager.Request.NETWORK_WIFI | DownloadManager.Request.NETWORK_MOBILE);
            request4.setAllowedOverRoaming(false);
            request4.setDestinationInExternalFilesDir(this, Environment.DIRECTORY_DOWNLOADS, "dodroot-med.p7b");

            request5.setAllowedNetworkTypes(DownloadManager.Request.NETWORK_WIFI | DownloadManager.Request.NETWORK_MOBILE);
            request5.setAllowedOverRoaming(false);
            request5.setDestinationInExternalFilesDir(this, Environment.DIRECTORY_DOWNLOADS, "rel3_dodroot_1024_retired.p7b");

            //Enqueue a new download and same the referenceId
            downloadReference = downloadManager.enqueue(request1);
            downloadReference = downloadManager.enqueue(request2);
            downloadReference = downloadManager.enqueue(request3);
            downloadReference = downloadManager.enqueue(request4);
            downloadReference = downloadManager.enqueue(request5);
            unregisterReceiver(downloadReceiver);
            SplashScreen();
        }

    }
    public static boolean isDownloadManagerAvailable(Context context) {
        try {
            if (Build.VERSION.SDK_INT < Build.VERSION_CODES.GINGERBREAD) {
                return false;
            }
            Intent intent = new Intent(Intent.ACTION_MAIN);
            intent.addCategory(Intent.CATEGORY_LAUNCHER);
            intent.setClassName("com.android.providers.downloads.ui", "com.android.providers.downloads.ui.DownloadList");
            List<ResolveInfo> list = context.getPackageManager().queryIntentActivities(intent,
                    PackageManager.MATCH_DEFAULT_ONLY);
            return list.size() > 0;
        } catch (Exception e) {
            return false;
        }
    }

    public void SplashScreen() {
        new Handler().postDelayed(new Runnable() {

            /*
             * Showing splash screen with a timer. This will be useful when you
             * want to show case your app logo / company
             */

            @Override
            public void run() {
                // This method will be executed once the timer is over
                // Start your app main activity
                Intent i = new Intent(StartupSplash.this, ISAgreement.class);
                startActivity(i);
                finish();
            }
        }, TIMEOUT);
    }
    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();
        if (id == R.id.action_refresh) {
            DetectUSB();
            return true;
        }
        if (id == R.id.action_stopserv) {
            finish();
            return true;
        }
        return super.onOptionsItemSelected(item);
    }

    public void DetectUSB() {

        UsbManager manager = (UsbManager) getSystemService(Context.USB_SERVICE);

        HashMap<String, UsbDevice> deviceList = manager.getDeviceList();
        Iterator<UsbDevice> deviceIterator = deviceList.values().iterator();

        while (deviceIterator.hasNext()) {

            UsbDevice device = deviceIterator.next();
            Toast.makeText(this, "Value of device :" + device.getVendorId() + ":" + device.getProductId(), Toast.LENGTH_LONG).show();
            //String update_str = getResources().getString (R.string.hello_world);

            if (device.getVendorId() == 0x4e6 && device.getProductId() == 0x5116) {
                //PingUSB();
                ((TextView) findViewById(R.id.DownloadProgress)).setText("Device: " + getResources().getString(R.string.iProduct) + "\nVendor ID: " + getResources().getString(R.string.idVendor) + "\nProduct ID: " + getResources().getString(R.string.idProduct));
            } else {
                ((TextView) findViewById(R.id.DownloadProgress)).setText("Device: " + device.getDeviceName() + "\n Vendor ID: " + device.getVendorId() + "\n Product ID: " + device.getProductId());
            }
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

    public void RootAchieved(){
        AlertDialog alertDialog = new AlertDialog.Builder(this).create();
        alertDialog.setTitle("Rooted Device Warning");
        alertDialog.setMessage("This Application Cannot Run on Rooted Devices");
        alertDialog.setButton("O K", new DialogInterface.OnClickListener() {
            public void onClick(DialogInterface dialog, int which) {
                finish();
            }
        });
// Set the Icon for the Dialog
        alertDialog.setIcon(R.drawable.ic_launcher);
        alertDialog.show();

    }

    private BroadcastReceiver downloadReceiver = new BroadcastReceiver() {

        @Override
        public void onReceive(Context context, Intent intent) {

            //check if the broadcast message is for our Enqueued download
            long referenceId = intent.getLongExtra(DownloadManager.EXTRA_DOWNLOAD_ID, -1);
            if(downloadReference == referenceId){

                int ch;
                ParcelFileDescriptor file;
                StringBuffer strContent = new StringBuffer("");
                StringBuffer countryData = new StringBuffer("");

                //parse the JSON data and display on the screen
                try {
                    file = downloadManager.openDownloadedFile(downloadReference);
                    FileInputStream fileInputStream
                            = new ParcelFileDescriptor.AutoCloseInputStream(file);

                    while( (ch = fileInputStream.read()) != -1)
                        strContent.append((char)ch);

                } catch (FileNotFoundException e) {
                    e.printStackTrace();
                } catch (IOException e) {
                    e.printStackTrace();
                }

            }
        }
    };
}