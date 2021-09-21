package com.example.ingredilist;


import android.app.AlertDialog;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.net.ConnectivityManager;
import android.net.wifi.WifiManager;
import android.provider.Settings;
import android.widget.Toast;

public class NetworkBCReceiver extends BroadcastReceiver {
    //Broadcast receiver on the MainActivity screen that displays whether the smartphone is connected to a wireless network or not.
//Displays an alert dialogue if the user is not connected to their interenet regarding limited online functionality
// intent to move outside the application to settings if user wishes to go to network settings on alert dialog popup.
    @Override
    public void onReceive(Context context, Intent intent) {
        if (ConnectivityManager.CONNECTIVITY_ACTION.equals(intent.getAction())) {
            boolean noConnectivity = intent.getBooleanExtra(
                    ConnectivityManager.EXTRA_NO_CONNECTIVITY, false
            );
            if (noConnectivity) {
                AlertDialog.Builder b = new AlertDialog.Builder(context);
                b.setTitle("NETWORK:");
                b.setMessage("No Network Connection, So Limited Functionality");
                b.setIcon(R.drawable.settingsicon);
                b.setCancelable(true);

                b.setPositiveButton("Go to Network Settings", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        context.startActivity(new Intent(Settings.ACTION_WIRELESS_SETTINGS));
                        dialog.cancel();
                    }
                });

                b.setNegativeButton("Continue Without Connection", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        dialog.cancel();
                    }
                });
                AlertDialog alertDialog = b.create();
                alertDialog.show();


            } else {
                Toast.makeText(context, "Network Connected, Full Functionality", Toast.LENGTH_SHORT).show();
            }
        }
    }
}
