package ch.inetware.timebox.db;

import java.sql.Date;

/**
 * TimeEvent Entity
 *
 * Speichert ein "Zeitereignis"
 *
 * Created by ralph on 12.05.17.
 */
public class TimeEvent {

  public static final String TABLE_NAME = "timeEvent";

  public static final String _ID = "_id";
  public static final String EVENT_TIME = "eventTime";
  public static final String EVENT_TYPE = "eventType";
  public static final String EVENT_DESC = "eventDesc";

  public long id;
  public Date eventTime;
  public TimeEventType eventType;
  public String eventDesc;

  public TimeEvent() {
    this.eventTime = new Date(new java.util.Date().getTime());
    this.eventType = TimeEventType.APP_OPENED;
  }

  public TimeEvent(TimeEventType type) {
    this.eventTime = new Date(new java.util.Date().getTime());
    this.eventType = type;
  }

  public TimeEvent(TimeEventType type, String desc) {
    this.eventTime = new Date(new java.util.Date().getTime());
    this.eventType = type;
    this.eventDesc = desc;
  }

  public TimeEvent(long id, long time, TimeEventType type, String desc) {
    this.id = id;
    this.eventTime = new Date(time);
    this.eventType = type;
    this.eventDesc = desc;
  }
}
