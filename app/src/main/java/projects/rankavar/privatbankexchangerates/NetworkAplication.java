package projects.rankavar.privatbankexchangerates;

import android.app.Application;

import projects.rankavar.privatbankexchangerates.API.NetworkAPI;
import projects.rankavar.privatbankexchangerates.controller.Controller;
import projects.rankavar.privatbankexchangerates.controller.ControllerInteractor;

/**
 * Created by furch on 18.10.2016.
 */
public class NetworkAplication extends Application {

    private ControllerInteractor controllerInteractor;
    @Override
    public void onCreate() {
        super.onCreate();
        controllerInteractor = new Controller(getApplicationContext());
    }
    public ControllerInteractor getControllerInteractor(){
        return controllerInteractor;
    }
}
