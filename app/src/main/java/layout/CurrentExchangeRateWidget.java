package layout;

import android.appwidget.AppWidgetManager;
import android.appwidget.AppWidgetProvider;
import android.content.ComponentName;
import android.content.Context;
import android.content.Intent;
import android.widget.RemoteViews;
import java.util.List;
import projects.rankavar.privatbankexchangerates.API.NetworkAPI;
import projects.rankavar.privatbankexchangerates.R;
import projects.rankavar.privatbankexchangerates.data.DataForShow;
import projects.rankavar.privatbankexchangerates.data.pojos.ExchangeSimpleRate;
import rx.Observable;
import rx.Observer;
import rx.Subscription;
import rx.android.schedulers.AndroidSchedulers;
import rx.schedulers.Schedulers;

/**
 * Implementation of App Widget functionality.
 *
 */
public class CurrentExchangeRateWidget extends AppWidgetProvider {
    private NetworkAPI networkAPI;
    private DataForShow data;

    /**
     * Method that update widget when Internet connection on
     * @param context
     * @param intent
     */
    @Override
    public void onReceive(Context context, Intent intent) {
        AppWidgetManager appwidget = AppWidgetManager.getInstance(context);
        ComponentName comp = new ComponentName(context,CurrentExchangeRateWidget.class);
        int[] allwidgets = appwidget.getAppWidgetIds(comp);
        if(allwidgets.length !=0){
            onUpdate(context,appwidget,allwidgets);
        }

    }

    /**
     *  Method to load Exchange rate data
     * @param cont
     * @param ids
     * @param appman
     */
    public void loadCurrentRate(Context cont, int[] ids, AppWidgetManager appman) {
        final Context context = cont;
        final int[] appWidgetIds = ids;
        final AppWidgetManager app = appman;
        Observable<List<ExchangeSimpleRate>> informationObservable = networkAPI.getApi().privateCurrentRates().subscribeOn(Schedulers.newThread()).observeOn(AndroidSchedulers.mainThread());
        Subscription subscription = informationObservable.subscribe(new Observer<List<ExchangeSimpleRate>>() {
            @Override
            public void onCompleted() {

            }

            @Override
            public void onError(Throwable e) {

            }

            @Override
            public void onNext(List<ExchangeSimpleRate> information) {
                data = new DataForShow();
                for(int i = 0;i<information.size();i++){
                    data.addCurrency(information.get(i).getCcy());
                    data.addBuy(Double.valueOf(information.get(i).getBuy()));
                    data.addSale(Double.valueOf(information.get(i).getSale()));
                }
                for (int appWidgetId : appWidgetIds) {
                    updateAppWidget(context, app, appWidgetId);
                }
            }
        });
    }

    /**
     * Method to set a new received data
     * @param context
     * @param appWidgetManager
     * @param appWidgetId
     */
      void updateAppWidget(Context context, AppWidgetManager appWidgetManager, int appWidgetId) {
          RemoteViews views = new RemoteViews(context.getPackageName(), R.layout.current_exchange_rate_widget);
          views.setTextViewText(R.id.widget_text_buy1,data.getBuy().get(0)+"");
          views.setTextViewText(R.id.widget_text_sale1,data.getSale().get(0)+"");
          views.setTextViewText(R.id.widget_text_currency1,data.getCurrency().get(0));

          views.setTextViewText(R.id.widget_text_buy2,data.getBuy().get(1)+"");
          views.setTextViewText(R.id.widget_text_sale2,data.getSale().get(1)+"");
          views.setTextViewText(R.id.widget_text_currency2,data.getCurrency().get(1));

          views.setTextViewText(R.id.widget_text_buy3,data.getBuy().get(2)+"");
          views.setTextViewText(R.id.widget_text_sale3,data.getSale().get(2)+"");
          views.setTextViewText(R.id.widget_text_currency3,data.getCurrency().get(2));
          appWidgetManager.updateAppWidget(appWidgetId, views);
    }

    @Override
    public void onUpdate(Context context, AppWidgetManager appWidgetManager, int[] appWidgetIds) {
        // There may be multiple widgets active, so update all of them
        networkAPI = new NetworkAPI();
        if(networkAPI.isOnline(context)) {

            loadCurrentRate(context, appWidgetIds, appWidgetManager);
        }
    }

    @Override
    public void onEnabled(Context context) {
        // Enter relevant functionality for when the first widget is created
    }

    @Override
    public void onDisabled(Context context) {
    }



}

