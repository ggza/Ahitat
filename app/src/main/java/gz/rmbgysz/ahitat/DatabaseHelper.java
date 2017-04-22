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

    private static final String TAG = "DatabaseHelper";

    //The Android's default system path of your application database.
    private String DB_PATH = null;
    private SQLiteDatabase myDataBase;
    private static Context myContext;

    //for singleton instance

    private static DatabaseHelper instance = null;

    private DatabaseHelper(Context context) {
        super(context, DB_NAME, null, 1);
        this.myContext = context;
        DB_PATH = "/data/data/"+context.getPackageName()+"/databases/";
    }

    public static DatabaseHelper getInstance(Context context) {
        if (instance == null)
            instance = new DatabaseHelper(context.getApplicationContext());
        return instance;
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        if(newVersion>oldVersion)
            try {
                copyDataBase();
            } catch (IOException e) {
                e.printStackTrace();
            }
    }

    /**
     * Creates a empty database on the system and rewrites it with your own database.
     * */
    public void createDataBase() throws IOException {

        boolean dbExist = checkDataBase();

        if(dbExist){
            Log.d(TAG, "database exist");
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
        return checkDB != null;
    }

    /**
     * Copies your database from your local assets-folder to the just created empty database in the
     * system folder, from where it can be accessed and handled.
     * This is done by transfering bytestream.
     * */
    private void copyDataBase() throws IOException{

        //Open your local db as the input stream
        InputStream myInput = this.myContext.getAssets().open(DB_NAME);

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

    public DailyDevotion getDailyDevotionByDate(String datum) {
        SQLiteDatabase db = this.getReadableDatabase();

        Cursor res = db.query(AHITATOK_TABLE_NAME, new String[]
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
                new String[] {datum}, null, null, null, null);

        if (res != null)
            res.moveToFirst();
        else
            return null;

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

        return returnValue;
    }


    public ArrayList<Favorite> getAllFavoritesWithTitles() {
        ArrayList<Favorite> array_list = new ArrayList<>();

        SQLiteDatabase db = this.getReadableDatabase();

        Cursor res =  db.rawQuery( "select t.datum as datum, a.de_cim as de_cim, " +
                "a.du_cim as du_cim from kedvencek t, ahitatok a where a.datum=t.datum order by t.datum", null );
        res.moveToFirst();

        while(!res.isAfterLast()){
            Favorite k = new Favorite(res.getString(res.getColumnIndex(AHITATOK_COLUMN_DATE)),
                    "d.e:    " + res.getString(res.getColumnIndex(AHITATOK_COLUMN_DE_CIM)),
                    "d.u.:   " + res.getString(res.getColumnIndex(AHITATOK_COLUMN_DU_CIM)));

            array_list.add(k);
            res.moveToNext();
        }
        res.close();
        return array_list;
    }

    public boolean insertFavoriteIfNotExist (String datum) {
        SQLiteDatabase db = this.getWritableDatabase();

        long count = DatabaseUtils.queryNumEntries(db, KEDVENCEK_TABLE_NAME,
                KEDVENCEK_COLUMN_DATE+"=? ", new String[] {datum});

        if (count == 0) {
            ContentValues contentValues = new ContentValues();
            contentValues.put(KEDVENCEK_COLUMN_DATE, datum);
            db.insert(KEDVENCEK_TABLE_NAME, null, contentValues);
        }

        return (count == 0);
    }

    public Integer deleteFavorite(String date) {
        SQLiteDatabase db = this.getWritableDatabase();
        return db.delete(KEDVENCEK_TABLE_NAME,
                KEDVENCEK_COLUMN_DATE + "= ? ",
                new String[] { date });
    }

    // closing database
    public void closeDB() {
        SQLiteDatabase db = this.getReadableDatabase();
        if (db != null && db.isOpen())
            db.close();
    }

}
