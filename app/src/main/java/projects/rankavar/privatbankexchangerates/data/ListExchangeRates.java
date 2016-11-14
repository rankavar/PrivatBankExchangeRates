package projects.rankavar.privatbankexchangerates.data;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.util.List;

/**
 * Created by furch on 28.10.2016.
 */
public class ListExchangeRates {
    @SerializedName("date")
    @Expose
    private String date;
    @SerializedName("bank")
    @Expose
    private String bank;
    @SerializedName("baseCurrency")
    @Expose
    private int baseCurrency;
    @SerializedName("baseCurrencyLit")
    @Expose
    private String baseCurrencyLit;
    @SerializedName("exchangeRate")
    @Expose
    private List<ExchangeRate> exchangeRate;

    public List<ExchangeRate> getExchangeRate() {
        return exchangeRate;
    }
}
