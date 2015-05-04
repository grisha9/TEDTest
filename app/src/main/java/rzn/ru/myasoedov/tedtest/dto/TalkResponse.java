package rzn.ru.myasoedov.tedtest.dto;

import java.util.List;

/**
 * Created by grisha on 03.05.15.
 */
public class TalkResponse {
    private List<TalkInfo> talks;

    public List<TalkInfo> getTalks() {
        return talks;
    }

    public void setTalks(List<TalkInfo> talks) {
        this.talks = talks;
    }
}
