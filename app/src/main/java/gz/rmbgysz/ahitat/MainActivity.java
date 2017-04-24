package gz.rmbgysz.ahitat;

import android.content.Intent;
import android.os.Handler;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.app.AppCompatDelegate;
import android.view.View;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.ImageView;


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
    }

    ImageView rotateImage;

    public void startRotatingImage(View view) {
        rotateImage = (ImageView) findViewById(R.id.rotate_image);
        Animation startRotateAnimation = AnimationUtils.loadAnimation(getApplicationContext(), R.anim.android_rotate_animation);
        rotateImage.startAnimation(startRotateAnimation);

        try {
            DatabaseHelper.getInstance(MainActivity.this).createDataBase();
            new Handler().postDelayed(new Runnable() {
                @Override
                public void run() {
                Intent intent = new Intent(MainActivity.this, MainAppActivity.class);
                startActivity(intent);
                }
            },getApplicationContext().getResources().
                    getInteger(R.integer.imagedaley));

        } catch (IOException ioe) {
            throw new Error("Unable to create database");
        }
    }
}