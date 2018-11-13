package com.broadcastreceiver;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.Toast;

public class MainActivity extends AppCompatActivity {

    private BroadcastReceiver internetReceiver;
    //TODO add Boolean that will store the internet stat, and whenever it change show a toast (rxAndroid)
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        registerInternetReceiver();
    }

    private void registerInternetReceiver(){
        if (this.internetReceiver != null) return;
        this.internetReceiver = new BroadcastReceiver(){
            @Override
            public void onReceive (Context context, Intent intent)
            {
                if (isNetworkAvailable())
                    Toast.makeText(context, "Internet est Disponible", Toast.LENGTH_SHORT).show();
                else
                    Toast.makeText(context, "Internet non Disponible", Toast.LENGTH_SHORT).show();
            }
        };
        IntentFilter filter = new IntentFilter();
        filter.addAction (ConnectivityManager.CONNECTIVITY_ACTION);
        registerReceiver (internetReceiver, filter);
    }

    //TODO Doesn't guarantee that internet is available, it's better to implement rxAndroid solution from this stack thread
    //https://stackoverflow.com/questions/1560788/how-to-check-internet-access-on-android-inetaddress-never-times-out
    private boolean isNetworkAvailable(){
        ConnectivityManager cm = (ConnectivityManager)this.getSystemService(Context.CONNECTIVITY_SERVICE);
        NetworkInfo activeNetwork = cm != null ? cm.getActiveNetworkInfo() : null;
        return activeNetwork != null &&
                activeNetwork.isConnectedOrConnecting();
    }
}
