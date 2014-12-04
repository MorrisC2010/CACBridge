package army.mil.cacbridge;

import android.app.Activity;
import android.app.ProgressDialog;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.hardware.usb.UsbDevice;
import android.hardware.usb.UsbDeviceConnection;
import android.hardware.usb.UsbEndpoint;
import android.hardware.usb.UsbInterface;
import android.hardware.usb.UsbManager;
import android.os.AsyncTask;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.TextView;
import android.widget.Toast;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;

import libsuperuser.Shell;

public class CACBridgeActivity extends Activity {

    public Byte[] bytes;
    public static int TIMEOUT = 0;
    public boolean forceClaim = true;

    public boolean suAvailable = false;

    private class Startup extends AsyncTask<Void, Void, Void> {
        private ProgressDialog dialog = null;
        private Context context = null;
        private String suVersion = null;
        private String suVersionInternal = null;
        public List<String> suResult = null;

        public Startup setContext(Context context) {
            this.context = context;
            return this;
        }

    @Override
    protected Void doInBackground(Void... params) {
        // Let's do some SU stuff
        suAvailable = Shell.SU.available();
        if (suAvailable) {
            suVersion = Shell.SU.version(false);
            suVersionInternal = Shell.SU.version(true);
            suResult = Shell.SU.run(new String[] {
                    "id",
                    "ls -l /"
            });
        }

        // This is just so you see we had a progress dialog,
        // don't do this in production code
        try { Thread.sleep(5000); } catch(Exception e) { }

        return null;
    }

    @Override
    protected void onPostExecute(Void result) {

        // output
        StringBuilder sb = (new StringBuilder()).
                append("Root? ").append(suAvailable ? "Yes" : "No").append((char)10).
                append("Version: ").append(suVersion == null ? "N/A" : suVersion).append((char)10).
                append("Version (internal): ").append(suVersionInternal == null ? "N/A" : suVersionInternal).append((char)10).
                append((char)10);
        if (suResult != null) {
            for (String line : suResult) {
                sb.append(line).append((char)10);
            }
        }
        if (suAvailable){
            ((TextView) findViewById(R.id.DownloadProgress)).setText("Your Device is Not Secured.");
        }
        else if (!suAvailable){
            DetectUSB();
        }
    }
}
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_cacbridge);
        (new Startup()).execute();
        
    }


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.cacbridge, menu);
        return true;
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
                ((TextView) findViewById(R.id.DownloadProgress)).setText("Device: " + getResources().getString(R.string.iProduct) + "\nVendor ID: " + getResources().getString(R.string.idVendor) + "\nProduct ID: " + getResources().getString(R.string.idProduct) + "\nRoot Availible:" + String.valueOf(suAvailable));
            } else {
                ((TextView) findViewById(R.id.DownloadProgress)).setText("Device: " + device.getDeviceName() + "\n Vendor ID: " + device.getVendorId() + "\n Product ID: " + device.getProductId() + "\nRoot Availible:" + String.valueOf(suAvailable));
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
}