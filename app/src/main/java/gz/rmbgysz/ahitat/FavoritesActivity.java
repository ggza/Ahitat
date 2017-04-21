package gz.rmbgysz.ahitat;

import android.content.DialogInterface;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListView;
import java.text.ParseException;

public class FavoritesActivity extends AppCompatActivity implements UpdateFavoritesInterface {

    private int selectedForDeleteCount = 0;
    private Menu optionsMenu = null;
    private ListView listView;
    private FavoritesAdapter adapter;
    private DateManager dateManager = DateManager.getInstance();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setTheme(R.style.MainAppTheme);
        setContentView(R.layout.activity_favorites);

        Toolbar favoritesToolbar = (Toolbar) findViewById(R.id.favorites_toolbar);
        setSupportActionBar(favoritesToolbar);

        if (getSupportActionBar() != null) {
            getSupportActionBar().setDisplayHomeAsUpEnabled(true);
            getSupportActionBar().setDisplayShowHomeEnabled(true);
        }

        listView = (ListView) findViewById(R.id.favorites_list);

        adapter = new FavoritesAdapter(this);

        // Assign adapter to ListView
        listView.setAdapter(adapter);

        // ListView Item Click Listener
        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {

            @Override
            public void onItemClick(AdapterView<?> parent, View view,
                                    int position, long id) {

                // ListView Clicked item value
                Favorite item = (Favorite) listView.getItemAtPosition(position);

                try {
                    dateManager.setDate(item.getDate());
                    finish();
                } catch (ParseException e) {
                    e.printStackTrace();
                }
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
        if (item.getItemId() == R.id.delete_from_favorites) {
            showAlertDialog();
        }
        else if (item.getItemId() == android.R.id.home)
            finish();
        //setResult when returning: http://stackoverflow.com/questions/20700805/go-back-to-main-activity-on-menu-item-click
        return super.onOptionsItemSelected(item);
    }

    public void updateMenu(int count) {
        this.selectedForDeleteCount = count;
        if (optionsMenu != null) {
            onPrepareOptionsMenu(optionsMenu);
        }
    }

    public void gotPositiveResult() {
        adapter.deleteItemsAndRefresh();
    }

    public void gotNegativeResult() {
    }

    public void showAlertDialog() {
        final UpdateFavoritesInterface listener = this;
        AlertDialog.Builder builder = new AlertDialog.Builder(this);

        builder.setTitle(getString(R.string.deletetitle));
        builder.setMessage(getString(R.string.areyousure));

        builder.setPositiveButton(getString(R.string.ok2), new DialogInterface.OnClickListener() {

            public void onClick(DialogInterface dialog, int which) {
                listener.gotPositiveResult();
                dialog.dismiss();
            }
        });

        builder.setNegativeButton(getString(R.string.cancel), new DialogInterface.OnClickListener() {

            @Override
            public void onClick(DialogInterface dialog, int which) {
                // Do nothing
                listener.gotNegativeResult();
                dialog.dismiss();
            }
        });
        builder.show();
    }
}
