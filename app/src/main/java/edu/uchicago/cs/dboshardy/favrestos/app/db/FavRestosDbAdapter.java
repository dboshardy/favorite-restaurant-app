package edu.uchicago.cs.dboshardy.favrestos.app.db;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.SQLException;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.util.Log;

import java.net.MalformedURLException;
import java.net.URL;

import javax.net.ssl.TrustManagerFactory;

import edu.uchicago.cs.dboshardy.favrestos.app.Resto;

public class FavRestosDbAdapter {

    //these are the field names
    public static final String KEY_ID = "_id";
    public static final String KEY_NAME = "name";
    public static final String KEY_FAVORITE = "fav";
    public static final String KEY_YELP_URL = "yelp_url";
    public static final String KEY_ADDRESS = "address";
    public static final String KEY_IMAGE_URL = "image_url";
    public static final String KEY_PHONE_NUMBER = "phone";
    public static final String KEY_IMAGE = "image";
    public static final String KEY_RATING = "rating";
    public static final String KEY_NOTES = "notes";
    public static final String KEY_CITY = "city";
    //these are the corresponding indices
    public static final int KEY_ID_INDEX = 0;
    public static final int KEY_NAME_INDEX = 1;
    public static final int KEY_FAVORITE_INDEX = 2;
    public static final int KEY_YELP_URL_INDEX = 3;
    public static final int KEY_ADDRESS_INDEX = 4;
    public static final int KEY_IMAGE_URL_INDEX = 5;
    public static final int KEY_PHONE_NUMBER_INDEX = 6;
    public static final int KEY_RATING_INDEX = 7;
    public static final int KEY_NOTES_INDEX = 8;
    public static final int KEY_CITY_INDEX = 9;

    //used for logging
    private static final String TAG = "FavRestosDbAdapter";


    private DatabaseHelper mDbHelper;
    private SQLiteDatabase mDb;

    private static final String DATABASE_NAME = "dba_favr";
    private static final String TABLE_NAME = "tbl_favr";
    private static final int DATABASE_VERSION = 1;


    private final Context mCtx;

    private static final String DATABASE_CREATE =
            "CREATE TABLE if not exists " + TABLE_NAME + " ( " +
                    KEY_ID + " INTEGER PRIMARY KEY autoincrement, " +
                    KEY_NAME + " TEXT, " +
                    KEY_FAVORITE + " INTEGER, " +
                    KEY_YELP_URL + " TEXT, " +
                    KEY_ADDRESS + " TEXT, " +
                    KEY_IMAGE_URL + " TEXT, " +
                    KEY_PHONE_NUMBER + " TEXT, " +
                    KEY_RATING + " DOUBLE, " +
                    KEY_NOTES + " TEXT, " +
                    KEY_CITY + " TEXT );";


    public FavRestosDbAdapter(Context ctx) {
        this.mCtx = ctx;
    }

    //open
    public void open() throws SQLException {
        mDbHelper = new DatabaseHelper(mCtx);
        mDb = mDbHelper.getWritableDatabase();

    }

    //close
    public void close() {
        if (mDbHelper != null) {
            mDbHelper.close();
        }
    }

    //CREATE
    //note that the id will be created for you automatically
//    public void createFavResto(Resto resto) {
//
//        ContentValues values = new ContentValues();
//        values.put(KEY_NAME, resto.getName());
//        values.put(KEY_FAVORITE, resto.getFavorite());
//        values.put(KEY_YELP_URL, String.valueOf(resto.getYelpURL()));
//        values.put(KEY_LATITUDE, resto.getLatitude());
//        values.put(KEY_LONGITUDE, resto.getLongitude());
//        values.put(KEY_IMAGE_URL, String.valueOf(resto.getImageUrl()));
//        values.put(KEY_PHONE_NUMBER, resto.getPhoneNumber());
//        values.put(KEY_IMAGE_URL, resto.getImage());
//        values.put(KEY_RATING, resto.getRating());
//
//        mDb.insert(TABLE_NAME, null, values);
//
//    }

    //overloaded to take a Resto
    public long createFavResto(Resto resto) {

        ContentValues values = new ContentValues();
        values.put(KEY_NAME, resto.getName());
        values.put(KEY_FAVORITE, resto.getFavorite());
        values.put(KEY_YELP_URL, String.valueOf(resto.getYelpURL()));
        values.put(KEY_IMAGE_URL, String.valueOf(resto.getImageUrl()));
        values.put(KEY_PHONE_NUMBER, resto.getPhoneNumber());
        values.put(KEY_RATING, resto.getRating());
        values.put(KEY_NOTES,resto.getNotes());

        // Inserting Row
        return mDb.insert(TABLE_NAME, null, values);

    }


    //READ
    public Resto fetchFavRestoById(int id) throws MalformedURLException {

        Cursor cursor = mDb.query(TABLE_NAME, new String[]{KEY_ID,
                        KEY_NAME, KEY_FAVORITE, KEY_YELP_URL, KEY_ADDRESS, KEY_IMAGE_URL,
                        KEY_PHONE_NUMBER, KEY_RATING, KEY_CITY,KEY_NOTES}, KEY_ID + "=?",
                new String[]{String.valueOf(id)}, null, null, null, null
        );
        if (cursor != null)
            cursor.moveToFirst();

        Resto resto =  new Resto(
                //id
                cursor.getInt(KEY_ID_INDEX),
                //name
                cursor.getString(KEY_NAME_INDEX),
                //favorite
                cursor.getInt(KEY_FAVORITE_INDEX),
                //yelp url
                new URL(cursor.getString(KEY_YELP_URL_INDEX)),
                //address
                cursor.getString(KEY_ADDRESS_INDEX),
                //image url
                new URL(cursor.getString(KEY_IMAGE_URL_INDEX)),
                //phone
                cursor.getString(KEY_PHONE_NUMBER_INDEX),
                //rating
                cursor.getDouble(KEY_RATING_INDEX),
                //notes
                cursor.getString(KEY_NOTES_INDEX),
                //city
                cursor.getString(KEY_CITY_INDEX)
        );
        return resto;


    }

    public Cursor fetchAllFavRestos() {

        Cursor mCursor = mDb.query(TABLE_NAME, new String[]{KEY_ID,
                        KEY_NAME, KEY_FAVORITE,KEY_ADDRESS,KEY_YELP_URL,KEY_IMAGE_URL,KEY_PHONE_NUMBER,KEY_RATING,KEY_NOTES,KEY_CITY},
                null, null, null, null, null
        );

        if (mCursor != null) {
            mCursor.moveToFirst();
        }
        return mCursor;
    }


    //UPDATE
    public void updateFavResto(Resto resto) {

        ContentValues values = new ContentValues();
        values.put(KEY_NAME, resto.getName());
        values.put(KEY_FAVORITE, resto.getFavorite());
        values.put(KEY_YELP_URL, String.valueOf(resto.getYelpURL()));
        values.put(KEY_IMAGE_URL, String.valueOf(resto.getImageUrl()));
        values.put(KEY_PHONE_NUMBER, resto.getPhoneNumber());
        values.put(KEY_RATING, resto.getRating());
        values.put(KEY_NOTES, resto.getNotes());
        values.put(KEY_CITY, resto.getCity());

        mDb.update(TABLE_NAME, values,
                KEY_ID + "=?", new String[]{String.valueOf(resto.getID())});

    }

    //contains?
    public boolean dbContainsResto(Resto resto) {
        String name = resto.getName();
        String address = resto.getAddress();

        String query = "Select * from " + TABLE_NAME + " where " + KEY_NAME + "="
                +"\""+ name +"\""+ " AND " + KEY_ADDRESS + "=" +"\""+address+"\"";
        Cursor cursor = mDb.rawQuery(query, null);
        if (cursor.getCount() <= 0) {
            cursor.close();
            return false;
        }
        else {
            cursor.moveToFirst();
            cursor.close();
            return true;
        }
    }

    //DELETE
    public void deleteFavRestoById(int nId) {

        mDb.delete(TABLE_NAME, KEY_ID + "=?", new String[]{String.valueOf(nId)});

    }


    public void deleteAllFavRestos() {

        mDb.delete(TABLE_NAME, null, null);

    }

//    public void insertSomeFavRestos() {

//        createFavResto("Send Dad birthday gift", false);
//        createFavResto("Anniversary on Friday - dinner", false);
//        createFavResto("String squash racket", false);
//        createFavResto("Shovel and salt walkways", false);
//        createFavResto("Prepare Advanced Android syllabus", true);
//        createFavResto("Buy new office chair", false);
//        createFavResto("Call Auto-body shop for quote", false);
//        createFavResto("Renew membership to Costco", false);
//        createFavResto("Buy new Galaxy 5 Android phone", true);
//        createFavResto("Sell old HTC Android phone - auction", false);
//        createFavResto("Buy new paddles for kayaks", false);
//        createFavResto("Call accountant about tax returns", false);
//        createFavResto("Buy 300,000 shares of Google", false);
//        createFavResto("Call the Dalai Lama back", true);


    //}


    //static inner class
    private static class DatabaseHelper extends SQLiteOpenHelper {

        DatabaseHelper(Context context) {
            super(context, DATABASE_NAME, null, DATABASE_VERSION);
        }


        @Override
        public void onCreate(SQLiteDatabase db) {
            Log.w(TAG, DATABASE_CREATE);
            db.execSQL(DATABASE_CREATE);
        }

        @Override
        public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
            Log.w(TAG, "Upgrading database from version " + oldVersion + " to "
                    + newVersion + ", which will destroy all old data");
            db.execSQL("DROP TABLE IF EXISTS " + TABLE_NAME);
            onCreate(db);
        }
    }

}