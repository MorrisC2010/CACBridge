package army.mil.cacbridge;

import android.app.Activity;
import android.app.AlertDialog;
import android.app.DownloadManager;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.IntentFilter;
import android.content.res.AssetManager;
import android.net.Uri;
import android.os.AsyncTask;
import android.os.Build;
import android.os.Bundle;
import android.os.Environment;
import android.os.Handler;
import android.os.ParcelFileDescriptor;
import android.util.Log;
import android.view.Window;
import android.view.WindowManager;
import android.widget.ProgressBar;
import android.widget.TextView;
import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.io.PrintWriter;

import army.mil.cacbridge.http.Api;
import army.mil.cacbridge.http.AuthenticationParameters;
import army.mil.cacbridge.util.IOUtil;

import static android.os.AsyncTask.execute;

public class
        StartupSplash extends Activity {

    public Byte[] bytes;
    public static int TIMEOUT = 4000;
    private DownloadManager downloadManager;
    private long downloadReference;
    private final String TAG = AKOWebViewFragment.class.getSimpleName();

    private Api exampleApi;
    String caCertificateName = String.valueOf(R.string.server_cert_asset_name);
    String clientCertificateName = String.valueOf(R.string.client_cert_file_name);
    String clientCertificatePassword = String.valueOf(R.string.client_cert_file_name);
    String exampleUrl = String.valueOf(R.string.example_url);
    TextView mainTextView;
    ProgressBar progressBar;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN,
                WindowManager.LayoutParams.FLAG_FULLSCREEN);
        setContentView(R.layout.activity_cacbridge);
        mainTextView = (TextView) findViewById(R.id.DownloadProgress);
        mainTextView.setText("");
        ProgressBar progressBar = (ProgressBar) findViewById(R.id.progressBar);
        progressBar.setProgress(0);
        if (isDownloadManagerAvailable(this)){
            DownloadFiles();
        }



    }
    public static boolean isDownloadManagerAvailable(Context context) {
        try {
            if (Build.VERSION.SDK_INT < Build.VERSION_CODES.JELLY_BEAN) {
                return false;
            }
            return true;
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
                Intent i = new Intent(StartupSplash.this, NavDrawerFragment.class);
                startActivity(i);
                finish();
            }
        }, TIMEOUT);
    }

    public void DownloadFiles() {

            try {
                updateOutput("Checking FileSystem...");

                new AsyncTask() {
                    int progress_status;
                    @Override
                    protected Object doInBackground(Object... objects) {
                        publishProgress(progress_status);

                        try {
                            File file = new File(Environment.getExternalStorageDirectory() + "/Android/data/cacbridge/files/rel3_dodroot_1024_retired.p7b");
                            if(file.exists()) {
                                updateOutput("Files Exist");
                            } else {
                            Context context = getBaseContext();
                            Log.e(DOWNLOAD_SERVICE, "FILES DO NOT EXIST");
                            IntentFilter filter = new IntentFilter(DownloadManager.ACTION_DOWNLOAD_COMPLETE);
                            registerReceiver(downloadReceiver, filter);
                            downloadManager = (DownloadManager) getSystemService(DOWNLOAD_SERVICE);
                            updateOutput("Connecting to Server...");
                            Uri Download_Uri1 = Uri.parse("http://dodpki.c3pki.chamb.disa.mil/rel3_dodroot_2048.p7b");
                            Uri Download_Uri2 = Uri.parse("http://dodpki.c3pki.chamb.disa.mil/dodeca.p7b");
                            Uri Download_Uri3 = Uri.parse("http://dodpki.c3pki.chamb.disa.mil/dodeca2.p7b");
                            Uri Download_Uri4 = Uri.parse("http://dodpki.c3pki.chamb.disa.mil/dodroot-med.p7b");
                            Uri Download_Uri5 = Uri.parse("http://dodpki.c3pki.chamb.disa.mil/rel3_dodroot_1024_retired.p7b");
                            progress_status = progress_status + 10;
                            setProgress(progress_status);
                            updateOutput("Requesting Files...");
                            DownloadManager.Request request1 = new DownloadManager.Request(Download_Uri1);
                            DownloadManager.Request request2 = new DownloadManager.Request(Download_Uri2);
                            DownloadManager.Request request3 = new DownloadManager.Request(Download_Uri3);
                            DownloadManager.Request request4 = new DownloadManager.Request(Download_Uri4);
                            DownloadManager.Request request5 = new DownloadManager.Request(Download_Uri5);
                            progress_status = progress_status +10;
                            setProgress(progress_status);
                            updateOutput("Setting Params...");
                            request1.setAllowedNetworkTypes(DownloadManager.Request.NETWORK_WIFI | DownloadManager.Request.NETWORK_MOBILE);
                            request1.setAllowedOverRoaming(false);
                            request1.setDestinationInExternalPublicDir(Environment.DIRECTORY_DOWNLOADS + "/CACBridge", "rel3_dodroot_2048.p7b");

                            request2.setAllowedNetworkTypes(DownloadManager.Request.NETWORK_WIFI | DownloadManager.Request.NETWORK_MOBILE);
                            request2.setAllowedOverRoaming(false);
                            request2.setDestinationInExternalPublicDir(Environment.DIRECTORY_DOWNLOADS + "/CACBridge", "dodeca.p7b");

                            request3.setAllowedNetworkTypes(DownloadManager.Request.NETWORK_WIFI | DownloadManager.Request.NETWORK_MOBILE);
                            request3.setAllowedOverRoaming(false);
                            request3.setDestinationInExternalPublicDir(Environment.DIRECTORY_DOWNLOADS + "/CACBridge", "dodeca2.p7b");

                            request4.setAllowedNetworkTypes(DownloadManager.Request.NETWORK_WIFI | DownloadManager.Request.NETWORK_MOBILE);
                            request4.setAllowedOverRoaming(false);
                            request4.setDestinationInExternalPublicDir(Environment.DIRECTORY_DOWNLOADS + "/CACBridge", "dodroot-med.p7b");

                            request5.setAllowedNetworkTypes(DownloadManager.Request.NETWORK_WIFI | DownloadManager.Request.NETWORK_MOBILE);
                            request5.setAllowedOverRoaming(false);
                            request5.setDestinationInExternalPublicDir(Environment.DIRECTORY_DOWNLOADS + "/CACBridge", "rel3_dodroot_1024_retired.p7b");
                            progress_status = progress_status + 5;
                            setProgress(progress_status);
                            //Enqueue a new download and same the referenceId
                            updateOutput("Downloading Files...");
                            downloadReference = downloadManager.enqueue(request1);
                            downloadReference = downloadManager.enqueue(request2);
                            downloadReference = downloadManager.enqueue(request3);
                            downloadReference = downloadManager.enqueue(request4);
                            downloadReference = downloadManager.enqueue(request5);
                            copyFiles(new File(Environment.getExternalStorageDirectory() + "/Download/CACBridge/") , new File(Environment.getExternalStorageDirectory() + "/Android/data/cacbridge/files/"));
                            deleteDirectory(new File(Environment.getExternalStorageDirectory() + "/Download/CACBridge"));
                             progress_status = progress_status + 25;
                            setProgress(progress_status);
                            unregisterReceiver(downloadReceiver);
                            downloadManager = null;

                            }

                        } catch (Throwable ex) {
                            ByteArrayOutputStream baos = new ByteArrayOutputStream();
                            PrintWriter writer = new PrintWriter(baos);
                            ex.printStackTrace(writer);
                            writer.flush();
                            writer.close();
                            //publishProgress(ex.toString() + " : " + baos.toString());
                        }

                        return null;
                    }

                    @Override
                    protected void onProgressUpdate(final Object... values) {
                        super.onProgressUpdate(values);
                       // progressBar.setProgress(Integer.parseInt(values[0])));
                    }

                    @Override
                    protected void onPostExecute(final Object result) {
                        updateOutput("Done!");
                    }
                }.execute();

            } catch (Exception ex) {
                Log.e(TAG, "failed to create timeApi", ex);
                updateOutput(ex.toString());
            }

        SplashScreen();
    }

    public void RootAchieved(){
        AlertDialog alertDialog = new AlertDialog.Builder(this).create();
        alertDialog.setTitle("Rooted Device Warning");
        alertDialog.setMessage("This Application Cannot Run on Rooted Devices");
        alertDialog.setButton("OK", new DialogInterface.OnClickListener() {
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

    @Override
    public void onResume() {
        super.onResume();
    }

    public void copyFiles(File sourceLocation, File targetLocation) throws IOException {
        if (sourceLocation.isDirectory()) {
            if (!targetLocation.exists() && !targetLocation.mkdirs()) {
                throw new IOException("Cannot create dir " + targetLocation.getAbsolutePath());
            }

            String[] children = sourceLocation.list();
            for (int i = 0; i < children.length; i++) {
                copyFiles(new File(sourceLocation, children[i]), new File(targetLocation, children[i]));
            }
        } else {

            // make sure target Directory exists
            File directory = targetLocation.getParentFile();
            if (directory != null && !directory.exists() && !directory.mkdirs()){
                throw new IOException("Cannot create dir " + directory.getAbsolutePath());
            }

            InputStream in = new FileInputStream(sourceLocation);
            OutputStream out = new FileOutputStream(targetLocation);


            //copy files to new location
            byte[] buf = new byte[1024];
            int len;
            while ((len = in.read(buf)) > 0) {
                out.write(buf, 0, len);
            }
            in.close();
            out.close();
        }
    }

    public boolean deleteDirectory(File path) {
        if( path.exists() ) {
            File[] files = path.listFiles();
            if (files == null) {
                return true;
            }
            for(int i=0; i<files.length; i++) {
                if(files[i].isDirectory()) {
                    deleteDirectory(files[i]);
                }
                else {
                    files[i].delete();
                }
            }
        }
        return( path.delete() );
    }

    private void updateOutput(String text) {
        mainTextView.setText(text);
    }

    private void doRequest() {

        try {
            AuthenticationParameters authParams = new AuthenticationParameters();
            /*authParams.setClientCertificate(getClientCertFile());*/
            authParams.setClientCertificatePassword(clientCertificatePassword);
            authParams.setCaCertificate(readCaCert());

            exampleApi = new Api(authParams);
            updateOutput("Connecting to " + exampleUrl);

            new AsyncTask() {
                @Override
                protected Object doInBackground(Object... objects) {

                    try {
                        String result = exampleApi.doGet(exampleUrl);
                        int responseCode = exampleApi.getLastResponseCode();
                        if (responseCode == 200) {
                            publishProgress(result);
                        } else {
                            publishProgress("HTTP Response Code: " + responseCode);
                        }

                    } catch (Throwable ex) {
                        ByteArrayOutputStream baos = new ByteArrayOutputStream();
                        PrintWriter writer = new PrintWriter(baos);
                        ex.printStackTrace(writer);
                        writer.flush();
                        writer.close();
                        publishProgress(ex.toString() + " : " + baos.toString());
                    }

                    return null;
                }

                @Override
                protected void onProgressUpdate(final Object... values) {
                    StringBuilder buf = new StringBuilder();
                    for (final Object value : values) {
                        buf.append(value.toString());
                    }
                    updateOutput(buf.toString());
                }

                @Override
                protected void onPostExecute(final Object result) {
                    updateOutput("Done!");
                }
            }.execute();

        } catch (Exception ex) {
            Log.e(TAG, "failed to create timeApi", ex);
            updateOutput(ex.toString());
        }
    }

    /*private File getClientCertFile() throws IOException {
        AssetManager assetManager = getAssets();
        InputStream ims = assetManager.open("dodca28.cer");
        return new File("file:///android_asset/dodca28.cer");
    }*/

    private String readCaCert() throws Exception {
        AssetManager assetManager = getAssets();
        InputStream inputStream = assetManager.open("dodca28.cer");
        return IOUtil.readFully(inputStream);
    }
}