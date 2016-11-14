package projects.rankavar.privatbankexchangerates.data;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

/**
 * Created by furch on 20.10.2016.
 */
public class ExchangeSimpleRate {
    @SerializedName("ccy")
    @Expose
    private String ccy;
    @SerializedName("buy")
    @Expose
    private String buy;
    @SerializedName("base_ccy")
    @Expose
    private String base_ccy;
    @SerializedName("sale")
    @Expose
    private String sale;

    public String getCcy() {
        return ccy;
    }



    public String getBuy() {
        return buy;
    }



    public String getSale() {
        return sale;
    }

}