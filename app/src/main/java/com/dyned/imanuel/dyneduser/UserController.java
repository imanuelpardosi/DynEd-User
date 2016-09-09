package com.dyned.imanuel.dyneduser;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.SQLException;
import android.database.sqlite.SQLiteDatabase;

import java.util.ArrayList;

/**
 * Created by nuel4 on 09/09/2016.
 */
public class UserController {
    // Database fields
    private DBHelper dbHelper;
    private SQLiteDatabase database;

    public static final String TABLE_NAME = "USER";
    public static final String ID = "id";
    public static final String NAME = "name";
    public static final String USERNAME = "username";
    public static final String EMAIL = "email";
    //public static final String ADDRESS = "bahan";
    //public static final String STREET = "cara_pembuatan";


    public static final String CREATE_USER = "CREATE TABLE "+TABLE_NAME+""+
            "("+ID+" integer primary key, "+
            NAME+" VARCHAR(32), "+
            USERNAME+" VARCHAR(32), "+
            EMAIL+" VARCHAR(32))";
    private String[] TABLE_COLUMNS = {ID, NAME, USERNAME, EMAIL};

    public UserController(Context context) {
        dbHelper = new DBHelper(context);
    }
    public void open() throws SQLException {
        database = dbHelper.getWritableDatabase();
    }

    public void close() {
        dbHelper.close();
    }

    public void deleteData (){
        database.delete(TABLE_NAME, null, null);
    }

    public void insertData(int id, String name, String username, String email){
        ContentValues contentValues = new ContentValues();
        contentValues.put(ID, id);
        contentValues.put(NAME, name);
        contentValues.put(USERNAME, username);
        contentValues.put(EMAIL, email);
        database.insert(TABLE_NAME, null, contentValues);
    }

    public ArrayList<User> getData() {
        ArrayList<User> allData = new ArrayList<User>();
        Cursor cursor = null;

        cursor = database.query(TABLE_NAME, TABLE_COLUMNS, null, null, null, null,ID + " ASC");

        cursor.moveToFirst();
        while (!cursor.isAfterLast()) {
            allData.add(parseData(cursor));

            cursor.moveToNext();
        }

        cursor.close();
        return allData;
    }

    private User parseData(Cursor cursor){
        User curData = new User();

        curData.setId(cursor.getInt(0));
        curData.setName(cursor.getString(1));
        curData.setUsername(cursor.getString(2));
        curData.setEmail(cursor.getString(3));

        return curData;
    }
}
