package gz.rmbgysz.ahitat;

import android.os.Bundle;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;

import android.text.util.Linkify;
import android.view.MenuItem;
import android.graphics.Typeface;
import android.widget.TextView;

import static android.text.Html.fromHtml;

public class HelpActivity extends AppCompatActivity {
    TextView  help_title1, help_title2, help_title3, help_title4, help_content1, help_content2, help_content3, help_content4, help_content5, help_content6, help_content7;

    private String M_BOLD= "merriweather_bold.ttf";
    private String M_REGULAR= "merriweather_regular.ttf";
    private String M_ITALIC= "merriweather_italic.ttf";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setTheme(R.style.MainAppTheme);

        Iconify
                .with(new AhitatIconModule());

        setContentView(R.layout.activity_help);
        Toolbar toolbar = (Toolbar) findViewById(R.id.help_toolbar);
        setSupportActionBar(toolbar);

        TextView help_title1 = (TextView)findViewById(R.id.help_title1);
        TextView help_title2 = (TextView)findViewById(R.id.help_title2);
        TextView help_title3 = (TextView)findViewById(R.id.help_title3);
        TextView help_title4 = (TextView)findViewById(R.id.help_title4);
        TextView help_content1 = (TextView)findViewById(R.id.help_content1);
        TextView help_content2 = (TextView)findViewById(R.id.help_content2);
        TextView help_content3 = (TextView)findViewById(R.id.help_content3);
        TextView help_content4 = (TextView)findViewById(R.id.help_content4);
        TextView help_content5 = (TextView)findViewById(R.id.help_content5);
        TextView help_content6_tv = (TextView)findViewById(R.id.help_content6);
        TextView help_content7 = (TextView)findViewById(R.id.help_content7);

        help_title1.setTypeface(Typeface.createFromAsset(getAssets(), M_BOLD));
        help_title2.setTypeface(Typeface.createFromAsset(getAssets(), M_BOLD));
        help_title3.setTypeface(Typeface.createFromAsset(getAssets(), M_BOLD));
        help_title4.setTypeface(Typeface.createFromAsset(getAssets(), M_BOLD));
        help_content1.setTypeface(Typeface.createFromAsset(getAssets(), M_REGULAR));
        help_content2.setTypeface(Typeface.createFromAsset(getAssets(), M_REGULAR));
        help_content3.setTypeface(Typeface.createFromAsset(getAssets(), M_REGULAR));
        help_content4.setTypeface(Typeface.createFromAsset(getAssets(), M_REGULAR));
        help_content5.setTypeface(Typeface.createFromAsset(getAssets(), M_REGULAR));
        help_content6_tv.setTypeface(Typeface.createFromAsset(getAssets(), M_REGULAR));
        help_content7.setTypeface(Typeface.createFromAsset(getAssets(), M_REGULAR));

        if (getSupportActionBar() != null) {
            getSupportActionBar().setDisplayHomeAsUpEnabled(true);
            getSupportActionBar().setDisplayShowHomeEnabled(true);
        }

        //help_content6_tv.setText(Html.fromHtml(getString(R.string.help_content6)));
        //help_content6_tv.setMovementMethod(LinkMovementMethod.getInstance());

        Linkify.addLinks(help_content6_tv, Linkify.EMAIL_ADDRESSES);

        //help_content6_tv.setAutoLinkMask(Linkify.EMAIL_ADDRESSES);

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

