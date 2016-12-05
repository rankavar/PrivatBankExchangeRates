package projects.rankavar.privatbankexchangerates.data.pojos;

/**
 * Created by furch on 15.10.2016.
 */
import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;


public class ExchangeRate {

    @SerializedName("baseCurrency")
    @Expose
    private String baseCurrency;
    @SerializedName("currency")
    @Expose
    private String currency;
    @SerializedName("saleRateNB")
    @Expose
    private Double saleRateNB;
    @SerializedName("purchaseRateNB")
    @Expose
    private Double purchaseRateNB;
    @SerializedName("saleRate")
    @Expose
    private Double saleRate;
    @SerializedName("purchaseRate")
    @Expose
    private Double purchaseRate;


    public String getCurrency() {
        return currency;
    }


    /**
     *
     * @return
     * The saleRateNB
     */
    public Double getSaleRateNB() {
        return saleRateNB;
    }



    /**
     *
     * @return
     * The purchaseRateNB
     */
    public Double getPurchaseRateNB() {
        return purchaseRateNB;
    }



    /**
     *
     * @return
     * The saleRate
     */
    public Double getSaleRate() {
        return saleRate;
    }



    /**
     *
     * @return
     * The purchaseRate
     */
    public Double getPurchaseRate() {
        return purchaseRate;
    }



}