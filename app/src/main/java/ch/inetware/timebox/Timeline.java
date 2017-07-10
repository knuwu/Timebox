package ch.inetware.timebox;

import android.content.ContentValues;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.ViewGroup.LayoutParams;
import android.widget.Button;
import android.widget.LinearLayout;
import ch.inetware.timebox.db.TimeDb;
import ch.inetware.timebox.db.TimeEvent;
import ch.inetware.timebox.db.TimeEventType;

import java.text.SimpleDateFormat;

public class Timeline extends AppCompatActivity {

  private final static String DEBUG_TAG = Timeline.class.getName();

  private static final SimpleDateFormat dateFormat = new SimpleDateFormat("dd.MM.yyyy HH:mm");
  private SQLiteDatabase db;

  @Override
  protected void onCreate(Bundle savedInstanceState) {
    initLayout(savedInstanceState);
    initDb();

    logTimeEvent();

    displayTimeEvents();
  }

  private void initLayout(Bundle savedInstanceState) {
    super.onCreate(savedInstanceState);
    setContentView(R.layout.activity_timeline);
  }

  private void initDb() {
    db = new TimeDb(this).getWritableDatabase();
  }

  private TimeEvent logTimeEvent() {
    TimeEvent event = new TimeEvent();
    ContentValues values = new ContentValues();
    values.put(TimeEvent.EVENT_TIME, event.eventTime.getTime());
    values.put(TimeEvent.EVENT_TYPE, event.eventType.name());
    values.put(TimeEvent.EVENT_DESC, event.eventDesc);

    event.id = db.insert(TimeEvent.TABLE_NAME, null, values);

    return event;
  }

  private void displayTimeEvents() {
    Cursor cursor = db.rawQuery("select * from " + TimeEvent.TABLE_NAME, null);
    while (cursor.moveToNext()) {
      TimeEvent event = getTimeEvent(cursor);
      createTimeEventButton(event);
    }
  }

  private TimeEvent getTimeEvent(Cursor cursor) {
    long id = cursor.getLong(cursor.getColumnIndex(TimeEvent._ID));
    long time = cursor.getLong(cursor.getColumnIndex(TimeEvent.EVENT_TIME));
    String type = cursor.getString(cursor.getColumnIndex(TimeEvent.EVENT_TYPE));
    String desc = cursor.getString(cursor.getColumnIndex(TimeEvent.EVENT_DESC));
    return new TimeEvent(id, time, TimeEventType.valueOf(type), desc);
  }

  private void createTimeEventButton(TimeEvent event) {
    Button button = new Button(this);
    button.setText(dateFormat.format(event.eventTime));

    LinearLayout liste = (LinearLayout) findViewById(R.id.liste);
    LayoutParams lp = new LayoutParams(LayoutParams.MATCH_PARENT, LayoutParams.WRAP_CONTENT);
    liste.addView(button, lp);
  }
}
