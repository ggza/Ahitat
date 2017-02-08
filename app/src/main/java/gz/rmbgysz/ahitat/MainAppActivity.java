package gz.rmbgysz.ahitat;

import android.app.DatePickerDialog;
import android.app.Dialog;
import android.app.FragmentManager;
import android.app.FragmentTransaction;
import android.content.Intent;
import android.icu.util.Calendar;
import android.os.Build;
import android.os.Bundle;
//import android.support.design.widget.FloatingActionButton;
import com.getbase.floatingactionbutton.FloatingActionButton;
import com.getbase.floatingactionbutton.FloatingActionsMenu;

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
import android.widget.TextView;
import android.widget.DatePicker;

/*
DatePicker
https://android--examples.blogspot.hu/2015/05/how-to-use-datepickerdialog-in-android.html
https://www.codota.com/android/methods/android.widget.DatePicker/setMaxDate
https://inducesmile.com/android/android-timepicker-and-datepicker-examples/
http://stackoverflow.com/questions/27225815/android-how-to-show-datepicker-in-fragment
*/

public class MainAppActivity extends AppCompatActivity
        implements NavigationView.OnNavigationItemSelectedListener {

    private FragmentManager fragmentManager = getFragmentManager();
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

        //FIXME: folyt kov: https://blog.stylingandroid.com/floating-action-button-part-3/
        //https://blenderviking.github.io/2016/11/26/Android-How-to-build-an-Android-Floating-Action-Button/

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
        mDrawerLayout.setDrawerListener(toggle);
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

/*  FIXME: menu kikapcsolas
    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_settings) {
            return true;
        }

        return super.onOptionsItemSelected(item);
    }
*/


    @Override
    public boolean onNavigationItemSelected(MenuItem item) {
        // Handle navigation view item clicks here.
        int id = item.getItemId();

        if (id == R.id.nav_actual_lecture) {

            Snackbar.make(findViewById(R.id.content_main_app), "Mai áhitat", Snackbar.LENGTH_LONG)
                    .setAction("clicked", null)
                    .show();

        } else if (id == R.id.nav_search_by_date) {

            //Snackbar.make(findViewById(R.id.content_main_app), "Keresés dátum szerint", Snackbar.LENGTH_LONG)
            //        .setAction("clicked", null)
            //        .show();
            DatePickerFragment mDatePicker = new DatePickerFragment();
            FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();
            //mDatePicker.show(fragmentTransaction, "Select date");


        } else if (id == R.id.nav_search) {

            Snackbar.make(findViewById(R.id.content_main_app), "Keresés", Snackbar.LENGTH_LONG)
                    .setAction("clicked", null)
                    .show();

        } else if (id == R.id.nav_favorites) {

            Intent intent = new Intent(this, FavoritesActivity.class);
            startActivity(intent);

        } else if (id == R.id.nav_share) {

            Snackbar.make(findViewById(R.id.content_main_app), "Megosztás", Snackbar.LENGTH_LONG)
                    .setAction("clicked", null)
                    .show();
        }

        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        drawer.closeDrawer(GravityCompat.START);
        return true;
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

    public static class DatePickerFragment extends DialogFragment implements DatePickerDialog.OnDateSetListener {
        @RequiresApi(api = Build.VERSION_CODES.N)
        @Override
        public Dialog onCreateDialog(Bundle savedInstanceState) {
            final Calendar c = Calendar.getInstance();
            int year = c.get(Calendar.YEAR);
            int month = c.get(Calendar.MONTH);
            int day = c.get(Calendar.DAY_OF_MONTH);
            return new DatePickerDialog(getActivity(), this, year, month, day);
        }
        public void onDateSet(DatePicker view, int year, int month, int day) {
            //displayCurrentTime.setText("Selected date: " + String.valueOf(year) + " - " + String.valueOf(month) + " - " + String.valueOf(day));
        }
    }
}
