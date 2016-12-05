package projects.rankavar.privatbankexchangerates.controller;

/**
 * Created by furch on 05.12.2016.
 */
public interface ControllerInteractor {
    void getTodayRates();
    void getArchiveRates();
    void getChartsData();
    void setTodayFragment();
    void setArchiveFragment();
    void setChartsFragment();
}
