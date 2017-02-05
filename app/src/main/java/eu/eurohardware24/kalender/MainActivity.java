package eu.eurohardware24.kalender;


import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.CalendarView;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.TextView;

import java.text.SimpleDateFormat;

public class MainActivity extends AppCompatActivity {

    LinearLayout WerbungLayout;
    TextView Datum;
    EditText Text;
    CalendarView Kalender;



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        WerbungLayout = (LinearLayout)findViewById(R.id.WerbungLayout);
        Datum = (TextView)findViewById(R.id.textView);
        Text = (EditText)findViewById(R.id.editText);
        Kalender = (CalendarView)findViewById(R.id.calendarView);
        Kalender.setOnDateChangeListener(new CalendarView.OnDateChangeListener() {

            @Override
            public void onSelectedDayChange(CalendarView view, int year, int month,
                                            int dayOfMonth) {
                SimpleDateFormat df = new SimpleDateFormat("dd-MMM-yyyy");
                String formattedDate = df.format(view.getDate());
                Datum.setText(formattedDate);
                //Datum.setText(dayOfMonth+"-"+month+"-"+year);
            }
        });
        long time= System.currentTimeMillis();
        Kalender.setDate(time, true, true);
        SimpleDateFormat df = new SimpleDateFormat("dd-MMM-yyyy");
        String formattedDate = df.format(Kalender.getDate());
        Datum.setText(formattedDate);
    }
}
