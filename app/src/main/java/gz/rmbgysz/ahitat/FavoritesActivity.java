package gz.rmbgysz.ahitat;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.view.MenuItem;

public class FavoritesActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setTheme(R.style.MainAppTheme);
        setContentView(R.layout.activity_favorites);

        Toolbar toolbar = (Toolbar) findViewById(R.id.favorites_toolbar);
        setSupportActionBar(toolbar);

        if (getSupportActionBar() != null) {
            getSupportActionBar().setDisplayHomeAsUpEnabled(true);
            getSupportActionBar().setDisplayShowHomeEnabled(true);
        }
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        if (item.getItemId() == android.R.id.home)
            finish();
        //FIXME: setResult when returning: http://stackoverflow.com/questions/20700805/go-back-to-main-activity-on-menu-item-click
        return super.onOptionsItemSelected(item);
    }
}
