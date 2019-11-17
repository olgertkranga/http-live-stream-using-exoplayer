package com.example.oleg.exoplayer.db;

import android.content.ContentValues;
import android.database.Cursor;
import android.database.sqlite.SQLiteOpenHelper;
import android.content.Context;
import android.database.sqlite.SQLiteDatabase;

import com.example.oleg.exoplayer.models.Currency;
import java.util.ArrayList;
import java.util.List;

public class SQLiteDatabaseHandler extends SQLiteOpenHelper {

    // All Static variables
    // Database Version
    private static final int DATABASE_VERSION = 1;

    // Database Name
    private static final String DATABASE_NAME = "revolut_db";

    //TABLE_CONTAINERS_SALES
    //ContainersSales table name
    private static final String TABLE_CURRECY = "Currency";

    //TABLE CURRENCY
    //in glob2000 orders
    private static final String CUR_ID = "cur_id"; //PK 1
    private static final String RATE_DATE = "rate_date"; //2
    private static final String CUR_NAME = "cur_name"; //3
    private static final String CUR_RATE = "cur_rate"; //4
    private static final String CUR_DESC = "cur_desc"; //5
    private static final String CUR_URL_FLAG = "cur_url_flag"; //6

    public SQLiteDatabaseHandler(Context context) {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
    }

    // Creating Tables
    @Override
    public void onCreate(SQLiteDatabase db) {
        //ORDERS
        String CREATE_CURRECY_TABLE = "CREATE TABLE " + TABLE_CURRECY + "("
                + CUR_ID + " INTEGER PRIMARY KEY," //PK 1
                + RATE_DATE + " DATE," // 2
                + CUR_NAME + " TEXT," // 3
                + CUR_RATE + " DOUBLE," // 4
                + CUR_DESC + " TEXT," // 5
                + CUR_URL_FLAG + " TEXT ) "; //6

        db.execSQL(CREATE_CURRECY_TABLE);

    }

    // Upgrading database
    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        // Drop older table if existed
        db.execSQL("DROP TABLE IF EXISTS " + TABLE_CURRECY);

        // Create tables again♠
        onCreate(db);
    }

    /**
     * All CRUD(Create, Read, Update, Delete) Operations
     */
    //ADDING NEW ORDERS
     public void addNewCurrency(Currency currency) {
         SQLiteDatabase db = this.getWritableDatabase();

         ContentValues values = new ContentValues();

         values.put(RATE_DATE, currency.getRateDate()); // 2
         values.put(CUR_NAME, currency.getCurName()); // 3
         values.put(CUR_RATE, currency.getCurRate()); // 4
         values.put(CUR_DESC, currency.getCurDescription()); // 5
         values.put(CUR_URL_FLAG, currency.getCurUrlFlag()); // 6

         // Inserting Row
         db.insert(TABLE_CURRECY, null, values);
         db.close(); // Closing database connection
    }

    ///SELECT FOR ALL ORDERS ACTIVITY
    public List<Currency> getCurrencyList(String whereCurrencyListStr) {
        List<Currency> currencyList = new ArrayList<Currency>();
        // Select All Query
        String selectQuery = "SELECT" +
                " rate_date," + //2
                " cur_name," + //3
                " cur_rate," + //4
                " cur_desc," + //5
                " cur_url_flag" + //6
                " FROM " + TABLE_CURRECY + " " + whereCurrencyListStr + " ORDER BY cur_id";
        SQLiteDatabase db = this.getWritableDatabase();
        Cursor cursor = db.rawQuery(selectQuery, null);
        // looping through all rows and adding to list
        if (cursor.moveToFirst()) {
            do {
                Currency currency = new Currency();

                currency.setRateDate(cursor.getString(0)); //2
                currency.setCurName(cursor.getString(1)); //3
                currency.setCurRate(cursor.getDouble(2)); //4
                currency.setCurDescription(cursor.getString(3)); //5
                currency.setCurUrlFlag(cursor.getString(4)); //6

                // Adding country to list
                currencyList.add(currency);
            } while (cursor.moveToNext());
        }
        return currencyList;
    }

    //update String form Currency
    public void updateCur(String update_cur_Str) {
        SQLiteDatabase db = this.getWritableDatabase();
        db.execSQL(update_cur_Str);
    }

}