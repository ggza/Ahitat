package gz.rmbgysz.ahitat;

import android.content.Intent;
import android.content.pm.ActivityInfo;
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
        setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_PORTRAIT);
    }


    @Override
    public void onDestroy() {
        DatabaseHelper.getInstance(this).closeDB();
        super.onDestroy();
    }

    public void startMainApp(View view) {
        try {
            DatabaseHelper.getInstance(this).init();
            Intent intent = new Intent(this, MainAppActivity.class);
            startActivity(intent);
        } catch (IOException ioe) {
            throw new Error("Unable to create database");
        }
    }
}