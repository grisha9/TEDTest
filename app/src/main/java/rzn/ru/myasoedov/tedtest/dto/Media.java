package rzn.ru.myasoedov.tedtest.dto;

import com.google.gson.annotations.SerializedName;

/**
 * Created by grisha on 03.05.15.
 */
public class Media {
    private Internal internal;

    public Internal getInternal() {
        return internal;
    }

    public void setInternal(Internal internal) {
        this.internal = internal;
    }

    public class Internal {
        @SerializedName("320k")
        private Video videoLow;
        @SerializedName("600k")
        private Video videoHeight;

        public Video getVideoLow() {
            return videoLow;
        }

        public void setVideoLow(Video videoLow) {
            this.videoLow = videoLow;
        }

        public Video getVideoHeight() {
            return videoHeight;
        }

        public void setVideoHeight(Video videoHeight) {
            this.videoHeight = videoHeight;
        }

        public class Video {
            private String uri;

            public String getUri() {
                return uri;
            }

            public void setUri(String uri) {
                this.uri = uri;
            }
        }
    }
}
