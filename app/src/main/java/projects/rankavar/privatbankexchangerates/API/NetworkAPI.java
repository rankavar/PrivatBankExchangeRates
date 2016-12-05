package projects.rankavar.privatbankexchangerates.API;

import android.content.Context;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.support.v4.util.LruCache;
import okhttp3.OkHttpClient;
import okhttp3.logging.HttpLoggingInterceptor;
import retrofit2.Retrofit;
import retrofit2.adapter.rxjava.RxJavaCallAdapterFactory;
import retrofit2.converter.gson.GsonConverterFactory;
import rx.Observable;
import rx.android.schedulers.AndroidSchedulers;
import rx.schedulers.Schedulers;

/**
 * Class for making requests to PrivatBank API
 * Created by furch on 18.10.2016.
 */
public class NetworkAPI {

    private static String baseurl = "https://api.privatbank.ua/p24api/";
    // Interface with method to downloading data
    private NetworkData networkPrivatData;
    public NetworkAPI(){
        this(baseurl);
    }

    public NetworkAPI(String baseurl){
        HttpLoggingInterceptor log = new HttpLoggingInterceptor();
        log.setLevel(HttpLoggingInterceptor.Level.BASIC);
        OkHttpClient http = new OkHttpClient.Builder()
                .addInterceptor(log)
                .build();
        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl(baseurl)
                .addConverterFactory(GsonConverterFactory.create())
                .addCallAdapterFactory(RxJavaCallAdapterFactory.create())
                .client(http)
                .build();
        networkPrivatData = retrofit.create(NetworkData.class);

    }
    public NetworkData getApi(){
        return networkPrivatData;
    }


    public  boolean isOnline(Context context)
    {
        ConnectivityManager cm =
                (ConnectivityManager) context.getSystemService(Context.CONNECTIVITY_SERVICE);
        NetworkInfo netInfo = cm.getActiveNetworkInfo();
        if (netInfo != null && netInfo.isConnected())
        {
            return true;
        }
        return false;
    }

}
