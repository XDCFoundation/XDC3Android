package xinfin.sdk.model.api.app;

import android.content.Context;


public class EventModel {

    public String eventName;
    public Object requestObj;
    public Object responseObj;
    public Context context;
    public Object requestObj1;

    public EventModel() {
        //default constructor
    }

    public EventModel(Object requestObj, Object responseObj, String eventName, Context context) {
        this.requestObj = requestObj;
        this.responseObj = responseObj;
        this.eventName = eventName;
        this.context = context;
    }
    public EventModel(Object requestObj, Object requestObj1, Object responseObj, String eventName, Context context) {
        this.requestObj = requestObj;
        this.responseObj = responseObj;
        this.requestObj1 = requestObj1;
        this.eventName = eventName;
        this.context = context;
    }

}
