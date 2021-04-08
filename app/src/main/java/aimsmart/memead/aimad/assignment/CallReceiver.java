package aimsmart.memead.aimad.assignment;

import android.Manifest;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.database.Cursor;
import android.net.Uri;
import android.os.Build;
import android.telecom.TelecomManager;
import android.telephony.TelephonyManager;
import android.util.Log;
import android.widget.Toast;


import androidx.annotation.RequiresApi;
import androidx.core.app.ActivityCompat;

import java.lang.reflect.Method;
import java.util.Date;

import static android.content.Context.TELECOM_SERVICE;

public class CallReceiver extends PhonecallReceiver {
    Mydatabase mydatabase;
    private String link = "";
    @RequiresApi(api = Build.VERSION_CODES.LOLLIPOP)
    @Override
    protected void onIncomingCallReceived(Context context, String number, Date start) {
        TelecomManager telecomManager = (TelecomManager) context.getSystemService(TELECOM_SERVICE);
        if (ActivityCompat.checkSelfPermission(context, Manifest.permission.READ_PHONE_STATE) != PackageManager.PERMISSION_GRANTED || telecomManager == null) {
            Toast.makeText(context, "Errror", Toast.LENGTH_SHORT).show();
        }
        if (telecomManager.isInCall()) {
            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.P) {
                telecomManager.endCall();

            }
        }
        mydatabase = new Mydatabase(context);
        Cursor cursor = mydatabase.getdata();
        if(cursor.getCount()>0) {
            while (cursor.moveToNext()) {
                Toast.makeText(context, cursor.getString(1), Toast.LENGTH_SHORT).show();
                link = cursor.getString(1);
            }
        }
        String err = (number == null) ? "PrivateNum" : number;
        String num = err.substring(3, err.length());
        Toast.makeText(context, err, Toast.LENGTH_SHORT).show();


        Intent browserIntent = new Intent(Intent.ACTION_VIEW, Uri.parse(link + num));
        browserIntent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
        context.startActivity(browserIntent);

    }
}
