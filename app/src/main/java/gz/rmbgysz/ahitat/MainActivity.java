package gz.rmbgysz.ahitat;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.app.AppCompatDelegate;

import java.io.IOException;

public class MainActivity extends AppCompatActivity {


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        AppCompatDelegate.setCompatVectorFromResourcesEnabled(true);
        setContentView(R.layout.activity_main);
        try {
            DatabaseHelper.getInstance(this).createDataBase();

        } catch (IOException ioe) {
            throw new Error("Unable to create database");
        }
    }


    @Override
    public void onDestroy() {
        DatabaseHelper.getInstance(this).closeDB();
        super.onDestroy();
    }

    public void startMainApp() {
        Intent intent = new Intent(this, MainAppActivity.class);
        startActivity(intent);
    }
}