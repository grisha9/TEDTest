package rzn.ru.myasoedov.tedtest.loader;

import android.content.AsyncTaskLoader;
import android.content.Context;
import android.os.Bundle;

import java.util.Collections;
import java.util.List;

import rzn.ru.myasoedov.tedtest.TalksFragment;
import rzn.ru.myasoedov.tedtest.TedApplication;
import rzn.ru.myasoedov.tedtest.dto.ResponseWrapper;
import rzn.ru.myasoedov.tedtest.dto.Talk;
import rzn.ru.myasoedov.tedtest.dto.TalkInfo;

/**
 * Created by grisha on 03.05.15.
 */
public class TalksLoader extends AsyncTaskLoader<ResponseWrapper<List<TalkInfo>>> {
    public static final String NAME = "name";
    public static final String OFFSET = "offset";
    public static final int ITEMS_ON_PAGE = 20;
    private String name;
    private int offset;

    public TalksLoader(Context context, Bundle bundle) {
        super(context);
        if (bundle != null) {
            name = bundle.getString(NAME);
            offset = bundle.getInt(OFFSET, 0);
        }
    }

    @Override
    protected void onStartLoading() {
        super.onStartLoading();
        forceLoad();
    }

    @Override
    public ResponseWrapper<List<TalkInfo>> loadInBackground() {
        ResponseWrapper<List<TalkInfo>> response = new ResponseWrapper<>();
        try {
            List<TalkInfo> talks = TedApplication.getService().getTalks(name, ITEMS_ON_PAGE, offset).getTalks();
            response.setResponse((talks != null) ? talks : Collections.EMPTY_LIST);
        } catch (Exception e) {
            response.setException(e);
        }
        return response;
    }
}
