package com.dyned.imanuel.dyneduser.Controller;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.SQLException;
import android.database.sqlite.SQLiteDatabase;

import com.dyned.imanuel.dyneduser.DBHelper.DBHelper;
import com.dyned.imanuel.dyneduser.Model.Address;
import com.dyned.imanuel.dyneduser.Model.Company;
import com.dyned.imanuel.dyneduser.Model.Geo;
import com.dyned.imanuel.dyneduser.Model.User;

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
    public static final String ADDRESS_STREET = "address_street";
    public static final String ADDRESS_SUITE = "address_suite";
    public static final String ADDRESS_CITY = "address_city";
    public static final String ADDRESS_ZIPCODE = "address_zipcode";
    public static final String ADDRESS_GEO_LAT = "address_geo_lat";
    public static final String ADDRESS_GEO_LNG = "address_geo_lng";
    public static final String PHONE = "phone";
    public static final String WEBSITE = "website";
    public static final String COMPANY_NAME = "company_name";
    public static final String COMPANY_CATCHPHRASE = "company_catchphrase";
    public static final String COMPANY_BS = "company_bs";


    public static final String CREATE_USER = "CREATE TABLE " + TABLE_NAME + "" +
            "(" + ID + " integer primary key, " +
            NAME + " VARCHAR(32), " +
            USERNAME + " VARCHAR(15), " +
            EMAIL + " VARCHAR(32), " +
            ADDRESS_STREET + " VARCHAR(32), " +
            ADDRESS_SUITE + " VARCHAR(32), " +
            ADDRESS_CITY + " VARCHAR(32), " +
            ADDRESS_ZIPCODE + " VARCHAR(15), " +
            ADDRESS_GEO_LAT + " VARCHAR(10), " +
            ADDRESS_GEO_LNG + " VARCHAR(10), " +
            PHONE + " VARCHAR(32), " +
            WEBSITE + " VARCHAR(32), " +
            COMPANY_NAME + " VARCHAR(50), " +
            COMPANY_CATCHPHRASE + " VARCHAR(100), " +
            COMPANY_BS + " VARCHAR(100))";
    private String[] TABLE_COLUMNS = {ID, NAME, USERNAME, EMAIL, ADDRESS_STREET, ADDRESS_SUITE,
            ADDRESS_CITY, ADDRESS_ZIPCODE, ADDRESS_GEO_LAT, ADDRESS_GEO_LNG, PHONE, WEBSITE,
            COMPANY_NAME, COMPANY_CATCHPHRASE, COMPANY_BS};

    public UserController(Context context) {
        dbHelper = new DBHelper(context);
    }

    public void open() throws SQLException {
        database = dbHelper.getWritableDatabase();
    }

    public void close() {
        dbHelper.close();
    }

    public void deleteData() {
        database.delete(TABLE_NAME, null, null);
    }

    public void insertData(int id, String name, String username, String email, String address_street,
                           String address_suite, String address_city, String address_zipcode,
                           String address_geo_lat, String address_geo_lng, String phone, String website,
                           String company_name, String company_catchphrase, String company_bs) {
        ContentValues contentValues = new ContentValues();
        contentValues.put(ID, id);
        contentValues.put(NAME, name);
        contentValues.put(USERNAME, username);
        contentValues.put(EMAIL, email);
        contentValues.put(ADDRESS_STREET, address_street);
        contentValues.put(ADDRESS_SUITE, address_suite);
        contentValues.put(ADDRESS_CITY, address_city);
        contentValues.put(ADDRESS_ZIPCODE, address_zipcode);
        contentValues.put(ADDRESS_GEO_LAT, address_geo_lat);
        contentValues.put(ADDRESS_GEO_LNG, address_geo_lng);
        contentValues.put(PHONE, phone);
        contentValues.put(WEBSITE, website);
        contentValues.put(COMPANY_NAME, company_name);
        contentValues.put(COMPANY_CATCHPHRASE, company_catchphrase);
        contentValues.put(COMPANY_BS, company_bs);

        database.insert(TABLE_NAME, null, contentValues);
    }

    public ArrayList<User> getData() {
        ArrayList<User> allData = new ArrayList<User>();
        Cursor cursor = null;

        cursor = database.query(TABLE_NAME, TABLE_COLUMNS, null, null, null, null, ID + " ASC");

        cursor.moveToFirst();
        while (!cursor.isAfterLast()) {
            allData.add(parseData(cursor));

            cursor.moveToNext();
        }

        cursor.close();
        return allData;
    }

    private User parseData(Cursor cursor) {
        User curData = new User();
        curData.setAddress(new Address());
        curData.setCompany(new Company());
        curData.address.setGeo(new Geo());

        curData.setId(cursor.getInt(0));
        curData.setName(cursor.getString(1));
        curData.setUsername(cursor.getString(2));
        curData.setEmail(cursor.getString(3));

        curData.address.setStreet(cursor.getString(4));
        curData.address.setSuite(cursor.getString(5));
        curData.address.setCity(cursor.getString(6));
        curData.address.setZipcode(cursor.getString(7));
        curData.address.geo.setLat(cursor.getString(8));
        curData.address.geo.setLng(cursor.getString(9));
        curData.setPhone(cursor.getString(10));
        curData.setWebsite(cursor.getString(11));
        curData.company.setName(cursor.getString(12));
        curData.company.setCatchPhrase(cursor.getString(13));
        curData.company.setBs(cursor.getString(14));

        return curData;
    }
}
