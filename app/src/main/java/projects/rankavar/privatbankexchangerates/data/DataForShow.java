package projects.rankavar.privatbankexchangerates.data;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by furch on 27.10.2016.
 */
public class DataForShow {
    private List<String> currency = new ArrayList<>();
    private List<Double> buy = new ArrayList<>();;
    private List<Double> sale = new ArrayList<>();;
    public void addCurrency(String nameCurrency){
        currency.add(nameCurrency);
    }
    public void addBuy(double price){
        buy.add(price);
    }
    public void addSale(double price){
        sale.add(price);
    }

    public List<Double> getBuy() {
        return buy;
    }

    public List<String> getCurrency() {
        return currency;
    }

    public List<Double> getSale() {
        return sale;
    }
}
