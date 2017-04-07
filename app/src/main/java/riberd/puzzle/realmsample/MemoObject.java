package riberd.puzzle.realmsample;

import io.realm.RealmObject;

/**
 * Created by Riberd on 2017/04/06.
 */

public class MemoObject extends RealmObject {
    private String title;
    private String date;

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getDate() {
        return date;
    }

    public void setDate(String date) {
        this.date = date;
    }
}
