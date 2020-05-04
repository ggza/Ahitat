package gz.rmbgysz.ahitat;

/*
http://stackoverflow.com/questions/513084/ship-an-application-with-a-database
https://blog.reigndesign.com/blog/using-your-own-sqlite-database-in-android-applications/
* */

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.DatabaseUtils;
import android.database.SQLException;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteException;
import android.database.sqlite.SQLiteOpenHelper;
import android.util.Log;
//import android.widget.Toast;

import com.getbase.floatingactionbutton.BuildConfig;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.util.ArrayList;
import java.util.HashMap;

/**
 * Created by gz on 2017.02.09..
 * TODO: majd ezt is angolositani kell
 */

public class DatabaseHelper extends SQLiteOpenHelper {

    private static final String DB_NAME = "devotional2017.sqlite";
    private static final String AHITATOK_TABLE_NAME = "ahitatok";
    private static final String AHITATOK_COLUMN_ID = "id";
    private static final String AHITATOK_COLUMN_DATE = "datum";
    private static final String AHITATOK_COLUMN_DE_CIM = "de_cim";
    private static final String AHITATOK_COLUMN_DE_IGE = "de_ige";
    private static final String AHITATOK_COLUMN_DE_SZOVEG = "de_szoveg";
    private static final String AHITATOK_COLUMN_DE_SZERZO = "de_szerzo";
    private static final String AHITATOK_COLUMN_DU_CIM = "du_cim";
    private static final String AHITATOK_COLUMN_DU_IGE = "du_ige";
    private static final String AHITATOK_COLUMN_DU_SZOVEG = "du_szoveg";
    private static final String AHITATOK_COLUMN_DU_SZERZO = "du_szerzo";
    private static final String AHITATOK_COLUMN_BIBLIAORA = "bibliaora";
    private static final String AHITATOK_COLUMN_IMAORA = "imaora";
    private static final String KEDVENCEK_TABLE_NAME = "kedvencek";
    private static final String KEDVENCEK_COLUMN_ID = "id";
    private static final String KEDVENCEK_COLUMN_DATE = "datum";

    private static final String TAG = "AhitatokDatabaseHelper";
    private static int DATABASE_VERSION = BuildConfig.VERSION_CODE;
    //private static final int DATABASE_VERSION = 6;
    //The Android's default system path of your application database.
    private String DB_PATH = null;
    private SQLiteDatabase myDataBase;
    private static Context myContext;

    //for singleton instance

    private static DatabaseHelper instance = null;

    private DatabaseHelper(Context context) {
        super(context, DB_NAME, null, DATABASE_VERSION);
        myContext = context;
        DB_PATH = "/data/data/"+context.getPackageName()+"/databases/";

    }

    public static synchronized DatabaseHelper getInstance(Context context) {
        if (instance == null)
            instance = new DatabaseHelper(context.getApplicationContext());
        return instance;
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        Log.d(TAG, "onCreate called");
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        Log.d(TAG, "onUpgrade start");
        if(newVersion != oldVersion)
            try {
                Log.d(TAG, "onUpgrade version differs");

                File f = new File(DB_PATH);
                String[] fileList;

                fileList = f.list();

                for ( int i=0 ; i < fileList.length; i++) {
                    File myFile = new File(f, fileList[i]);
                    myFile.delete();
                    Log.d(TAG, "onUpgrade deleting file: " + fileList[i]);
                }

                copyDataBase();
            } catch (Exception e) {
                e.printStackTrace();
            }
    }


    public void init() throws IOException {

        try {
            instance.createDataBase();
        } catch (IOException ioe) {
            throw  new Error("Unable to create database");
        }

        try {
            instance.openDataBase();
        } catch (SQLException sqle) {
            throw sqle;
        }
    }


    /**
     * Creates a empty database on the system and rewrites it with your own database.
     * */
    public void createDataBase() throws IOException {

        Log.d(TAG, "createDatabase start");

        boolean dbExist = checkDataBase();

        if(dbExist){
            Log.d(TAG, "database exist");
            //Toast.makeText( myContext,"database exist", Toast.LENGTH_SHORT).show();
        }else{
            //By calling this method and empty database will be created into the default system path
            //of your application so we are gonna be able to overwrite that database with our database.
            Log.d(TAG, "creating database from assets");
            this.getWritableDatabase();
            try {
                copyDataBase();
            } catch (IOException e) {
                Log.d(TAG, "Error copying database");
                throw new Error("Error copying database");
            }
        }

        onUpgrade(myDataBase, (DATABASE_VERSION-1), DATABASE_VERSION);
    }

    /**
     * Check if the database already exist to avoid re-copying the file each time you open the application.
     * @return true if it exists, false if it doesn't
     */
    private boolean checkDataBase(){

        SQLiteDatabase checkDB = null;

        Log.d(TAG, "checkDataBase start");

        try{
            String myPath = DB_PATH + DB_NAME;
            checkDB = SQLiteDatabase.openDatabase(myPath, null, SQLiteDatabase.OPEN_READONLY);

        }catch(SQLiteException e){
            Log.d(TAG, "checkDataBase cannot find database");
            e.printStackTrace();
        }

        if(checkDB != null){
            checkDB.close();
        }
        return checkDB != null;
    }

    /**
     * Copies your database from your local assets-folder to the just created empty database in the
     * system folder, from where it can be accessed and handled.
     * This is done by transfering bytestream.
     * */
    private void copyDataBase() throws IOException{

        Log.d(TAG, "copy database 1");
        //Open your local db as the input stream
        InputStream myInput = myContext.getAssets().open(DB_NAME);
        Log.d(TAG, "copy database 2");
        // Path to the just created empty db
        String outFileName = DB_PATH + DB_NAME;


        // if the path doesn't exist first, create it
        File f = new File(DB_PATH);
        if (!f.exists())
            f.mkdir();

        //Open the empty db as the output stream
        OutputStream myOutput = new FileOutputStream(outFileName);
        Log.d(TAG, "copy database 3");
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

    //https://stackoverflow.com/questions/50476782/android-p-sqlite-no-such-table-error-after-copying-database-from-assets
    @Override
    public void onOpen(SQLiteDatabase db) {
        super.onOpen(db);
        db.disableWriteAheadLogging();
    }


    /*
    public HashMap<String, DailyDevotion> getAllDevotionals() {
        HashMap<String, DailyDevotion> hash_map = new HashMap<>();

        SQLiteDatabase db = this.getReadableDatabase();
        Cursor res =  db.rawQuery( "select * from ahitatok", null );
        res.moveToFirst();

        while(!res.isAfterLast()){
            hash_map.put(res.getString(res.getColumnIndex(AHITATOK_COLUMN_DATE)),
                        new DailyDevotion(res.getInt(res.getColumnIndex(AHITATOK_COLUMN_ID)),
                                res.getString(res.getColumnIndex(AHITATOK_COLUMN_DATE)),
                                res.getString(res.getColumnIndex(AHITATOK_COLUMN_DE_CIM)),
                                res.getString(res.getColumnIndex(AHITATOK_COLUMN_DE_IGE)),
                                res.getString(res.getColumnIndex(AHITATOK_COLUMN_DE_SZOVEG)),
                                res.getString(res.getColumnIndex(AHITATOK_COLUMN_DE_SZERZO)),
                                res.getString(res.getColumnIndex(AHITATOK_COLUMN_DU_CIM)),
                                res.getString(res.getColumnIndex(AHITATOK_COLUMN_DU_IGE)),
                                res.getString(res.getColumnIndex(AHITATOK_COLUMN_DU_SZOVEG)),
                                res.getString(res.getColumnIndex(AHITATOK_COLUMN_DU_SZERZO)),
                                res.getString(res.getColumnIndex(AHITATOK_COLUMN_BIBLIAORA)),
                                res.getString(res.getColumnIndex(AHITATOK_COLUMN_IMAORA))));


            res.moveToNext();
        }
        res.close();
        return hash_map;
    }
    */

    public DailyDevotion getDailyDevotionByDate(String datum) {
        SQLiteDatabase db;
        try {
            db = this.getReadableDatabase();
        }
        catch (SQLException e) {
            return null;
        }

        Cursor res = null;
        try {
            res = db.query(AHITATOK_TABLE_NAME, new String[]
                            {AHITATOK_COLUMN_ID, AHITATOK_COLUMN_DATE,
                                    AHITATOK_COLUMN_DE_CIM,
                                    AHITATOK_COLUMN_DE_IGE,
                                    AHITATOK_COLUMN_DE_SZOVEG,
                                    AHITATOK_COLUMN_DE_SZERZO,
                                    AHITATOK_COLUMN_DU_CIM,
                                    AHITATOK_COLUMN_DU_IGE,
                                    AHITATOK_COLUMN_DU_SZOVEG,
                                    AHITATOK_COLUMN_DU_SZERZO,
                                    AHITATOK_COLUMN_BIBLIAORA,
                                    AHITATOK_COLUMN_IMAORA}, AHITATOK_COLUMN_DATE + "=?",
                    new String[]{datum}, null, null, null, null);


            if (res != null && (res.getCount() > 0)) {
                res.moveToFirst();
            } else {
                res.close();
                return null;
            }
        }
        catch (Exception e) {
            if (res != null)
                res.close();
            return null;
        }

        DailyDevotion returnValue = new DailyDevotion(res.getInt(res.getColumnIndex(AHITATOK_COLUMN_ID)),
                res.getString(res.getColumnIndex(AHITATOK_COLUMN_DATE)),
                res.getString(res.getColumnIndex(AHITATOK_COLUMN_DE_CIM)),
                res.getString(res.getColumnIndex(AHITATOK_COLUMN_DE_IGE)),
                res.getString(res.getColumnIndex(AHITATOK_COLUMN_DE_SZOVEG)),
                res.getString(res.getColumnIndex(AHITATOK_COLUMN_DE_SZERZO)),
                res.getString(res.getColumnIndex(AHITATOK_COLUMN_DU_CIM)),
                res.getString(res.getColumnIndex(AHITATOK_COLUMN_DU_IGE)),
                res.getString(res.getColumnIndex(AHITATOK_COLUMN_DU_SZOVEG)),
                res.getString(res.getColumnIndex(AHITATOK_COLUMN_DU_SZERZO)),
                res.getString(res.getColumnIndex(AHITATOK_COLUMN_BIBLIAORA)),
                res.getString(res.getColumnIndex(AHITATOK_COLUMN_IMAORA)));

        res.close();

        return returnValue;
    }


    public ArrayList<Favorite> getAllFavoritesWithTitles() {
        ArrayList<Favorite> array_list = new ArrayList<>();
        SQLiteDatabase db;

        try {
            db = this.getReadableDatabase();
        }
        catch (SQLException e) {
            return array_list;
        }

        Cursor res =  null;
        try {
            res = db.rawQuery("select t.datum as datum, a.de_cim as de_cim, " +
                    "a.du_cim as du_cim from kedvencek t, ahitatok a where a.datum=t.datum order by t.datum", null);
            res.moveToFirst();

            while (!res.isAfterLast()) {
                Favorite k = new Favorite(res.getString(res.getColumnIndex(AHITATOK_COLUMN_DATE)),
                        "d.e:    " + res.getString(res.getColumnIndex(AHITATOK_COLUMN_DE_CIM)),
                        "d.u.:   " + res.getString(res.getColumnIndex(AHITATOK_COLUMN_DU_CIM)));

                array_list.add(k);
                res.moveToNext();
            }
        }
        catch (Exception e) {
            if (res != null)
                res.close();
            return array_list;
        }

        res.close();
        return array_list;
    }

    public boolean insertFavoriteIfNotExist (String datum){
        long count = 0;
        SQLiteDatabase db;

        try {
            db = this.getWritableDatabase();
            count = DatabaseUtils.queryNumEntries(db, KEDVENCEK_TABLE_NAME,
                    KEDVENCEK_COLUMN_DATE+"=? ", new String[] {datum});

            if (count == 0) {
                ContentValues contentValues = new ContentValues();
                contentValues.put(KEDVENCEK_COLUMN_DATE, datum);
                db.insert(KEDVENCEK_TABLE_NAME, null, contentValues);
            }

        }
        catch (SQLException e) {
            return false;
        }
        catch (Exception e) {
            return false;
        }
        finally {
            return (count == 0);
        }

    }

    public void deleteFavorite(String date) {
        try {
            SQLiteDatabase db = this.getWritableDatabase();
            db.delete(KEDVENCEK_TABLE_NAME,
                    KEDVENCEK_COLUMN_DATE + "= ? ",
                    new String[]{date});
        }
        catch (SQLException e) {}
        catch (Exception ex) {}
    }

    // closing database
    public void closeDB() {
        SQLiteDatabase db = this.getReadableDatabase();
        if (db != null && db.isOpen())
            db.close();
    }

}
