package gz.rmbgysz.ahitat;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.app.AppCompatDelegate;
import android.view.View;

import java.io.IOException;

public class MainActivity extends AppCompatActivity {


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        AppCompatDelegate.setCompatVectorFromResourcesEnabled(true);
        setContentView(R.layout.activity_main);

    }


    @Override
    public void onDestroy() {
        DatabaseHelper.getInstance(this).closeDB();
        super.onDestroy();
        //Toast.makeText(this, "Before create db.", Toast.LENGTH_SHORT).show();
        try {
            DatabaseHelper.getInstance(this).createDataBase();

        } catch (IOException ioe) {
            throw new Error("Unable to create database");
        }
        //Toast.makeText(this, "After create db.", Toast.LENGTH_SHORT).show();
    }

    public void startMainApp() {
        Intent intent = new Intent(this, MainAppActivity.class);
        startActivity(intent);
        //Toast.makeText(this, "After start activity", Toast.LENGTH_SHORT).show();
    }
}