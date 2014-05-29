package edu.uchicago.cs.dboshardy.favrestos.app.db;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.SQLException;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.util.Log;

import edu.uchicago.cs.dboshardy.favrestos.app.FavResto;

/**
 * Created by Adam Gerber on 5/12/2014.
 * University of Chicago
 */
public class FavRestosDbAdapter {

    //these are the field names
    public static final String KEY_ID = "_id";
    public static final String KEY_CONTENT = "name";
    public static final String KEY_IMPORTANT = "imp";

    //these are the corresponding indices
    public static final int KEY_ID_INDEX = 0;
    public static final int KEY_CONTENT_INDEX = 1;
    public static final int KEY_IMPORTANT_INDEX = 2;

    //used for logging
    private static final String TAG = "FavRestosDbAdapter";


    private DatabaseHelper mDbHelper;
    private SQLiteDatabase mDb;

    private static final String DATABASE_NAME = "dba_remdr";
    private static final String TABLE_NAME = "tbl_remdr";
    private static final int DATABASE_VERSION = 1;


    private final Context mCtx;

    private static final String DATABASE_CREATE =
            "CREATE TABLE if not exists " + TABLE_NAME + " ( " +
                    KEY_ID + " INTEGER PRIMARY KEY autoincrement, " +
                    KEY_CONTENT + " TEXT, " +
                    KEY_IMPORTANT + " INTEGER );";


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
    public void createFavResto(String name, boolean important) {

        ContentValues values = new ContentValues();
        values.put(KEY_CONTENT, name);
        values.put(KEY_IMPORTANT, important ? 1 : 0);
        mDb.insert(TABLE_NAME, null, values);

    }

    //overloaded to take a reminder
    public long createFavResto(FavResto reminder) {

        ContentValues values = new ContentValues();
        values.put(KEY_CONTENT, reminder.getContent()); // Contact Name
        values.put(KEY_IMPORTANT, reminder.getImportant()); // Contact Phone Number

        // Inserting Row
        return mDb.insert(TABLE_NAME, null, values);

    }


    //READ
    public FavResto fetchFavRestoById(int id) {

        Cursor cursor = mDb.query(TABLE_NAME, new String[]{KEY_ID,
                        KEY_CONTENT, KEY_IMPORTANT}, KEY_ID + "=?",
                new String[]{String.valueOf(id)}, null, null, null, null
        );
        if (cursor != null)
            cursor.moveToFirst();

        return new FavResto(
                cursor.getInt(0),
                cursor.getString(1),
                cursor.getInt(2)

        );


    }

    public Cursor fetchAllFavRestos() {

        Cursor mCursor = mDb.query(TABLE_NAME, new String[]{KEY_ID,
                        KEY_CONTENT, KEY_IMPORTANT},
                null, null, null, null, null
        );

        if (mCursor != null) {
            mCursor.moveToFirst();
        }
        return mCursor;
    }



    //UPDATE
    public void updateFavResto(FavResto reminder) {

        ContentValues values = new ContentValues();
        values.put(KEY_CONTENT, reminder.getContent());
        values.put(KEY_IMPORTANT, reminder.getImportant());

         mDb.update(TABLE_NAME, values,
                KEY_ID + "=?", new String[]{String.valueOf(reminder.getId())});

    }


    //DELETE
    public void deleteFavRestoById(int nId) {

        mDb.delete(TABLE_NAME, KEY_ID + "=?", new String[]{String.valueOf(nId)});

    }


    public void deleteAllFavRestos() {

        mDb.delete(TABLE_NAME, null, null);

    }

    public void insertSomeFavRestos() {


        createFavResto("Buy Learn Android Studio by Gerber", true);
        createFavResto("Send Dad birthday gift", false);
        createFavResto("Anniversary on Friday - dinner", false);
        createFavResto("String squash racket", false);
        createFavResto("Shovel and salt walkways", false);
        createFavResto("Prepare Advanced Android syllabus", true);
        createFavResto("Buy new office chair", false);
        createFavResto("Call Auto-body shop for quote", false);
        createFavResto("Renew membership to Costco", false);
        createFavResto("Buy new Galaxy 5 Android phone", true);
        createFavResto("Sell old HTC Android phone - auction", false);
        createFavResto("Buy new paddles for kayaks", false);
        createFavResto("Call accountant about tax returns", false);
        createFavResto("Buy 300,000 shares of Google", false);
        createFavResto("Call the Dalai Lama back", true);


    }



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