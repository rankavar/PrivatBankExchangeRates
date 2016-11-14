package projects.rankavar.privatbankexchangerates.controller;

/**
 * Created by furch on 18.10.2016.
 */
public interface ControllerInteractor {
    void loadArchiveRate(String date);
    void unSubscribe();
    void loadCurrentRate();
}
