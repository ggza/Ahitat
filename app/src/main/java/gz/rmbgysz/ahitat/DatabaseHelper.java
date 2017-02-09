package gz.rmbgysz.ahitat;

/*
http://stackoverflow.com/questions/513084/ship-an-application-with-a-database
https://blog.reigndesign.com/blog/using-your-own-sqlite-database-in-android-applications/
* */


import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.SQLException;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteException;
import android.database.sqlite.SQLiteOpenHelper;

import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;

/**
 * Created by gzoli on 2017.02.09..
 */

public class DatabaseHelper extends SQLiteOpenHelper {

    private static String DB_NAME = "devotional2017.db";
    public static final String DEVOTIONALS_TABLE_NAME = "devotionals";
    public static final String DEVOTIONALS_COLUMN_ID = "id";
    public static final String DEVOTIONALS_COLUMN_DATE = "dev_date";
    public static final String DEVOTIONALS_COLUMN_HEADER = "header";
    public static final String DEVOTIONALS_COLUMN_BODY = "body";
    public static final String DEVOTIONALS_COLUMN_AUTHOR = "author";

    public static final String FAVORITES_TABLE_NAME = "favorites";
    public static final String FAVORITES_COLUMN_ID = "id";
    public static final String FAVORITES_COLUMN_DATE = "favorite_date";


    //The Android's default system path of your application database.
    private static String DB_PATH = "/data/data/gz.rmbgysz.ahitat/databases/";



    private SQLiteDatabase myDataBase;

    private final Context myContext;

    private HashMap hp;

    public DatabaseHelper(Context context) {
        super(context, DB_NAME, null, 1);
        this.myContext = context;
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {

    }

    /**
     * Creates a empty database on the system and rewrites it with your own database.
     * */
    public void createDataBase() throws IOException {

        boolean dbExist = checkDataBase();

        if(dbExist){
            //do nothing - database already exist
        }else{
            //By calling this method and empty database will be created into the default system path
            //of your application so we are gonna be able to overwrite that database with our database.
            this.getReadableDatabase();
            try {
                copyDataBase();
            } catch (IOException e) {
                throw new Error("Error copying database");
            }
        }
    }

    /**
     * Check if the database already exist to avoid re-copying the file each time you open the application.
     * @return true if it exists, false if it doesn't
     */
    private boolean checkDataBase(){

        SQLiteDatabase checkDB = null;

        try{
            String myPath = DB_PATH + DB_NAME;
            checkDB = SQLiteDatabase.openDatabase(myPath, null, SQLiteDatabase.OPEN_READONLY);

        }catch(SQLiteException e){
            //database does't exist yet.
        }

        if(checkDB != null){
            checkDB.close();
        }
        return checkDB != null ? true : false;
    }

    /**
     * Copies your database from your local assets-folder to the just created empty database in the
     * system folder, from where it can be accessed and handled.
     * This is done by transfering bytestream.
     * */
    private void copyDataBase() throws IOException{

        //Open your local db as the input stream
        InputStream myInput = myContext.getAssets().open(DB_NAME);

        // Path to the just created empty db
        String outFileName = DB_PATH + DB_NAME;

        //Open the empty db as the output stream
        OutputStream myOutput = new FileOutputStream(outFileName);

        //transfer bytes from the inputfile to the outputfile
        byte[] buffer = new byte[1024];
        int length;
        while ((length = myInput.read(buffer))>0){
            myOutput.write(buffer, 0, length);
        }

        //Close the streams
        myOutput.flush();
        myOutput.close();
        myInput.close();

    }

    public void openDataBase() throws SQLException {

        //Open the database
        String myPath = DB_PATH + DB_NAME;
        myDataBase = SQLiteDatabase.openDatabase(myPath, null, SQLiteDatabase.OPEN_READONLY);

    }

    @Override
    public synchronized void close() {

        if(myDataBase != null)
            myDataBase.close();

        super.close();

    }


    // Add your public helper methods to access and get content from the database.
    // You could return cursors by doing "return myDataBase.query(....)" so it'd be easy
    // to you to create adapters for your views.

    public void createTables() {
        SQLiteDatabase db = this.getWritableDatabase();
        db.execSQL(
                "create table devotionals " +
                        "(id integer primary key, dev_date string, header text,body text, author text)"
        );

        db.execSQL(
                "create table favorites " +
                        "(id integer primary key, favorite_date string)"
        );
    }

    public ArrayList<String> getAllDevotionals() {
        ArrayList<String> array_list = new ArrayList<String>();

        //hp = new HashMap();
        SQLiteDatabase db = this.getReadableDatabase();
        Cursor res =  db.rawQuery( "select * from devotionals", null );
        res.moveToFirst();

        while(res.isAfterLast() == false){
            array_list.add(res.getString(res.getColumnIndex(DEVOTIONALS_COLUMN_DATE)));
            res.moveToNext();
        }
        return array_list;
    }

    public boolean deleteDevotionals() {
        SQLiteDatabase db = this.getWritableDatabase();
        db.execSQL("delete from devotionals");
        return true;
    }

    public ArrayList<String> getAllFavorites() {
        ArrayList<String> array_list = new ArrayList<String>();

        //hp = new HashMap();
        SQLiteDatabase db = this.getReadableDatabase();
        Cursor res =  db.rawQuery( "select * from favorites", null );
        res.moveToFirst();

        while(res.isAfterLast() == false){
            array_list.add(res.getString(res.getColumnIndex(FAVORITES_COLUMN_DATE)));
            res.moveToNext();
        }
        return array_list;
    }

    public boolean insertFavorite (String date) {
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues contentValues = new ContentValues();
        contentValues.put("favorite_date", date);
        db.insert("favorites", null, contentValues);
        return true;
    }

    public Integer deleteFavorite(Integer id) {
        SQLiteDatabase db = this.getWritableDatabase();
        return db.delete("favorites",
                "id = ? ",
                new String[] { Integer.toString(id) });
    }

    public Integer deleteFavorite(String date) {
        SQLiteDatabase db = this.getWritableDatabase();
        return db.delete("favorites",
                "favorite_date= ? ",
                new String[] { date });
    }

    // closing database
    public void closeDB() {
        SQLiteDatabase db = this.getReadableDatabase();
        if (db != null && db.isOpen())
            db.close();
    }

}
