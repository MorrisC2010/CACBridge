package army.mil.cacbridge;
import android.app.Activity;
import android.app.FragmentManager;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.res.Configuration;
import android.content.res.TypedArray;
import android.hardware.usb.UsbDevice;
import android.hardware.usb.UsbDeviceConnection;
import android.hardware.usb.UsbEndpoint;
import android.hardware.usb.UsbInterface;
import android.hardware.usb.UsbManager;
import android.os.Bundle;
import android.support.v4.app.ActionBarDrawerToggle;
import android.app.Fragment;
import android.support.v4.widget.DrawerLayout;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.webkit.WebView;
import android.widget.AdapterView;
import android.widget.ListView;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;

public class NavDrawerFragment extends Activity {

    private DrawerLayout mDrawerLayout;

    private ListView mDrawerList;

    private ActionBarDrawerToggle mDrawerToggle;

    public boolean forceClaim = true;

    private int curfrag;



    // nav drawer title

    private CharSequence mDrawerTitle;



    // used to store app title

    private CharSequence mTitle;



    // slide menu items

    private String[] navMenuTitles;

    private TypedArray navMenuIcons;



    private ArrayList<NavDrawerItem> navDrawerItems;

    private NavDrawerListAdapter adapter;



    @Override

    protected void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);

        setContentView(R.layout.drawerfragmentlayout);

        mTitle = mDrawerTitle = getTitle();

        // load slide menu items

        navMenuTitles = getResources().getStringArray(R.array.drawer_array);

        // nav drawer icons from resources

        mDrawerLayout = (DrawerLayout) findViewById(R.id.drawer_layout);

        mDrawerList = (ListView) findViewById(R.id.left_drawer);

        navDrawerItems = new ArrayList<NavDrawerItem>();
        // adding nav drawer items to array

        //Bright_sun

        navDrawerItems.add(new NavDrawerItem(navMenuTitles[0]));

        //Night At

        navDrawerItems.add(new NavDrawerItem(navMenuTitles[1]));

        // Sunshine

        navDrawerItems.add(new NavDrawerItem(navMenuTitles[2]));

        // Rainy, Will add a counter here

        navDrawerItems.add(new NavDrawerItem(navMenuTitles[3]));

        //Snow

        navDrawerItems.add(new NavDrawerItem(navMenuTitles[4]));

        // What's hot, We  will add a counter here

        navDrawerItems.add(new NavDrawerItem(navMenuTitles[5]));


        // Recycle the typed array

//        navMenuIcons.recycle();

        mDrawerList.setOnItemClickListener(new SlideMenuClickListener());

        // setting the nav drawer list adapter

        adapter = new NavDrawerListAdapter(getApplicationContext(),

                navDrawerItems);

        mDrawerList.setAdapter(adapter);



        // enabling action bar app icon and behaving it as toggle button

        getActionBar().setDisplayHomeAsUpEnabled(true);

        getActionBar().setHomeButtonEnabled(true);



        mDrawerToggle = new ActionBarDrawerToggle(this, mDrawerLayout,

                R.drawable.ic_drawer, //nav menu toggle icon

                R.string.app_name, // nav drawer open - description for accessibility

                R.string.app_name // nav drawer close - description for accessibility

        ) {

            public void onDrawerClosed(View view) {

                getActionBar().setTitle(mTitle);

                // calling onPrepareOptionsMenu() to show action bar icons

                invalidateOptionsMenu();

            }



            public void onDrawerOpened(View drawerView) {

                getActionBar().setTitle("CACBridge");

                // calling onPrepareOptionsMenu() to hide action bar icons

                invalidateOptionsMenu();

            }

        };

        mDrawerLayout.setDrawerListener(mDrawerToggle);



        if (savedInstanceState == null) {

            // on first time display view for first nav item

            displayView(0);

        }


    }

    public void DetectUSB() {

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


    /**

     * Slide menu item click listener

     * */

    private class SlideMenuClickListener implements

            ListView.OnItemClickListener {

        @Override

        public void onItemClick(AdapterView<?> parent, View view, int position,

                                long id) {

            // display view for selected nav drawer item

            displayView(position);

        }

    }



    @Override

    public boolean onCreateOptionsMenu(Menu menu) {

        getMenuInflater().inflate(R.menu.main, menu);

        return true;

    }



    @Override

    public boolean onOptionsItemSelected(MenuItem item) {

        // toggle nav drawer on selecting action bar app icon/title

        if (mDrawerToggle.onOptionsItemSelected(item)) {

            return true;

        }

        // Handle action bar actions click

        switch (item.getItemId()) {

            case R.id.action_back:

                if(curfrag == 0){
                    AKOWebViewFragment.BackPressed();
                }
                else if (curfrag == 1){
                    WebMailWebViewFragment.BackPressed();
                }
                else if (curfrag == 2){
                    MyPayWebViewFragment.BackPressed();
                }


                return true;

            case R.id.action_forward:

                if(curfrag == 0){
                    AKOWebViewFragment.ForwardPressed();
                }
                else if (curfrag == 1){
                    WebMailWebViewFragment.ForwardPressed();
                }
                else if (curfrag == 2){
                    MyPayWebViewFragment.ForwardPressed();
                }

                return true;

            default:

                return super.onOptionsItemSelected(item);

        }

    }



    /***

     * Called when invalidateOptionsMenu() is triggered

     */

    @Override

    public boolean onPrepareOptionsMenu(Menu menu) {

        // if nav drawer is opened, hide the action items

        boolean drawerOpen = mDrawerLayout.isDrawerOpen(mDrawerList);

        menu.findItem(R.id.action_back).setVisible(!drawerOpen);

        return super.onPrepareOptionsMenu(menu);

    }



    /**

     * Diplaying fragment view for selected nav drawer list item

     * */

    private void displayView(int position) {

        // update the main content by replacing fragments

        Fragment fragment = null;

        switch (position) {

            case 0:

                fragment = new AKOWebViewFragment();
                curfrag = 0;

                break;
            case 1:
                fragment = new WebMailWebViewFragment();
                curfrag = 1;

                break;
            case 2:
                fragment = new MyPayWebViewFragment();
                curfrag = 2;

                break;
            case 4:
                fragment = new PreferencesMainFragment();
                curfrag = 4;

                break;


            default:

                break;

        }



        if (fragment != null) {

            FragmentManager fragmentManager = getFragmentManager();

            fragmentManager.beginTransaction()

                    .replace(R.id.content_frame, fragment).commit();



            // update selected item and title, then close the drawer

            mDrawerList.setItemChecked(position, true);

            mDrawerList.setSelection(position);

            setTitle(navMenuTitles[position]);

            mDrawerLayout.closeDrawer(mDrawerList);

        } else {

            // error in creating fragment

            Log.e("MainActivity", "Error in creating fragment");

        }

    }



    @Override

    public void setTitle(CharSequence title) {

        mTitle = title;

        getActionBar().setTitle(mTitle);

    }

    /**

     * When using the ActionBarDrawerToggle, you must call it during

     * onPostCreate() and onConfigurationChanged()...

     */



    @Override

    protected void onPostCreate(Bundle savedInstanceState) {

        super.onPostCreate(savedInstanceState);

        // Sync the toggle state after onRestoreInstanceState has occurred.

        mDrawerToggle.syncState();

    }

    @Override

    public void onConfigurationChanged(Configuration newConfig) {

        super.onConfigurationChanged(newConfig);

        // Pass any configuration change to the drawer toggls

        mDrawerToggle.onConfigurationChanged(newConfig);

    }
}