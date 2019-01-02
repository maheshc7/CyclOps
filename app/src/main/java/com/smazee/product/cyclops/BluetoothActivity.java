package com.smazee.product.cyclops;

import android.bluetooth.BluetoothAdapter;
import android.bluetooth.BluetoothDevice;
import android.bluetooth.BluetoothManager;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

import java.util.Set;

public class BluetoothActivity extends AppCompatActivity {

    static int REQUEST_ENABLE_BT =1;
    BluetoothAdapter mBluetoothAdapter;
    private MyBroadcastReceiver mReceiver;
    boolean isScan = false;
    Button button;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_bluetooth);

        button = findViewById(R.id.button);
        BluetoothManager bluetoothManager = (BluetoothManager)getSystemService(Context.BLUETOOTH_SERVICE);
        mBluetoothAdapter = bluetoothManager.getAdapter();//BluetoothAdapter.getDefaultAdapter();
        if (mBluetoothAdapter == null) {
            // Device doesn't support Bluetooth
            Log.d("Support--->","Device doesn't support Bluetooth");
        }
        else {
            if (!mBluetoothAdapter.isEnabled()) {
                Intent enableBtIntent = new Intent(BluetoothAdapter.ACTION_REQUEST_ENABLE);
                startActivityForResult(enableBtIntent, REQUEST_ENABLE_BT);
            } else {
                discoverDevice();
            }
        }

    }

    /*private final BroadcastReceiver mReceiver = new BroadcastReceiver() {
        public void onReceive(Context context, Intent intent) {
            String action = intent.getAction();
            if (BluetoothDevice.ACTION_FOUND.equals(action)) {
                // Discovery has found a device. Get the BluetoothDevice
                // object and its info from the Intent.
                BluetoothDevice device = intent.getParcelableExtra(BluetoothDevice.EXTRA_DEVICE);
                String deviceName = device.getName();
                String deviceHardwareAddress = device.getAddress(); // MAC address
                Log.i("Discovered Device--->",deviceName+" "+deviceHardwareAddress);
            }
        }
    };*/

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if(requestCode == REQUEST_ENABLE_BT){
            if(resultCode == RESULT_OK){
                Toast.makeText(this, "Blueetooth Enabled", Toast.LENGTH_SHORT).show();
                discoverDevice();
            }
            else{
                Toast.makeText(this, "Please Enable Bluetooth",Toast.LENGTH_SHORT).show();
            }
        }

        //To make our device discoverable...
        /*Intent discoverableIntent =
                new Intent(BluetoothAdapter.ACTION_REQUEST_DISCOVERABLE);
        discoverableIntent.putExtra(BluetoothAdapter.EXTRA_DISCOVERABLE_DURATION, 300);
        startActivity(discoverableIntent);*/
    }

    public void discoverDevice(){
        Toast.makeText(this,"Discovering Device",Toast.LENGTH_SHORT).show();
        Set<BluetoothDevice> pairedDevices = mBluetoothAdapter.getBondedDevices();

        if (pairedDevices.size() > 0) {
            // There are paired devices. Get the name and address of each paired device.
            for (BluetoothDevice device : pairedDevices) {
                String deviceName = device.getName();
                String deviceHardwareAddress = device.getAddress(); // MAC address
                Log.i("Discover Device-->",deviceName+"  "+deviceHardwareAddress);
            }
        }
           // Log.i("Discover Device--->","Discovering....");
            //No device paired... start device discovery..
            // Register for broadcasts when a device is discovered.
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();

        //Unregister the ACTION_FOUND receiver.
        //unregisterReceiver(mReceiver);
        //mBluetoothAdapter.cancelDiscovery();
    }

    public void scanDevice(View view){
        if(isScan){
            isScan=false;
            button.setText("SCAN : OFF");
            button.setTextColor(getResources().getColor(R.color.red));
            unregisterReceiver(mReceiver);
            if(mBluetoothAdapter.isDiscovering()) {
                mBluetoothAdapter.cancelDiscovery();
                Log.d("Discover Device--->","Discovery Canceled");
            }
        }
        else{
            Log.i("Discover Device--->","Discovering....");
            isScan = true;
            button.setText("SCAN : ON");
            button.setTextColor(getResources().getColor(R.color.green));
            mReceiver = new MyBroadcastReceiver();
            IntentFilter filter = new IntentFilter(BluetoothDevice.ACTION_FOUND);
            this.registerReceiver(mReceiver,filter);
            mBluetoothAdapter.startDiscovery();
        }
    }


}


