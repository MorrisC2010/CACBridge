package army.mil.cacbridge;

import android.app.Activity;
import android.app.AlertDialog;
import android.app.ProgressDialog;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.hardware.usb.UsbDevice;
import android.hardware.usb.UsbDeviceConnection;
import android.hardware.usb.UsbEndpoint;
import android.hardware.usb.UsbInterface;
import android.hardware.usb.UsbManager;
import android.os.AsyncTask;
import android.os.Bundle;
import android.os.Handler;
import android.view.Menu;
import android.view.MenuItem;
import android.view.Window;
import android.view.WindowManager;
import android.widget.TextView;
import android.widget.Toast;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;

import libsuperuser.Shell;

public class StartupSpash extends Activity {

    public Byte[] bytes;
    public static int TIMEOUT = 3000;
    public boolean forceClaim = true;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN,
                WindowManager.LayoutParams.FLAG_FULLSCREEN);
        setContentView(R.layout.activity_cacbridge);
        new Handler().postDelayed(new Runnable() {

            /*
             * Showing splash screen with a timer. This will be useful when you
             * want to show case your app logo / company
             */

            @Override
            public void run() {
                // This method will be executed once the timer is over
                // Start your app main activity
                Intent i = new Intent(StartupSpash.this, ISAgreement.class);
                startActivity(i);

                // close this activity
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
                ((TextView) findViewById(R.id.Hello_World)).setText("Device: " + getResources().getString(R.string.iProduct) + "\nVendor ID: " + getResources().getString(R.string.idVendor) + "\nProduct ID: " + getResources().getString(R.string.idProduct));
            } else {
                ((TextView) findViewById(R.id.Hello_World)).setText("Device: " + device.getDeviceName() + "\n Vendor ID: " + device.getVendorId() + "\n Product ID: " + device.getProductId());
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
}