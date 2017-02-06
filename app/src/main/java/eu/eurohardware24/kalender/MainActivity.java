package eu.eurohardware24.kalender;


import android.content.Context;
import android.content.SharedPreferences;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.view.inputmethod.InputMethodManager;
import android.widget.CalendarView;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.TextView;

import java.text.SimpleDateFormat;

import com.google.android.gms.ads.AdRequest;
import com.google.android.gms.ads.AdSize;
import com.google.android.gms.ads.AdView;
import com.google.android.gms.ads.InterstitialAd;
import com.google.firebase.analytics.FirebaseAnalytics;

public class MainActivity extends AppCompatActivity {
    private FirebaseAnalytics mFirebaseAnalytics;
    LinearLayout WerbungLayout;
    TextView Datum;
    EditText Text;
    CalendarView Kalender;
    SharedPreferences settings;
    SharedPreferences.Editor editor;
    public static final String PREFS_NAME = "MyPrefsFile";
    InputMethodManager imm;
    private AdView adView;
    private static final String MY_BANNER_UNIT_ID = "ca-app-pub-8124355001128596/3339556799";
    private static final String MY_INTERSTITIAL_UNIT_ID = "ca-app-pub-8124355001128596/1403745594";
    private InterstitialAd interstitial;
    int clickzaehler=0;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        mFirebaseAnalytics = FirebaseAnalytics.getInstance(this);
        setContentView(R.layout.activity_main);
        imm = (InputMethodManager)getSystemService(
                Context.INPUT_METHOD_SERVICE);
        settings = getSharedPreferences(PREFS_NAME, 0);
        editor = settings.edit();
        /*
        WerbungLayout = (LinearLayout)findViewById(R.id.WerbungLayout);
        adView = new AdView(this);
        adView.setAdUnitId(MY_BANNER_UNIT_ID);
        adView.setAdSize(AdSize.BANNER);
        WerbungLayout.addView(adView);
        AdRequest adRequest = new AdRequest.Builder().build();
        adView.loadAd(adRequest);
        interstitial = new InterstitialAd(this);
        interstitial.setAdUnitId(MY_INTERSTITIAL_UNIT_ID);
        AdRequest Request = new AdRequest.Builder().build();
        interstitial.loadAd(Request);
        */
        Datum = (TextView)findViewById(R.id.textView);
        Text = (EditText)findViewById(R.id.editText);
        Kalender = (CalendarView)findViewById(R.id.calendarView);
        Kalender.setOnDateChangeListener(new CalendarView.OnDateChangeListener() {

            @Override
            public void onSelectedDayChange(CalendarView view, int year, int month,
                                            int dayOfMonth) {

                editor.putString(Datum.getText().toString(), Text.getText().toString());
                editor.commit();

                SimpleDateFormat df = new SimpleDateFormat("dd-MMM-yyyy");
                String formattedDate = df.format(view.getDate());
                Datum.setText(formattedDate);
                String text = settings.getString(formattedDate, null);
                if (text != null){
                    Text.setText(text);
                }else Text.setText("");
                clickzaehler++;
                if (clickzaehler>=10){
                    displayInterstitial();
                    clickzaehler=0;
                }
            }
        });
        Kalender.setOnClickListener(keyboardgone);
        long time= System.currentTimeMillis();
        Kalender.setDate(time, true, true);
        SimpleDateFormat df = new SimpleDateFormat("dd-MMM-yyyy");
        String formattedDate = df.format(Kalender.getDate());
        String text = settings.getString(formattedDate, null);
        Text.setText(text);
        Datum.setText(formattedDate);
    }

    View.OnClickListener keyboardgone = new View.OnClickListener() {
        public void onClick(View v) {
            imm.hideSoftInputFromWindow(Text.getWindowToken(), 0);
            if (clickzaehler>=10){
                displayInterstitial();
                clickzaehler=0;
            }
        }
    };

    @Override
    protected void onStop(){
        super.onStop();
        settings = getSharedPreferences(PREFS_NAME, 0);
        editor = settings.edit();
        editor.putString(Datum.getText().toString(), Text.getText().toString());
        editor.commit();
    }

    @Override
    protected void onPause(){
        super.onPause();
        settings = getSharedPreferences(PREFS_NAME, 0);
        editor = settings.edit();
        editor.putString(Datum.getText().toString(), Text.getText().toString());
        editor.commit();

    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        settings = getSharedPreferences(PREFS_NAME, 0);
        editor = settings.edit();
        editor.putString(Datum.getText().toString(), Text.getText().toString());
        editor.commit();
    }

    public void displayInterstitial() {
        if (interstitial.isLoaded()) {
            interstitial.show();
        }
    }
}
