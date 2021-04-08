package aimsmart.memead.aimad.assignment;

import androidx.annotation.RequiresApi;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;

import android.Manifest;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.Build;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import java.util.Date;

public class MainActivity extends AppCompatActivity {

    private static final int MY_PERMISSIONS_REQUEST_READ_PHONE_STATE = 1 ;
    private static final int MY_PERMISSIONS_REQUEST_PROCESS_OUTGOING_CALLS = 1 ;
    private static final int MY_PERMISSIONS_REQUEST_READ_PHONE_CALL = 1 ;
    private String link = "https://www.carikture.com/internship/tasks/mobile-app/?phone=";
    Button editbtn;
    EditText editText;
    Mydatabase mydatabase;
    private String id = "1";

    @RequiresApi(api = Build.VERSION_CODES.M)
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        editbtn =findViewById(R.id.Editbtn);
        editText = findViewById(R.id.editlink);
        if (getApplicationContext().checkSelfPermission(Manifest.permission.READ_PHONE_STATE)
                != PackageManager.PERMISSION_GRANTED) {
            // Permission has not been granted, therefore prompt the user to grant permission
            ActivityCompat.requestPermissions(this,
                    new String[]{Manifest.permission.READ_PHONE_STATE},
                    MY_PERMISSIONS_REQUEST_READ_PHONE_STATE);
        }
        if (getApplicationContext().checkSelfPermission(Manifest.permission.READ_CALL_LOG)
                != PackageManager.PERMISSION_GRANTED) {
            // Permission has not been granted, therefore prompt the user to grant permission
            ActivityCompat.requestPermissions(this,
                    new String[]{Manifest.permission.READ_CALL_LOG},
                    MY_PERMISSIONS_REQUEST_READ_PHONE_CALL);
        }
        if (getApplicationContext().checkSelfPermission(Manifest.permission.PROCESS_OUTGOING_CALLS)
                != PackageManager.PERMISSION_GRANTED) {
            // Permission has not been granted, therefore prompt the user to grant permission
            ActivityCompat.requestPermissions(this,
                    new String[]{Manifest.permission.PROCESS_OUTGOING_CALLS},
                    MY_PERMISSIONS_REQUEST_PROCESS_OUTGOING_CALLS);
        }
        mydatabase = new Mydatabase(this);
        Cursor cursor = mydatabase.getdata();
        if(cursor.getCount()>0) {
            while (cursor.moveToNext()) {
                editText.setText(cursor.getString(cursor.getCount()));
            }
        }
        if(editText.getText().toString().equals("")){
            editText.setText(link);
            Boolean insert = mydatabase.insertdata(id,link);
        }
        startService(new Intent(this, CallReceiver.class));

        editbtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String edlink = editText.getText().toString();
                Boolean updatedata = mydatabase.updatedata(id , edlink);
                if(updatedata = true){
                    Toast.makeText(MainActivity.this, "URL Added", Toast.LENGTH_SHORT).show();
                }
                else {
                    Toast.makeText(MainActivity.this, "Unable to add URL", Toast.LENGTH_SHORT).show();
                }
                Cursor cursor = mydatabase.getdata();
                while (cursor.moveToNext()) {
                    editText.setText(cursor.getString(cursor.getCount()));
                }
            }
        });
    }
}