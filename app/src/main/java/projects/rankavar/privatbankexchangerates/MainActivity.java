package projects.rankavar.privatbankexchangerates;

import android.app.Activity;
import android.app.DatePickerDialog;
import android.app.Dialog;
import android.content.Context;
import android.content.DialogInterface;
import android.os.Build;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.ProgressBar;
import android.widget.Toast;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;

import butterknife.BindView;
import butterknife.ButterKnife;
import projects.rankavar.privatbankexchangerates.API.NetworkAPI;
import projects.rankavar.privatbankexchangerates.controller.Controller;
import projects.rankavar.privatbankexchangerates.controller.ControllerInteractor;
import projects.rankavar.privatbankexchangerates.data.DataForShow;
import projects.rankavar.privatbankexchangerates.myAdapter.ExpandebleRates;
import projects.rankavar.privatbankexchangerates.myAdapter.HeaderRecyclerButtonsClick;

public class MainActivity extends AppCompatActivity implements HeaderRecyclerButtonsClick {

    //variables for saving information request and chosen date
    private static final String ARCHIVERQ = "ARCHIVERQ";
    private static final String TODAYRQ = "TODAYRQ";
    private static final String DAY = "DAY";
    private static final String MONTH = "MONTH";
    private static final String YEAR = "YEAR";

    private int myYear = 2015;
    private int myMonth = 10;
    private int myDay = 5;
    private int checkDate ;
    private boolean archiveRequestInWork = false;
    private boolean todayRequestInWork = false;

    private boolean isNew = false;

//    private boolean isRotate = false;
//    private boolean isResults = false;
//    private int currentVersionOs ;

    private String chosenDate = myDay+"."+myMonth+"."+myYear;
    private RecyclerView recyclerView;
    private ExpandebleRates expandAdapter;
    Context cont ;
    @BindView(R.id.progressBar)
    ProgressBar progressBar;
    private NetworkAPI networkAPI;
    private ControllerInteractor controller;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_archive_rates);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        networkAPI = ((NetworkAplication)getApplication()).getNetworkAPI();
        controller = new Controller(this,networkAPI);
        if(savedInstanceState!=null){
            archiveRequestInWork = savedInstanceState.getBoolean(ARCHIVERQ);
            todayRequestInWork = savedInstanceState.getBoolean(TODAYRQ);
            myDay = savedInstanceState.getInt(DAY);
            myMonth = savedInstanceState.getInt(MONTH);
            myYear = savedInstanceState.getInt(YEAR);
//            isRotate = savedInstanceState.getBoolean(ROTATE);
//            isResults = savedInstanceState.getBoolean(SHOW_RESULTS);
        }
        ButterKnife.bind(this);
 //       currentVersionOs = Build.VERSION.SDK_INT;
        cont = this;
        Activity activity = this;
        recyclerView = (RecyclerView)findViewById(R.id.recycler);
        expandAdapter = new ExpandebleRates(new DataForShow(),activity);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        recyclerView.setAdapter(expandAdapter);
    }

    @Override
    protected void onSaveInstanceState(Bundle outState) {
        super.onSaveInstanceState(outState);
        outState.putBoolean(TODAYRQ, todayRequestInWork);
        outState.putBoolean(ARCHIVERQ,archiveRequestInWork);
        outState.putInt(DAY,myDay);
        outState.putInt(MONTH,myMonth);
        outState.putInt(YEAR,myYear);
//        outState.putBoolean(ROTATE,true);
     //   outState.putBoolean(SHOW_RESULTS,isResults);
    }

    @Override
    public void getArchiveRateButton() {
        isNew = true;
        getArchiveRate();

    }
    public void getArchiveRate(){
        if(isNew){
            archiveRequestInWork = false;
            networkAPI.clearCache();
        }else {
//            if(!isRotate) {
//                if(!isResults){
//                    archiveRequestInWork = false;
//                    networkAPI.clearCache();
//                }
//            }
        }

        if(archiveRequestInWork) {
            controller.loadArchiveRate(chosenDate);
        }else {
            getDate();
        }
        isNew = false;
    }
    @Override
    public void getTodayRateButton() {
        isNew = true;
        getTodayRate();
    }


    public void getTodayRate(){
        if(isNew){
            todayRequestInWork = false;
            networkAPI.clearCache();
        }else{
//            if(!isRotate){
//                if(!isResults){
//                    todayRequestInWork = false;
//                    networkAPI.clearCache();
//                }
//            }
        }
        controller.loadCurrentRate();
        todayRequestInWork = true;
        archiveRequestInWork = false;
        isNew = false;
    }
    @Override
    protected void onPause() {
        super.onPause();
        controller.unSubscribe();

    }

    @Override
    protected void onResume() {
        super.onResume();
        if(todayRequestInWork)
            getTodayRate();
        if(archiveRequestInWork)
            getArchiveRate();
    }
    public void showResults(DataForShow data){
        progressBar.setVisibility(View.GONE);
   //     isResults = true;

        expandAdapter.notifyDataSetChanged();
        if(archiveRequestInWork){
            expandAdapter.setNewData(data,cont.getString(R.string.app_name)+" "+myDay+"."+myMonth+"."+myYear);
        }else{
            expandAdapter.setNewData(data,cont.getString(R.string.exchange_rate_today));
        }
    //    isRotate = false;

    }
    public void showRqInProcess(){

        progressBar.setVisibility(View.VISIBLE);
    }
    public void showRqFailed(Throwable throwable){
        archiveRequestInWork = false;
        todayRequestInWork = false;
        progressBar.setVisibility(View.GONE);
        networkAPI.clearCache();
        Toast.makeText(cont,cont.getString(R.string.error_bad_request)
                ,Toast.LENGTH_SHORT).show();
    }

    protected void getDate(){
//        DatePickerDialog.OnDateSetListener dateSetListener = new DatePickerDialog.OnDateSetListener() {
//            @Override
//            public void onDateSet(DatePicker view, int year, int monthOfYear, int dayOfMonth) {
//                myYear = year;
//                myMonth = monthOfYear;
//                myDay = dayOfMonth;
//                DateFormat sdf = new SimpleDateFormat("yyyy");
//                String date = sdf.format(new Date());
//                checkDate = Integer.valueOf(date)- myYear;
//                if(checkDate>4){
//                    Toast.makeText(cont,"Выбрана неверная дата",Toast.LENGTH_SHORT).show();
//                    getDate();
//                }else {
//                        myMonth = myMonth +1;
//                        chosenDate = myDay+"."+(myMonth)+"."+myYear;
//                    controller.loadArchiveRate(chosenDate);
//                    archiveRequestInWork = true;
//                    todayRequestInWork = false;
//                }
//            }
//        };
        final DatePickerDialog dpd = new DatePickerDialog(cont,R.style.DialogTheme,null,myYear,myMonth,myDay);
        DialogInterface.OnClickListener positivelistener = new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                myYear = dpd.getDatePicker().getYear();
                myMonth = dpd.getDatePicker().getMonth();
                myDay = dpd.getDatePicker().getDayOfMonth();
                DateFormat sdf = new SimpleDateFormat("yyyy");
                String date = sdf.format(new Date());
                checkDate = Integer.valueOf(date)- myYear;
                if(checkDate>4){
                    Toast.makeText(cont,"Выбрана неверная дата",Toast.LENGTH_SHORT).show();
                    getDate();
                }else {
                    myMonth = myMonth +1;
                    chosenDate = myDay+"."+(myMonth)+"."+myYear;
                    controller.loadArchiveRate(chosenDate);
                    myMonth = myMonth -1;
                    archiveRequestInWork = true;
                    todayRequestInWork = false;
                }

            }
        };
        dpd.setButton(Dialog.BUTTON_POSITIVE, cont.getString(R.string.positive_dialog_button),positivelistener);
        dpd.setButton(Dialog.BUTTON_NEGATIVE,cont.getString(R.string.negative_dialog_button), new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                dpd.dismiss();
            }
        });
        final Calendar c = Calendar.getInstance();
        c.set(c.get(Calendar.YEAR),c.get(Calendar.MONTH),c.get(Calendar.DAY_OF_MONTH)-5);
        dpd.getDatePicker().setMaxDate(c.getTimeInMillis());
        c.set(c.get(Calendar.YEAR) - 4,c.get(Calendar.MONTH),c.get(Calendar.DAY_OF_MONTH)+5);
        dpd.getDatePicker().setMinDate(c.getTimeInMillis());

        dpd.show();
    }
    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_archive_rates, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
//        if (id == R.id.action_settings) {
//            return true;
//        }
        if(id == R.id.hint_setting){
            Toast.makeText(cont,cont.getString(R.string.hint_button_toast),Toast.LENGTH_SHORT).show();
        }

        return super.onOptionsItemSelected(item);
    }


}
