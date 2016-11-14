package projects.rankavar.privatbankexchangerates;

import android.app.Application;

import projects.rankavar.privatbankexchangerates.API.NetworkAPI;

/**
 * Created by furch on 18.10.2016.
 */
public class NetworkAplication extends Application {
    private NetworkAPI networkAPI;

    @Override
    public void onCreate() {
        super.onCreate();
        networkAPI = new NetworkAPI();
    }
    public NetworkAPI getNetworkAPI(){
        return networkAPI;
    }
}
