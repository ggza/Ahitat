package gz.rmbgysz.ahitat;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListView;
import android.widget.Toast;

import java.text.ParseException;
import java.util.ArrayList;


public class FavoritesActivity extends AppCompatActivity implements UpdateFavoritesInterface {

    private int selectedForDeleteCount = 0;
    private Menu optionsMenu = null;
    private ListView listView;
    private FavoritesAdapter adapter;
    private DatabaseHelper mydb;
    ArrayList<Favorite> array_list;
    private DateManager dateManager = DateManager.getInstance(this);

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

        listView = (ListView) findViewById(R.id.favorites_list);
        mydb = new DatabaseHelper(this);
        array_list = mydb.getAllFavoritesWithTitles();
        adapter = new FavoritesAdapter(this, array_list);

        // Assign adapter to ListView
        listView.setAdapter(adapter);

        // ListView Item Click Listener

        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {

            @Override
            public void onItemClick(AdapterView<?> parent, View view,
                                    int position, long id) {

                // ListView Clicked item index
                int itemPosition = position;

                // ListView Clicked item value
                Favorite item = (Favorite) listView.getItemAtPosition(position);

                try {
                    dateManager.setDate(item.datum);
                    finish();
                } catch (ParseException e) {
                    e.printStackTrace();
                }
                // Show Alert
                //Toast.makeText(getApplicationContext(),
                //        "Position :"+itemPosition+"  ListItem : " +item , Toast.LENGTH_LONG)
                //        .show();
            }

        });
    }


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.favorite_app , menu);
        optionsMenu = menu;
        return true;
    }

    @Override
    public boolean onPrepareOptionsMenu (Menu menu) {
            menu.getItem(0).setVisible(this.selectedForDeleteCount > 0);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        if (item.getItemId() == android.R.id.home)
            finish();
        //FIXME: setResult when returning: http://stackoverflow.com/questions/20700805/go-back-to-main-activity-on-menu-item-click
        return super.onOptionsItemSelected(item);
    }

    public void updateMenu(int count) {
        this.selectedForDeleteCount = count;
        if (optionsMenu != null) {
            onPrepareOptionsMenu(optionsMenu);
        }
    }

}
