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
import android.view.View;
import android.support.design.widget.NavigationView;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.DatePicker;
import android.widget.Toast;

import java.io.IOException;
import java.text.ParseException;
import java.util.HashMap;
import java.util.Locale;

public class MainAppActivity extends AppCompatActivity
        implements NavigationView.OnNavigationItemSelectedListener, DatePickerDialog.OnDateSetListener  {

    public static final int FAVORITES_REQUEST_CODE = 0xe23;
    private DatabaseHelper mydb ;
    private HashMap texts_map;
    private DrawerLayout mDrawerLayout;
    private DateManager dateManager = DateManager.getInstance();

    private int originalBibHeight;
    private int originalImaHeight;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setTheme(R.style.MainAppTheme);
        setContentView(R.layout.activity_main_app);
        getInitalHeights();

        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        initFloatingActionButtonMenu();

        mydb = DatabaseHelper.getInstance(this);

        try {
            mydb.createDataBase();

        } catch (IOException ioe) {
            throw new Error("Unable to create database");
        }

        texts_map= mydb.getAllDevotionals();

        getItemFromMap(texts_map);

        //setTextViews(dateManager.getFormattedDate());

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

    public void refreshActivity() {
        getItemFromMap(texts_map);
    }

    private void getInitalHeights() {
        TextView bibleLecture = (TextView)findViewById(R.id.bibleLecture);
        TextView prayerTemp = (TextView)findViewById(R.id.prayer);

        originalBibHeight = bibleLecture.getLayoutParams().height;
        originalImaHeight = prayerTemp.getLayoutParams().height;

    }

    private void getItemFromMap(HashMap texts_map) {
        if (texts_map.isEmpty()  || !(texts_map.containsKey(dateManager.getDateString()))) {
            fillTextViewsWithEmptyText();
            Toast.makeText(this, getString(R.string.notfounddailydevotion) + " (" +
                    dateManager.getDateString() + ")", Toast.LENGTH_SHORT).show();
        }
        else {
            DailyDevotion item = (DailyDevotion) texts_map.get(dateManager.getDateString());
            setTextViews(dateManager.getFormattedDateWithDayName(this), item);
        }
    }

    private void setTextViews(String actualDateString, DailyDevotion item) {
        TextView actualDate = (TextView)findViewById(R.id.actual_date);
        actualDate.setText(actualDateString);

        TextView amTitle = (TextView)findViewById(R.id.amTitle);
        amTitle.setText(item.getAmTitle());

        TextView amVerse = (TextView)findViewById(R.id.amVerse);
        amVerse.setText(item.getAmVerse());

        TextView amDailyDevotion = (TextView)findViewById(R.id.amDailyDevotion);
        amDailyDevotion.setText(item.getAmDailyDevotion());

        TextView amDailyDevotionAuthor = (TextView)findViewById(R.id.amDailyDevotionAuthor);
        amDailyDevotionAuthor.setText(item.getAmDailyDevotionAuthor());
        TextView bibleLecture = (TextView) findViewById(R.id.bibleLecture);
        TextView prayer = (TextView) findViewById(R.id.prayer);

        LinearLayout.LayoutParams bParams = (LinearLayout.LayoutParams) bibleLecture.getLayoutParams();
        LinearLayout.LayoutParams iParams = (LinearLayout.LayoutParams) prayer.getLayoutParams();

        if (item.getBibleLecture().isEmpty() && (bibleLecture != null) && (prayer != null) && (bParams != null) && (iParams != null)) {
            bParams.height = 0;
            bParams.setMargins(0,0,0,0);
            iParams.height = 0;
            iParams.setMargins(0,0,0,0);
        }
        else {
            bParams.height = originalBibHeight;
            // FIXME:egyelore nem talaltam meg  hogyan lehet lekerdezni,
            // megneztem a designerben es ott 15-re van beallitva ha ott valtozik itt is hozza kell nyulni
            bParams.setMargins(0,30,0,30);
            iParams.height = originalImaHeight;
            iParams.setMargins(0,30,0,30);
        }

        bibleLecture.setText(item.getBibleLecture());
        prayer.setText(item.getPrayer());

        TextView pmTitle = (TextView)findViewById(R.id.pmTitle);
        pmTitle.setText(item.getPmTitle());

        TextView pmVerse = (TextView)findViewById(R.id.pmVerse);
        pmVerse.setText(item.getPmVerse());

        TextView pmDailyDevotion = (TextView)findViewById(R.id.pmDailyDevotion);
        pmDailyDevotion.setText(item.getPmDailyDevotion());

        TextView pmDailyDevotionAuthor = (TextView)findViewById(R.id.pmDailyDevotionAuthor);
        pmDailyDevotionAuthor.setText(item.getPmDailyDevotionAuthor());

        TextView am = (TextView)findViewById(R.id.delelott);
        am.setVisibility(View.VISIBLE);

        TextView pm = (TextView)findViewById(R.id.delutan);
        pm.setVisibility(View.VISIBLE);

    }

    private void fillTextViewsWithEmptyText() {
        TextView actualDate = (TextView)findViewById(R.id.actual_date);
        actualDate.setText("");

        TextView amTitle = (TextView)findViewById(R.id.amTitle);
        amTitle.setText("");

        TextView amVerse = (TextView)findViewById(R.id.amVerse);
        amVerse.setText("");

        TextView amDailyDevotion = (TextView)findViewById(R.id.amDailyDevotion);
        amDailyDevotion.setText("");

        TextView amDailyDevotionAuthor = (TextView)findViewById(R.id.amDailyDevotionAuthor);
        amDailyDevotionAuthor.setText("");

        TextView pmTitle = (TextView)findViewById(R.id.pmTitle);
        pmTitle.setText("");

        TextView pmVerse = (TextView)findViewById(R.id.pmVerse);
        pmVerse.setText("");

        TextView pmDailyDevotion = (TextView)findViewById(R.id.pmDailyDevotion);
        pmDailyDevotion.setText("");

        TextView pmDailyDevotionAuthor = (TextView)findViewById(R.id.pmDailyDevotionAuthor);
        pmDailyDevotionAuthor.setText("");

        TextView bibleLecture = (TextView)findViewById(R.id.bibleLecture);
        bibleLecture.setVisibility(View.GONE);

        TextView prayer = (TextView)findViewById(R.id.prayer);
        prayer.setVisibility(View.GONE);

        TextView am = (TextView)findViewById(R.id.delelott);
        am.setVisibility(View.GONE);

        TextView pm = (TextView)findViewById(R.id.delutan);
        pm.setVisibility(View.GONE);

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
    public void onDestroy() {
        mydb.closeDB();
        super.onDestroy();
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.main_app, menu);
        return true;
    }

    @Override
    protected void onActivityResult( int requestCode, int resultCode, Intent data) {
     super.onActivityResult(requestCode, resultCode, data);

        if (requestCode == FAVORITES_REQUEST_CODE) {
            refreshActivity();
        }
    }


    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
            boolean ret = mydb.insertFavoriteIfNotExist(dateManager.getDateString());
            if (ret) {
                if (id == R.id.add_to_favorites) {
                    Snackbar.make(findViewById(R.id.content_main_app), "Kedvencekhez hozzáadva: " + dateManager.getFormattedDateWithDayName(this), Snackbar.LENGTH_LONG)
                            .setAction("clicked", null)
                            .show();
            }
            return true;
        }
        return super.onOptionsItemSelected(item);
    }


    @Override
    public boolean onNavigationItemSelected(@NonNull MenuItem item) {
        // Handle navigation view item clicks here.
        int id = item.getItemId();

        if (id == R.id.nav_actual_lecture) {
            dateManager.setActualDate();
            getItemFromMap(texts_map);

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
            startActivityForResult(intent, FAVORITES_REQUEST_CODE);
        } else if (id == R.id.nav_share) {

            //TODO: ezt nem tudtam emulatoron tesztelni
            Intent sendIntent = new Intent();
            sendIntent.setAction(Intent.ACTION_SEND);
            sendIntent.putExtra(Intent.EXTRA_TEXT, "This is my text to send.");
            sendIntent.setType("text/html");
            startActivity(Intent.createChooser(sendIntent, getString(R.string.shareactual)));


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
                getItemFromMap(texts_map);
                floatingActionsMenu.collapse();
            }
        });
        final FloatingActionButton goForward = (FloatingActionButton) findViewById(R.id.action_go_forward);
        goForward.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                dateManager.stepToNextDay();
                getItemFromMap(texts_map);
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
            dateManager.setDate(year, month , dayOfMonth);
            getItemFromMap(texts_map);
        } catch (ParseException e) {
            Toast.makeText(view.getContext(), R.string.datesettingerror, Toast.LENGTH_SHORT).show();
        }
    }


    public static class DatePickerFragment extends DialogFragment {
        @NonNull
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
            dpd.setButton(DatePickerDialog.BUTTON_NEGATIVE, getString(R.string.cancel), dpd);
            dpd.setButton(DatePickerDialog.BUTTON_POSITIVE, getString(R.string.choice), dpd);

            return dpd;

        }
    }
}
