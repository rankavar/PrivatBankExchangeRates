package projects.rankavar.privatbankexchangerates.activities;

import android.content.Intent;
import android.os.Bundle;
import android.support.design.widget.NavigationView;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;

import butterknife.BindView;
import butterknife.ButterKnife;
import projects.rankavar.privatbankexchangerates.R;
import projects.rankavar.privatbankexchangerates.fragments.ArchiveFragment;
import projects.rankavar.privatbankexchangerates.fragments.ChartsFragment;
import projects.rankavar.privatbankexchangerates.fragments.TodayFragment;

public class MainActivity extends AppCompatActivity
        implements NavigationView.OnNavigationItemSelectedListener {

    @BindView(R.id.drawer_layout)
    DrawerLayout drawer;
    @BindView(R.id.nav_view)  NavigationView navigationView;
    @BindView(R.id.toolbar) Toolbar toolbar;
    private boolean isResume = false;
    private static final String RESUME = "RESUME";


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        ButterKnife.bind(this);
        setSupportActionBar(toolbar);
        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(
                this, drawer, toolbar, R.string.navigation_drawer_open, R.string.navigation_drawer_close);
        drawer.addDrawerListener(toggle);
        toggle.syncState();
        navigationView.setNavigationItemSelectedListener(this);
        if(!isResume){
            openTodayFragment();
        }
    }
    @Override
    protected void onSaveInstanceState(Bundle outState) {
        super.onSaveInstanceState(outState);
        outState.putBoolean(RESUME,isResume);
    }
    @Override
    protected void onPause() {
        super.onPause();
        isResume = true;
    }


    @Override
    protected void onResumeFragments() {
        super.onResumeFragments();
        isResume = false;

    }

    @Override
    public void onBackPressed() {
        if (drawer.isDrawerOpen(GravityCompat.START)) {
            drawer.closeDrawer(GravityCompat.START);
        } else {
            super.onBackPressed();
            if(getSupportFragmentManager().getBackStackEntryCount()==0){
                finish();
            }
        }

    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.main_activity, menu);
        return true;
    }

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

    @SuppressWarnings("StatementWithEmptyBody")
    @Override
    public boolean onNavigationItemSelected(MenuItem item) {
        // Handle navigation view item clicks here.
        int id = item.getItemId();


        switch (id){
            case R.id.nav_settings:{
                Intent intent = new Intent(MainActivity.this,SettingsActivity.class);
                startActivity(intent);
                break;
            }
            case R.id.nav_today:{
                openTodayFragment();
                break;
            }
            case R.id.nav_archive:{
                openArchiveFragment();
                break;
            }
            case R.id.nav_charts:{
                openChartFragment();
                break;
            }
            case R.id.nav_share:{
                break;

            }
        }
        drawer.closeDrawer(GravityCompat.START);
        return true;
    }
    private void openTodayFragment(){
        FragmentManager manager = getSupportFragmentManager();
        FragmentTransaction t = manager.beginTransaction();
        t.replace(R.id.containerFragment, new TodayFragment(), "today").addToBackStack(null).commit();

    }
    private void openArchiveFragment(){
        FragmentManager manager = getSupportFragmentManager();
        FragmentTransaction t = manager.beginTransaction();
        t.replace(R.id.containerFragment,new ArchiveFragment(),"archive").addToBackStack(null).commit();
    }
    private void openChartFragment(){
        FragmentManager manager = getSupportFragmentManager();
        FragmentTransaction t = manager.beginTransaction();
        t.replace(R.id.containerFragment,new ChartsFragment(),"chart").addToBackStack(null).commit();


    }
    public void setItemNavChecked(int position){
        navigationView.setCheckedItem(position);

    }
}
