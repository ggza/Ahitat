package gz.rmbgysz.ahitat;

import android.app.DatePickerDialog;
import android.app.Dialog;
import android.content.Intent;
import android.content.res.Configuration;
import android.os.Build;
import android.os.Bundle;
//import android.support.design.widget.FloatingActionButton;
import com.getbase.floatingactionbutton.FloatingActionButton;
import com.getbase.floatingactionbutton.FloatingActionsMenu;

import android.support.annotation.NonNull;
import android.support.annotation.RequiresApi;
import android.support.design.widget.Snackbar;

import android.support.v4.app.DialogFragment;
import android.support.v4.app.ShareCompat;
import android.text.Html;
import android.view.View;
import android.support.design.widget.NavigationView;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.TextView;
import android.widget.DatePicker;
import android.widget.Toast;

import java.text.ParseException;
import java.util.Date;
import java.util.Locale;

public class MainAppActivity extends AppCompatActivity
        implements NavigationView.OnNavigationItemSelectedListener, DatePickerDialog.OnDateSetListener  {

    private DrawerLayout mDrawerLayout;
    private DateManager dateManager = new DateManager(this);

    private static String demoContent = " Lorem ipsum dolor sit amet, consectetur adipiscing elit, " +
            "sed do eiusmod tempor incididunt ut labore et dolore magna aliqua. " +
            "Ut enim ad minim veniam, quis nostrud exercitation ullamco laboris " +
            "nisi ut aliquip ex ea commodo consequat. Duis aute irure dolor in " +
            "reprehenderit in voluptate velit esse cillum dolore eu fugiat nulla pariatur. \n" +
            "Excepteur sint occaecat cupidatat non proident, sunt in culpa qui " +
            "officia deserunt mollit anim id est laborum.";

    private static String demoTitle = "Ez lesz a cím";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setTheme(R.style.MainAppTheme);
        setContentView(R.layout.activity_main_app);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        initFloatingActionButtonMenu();

        setTextViews(dateManager.getFormattedDate());

        /* ez a regi megoldas egyelore nem kell
        FloatingActionButton fab = (FloatingActionButton) findViewById(R.id.fab_menu);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Snackbar.make(view, "Ide jön majd a helyi menü", Snackbar.LENGTH_LONG)
                        .setAction("Action", null).show();
            }
        });
        */

        mDrawerLayout = (DrawerLayout) findViewById(R.id.drawer_layout);

        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(
                this, mDrawerLayout, toolbar, R.string.navigation_drawer_open, R.string.navigation_drawer_close);
        mDrawerLayout.addDrawerListener(toggle);
        toggle.syncState();

        NavigationView navigationView = (NavigationView) findViewById(R.id.nav_view);
        navigationView.setNavigationItemSelectedListener(this);
    }


    //TODO: vhova kitenni ?
    private void setTextViews(String actualDateString) {
        TextView actualDate = (TextView)findViewById(R.id.actual_date);
        actualDate.setText(actualDateString);

        TextView heading = (TextView)findViewById(R.id.heading);
        heading.setText(demoTitle);

        TextView content = (TextView)findViewById(R.id.content);
        content.setText(actualDateString + demoContent);
    }

    @Override
    public void onBackPressed() {
        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        if (drawer.isDrawerOpen(GravityCompat.START)) {
            drawer.closeDrawer(GravityCompat.START);
        } else {
            super.onBackPressed();
        }
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.main_app, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.add_to_favorites) {
            Snackbar.make(findViewById(R.id.content_main_app), "Kedvencekhez hozzáadva " + dateManager.getDateString(), Snackbar.LENGTH_LONG)
                    .setAction("clicked", null)
                    .show();

            return true;
        }

        return super.onOptionsItemSelected(item);
    }


    @Override
    public boolean onNavigationItemSelected(MenuItem item) {
        // Handle navigation view item clicks here.
        int id = item.getItemId();

        if (id == R.id.nav_actual_lecture) {
            dateManager.setDate(new Date());
            setTextViews(dateManager.getFormattedDate());


        } else if (id == R.id.nav_search_by_date) {

            DialogFragment newFragment = new DatePickerFragment();
            Bundle bundle = null;
            try {
                bundle = getBundleForDatePicker();
            } catch (ParseException e) {
                e.printStackTrace();
            }

            newFragment.setArguments(bundle);
            newFragment.show(getSupportFragmentManager(), "datePicker");

        /*FIXME: a keressel egyelore nem foglalkozunk
        } else if (id == R.id.nav_search) {

            Snackbar.make(findViewById(R.id.content_main_app), "Keresés", Snackbar.LENGTH_LONG)
                    .setAction("clicked", null)
                    .show();
        */
        } else if (id == R.id.nav_favorites) {

            Intent intent = new Intent(this, FavoritesActivity.class);
            startActivity(intent);

        } else if (id == R.id.nav_share) {

            //TODO: ezt nem tudtam emulatoron tesztelni
            Intent sendIntent = new Intent();
            sendIntent.setAction(Intent.ACTION_SEND);
            sendIntent.putExtra(Intent.EXTRA_TEXT, "This is my text to send.");
            sendIntent.setType("text/html");
            startActivity(Intent.createChooser(sendIntent, "Áhitat megosztása"));


            /*
            Intent sharingIntent = new Intent(Intent.ACTION_SEND);
            sharingIntent.setType("text/html");


            String shareString = Html.fromHtml("<p>Store Name:</p>") .toString();
            sharingIntent.putExtra(android.content.Intent.EXTRA_TEXT, shareString);

            if (sharingIntent.resolveActivity(getPackageManager()) != null)
                startActivity(Intent.createChooser(sharingIntent, "Áhitat megosztása"));
            else {
                Toast.makeText(this, "No app found on your phone which can perform this action", Toast.LENGTH_SHORT).show();
            }
            */

            /*
            ShareCompat.IntentBuilder.from(this)
                    .setText("blabla")
                    .setType("string/html")
                    .setChooserTitle("Kiválasztott áhitat megosztása")
                    .startChooser();
            */
            /*Snackbar.make(findViewById(R.id.content_main_app), "Megosztás", Snackbar.LENGTH_LONG)
                    .setAction("clicked", null)
                    .show();
            */
        }

        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        drawer.closeDrawer(GravityCompat.START);
        return true;
    }

    @NonNull
    private Bundle getBundleForDatePicker() throws ParseException {
        Bundle bundle = new Bundle();
        bundle.putInt("year",dateManager.getYear());
        bundle.putInt("month",dateManager.getMonth());
        bundle.putInt("day",dateManager.getDay());
        bundle.putLong("minDate", dateManager.getMinDate());
        bundle.putLong("maxDate", dateManager.getMaxDate());
        return bundle;
    }

    private void initFloatingActionButtonMenu() {
        final FloatingActionsMenu floatingActionsMenu = (FloatingActionsMenu) findViewById(R.id.fab_menu);
        final FloatingActionButton goBack = (FloatingActionButton) findViewById(R.id.action_go_back);
        goBack.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                dateManager.stepToPreviousDay();
                setTextViews(dateManager.getFormattedDate());
                floatingActionsMenu.collapse();
            }
        });
        final FloatingActionButton goForward = (FloatingActionButton) findViewById(R.id.action_go_forward);
        goForward.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                dateManager.stepToNextDay();
                setTextViews(dateManager.getFormattedDate());
                floatingActionsMenu.collapse();
            }
        });

        final View floatingMenuBackground = findViewById(R.id.floating_menu_background);
        floatingMenuBackground.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                floatingActionsMenu.collapse();
            }
        });

        floatingActionsMenu.setOnFloatingActionsMenuUpdateListener(new FloatingActionsMenu.OnFloatingActionsMenuUpdateListener() {
            @Override
            public void onMenuExpanded() {
                floatingMenuBackground.setVisibility(View.VISIBLE);
            }

            @Override
            public void onMenuCollapsed() {
                floatingMenuBackground.setVisibility(View.GONE);
            }
        });
    }

    @Override
    public void onDateSet(DatePicker view, int year, int month, int dayOfMonth) {
        try {
            dateManager.setDate(year, month +1 , dayOfMonth);
            setTextViews(dateManager.getFormattedDate());
        } catch (ParseException e) {
            Toast.makeText(view.getContext(), R.string.date_set_error, Toast.LENGTH_SHORT).show();
        }
    }


    public static class DatePickerFragment extends DialogFragment {
        @RequiresApi(api = Build.VERSION_CODES.N)
        @Override

        public Dialog onCreateDialog(Bundle savedInstanceState) {
            //lokalizacio
            Locale locale = new Locale("HU");
            locale.setDefault(locale);

            Configuration config = new Configuration();
            config.setLocale(locale);
            getResources().getConfiguration().updateFrom(config);

            int year = getArguments().getInt("year");
            int month = getArguments().getInt("month");
            int day = getArguments().getInt("day");
            long minDate = getArguments().getLong("minDate");
            long maxDate = getArguments().getLong("maxDate");
            DatePickerDialog dpd = new DatePickerDialog(getActivity(),
                    (DatePickerDialog.OnDateSetListener)
                            getActivity(), year, month, day);

            dpd.getDatePicker().setMinDate(minDate);
            dpd.getDatePicker().setMaxDate(maxDate);
            //TODO: meg kell oldani hogy resbol jojjon
            dpd.setButton(DatePickerDialog.BUTTON_NEGATIVE, "Mégse", dpd);
            dpd.setButton(DatePickerDialog.BUTTON_POSITIVE, "Beállít", dpd);

            return dpd;

        }
    }
}
