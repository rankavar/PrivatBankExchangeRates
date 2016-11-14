package projects.rankavar.privatbankexchangerates.API;

/**
 * Created by furch on 18.10.2016.
 */
import java.util.List;

import projects.rankavar.privatbankexchangerates.data.ExchangeSimpleRate;
import projects.rankavar.privatbankexchangerates.data.ListExchangeRates;
import retrofit2.http.GET;
import retrofit2.http.Query;
import rx.Observable;

public interface NetworkData {

    @GET("exchange_rates?json")
    Observable<ListExchangeRates> privateArchiveRates(
                    @Query("date") String date);
    @GET("pubinfo?json&exchange&coursid=5")
    Observable<List<ExchangeSimpleRate>> privateCurrentRates();
}
