package gz.rmbgysz.ahitat;

import android.annotation.TargetApi;
import android.app.DatePickerDialog;
import android.app.Dialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import com.getbase.floatingactionbutton.FloatingActionButton;
import com.getbase.floatingactionbutton.FloatingActionsMenu;

import android.support.annotation.NonNull;
import android.support.annotation.RequiresApi;
import android.support.design.widget.Snackbar;

import android.support.v4.app.DialogFragment;
import android.support.v7.app.AlertDialog;
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
import android.graphics.Typeface;

import java.text.ParseException;
import java.util.Locale;


public class MainAppActivity extends AppCompatActivity
        implements NavigationView.OnNavigationItemSelectedListener, DatePickerDialog.OnDateSetListener, ShareTypeListenerInterface {

    TextView actual_date, delelott, delutan, amTitle, amVerse, amDailyDevotion, amDailyDevotionAuthor, pmTitle, pmVerse, pmDailyDevotion, pmDailyDevotionAuthor;

    public static final int FAVORITES_REQUEST_CODE = 0xe23;
    public static final int AM_DAILYDEVOTION = 0;
    public static final int PM_DAILYDEVOTION = 1;
    private DrawerLayout mDrawerLayout;

    private int originalBibHeight;
    private int originalImaHeight;

    private String A_BOLD= "merriweather_bold.ttf";
    private String A_REGULAR= "merriweather_regular.ttf";
    private String O_ITALIC= "merriweather_italic.ttf";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setTheme(R.style.MainAppTheme);
        setContentView(R.layout.activity_main_app);
        setTitle(R.string.main_app__activity_title);
        getInitalHeights();

        actual_date=(TextView)findViewById(R.id.actual_date);
        delelott=(TextView)findViewById(R.id.delelott);
        delutan=(TextView)findViewById(R.id.delutan);
        amTitle=(TextView)findViewById(R.id.amTitle);
        amVerse=(TextView)findViewById(R.id.amVerse);
        amDailyDevotion=(TextView)findViewById(R.id.amDailyDevotion);
        amDailyDevotionAuthor=(TextView)findViewById(R.id.amDailyDevotionAuthor);
        pmTitle=(TextView)findViewById(R.id.pmTitle);
        pmVerse=(TextView)findViewById(R.id.pmVerse);
        pmDailyDevotion=(TextView)findViewById(R.id.pmDailyDevotion);
        pmDailyDevotionAuthor=(TextView)findViewById(R.id.pmDailyDevotionAuthor);

        actual_date.setTypeface(Typeface.createFromAsset(getAssets(), A_REGULAR));
        delelott.setTypeface(Typeface.createFromAsset(getAssets(), A_REGULAR));
        delutan.setTypeface(Typeface.createFromAsset(getAssets(), A_REGULAR));
        amTitle.setTypeface(Typeface.createFromAsset(getAssets(), A_BOLD));
        amVerse.setTypeface(Typeface.createFromAsset(getAssets(), O_ITALIC));
        amDailyDevotion.setTypeface(Typeface.createFromAsset(getAssets(), A_REGULAR));
        amDailyDevotionAuthor.setTypeface(Typeface.createFromAsset(getAssets(), O_ITALIC));
        pmTitle.setTypeface(Typeface.createFromAsset(getAssets(), A_BOLD));
        pmVerse.setTypeface(Typeface.createFromAsset(getAssets(), O_ITALIC));
        pmDailyDevotion.setTypeface(Typeface.createFromAsset(getAssets(), A_REGULAR));
        pmDailyDevotionAuthor.setTypeface(Typeface.createFromAsset(getAssets(), O_ITALIC));

        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        initFloatingActionButtonMenu();

        refresTextViews(DatabaseHelper.getInstance(this).
                getDailyDevotionByDate(DateManager.getInstance().getDateString()));

        mDrawerLayout = (DrawerLayout) findViewById(R.id.drawer_layout);

        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(
                this, mDrawerLayout, toolbar, R.string.navigation_drawer_open, R.string.navigation_drawer_close);
        mDrawerLayout.addDrawerListener(toggle);
        toggle.syncState();

        NavigationView navigationView = (NavigationView) findViewById(R.id.nav_view);
        navigationView.setNavigationItemSelectedListener(this);

    }

    public void refreshActivity() {
        refresTextViews(DatabaseHelper.getInstance(this).
                getDailyDevotionByDate(DateManager.getInstance().getDateString()));
    }

    private void getInitalHeights() {
        TextView bibleLecture = (TextView)findViewById(R.id.bibleLecture);
        TextView prayerTemp = (TextView)findViewById(R.id.prayer);

        originalBibHeight = bibleLecture.getLayoutParams().height;
        originalImaHeight = prayerTemp.getLayoutParams().height;

    }

    /*
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
    */

    private void refresTextViews(DailyDevotion devItem) {
        if (devItem == null) {
            fillTextViewsWithEmptyText();
            Toast.makeText(this, getString(R.string.notfounddailydevotion) + " (" +
                    DateManager.getInstance().getDateString() + ")", Toast.LENGTH_SHORT).show();
        } else {
            setTextViews(DateManager.getInstance().getFormattedDateWithDayName(this), devItem);
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

        setMarginsByItemType(item, bibleLecture, prayer, bParams, iParams);

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

    private void setMarginsByItemType(DailyDevotion item, TextView bibleLecture, TextView prayer, LinearLayout.LayoutParams bParams, LinearLayout.LayoutParams iParams) {
        if (item.getBibleLecture().isEmpty() && (bibleLecture != null) && (prayer != null) && (bParams != null) && (iParams != null)) {
            bParams.height = 0;
            bParams.setMargins(0,0,0,0);
            iParams.height = 0;
            iParams.setMargins(0,0,0,0);
        }
        else {
            assert bParams != null;
            bParams.height = originalBibHeight;
            // FIXME:egyelore nem talaltam meg  hogyan lehet lekerdezni,
            // megneztem a designerben es ott 30-ra van beallitva ha ott valtozik itt is hozza kell nyulni
            bParams.setMargins(0,30,0,30);
            assert iParams != null;
            iParams.height = originalImaHeight;
            iParams.setMargins(0,30,0,30);
        }
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

        if (id == R.id.go_to_bible) {
            Snackbar.make(findViewById(R.id.content_main_app), "a b ", Snackbar.LENGTH_LONG)
                    .setAction("clicked", null)
                    .show();
            Intent browserIntent = new Intent(Intent.ACTION_VIEW, Uri.parse("http://www.abibliamindenkie.hu"));
            startActivity(browserIntent);
        }


            boolean ret = DatabaseHelper.getInstance(this).
                    insertFavoriteIfNotExist(DateManager.getInstance().getDateString());

            if (ret) {
                if (id == R.id.add_to_favorites) {
                    Snackbar.make(findViewById(R.id.content_main_app), "Kedvencekhez hozzáadva: " + DateManager.getInstance().getFormattedDateWithDayName(this), Snackbar.LENGTH_LONG)
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
            DateManager.getInstance().setActualDate();
            refresTextViews(DatabaseHelper.getInstance(this).
                    getDailyDevotionByDate(DateManager.getInstance().getDateString()));

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

        } else if (id == R.id.nav_favorites) {
            Intent intent = new Intent(this, FavoritesActivity.class);
            startActivityForResult(intent, FAVORITES_REQUEST_CODE);
        } else if (id == R.id.nav_share) {
            DialogFragment newChoice = new ChoiceDialogFragment();
            newChoice.show(getSupportFragmentManager(), "choseDailyDevotionType");
        } else if (id == R.id.nav_help) {
            Intent intent = new Intent(this, HelpActivity.class);
            startActivity(intent);
        } else if (id == R.id.nav_info) {
            Intent intent = new Intent(this, InfoActivity.class);
            startActivity(intent);
        }

        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        drawer.closeDrawer(GravityCompat.START);
        return true;
    }

    @NonNull
    private Bundle getBundleForDatePicker() throws ParseException {
        Bundle bundle = new Bundle();
        bundle.putInt("year",DateManager.getInstance().getYear());
        bundle.putInt("month",DateManager.getInstance().getMonth());
        bundle.putInt("day",DateManager.getInstance().getDay());
        bundle.putLong("minDate", DateManager.getInstance().getMinDate());
        bundle.putLong("maxDate", DateManager.getInstance().getMaxDate());
        return bundle;
    }

    private void initFloatingActionButtonMenu() {
        final FloatingActionsMenu floatingActionsMenu = (FloatingActionsMenu) findViewById(R.id.fab_menu);
        final FloatingActionButton goBack = (FloatingActionButton) findViewById(R.id.action_go_back);
        goBack.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                DateManager.getInstance().stepToPreviousDay();
                refresTextViews(DatabaseHelper.getInstance(MainAppActivity.this).
                        getDailyDevotionByDate(DateManager.getInstance().getDateString()));
                floatingActionsMenu.collapse();
            }
        });
        final FloatingActionButton goForward = (FloatingActionButton) findViewById(R.id.action_go_forward);
        goForward.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                DateManager.getInstance().stepToNextDay();
                refresTextViews(DatabaseHelper.getInstance(MainAppActivity.this).
                        getDailyDevotionByDate(DateManager.getInstance().getDateString()));
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
            DateManager.getInstance().setDate(year, month , dayOfMonth);
            //getItemFromMap(texts_map);
            refresTextViews(DatabaseHelper.getInstance(this).
                    getDailyDevotionByDate(DateManager.getInstance().getDateString()));
        } catch (ParseException e) {
            Toast.makeText(view.getContext(), R.string.datesettingerror, Toast.LENGTH_SHORT).show();
        }
    }


    public static class DatePickerFragment extends DialogFragment {
        @TargetApi(Build.VERSION_CODES.JELLY_BEAN_MR1)
        @NonNull
        @RequiresApi(api = Build.VERSION_CODES.JELLY_BEAN)
        @Override

        public Dialog onCreateDialog(Bundle savedInstanceState) {
            //lokalizacio
            Locale.setDefault(new Locale("HU"));

            int year = getArguments().getInt("year");
            int month = getArguments().getInt("month");
            int day = getArguments().getInt("day");
            long minDate = getArguments().getLong("minDate");
            long maxDate = getArguments().getLong("maxDate");
            DatePickerDialog dpd = new DatePickerDialog(getActivity(),R.style.DatepickerTheme,
                    (DatePickerDialog.OnDateSetListener)
                            getActivity(), year, month, day);

            dpd.getDatePicker().setMinDate(minDate);
            dpd.getDatePicker().setMaxDate(maxDate);
            dpd.setButton(DatePickerDialog.BUTTON_NEGATIVE, getString(R.string.cancel), dpd);
            dpd.setButton(DatePickerDialog.BUTTON_POSITIVE, getString(R.string.ok), dpd);

            return dpd;
        }
    }


    /*
    public static Spanned fromHtml(String html){
        Spanned result;
        if (android.os.Build.VERSION.SDK_INT >= android.os.Build.VERSION_CODES.N) {
            result = Html.fromHtml(html,Html.FROM_HTML_MODE_LEGACY);
        } else {
            result = Html.fromHtml(html);
        }
        return result;
    }
    */

    @RequiresApi(api = Build.VERSION_CODES.JELLY_BEAN)
    @Override
    public void gotPositiveResultFromChoiceDialog(int choosedId) {
        prepareTextForSharing(choosedId);
    }

    @RequiresApi(api = Build.VERSION_CODES.JELLY_BEAN)
    private void prepareTextForSharing(int type) {
        //DailyDevotion actualItem = (DailyDevotion) texts_map.get(dateManager.getDateString());
        DailyDevotion actualItem = DatabaseHelper.getInstance(MainAppActivity.this).
                getDailyDevotionByDate(DateManager.getInstance().getDateString());
        String  shareString =  prepareStringForSharing(type, actualItem);

        if (!shareString.isEmpty()) {
            Intent sharingIntent = new Intent(Intent.ACTION_SEND);
            sharingIntent.setType("text/html");

            sharingIntent.putExtra(android.content.Intent.EXTRA_TEXT, shareString);

            if (sharingIntent.resolveActivity(getPackageManager()) != null)
                startActivity(Intent.createChooser(sharingIntent, getString(R.string.sharedtitle)));
            else {
                Toast.makeText(this, getString(R.string.nosharedapp), Toast.LENGTH_SHORT).show();
            }
        }
    }

    private String prepareStringForSharing(int type, DailyDevotion actualItem) {
        String returnString = "";
        if (type == AM_DAILYDEVOTION) {
            returnString =  DateManager.getInstance().
                    getFormattedDateWithDayName(MainAppActivity.this) + "\n\n" +
                            actualItem.getAmTitle() + "\n"+
                            actualItem.getAmVerse() + "\n\n" +
                            actualItem.getAmDailyDevotion() + "\n\n" +
                            actualItem.getAmDailyDevotionAuthor();

        }
        else if (type == PM_DAILYDEVOTION) {
            returnString =  DateManager.getInstance().
                    getFormattedDateWithDayName(MainAppActivity.this) + "\n\n" +
                    actualItem.getPmTitle() + "\n"+
                    actualItem.getPmVerse() + "\n\n" +
                    actualItem.getPmDailyDevotion() + "\n\n" +
                    actualItem.getPmDailyDevotionAuthor();
        }
        return returnString;
    }

    public static class ChoiceDialogFragment extends DialogFragment {
        @RequiresApi(api = Build.VERSION_CODES.JELLY_BEAN)

        ShareTypeListenerInterface mListener;

        @Override
        public void onAttach(Context context) {
            super.onAttach(context);
            try {
                mListener = (ShareTypeListenerInterface) getActivity();
            } catch (ClassCastException e) {
                throw new ClassCastException(getActivity().toString()
                        + " must implement ShareTypeListenerInterface");
            }
        }

        @NonNull
        public Dialog onCreateDialog(Bundle savedInstanceState) {

            AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());
            final int[] choosed = {-1};
            // Set the dialog title
            builder.setTitle(R.string.sharetypetitle);

            builder.setSingleChoiceItems(R.array.daily_devotion_choice_items, choosed[0],
                new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        choosed[0] = which;
                    }
            });

            // Set the action buttons
            builder.setPositiveButton(R.string.ok, new DialogInterface.OnClickListener() {
                @Override
                public void onClick(DialogInterface dialog, int id) {
                    mListener.gotPositiveResultFromChoiceDialog(choosed[0]);
                }
            });

            builder.setNegativeButton(R.string.cancel, new DialogInterface.OnClickListener() {
                @Override
                public void onClick(DialogInterface dialog, int id) {
                }
            });

            return builder.create();
        }
    }
}
