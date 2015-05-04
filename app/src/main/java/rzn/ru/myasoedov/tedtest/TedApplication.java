package rzn.ru.myasoedov.tedtest;

import android.app.Application;
import android.content.Context;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;

import java.util.List;

import retrofit.RestAdapter;
import rzn.ru.myasoedov.tedtest.dto.TalkInfo;
import rzn.ru.myasoedov.tedtest.service.TedService;

/**
 * Created by grisha on 03.05.15.
 */
public class TedApplication extends Application {

    private static TedService service;
    private static Context context;

    @Override
    public void onCreate() {
        super.onCreate();
        context = getApplicationContext();
        RestAdapter restAdapter = new RestAdapter.Builder()
                .setEndpoint(BuildConfig.TED_API)
                .build();

        service = restAdapter.create(TedService.class);
    }

    public static TedService getService() {
        return service;
    }

    public static boolean isConnectedWifi(){
        ConnectivityManager cm = (ConnectivityManager) context.getSystemService(Context.CONNECTIVITY_SERVICE);
        NetworkInfo info = cm.getActiveNetworkInfo();
        return (info != null && info.isConnected() && info.getType() == ConnectivityManager.TYPE_WIFI);
    }

}
