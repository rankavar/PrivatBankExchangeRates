package projects.rankavar.privatbankexchangerates.controller;

import android.content.Context;
import android.widget.Toast;

import java.util.List;

import projects.rankavar.privatbankexchangerates.API.NetworkAPI;
import projects.rankavar.privatbankexchangerates.ArchiveRatesView;
import projects.rankavar.privatbankexchangerates.R;
import projects.rankavar.privatbankexchangerates.data.DataForShow;
import projects.rankavar.privatbankexchangerates.data.ExchangeRate;
import projects.rankavar.privatbankexchangerates.data.ExchangeSimpleRate;
import projects.rankavar.privatbankexchangerates.data.ListExchangeRates;
import rx.Observable;
import rx.Observer;
import rx.Subscription;

/**
 * Created by furch on 18.10.2016.
 */
public class Controller implements ControllerInteractor {

    private ArchiveRatesView archiveRatesView;
    private NetworkAPI networkAPI;
    private Subscription subscription;
    private Context context;

    public Controller(ArchiveRatesView view, NetworkAPI api){
        this.archiveRatesView = view;
        this.networkAPI = api;
        context = archiveRatesView.getApplicationContext();
    }


    @Override
    public void loadArchiveRate(String date) {
        if(networkAPI.isOnline(context)){
            archiveRatesView.showRqInProcess();
            Observable<ListExchangeRates> informationObservable = (Observable<ListExchangeRates>)
                    networkAPI.getPreparedObservable(networkAPI.getApi().privateArchiveRates(date),ExchangeRate.class,true,true);
            subscription = informationObservable.subscribe(new Observer<ListExchangeRates>() {
                @Override
                public void onCompleted() {

                }

                @Override
                public void onError(Throwable e) {
                    archiveRatesView.showRqFailed(e);

                }

                @Override
                public void onNext(ListExchangeRates list) {
                    List <ExchangeRate> information = list.getExchangeRate();
                    DataForShow data = new DataForShow();
                    for(int i = 0;i<information.size();i++){
                        data.addCurrency(information.get(i).getCurrency());
                        if(information.get(i).getPurchaseRate()!=null){
                            data.addBuy(information.get(i).getPurchaseRate());
                        }else {
                            data.addBuy(information.get(i).getPurchaseRateNB());
                        }

                        if(information.get(i).getSaleRate()!=null){
                            data.addSale(information.get(i).getSaleRate());
                        }else {
                            data.addSale(information.get(i).getSaleRateNB());
                        }
                    }

                    archiveRatesView.showResults(data);


                }
            });
        }else{
            Toast.makeText(context,context.getString(R.string.error_internet_connection),Toast.LENGTH_SHORT).show();
        }


    }

    @Override
    public void unSubscribe() {
        if(subscription!=null && !subscription.isUnsubscribed()){
            subscription.unsubscribe();
        }
    }

    @Override
    public void loadCurrentRate() {
        if(networkAPI.isOnline(context)){
            archiveRatesView.showRqInProcess();
            Observable<List<ExchangeSimpleRate>> informationObservable = (Observable<List<ExchangeSimpleRate>>)
                    networkAPI.getPreparedObservable(networkAPI.getApi().privateCurrentRates(),ExchangeSimpleRate.class,true,true);
            subscription = informationObservable.subscribe(new Observer<List<ExchangeSimpleRate>>() {
                @Override
                public void onCompleted() {

                }

                @Override
                public void onError(Throwable e) {
                    archiveRatesView.showRqFailed(e);

                }

                @Override
                public void onNext(List<ExchangeSimpleRate> information) {
                    DataForShow data = new DataForShow();
                    for(int i = 0;i<information.size();i++){
                        data.addCurrency(information.get(i).getCcy());
                        data.addBuy(Double.valueOf(information.get(i).getBuy()));
                        data.addSale(Double.valueOf(information.get(i).getSale()));
                    }
                    archiveRatesView.showResults(data);


                }
            });
        }else{
            Toast.makeText(context,context.getString(R.string.error_internet_connection),Toast.LENGTH_SHORT).show();
        }


    }
}
