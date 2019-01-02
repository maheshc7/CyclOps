package com.smazee.product.cyclops;

import android.bluetooth.BluetoothDevice;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.location.LocationManager;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.util.Log;
import android.widget.Toast;

public class MyBroadcastReceiver extends BroadcastReceiver {
    private static final String TAG = "MyBroadcastReceiver";

    @Override
    public void onReceive(Context context, Intent intent) {
        StringBuilder sb = new StringBuilder();
        sb.append("Action: " + intent.getAction() + "\n");
        sb.append("Extras:" + intent.getType());
        //sb.append("URI: " + intent.toUri(Intent.URI_INTENT_SCHEME).toString() + "\n");

        String log = sb.toString();
        Log.d(TAG, log);
        //Toast.makeText(context, log, Toast.LENGTH_LONG).show();

        //Code from: https://stackoverflow.com/questions/15698790/broadcast-receiver-for-checking-internet-connection-in-android-app
        if (intent.getAction().equals(BluetoothDevice.ACTION_FOUND)) {
            BluetoothDevice device = intent.getParcelableExtra(BluetoothDevice.EXTRA_DEVICE);
            // Add the name and address to an array adapter to show in a Toast
            String derp = device.getName() + " - " + device.getAddress();
            Toast.makeText(context, derp, Toast.LENGTH_LONG).show();
            Log.d("Broadcast--->", derp);
        }
        else {
            Log.d("Broadcast--->", "Call from else block");
        }
    }
}

