package rzn.ru.myasoedov.tedtest.loader;

import android.content.AsyncTaskLoader;
import android.content.Context;
import android.os.Bundle;

import java.util.Collections;
import java.util.List;

import rzn.ru.myasoedov.tedtest.TedApplication;
import rzn.ru.myasoedov.tedtest.dto.ResponseWrapper;
import rzn.ru.myasoedov.tedtest.dto.TalkInfo;

/**
 * Created by grisha on 03.05.15.
 */
public class TalkLoader extends AsyncTaskLoader<ResponseWrapper<TalkInfo>> {
    public static final String ID = "id";
    private long id;

    public TalkLoader(Context context, Bundle bundle) {
        super(context);
        if (bundle != null) {
            id = bundle.getLong(ID);
        }
    }

    @Override
    protected void onStartLoading() {
        super.onStartLoading();
        forceLoad();
    }

    @Override
    public ResponseWrapper<TalkInfo> loadInBackground() {
        ResponseWrapper<TalkInfo> response = new ResponseWrapper<>();
        try {
            TalkInfo talk = TedApplication.getService().getTalkById(id);
            response.setResponse(talk);
        } catch (Exception e) {
            response.setException(e);
        }
        return response;
    }
}
