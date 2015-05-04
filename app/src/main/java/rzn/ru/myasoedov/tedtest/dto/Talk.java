package rzn.ru.myasoedov.tedtest.dto;

import com.google.gson.annotations.SerializedName;

import java.util.List;

/**
 * Created by grisha on 03.05.15.
 */
public class Talk {
    private long id;
    private String name;
    @SerializedName("photo_urls")
    private List<Photo> urls;
    private Media media;

    public long getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public List<Photo> getUrls() {
        return urls;
    }

    public void setUrls(List<Photo> urls) {
        this.urls = urls;
    }

    public Media getMedia() {
        return media;
    }

    public void setMedia(Media media) {
        this.media = media;
    }
}
