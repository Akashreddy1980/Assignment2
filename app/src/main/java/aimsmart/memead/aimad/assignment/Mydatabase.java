package aimsmart.memead.aimad.assignment;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import androidx.annotation.Nullable;

public class Mydatabase extends SQLiteOpenHelper {

    public Mydatabase(@Nullable Context context) {
        super(context,"Mydb3.db", null, 1);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
          db.execSQL("create Table url(id TEXT primary key, link TEXT)");
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
         db.execSQL("drop Table if exists url");
    }
    public boolean insertdata(String id ,String link ){
        SQLiteDatabase database = this.getWritableDatabase();
        ContentValues contentValues = new ContentValues();
        contentValues.put("link",  link);
        contentValues.put("id", id);
        long res = database.insert("url", null,contentValues);
        if (res == -1 ){
            return false;
        }
        else {
            return true;
        }
    }
    public boolean updatedata(String id ,String link ){
        SQLiteDatabase database = this.getWritableDatabase();
        ContentValues contentValues = new ContentValues();
        contentValues.put("link",link);
        Cursor cursor = database.rawQuery("Select * from url where id = ?" , new String[]{id});
        if(cursor.getCount()>0) {
            long res = database.update("url", contentValues, "id=?", new String[]{id});
            if (res == -1) {
                return false;
            } else {
                return true;
            }
        }else {
            return false;
        }
    }
    public Cursor getdata(){
        SQLiteDatabase database = this.getWritableDatabase();
        Cursor cursor = database.rawQuery("select * from url",null);
        return cursor;
    }
}
