package Result;

import Models.EventModel;

import java.util.List;

/**
 * Events getter JSON response object
 */
public class EventAllRes extends ParentRes {
    /**
     *  List of all events
     */
    private List<EventModel> data;

    /**
     *
     * @param data List of all events
     */
    public EventAllRes(List<EventModel> data, boolean success, String msg) {
        super(success, msg);
        this.data = data;
    }

    public List<EventModel> getData() {
        return data;
    }

    public void setData(List<EventModel> data) {
        this.data = data;
    }
}
