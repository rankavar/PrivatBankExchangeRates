package projects.rankavar.privatbankexchangerates.controller;


import android.content.Context;
import android.widget.Toast;

import java.util.List;

import projects.rankavar.privatbankexchangerates.API.NetworkAPI;
import projects.rankavar.privatbankexchangerates.R;
import projects.rankavar.privatbankexchangerates.data.DataForShow;
import projects.rankavar.privatbankexchangerates.data.pojos.ExchangeSimpleRate;
import projects.rankavar.privatbankexchangerates.fragments.ArchiveFragment;
import projects.rankavar.privatbankexchangerates.fragments.TodayFragment;
import rx.Observable;
import rx.Observer;
import rx.Subscription;
import rx.android.schedulers.AndroidSchedulers;
import rx.schedulers.Schedulers;

/**
 * Created by furch on 18.10.2016.
 */
public class Controller implements ControllerInteractor{
    private Context context;
    private NetworkAPI networkAPI;
    private TodayFragment todayFragment;
    private ArchiveFragment
    public Controller(Context context) {
        this.context = context;
        networkAPI = new NetworkAPI();
    }

    @Override
    public void getTodayRates() {
        if(networkAPI.isOnline(context)){
            Observable<List<ExchangeSimpleRate>> informationObservable = (Observable<List<ExchangeSimpleRate>>)
                    networkAPI.getApi().privateCurrentRates()
                    .subscribeOn(Schedulers.newThread())
                    .observeOn(AndroidSchedulers.mainThread());
            Subscription subscription = informationObservable.subscribe(new Observer<List<ExchangeSimpleRate>>() {
                @Override
                public void onCompleted() {
                }

                @Override
                public void onError(Throwable e) {

                }

                @Override
                public void onNext(List<ExchangeSimpleRate> information) {
                    DataForShow data = new DataForShow();
                    for(int i = 0;i<information.size();i++){
                        data.addCurrency(information.get(i).getCcy());
                        data.addBuy(Double.valueOf(information.get(i).getBuy()));
                        data.addSale(Double.valueOf(information.get(i).getSale()));
                    }
                }
            });
        }else{
            Toast.makeText(context,context.getString(R.string.error_internet_connection),Toast.LENGTH_SHORT).show();
        }


    }


    @Override
    public void getArchiveRates() {

    }

    @Override
    public void getChartsData() {

    }

    @Override
    public void setTodayFragment() {

    }

    @Override
    public void setArchiveFragment() {

    }

    @Override
    public void setChartsFragment() {

    }

    private void showTodayRates(){
        if()
    }
}