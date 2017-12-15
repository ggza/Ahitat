package gz.rmbgysz.ahitat;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.MenuItem;
import android.graphics.Typeface;
import android.widget.TextView;

public class InfoActivity extends AppCompatActivity {

    TextView info_title, info_title1, info_content, info_content1;

    private String M_BOLD= "merriweather_bold.ttf";
    private String M_REGULAR= "merriweather_regular.ttf";
    private String M_ITALIC= "merriweather_italic.ttf";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setTheme(R.style.MainAppTheme);
        setContentView(R.layout.activity_info);
        Toolbar toolbar = (Toolbar) findViewById(R.id.info_toolbar);
        setSupportActionBar(toolbar);

        info_title=(TextView)findViewById(R.id.info_title);
        info_title1=(TextView)findViewById(R.id.info_title1);
        info_content=(TextView)findViewById(R.id.info_content);
        info_content1=(TextView)findViewById(R.id.info_content1);

        info_title.setTypeface(Typeface.createFromAsset(getAssets(), M_BOLD));
        info_title1.setTypeface(Typeface.createFromAsset(getAssets(), M_BOLD));
        info_content.setTypeface(Typeface.createFromAsset(getAssets(), M_REGULAR));
        info_content1.setTypeface(Typeface.createFromAsset(getAssets(), M_REGULAR));

        if (getSupportActionBar() != null) {
            getSupportActionBar().setDisplayHomeAsUpEnabled(true);
            getSupportActionBar().setDisplayShowHomeEnabled(true);
        }

        /*
        FloatingActionButton fab = (FloatingActionButton) findViewById(R.id.fab);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Snackbar.make(view, "Replace with your own action", Snackbar.LENGTH_LONG)
                        .setAction("Action", null).show();
            }
        });
        */

    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        if (item.getItemId() == android.R.id.home)
            finish();
        return super.onOptionsItemSelected(item);
    }


}
