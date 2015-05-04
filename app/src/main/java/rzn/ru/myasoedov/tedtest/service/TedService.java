package rzn.ru.myasoedov.tedtest.service;

import retrofit.client.Response;
import retrofit.http.GET;
import retrofit.http.Path;
import retrofit.http.Query;
import rzn.ru.myasoedov.tedtest.BuildConfig;
import rzn.ru.myasoedov.tedtest.dto.TalkInfo;
import rzn.ru.myasoedov.tedtest.dto.TalkResponse;

/**
 * Created by grisha on 03.05.15.
 */
public interface TedService {
    @GET("/v1/talks.json?api-key=" + BuildConfig.TED_API_KEY +"&fields=photo_urls")
    TalkResponse getTalks(@Query("name") String name, @Query("limit")  int limit,
                          @Query("offset") int offset);

    @GET("/v1/talks/{id}.json?api-key=" + BuildConfig.TED_API_KEY)
    TalkInfo getTalkById(@Path("id") long id);
}
