package com.ideotic.edioticideas.aaya;

import android.app.Activity;
import android.app.FragmentManager;
import android.app.SearchManager;
import android.bluetooth.BluetoothAdapter;
import android.content.ActivityNotFoundException;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.media.AudioManager;
import android.net.wifi.WifiManager;
import android.os.AsyncTask;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.speech.RecognizerIntent;
import android.speech.tts.TextToSpeech;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.TextView;
import android.widget.Toast;
import android.net.Uri;
import org.xml.sax.InputSource;
import org.xml.sax.XMLReader;

import java.net.URL;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Locale;

import javax.xml.parsers.SAXParser;
import javax.xml.parsers.SAXParserFactory;

import static android.content.ContentValues.TAG;

/**
 * Created by Mukul on 13-05-2016.
 */
public class MainActivity extends Activity implements TextToSpeech.OnInitListener {
    Context context = this;
    AudioManager am;
    TextView showUspeak, dateView;
    Button help;
    public static String module = "";
    ImageButton speak;
    String command = "blabla";
    boolean check = false;
    private final int REQ_CODE = 100;
    private TextToSpeech tts;
    String welcome, date , myname,silent,salah;

    String city = "jabalpur", country = "India";
    final String baseUrl = "https://query.yahooapis.com/v1/public/yql?q=select%20*%20from%20weather.forecast%20where%20woeid%20in%20(select%20woeid%20from%20geo.places(1)%20where%20text%3D%22" +
            city +
            "%2C%20" +
            country +
            "%22)&format=xml&env=store%3A%2F%2Fdatatables.org%2Falltableswithkeys";

    String weatherText;
    FragmentManager f;
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);


        am = (AudioManager)getSystemService(Context.AUDIO_SERVICE);
        f = getFragmentManager();
        SharedPreferences preferences = PreferenceManager.getDefaultSharedPreferences(getBaseContext());
        welcome = "Hi " + preferences.getString(Needs.NAME, " ") + " what can i do for u today ? ";
        myname = "Hello your name is " + preferences.getString(Needs.NAME, " ") + " Have a good day " + preferences.getString(Needs.NAME, " ") ;
        salah = "No database " + preferences.getString(Needs.NAME, " ");
        silent = preferences.getString(Needs.NAME, " ") + " phone has entered silent mode";
        //Grabbing References
        showUspeak = (TextView) findViewById(R.id.textViewShow);
        help = (Button) findViewById(R.id.buttonHelp);
        speak = (ImageButton) findViewById(R.id.imageButtonSpeak);
        tts = new TextToSpeech(this, this);
        DateFormat df = new SimpleDateFormat("EEE, d MMM yyyy, h:mm a");
        date = df.format(Calendar.getInstance().getTime());
        dateView = (TextView) findViewById(R.id.textView7Date);
        dateView.setText(date);

        new MyTask().execute();
        //Welcome
        showUspeak.setText(welcome);
        tts.speak(welcome, TextToSpeech.QUEUE_FLUSH, null);


        speak.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                //Prompt speech input
                promptSpeechInput();
                check = true;


            }
        });

        help.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                launchModule(Commands.helpModule);
            }
        });
    }



    private void launchModule(String commandTolaunch) {
        switch (commandTolaunch) {
            case Commands.mailModule:
                Toast.makeText(getBaseContext(), "Mail Module", Toast.LENGTH_SHORT).show();
                Intent intentm = new Intent(MainActivity.this, GmailModule.class);
                startActivity(intentm);
                break;
            case Commands.callModule:
                Toast.makeText(getBaseContext(), "Call Module", Toast.LENGTH_SHORT).show();
                Intent intentc = new Intent(MainActivity.this, PhoneModule.class);
                startActivity(intentc);
                break;
            case Commands.emergencyModule:
                Toast.makeText(getBaseContext(), "Emergency Module", Toast.LENGTH_SHORT).show();
                Intent intente = new Intent(MainActivity.this, MapModule.class);
                intente.putExtra(Commands.EMERGENCY, true);
                startActivity(intente);
                break;

           // case Commands.locModule:
                //Toast.makeText(getBaseContext(), "Location Module", Toast.LENGTH_SHORT).show();
               // Intent intentl = new Intent(MainActivity.this, MapModule.class);
               // startActivity(intentl);
                //break;
            case Commands.musicModule:
                Toast.makeText(getBaseContext(), "Music Module", Toast.LENGTH_SHORT).show();
                Intent intentmu = new Intent(MainActivity.this, Music.class);
                startActivity(intentmu);
                break;
            case Commands.DATE:
                display_frag d = new display_frag();
                Bundle bundle = new Bundle();
                bundle.putString(Commands.DATE, date);
                d.setArguments(bundle);
                d.show(getFragmentManager(), "sss");
                break;
            case Commands.TIME:
                display_frag d2 = new display_frag();
                Bundle bundle2 = new Bundle();
                bundle2.putString(Commands.DATE, date);
                d2.setArguments(bundle2);
                d2.show(getFragmentManager(), "sss");
                break;
            case Commands.sayModule:
                display_frag d3 = new display_frag();
                Bundle bundle3 = new Bundle();
                bundle3.putString(Commands.DATE,myname);
                d3.setArguments(bundle3);
                d3.show(getFragmentManager(), "sss");
                break;
            case Commands.viaApps:
                Intent launchIntent = getPackageManager().getLaunchIntentForPackage("mark.via.gp");
                if (launchIntent != null) {
                    startActivity(launchIntent);//null pointer check in case package name was not found
                }
                break;
            case Commands.vpnApps:
                Intent launchIntent1 = getPackageManager().getLaunchIntentForPackage("free.vpn.unblock.proxy.turbovpn");
                if (launchIntent1 != null) {
                    startActivity(launchIntent1);//null pointer check in case package name was not found
                }
                break;
            case Commands.sendMSG:
                //Toast.makeText(getBaseContext(), "Choose Contatcs", Toast.LENGTH_SHORT).show();
                Intent intentmsg = new Intent(Intent.ACTION_SEND);
                intentmsg.setType("text/plain");
                String text = " Sorry , Im busy , later ! ";
                // change with required  application package

                intentmsg.setPackage("com.android.messaging");
                if (intentmsg != null) {
                    intentmsg.putExtra(Intent.EXTRA_TEXT, text);//
                    startActivity(Intent.createChooser(intentmsg, text));
                } else {

                    Toast.makeText(this, "App not found", Toast.LENGTH_SHORT)
                            .show();
                }
            case Commands.oMessage:
                Intent launchIntent2 = getPackageManager().getLaunchIntentForPackage("com.android.messaging");
               if (launchIntent2 != null) {
                   startActivity(launchIntent2);//null pointer check in case package name was not found
                }
                break;
            case Commands.playstore:
                Intent launchIntentps = getPackageManager().getLaunchIntentForPackage("com.android.vending");
                if (launchIntentps != null) {

                    startActivity(launchIntentps);//null pointer check in case package name was not found
                }

                break;
            case Commands.oInsta:
                Intent launchIntentoig = getPackageManager().getLaunchIntentForPackage("com.instagram.android");
                //Intent launchIntentpsIG = getPackageManager().getLaunchIntentForPackage("com.android.vending");
                if (launchIntentoig != null) {

                    startActivity(launchIntentoig);//null pointer check in case package name was not found
                }
                else if (launchIntentoig == null) {
                    Toast.makeText(getBaseContext(), "Instagram App is not installed, Redirect to Play Store !!", Toast.LENGTH_SHORT).show();
                    Intent webPS = new Intent();
                    webPS.setAction(Intent.ACTION_VIEW);
                    webPS.addCategory(Intent.CATEGORY_BROWSABLE);
                    webPS.setData(Uri.parse("http://play.google.com/store/apps/details?id=com.instagram.android"));
                    startActivity(webPS);

                }
                break;
            case Commands.oSnapchat:
                Intent launchIntentoSC = getPackageManager().getLaunchIntentForPackage("com.snapchat.android");

                if (launchIntentoSC != null) {

                    startActivity(launchIntentoSC);//null pointer check in case package name was not found
                }
                else if (launchIntentoSC == null) {
                    Toast.makeText(getBaseContext(), "Snapchat App is not installed, Redirect to Play Store !!", Toast.LENGTH_SHORT).show();
                    Intent webPS = new Intent();
                    webPS.setAction(Intent.ACTION_VIEW);
                    webPS.addCategory(Intent.CATEGORY_BROWSABLE);
                    webPS.setData(Uri.parse("http://play.google.com/store/apps/details?id=com.snapchat.android"));
                    startActivity(webPS);

                }
                break;
                // Facebook "website and app"
            case Commands.oFacebook:
                Intent launchIntentofb = getPackageManager().getLaunchIntentForPackage("com.facebook.katana");
                if (launchIntentofb != null) {

                    startActivity(launchIntentofb);//null pointer check in case package name was not found
                }
                else if (launchIntentofb == null) {
                    Toast.makeText(getBaseContext(), "Facebook App is not installed, Redirect to Play Store !!", Toast.LENGTH_SHORT).show();
                    Intent webPS = new Intent();
                    webPS.setAction(Intent.ACTION_VIEW);
                    webPS.addCategory(Intent.CATEGORY_BROWSABLE);
                    webPS.setData(Uri.parse("http://play.google.com/store/apps/details?id=com.facebook.katana"));
                    startActivity(webPS);

                }
                break;
            case Commands.facebook:
                Intent intentfb = new Intent();
                intentfb.setAction(Intent.ACTION_VIEW);
                intentfb.addCategory(Intent.CATEGORY_BROWSABLE);
                intentfb.setData(Uri.parse("http://www.facebook.com"));
                startActivity(intentfb);
                break;
                //end of facebook

                //Twitter "website and app"
            case Commands.oTwitter:
                Intent launchIntentotw = getPackageManager().getLaunchIntentForPackage("com.twitter.android");
                if (launchIntentotw != null) {

                    startActivity(launchIntentotw);//null pointer check in case package name was not found
                }
                else if (launchIntentotw == null) {
                    Toast.makeText(getBaseContext(), "Twitter App is not installed, Redirect to Play Store !!", Toast.LENGTH_SHORT).show();
                    Intent webPS = new Intent();
                    webPS.setAction(Intent.ACTION_VIEW);
                    webPS.addCategory(Intent.CATEGORY_BROWSABLE);
                    webPS.setData(Uri.parse("http://play.google.com/store/apps/details?id=com.twitter.android"));
                    startActivity(webPS);

                }
                break;
            case Commands.twitter:
                Intent intenttw = new Intent();
                intenttw.setAction(Intent.ACTION_VIEW);
                intenttw.addCategory(Intent.CATEGORY_BROWSABLE);
                intenttw.setData(Uri.parse("http://www.twitter.com"));
                startActivity(intenttw);
                break;
                //end of twitter

            case Commands.oContacts:
                Intent launchIntent3 = getPackageManager().getLaunchIntentForPackage("com.android.contacts");
                if (launchIntent3 != null) {
                    startActivity(launchIntent3);//null pointer check in case package name was not found
                }
                break;
            case Commands.oSetting:
                Intent Grab = getPackageManager().getLaunchIntentForPackage("com.android.settings");
                if (Grab != null) {

                    startActivity(Grab);//null pointer check in case package name was not found
                }
                break;
            case Commands.oGallery:
                Intent Grab = getPackageManager().getLaunchIntentForPackage("com.android.gallery3d");
                if (Grab != null) {

                    startActivity(Grab);//null pointer check in case package name was not found
                }
                break;
            case Commands.oCalendar:
                Intent Grab = getPackageManager().getLaunchIntentForPackage("com.android.calendar");
                if (Grab != null) {

                    startActivity(Grab);//null pointer check in case package name was not found
                }
                break;
            case Commands.oTerminal:
                Intent Grab = getPackageManager().getLaunchIntentForPackage("com.android.terminal");
                if (Grab != null) {

                    startActivity(Grab);//null pointer check in case package name was not found
                }
                break;
                //gmail "app and website"
            case Commands.oGmail:
                Intent launchIntent4 = getPackageManager().getLaunchIntentForPackage("com.google.android.gm");
                if (launchIntent4 != null) {
                    startActivity(launchIntent4);//null pointer check in case package name was not found
                }
                break;
            case Commands.gmail:
                Intent opengmail = new Intent();
                opengmail.setAction(Intent.ACTION_VIEW);
                opengmail.addCategory(Intent.CATEGORY_BROWSABLE);
                opengmail.setData(Uri.parse("http://www.gmail.com"));
                startActivity(opengmail);
                break;
                //end of gmail
            case Commands.oCalculator:
                Intent launchIntent5 = getPackageManager().getLaunchIntentForPackage("com.android.calculator2");
                if (launchIntent5 != null) {
                    startActivity(launchIntent5);//null pointer check in case package name was not found
                }
                break;
            case Commands.oMusic:
                Intent launchIntent6 = getPackageManager().getLaunchIntentForPackage("com.cyanogenmod.eleven");
                if (launchIntent6 != null) {
                    startActivity(launchIntent6);//null pointer check in case package name was not found
                }
                break;
            case Commands.oDiscord:
                Intent launchIntent7 = getPackageManager().getLaunchIntentForPackage("com.discord");
                if (launchIntent7 != null) {
                    startActivity(launchIntent7);//null pointer check in case package name was not found
                }
                break;
            case Commands.oGdrive:
                Intent launchIntent8 = getPackageManager().getLaunchIntentForPackage("com.google.android.apps.docs");
                if (launchIntent8 != null) {
                    startActivity(launchIntent8);//null pointer check in case package name was not found
                }
                break;
            case Commands.oTelegram:
                Intent launchIntentotg = getPackageManager().getLaunchIntentForPackage("org.telegram.messenger");
                if (launchIntentotg != null) {

                    startActivity(launchIntentotg);//null pointer check in case package name was not found
                }
                else if (launchIntentotg == null) {
                    Toast.makeText(getBaseContext(), "Telegram App is not installed, Redirect to Play Store !!", Toast.LENGTH_SHORT).show();
                    Intent webPS = new Intent();
                    webPS.setAction(Intent.ACTION_VIEW);
                    webPS.addCategory(Intent.CATEGORY_BROWSABLE);
                    webPS.setData(Uri.parse("http://play.google.com/store/apps/details?id=org.telegram.messenger"));
                    startActivity(webPS);

                }
                break;

                //youtube "website and app"
            case Commands.oYoutube:
                Intent launchIntentoyt = getPackageManager().getLaunchIntentForPackage("com.google.android.youtube");
                if (launchIntentoyt != null) {

                    startActivity(launchIntentoyt);//null pointer check in case package name was not found
                }
                else if (launchIntentoyt == null) {
                    Toast.makeText(getBaseContext(), "Youtube App is not installed, Redirect to Play Store !!", Toast.LENGTH_SHORT).show();
                    Intent webPS = new Intent();
                    webPS.setAction(Intent.ACTION_VIEW);
                    webPS.addCategory(Intent.CATEGORY_BROWSABLE);
                    webPS.setData(Uri.parse("http://play.google.com/store/apps/details?id=com.google.android.youtube"));
                    startActivity(webPS);

                }
                break;
            case Commands.youtube:
                Intent openyoutube = new Intent();
                openyoutube.setAction(Intent.ACTION_VIEW);
                openyoutube.addCategory(Intent.CATEGORY_BROWSABLE);
                openyoutube.setData(Uri.parse("http://www.youtube.com"));
                startActivity(openyoutube);
                break;
                //end of youtube

            case Commands.oNetflix:
                Intent launchIntentonf = getPackageManager().getLaunchIntentForPackage("com.netflix.mediaclient");
                if (launchIntentonf != null) {

                    startActivity(launchIntentonf);//null pointer check in case package name was not found
                }
                else if (launchIntentonf == null) {
                    Toast.makeText(getBaseContext(), "Netflix App is not installed, Redirect to Play Store !!", Toast.LENGTH_SHORT).show();
                    Intent webPS = new Intent();
                    webPS.setAction(Intent.ACTION_VIEW);
                    webPS.addCategory(Intent.CATEGORY_BROWSABLE);
                    webPS.setData(Uri.parse("http://play.google.com/store/apps/details?id=com.netflix.mediaclient"));
                    startActivity(webPS);

                }
                break;
                //Tokopedia "website and app"
            case Commands.oTokopedia:
                Intent tokopedia = getPackageManager().getLaunchIntentForPackage("com.tokopedia.tkpd");
                if (tokopedia != null) {

                    startActivity(tokopedia);//null pointer check in case package name was not found
                }
                else if (tokopedia == null) {
                    Toast.makeText(getBaseContext(), "Tokopedia App is not installed, Redirect to Play Store !!", Toast.LENGTH_SHORT).show();
                    Intent webPS = new Intent();
                    webPS.setAction(Intent.ACTION_VIEW);
                    webPS.addCategory(Intent.CATEGORY_BROWSABLE);
                    webPS.setData(Uri.parse("http://play.google.com/store/apps/details?id=com.tokopedia.tkpd"));
                    startActivity(webPS);

                }
                break;
            case Commands.tokopedia:
                Intent opentokopedia = new Intent();
                opentokopedia.setAction(Intent.ACTION_VIEW);
                opentokopedia.addCategory(Intent.CATEGORY_BROWSABLE);
                opentokopedia.setData(Uri.parse("http://www.tokopedia.com"));
                startActivity(opentokopedia);
                break;
                //end of tokopedia

                //bukalapak "website and app"
            case Commands.oBukalapak:
                Intent bukalapak = getPackageManager().getLaunchIntentForPackage("com.bukalapak.android");
                if (bukalapak != null) {

                    startActivity(bukalapak);//null pointer check in case package name was not found
                }
                else if (bukalapak == null) {
                    Toast.makeText(getBaseContext(), "bukalapak App is not installed, Redirect to Play Store !!", Toast.LENGTH_SHORT).show();
                    Intent webPS = new Intent();
                    webPS.setAction(Intent.ACTION_VIEW);
                    webPS.addCategory(Intent.CATEGORY_BROWSABLE);
                    webPS.setData(Uri.parse("http://play.google.com/store/apps/details?id=com.bukalapak.android"));
                    startActivity(webPS);

                }
                break;
            case Commands.bukalapak:
                Intent openbukalapak = new Intent();
                openbukalapak.setAction(Intent.ACTION_VIEW);
                openbukalapak.addCategory(Intent.CATEGORY_BROWSABLE);
                openbukalapak.setData(Uri.parse("http://www.bukalapak.com"));
                startActivity(openbukalapak);
                break;
                //end of bukalapak

                //bli bli "website and app"
            case Commands.oBlibli:
                Intent blibli = getPackageManager().getLaunchIntentForPackage("blibli.mobile.commerce");
                if (blibli != null) {

                    startActivity(blibli);//null pointer check in case package name was not found
                }
                else if (blibli == null) {
                    Toast.makeText(getBaseContext(), "Bli Bli App is not installed, Redirect to Play Store !!", Toast.LENGTH_SHORT).show();
                    Intent webPS = new Intent();
                    webPS.setAction(Intent.ACTION_VIEW);
                    webPS.addCategory(Intent.CATEGORY_BROWSABLE);
                    webPS.setData(Uri.parse("http://play.google.com/store/apps/details?id=blibli.mobile.commerce"));
                    startActivity(webPS);

                }
                break;
            case Commands.blibli:
                Intent openblibli = new Intent();
                openblibli.setAction(Intent.ACTION_VIEW);
                openblibli.addCategory(Intent.CATEGORY_BROWSABLE);
                openblibli.setData(Uri.parse("http://www.blibli.com"));
                startActivity(openblibli);
                break;
                //end of blibli

            case Commands.oGojek:
                Intent gojek = getPackageManager().getLaunchIntentForPackage("com.gojek.app");
                if (gojek != null) {

                    startActivity(gojek);//null pointer check in case package name was not found
                }
                else if (gojek == null) {
                    Toast.makeText(getBaseContext(), "Gojek App is not installed, Redirect to Play Store !!", Toast.LENGTH_SHORT).show();
                    Intent webPS = new Intent();
                    webPS.setAction(Intent.ACTION_VIEW);
                    webPS.addCategory(Intent.CATEGORY_BROWSABLE);
                    webPS.setData(Uri.parse("http://play.google.com/store/apps/details?id=com.gojek.app"));
                    startActivity(webPS);

                }
                break;

                //Amazon "website and app"
            case Commands.oAmazon:
                Intent Amazon = getPackageManager().getLaunchIntentForPackage("com.amazon.mShop.android.shopping");
                if (Amazon != null) {

                    startActivity(Amazon);//null pointer check in case package name was not found
                }
                else if (Amazon == null) {
                    Toast.makeText(getBaseContext(), "Amazon App is not installed, Redirect to Play Store !!", Toast.LENGTH_SHORT).show();
                    Intent webPS = new Intent();
                    webPS.setAction(Intent.ACTION_VIEW);
                    webPS.addCategory(Intent.CATEGORY_BROWSABLE);
                    webPS.setData(Uri.parse("http://play.google.com/store/apps/details?id=com.amazon.mShop.android.shopping"));
                    startActivity(webPS);

                }
                break;
            case Commands.amazon:
                Intent openamazon = new Intent();
                openamazon.setAction(Intent.ACTION_VIEW);
                openamazon.addCategory(Intent.CATEGORY_BROWSABLE);
                openamazon.setData(Uri.parse("http://www.amazon.com"));
                startActivity(openamazon);
                break;
                //end of Amazon

            //Yahoo "website and app"
            case Commands.oYahoo:
                Intent yahoo = getPackageManager().getLaunchIntentForPackage("com.yahoo.mobile.client.android.mail");
                if (yahoo != null) {

                    startActivity(yahoo);//null pointer check in case package name was not found
                }
                else if (yahoo == null) {
                    Toast.makeText(getBaseContext(), "Yahoo mail App is not installed, Redirect to Play Store !!", Toast.LENGTH_SHORT).show();
                    Intent webPS = new Intent();
                    webPS.setAction(Intent.ACTION_VIEW);
                    webPS.addCategory(Intent.CATEGORY_BROWSABLE);
                    webPS.setData(Uri.parse("http://play.google.com/store/apps/details?id=com.yahoo.mobile.client.android.mail"));
                    startActivity(webPS);

                }
                break;
            case Commands.yahoo:
                Intent openyahoo = new Intent();
                openyahoo.setAction(Intent.ACTION_VIEW);
                openyahoo.addCategory(Intent.CATEGORY_BROWSABLE);
                openyahoo.setData(Uri.parse("http://www.yahoo.com"));
                startActivity(openyahoo);
                break;
            //end of Yahoo

            case Commands.oGrab:
                Intent Grab = getPackageManager().getLaunchIntentForPackage("com.grabtaxi.passenger");
                if (Grab != null) {

                    startActivity(Grab);//null pointer check in case package name was not found
                }
                else if (Grab == null) {
                    Toast.makeText(getBaseContext(), "Grab App is not installed, Redirect to Play Store !!", Toast.LENGTH_SHORT).show();
                    Intent webPS = new Intent();
                    webPS.setAction(Intent.ACTION_VIEW);
                    webPS.addCategory(Intent.CATEGORY_BROWSABLE);
                    webPS.setData(Uri.parse("http://play.google.com/store/apps/details?id=com.grabtaxi.passenger"));
                    startActivity(webPS);

                }
                break;
            case Commands.remmodule:
                Toast.makeText(getBaseContext(), "Reminder Module", Toast.LENGTH_SHORT).show();
                Intent intentr = new Intent(MainActivity.this, ReminderModule.class);
                startActivity(intentr);
                break;
            case Commands.Silent:
                Toast.makeText(getBaseContext(), "Silent Mode !!!", Toast.LENGTH_SHORT).show();
                am.setStreamMute(AudioManager.STREAM_NOTIFICATION, true);
                am.setStreamMute(AudioManager.STREAM_ALARM, true);
                am.setStreamMute(AudioManager.STREAM_MUSIC, true);
                am.setStreamMute(AudioManager.STREAM_RING, true);
                am.setStreamMute(AudioManager.STREAM_SYSTEM, true);
                break;
            case Commands.Normal:
                Toast.makeText(getBaseContext(), "Normal Mode !!!", Toast.LENGTH_SHORT).show();
                am.setStreamMute(AudioManager.STREAM_NOTIFICATION, false);
                am.setStreamMute(AudioManager.STREAM_ALARM, false);
                am.setStreamMute(AudioManager.STREAM_MUSIC, false);
                am.setStreamMute(AudioManager.STREAM_RING, false);
                am.setStreamMute(AudioManager.STREAM_SYSTEM, false);
                break;
            case Commands.helpModule:
                module = "main";
                Toast.makeText(getBaseContext(), "Help Module", Toast.LENGTH_SHORT).show();
                HelpFrag frag = new HelpFrag();
                frag.show(f, null);
                break;
            case Commands.noteModule:
                Toast.makeText(getBaseContext(), "Note Module", Toast.LENGTH_SHORT).show();
                Intent intent = new Intent(MainActivity.this, NoteModule.class);
                startActivity(intent);
                break;
            case Commands.weather:
                display_frag d1 = new display_frag();
                Bundle bundle1 = new Bundle();
                bundle1.putString(Commands.DATE, weatherText);
                d1.setArguments(bundle1);
                d1.show(getFragmentManager(), "sss");
                break;
                //wifi state on and off
            case Commands.wifioff:
                Toast.makeText(getBaseContext(), "Wi-Fi is turn off", Toast.LENGTH_SHORT).show();
                WifiManager wifiManageroff = (WifiManager)this.context.getSystemService(Context.WIFI_SERVICE);
                wifiManageroff.setWifiEnabled(false);
                break;
            case Commands.wifion:
                Toast.makeText(getBaseContext(), "Wi-Fi is turn on", Toast.LENGTH_SHORT).show();
                WifiManager wifiManageron = (WifiManager)this.context.getSystemService(Context.WIFI_SERVICE);
                wifiManageron.setWifiEnabled(true);
                break;
                // wifi state end here

                //Bluetooth state on and off
            case Commands.bluetoothOff:
                Toast.makeText(getBaseContext(), "Bluetooth is turn off", Toast.LENGTH_SHORT).show();
                BluetoothAdapter mBluetoothAdapteroff = BluetoothAdapter.getDefaultAdapter();
                if (mBluetoothAdapteroff.isEnabled()) {
                    mBluetoothAdapteroff.disable();
                }
                break;
            case Commands.bluetoothOn:
                Toast.makeText(getBaseContext(), "Bluetooth is turn on", Toast.LENGTH_SHORT).show();
                BluetoothAdapter mBluetoothAdapteron = BluetoothAdapter.getDefaultAdapter();
                if (!mBluetoothAdapteron.isEnabled()) {
                    mBluetoothAdapteron.enable();
                }
                break;
                //Bluetooth state end here

                //Start of Root State
            case Commands.reboot:
                try {
                    Toast.makeText(getBaseContext(), "Phone Rebooting !!", Toast.LENGTH_SHORT).show();
                    Runtime.getRuntime().exec(new String[]{"/sbin/su", "-c", "reboot now"});
                } catch (Exception ex) {
                    Log.e(TAG, "Error ", ex);
                }
                break;
            case Commands.selfdestruction:
                try {
                    Toast.makeText(getBaseContext(), "Self Destruct !!", Toast.LENGTH_SHORT).show();
                    Runtime.getRuntime().exec(new String[]{"/sbin/su", "-c", "am force-stop com.ideotic.edioticideas.aaya"});
                } catch (Exception ex) {
                    Log.e(TAG, "Error ", ex);
                }
                break;
            case Commands.killthemall:
                try {
                    Toast.makeText(getBaseContext(), "ALL OF THEM HAS BEEN KILLED !!", Toast.LENGTH_SHORT).show();
                    Runtime.getRuntime().exec(new String[]{"/sbin/su", "-c", "am kill-all"});
                } catch (Exception ex) {
                    Log.e(TAG, "Error ", ex);
                }
                break;
            case Commands.rebootRecovery:
                try {
                    Toast.makeText(getBaseContext(), "Rebooting !!", Toast.LENGTH_SHORT).show();
                    Runtime.getRuntime().exec(new String[]{"/sbin/su", "-c", "reboot recovery"});
                } catch (Exception ex) {
                    Log.e(TAG, "Error ", ex);
                }
                break;

                //end of root state
            case Commands.wikipedia:
                Intent openwiki = new Intent();
                openwiki.setAction(Intent.ACTION_VIEW);
                openwiki.addCategory(Intent.CATEGORY_BROWSABLE);
                openwiki.setData(Uri.parse("http://www.wikipedia.com"));
                startActivity(openwiki);
            break;
            default:
                try {
                    Toast.makeText(getBaseContext(), "Error, Redirect to Google", Toast.LENGTH_SHORT).show();
                    Intent intents = new Intent(Intent.ACTION_WEB_SEARCH);
                    intents.putExtra(SearchManager.QUERY, commandTolaunch);
                    startActivity(intents);
                } catch (Exception e) {
                    // TODO: handle exception
                }
                break;
        }
    }


    /**
     * Showing google speech input dialog
     */
    private void promptSpeechInput() {
        Intent intent = new Intent(RecognizerIntent.ACTION_RECOGNIZE_SPEECH);
        intent.putExtra(RecognizerIntent.EXTRA_LANGUAGE_MODEL,
                RecognizerIntent.LANGUAGE_MODEL_FREE_FORM);
        intent.putExtra(RecognizerIntent.EXTRA_LANGUAGE, Locale.getDefault());
        intent.putExtra(RecognizerIntent.EXTRA_PROMPT,
                getString(R.string.speech_prompt));
        try {
            startActivityForResult(intent, REQ_CODE);

        } catch (ActivityNotFoundException a) {
            Toast.makeText(getApplicationContext(),
                    getString(R.string.speech_not_supported),
                    Toast.LENGTH_SHORT).show();
        }
    }


    class MyTask extends AsyncTask<Void, Void, Void> {
        myXMLWorker doing;

        @Override
        protected Void doInBackground(Void... params) {

            try {
                URL web = new URL(baseUrl);
                SAXParserFactory saxParserFactory = SAXParserFactory.newInstance();
                SAXParser sp = saxParserFactory.newSAXParser();
                XMLReader xmlReader = sp.getXMLReader();
                doing = new myXMLWorker();
                xmlReader.setContentHandler(doing);
                xmlReader.parse(new InputSource(web.openStream()));

            } catch (Exception e) {
                e.printStackTrace();
            }
            return null;
        }

        String command = Commands.TEMP;

        @Override
        protected void onPostExecute(Void aVoid) {
            super.onPostExecute(aVoid);
            switch (command) {
                case Commands.TEMP:
                    weatherText = doing.getTemp();
                    break;
            }
        }
    }

    /**
     * Receiving speech input
     */
    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        switch (requestCode) {
            case REQ_CODE: {
                if (resultCode == RESULT_OK && null != data) {

                    ArrayList<String> result = data
                            .getStringArrayListExtra(RecognizerIntent.EXTRA_RESULTS);
                    showUspeak.setText(result.get(0));

                    //Speak out
                    speakOut();

                }
                break;
            }

        }
    }

    //Speak Out
    private void speakOut() {

        String text = showUspeak.getText().toString();

        tts.speak(text, TextToSpeech.QUEUE_FLUSH, null);

        command = text;

        //Launch Module
        if (check) {
            launchModule(command);
        }
    }

    @Override
    public void onInit(int status) {

        if (status == TextToSpeech.SUCCESS) {

            int result = tts.setLanguage(Locale.getDefault());

            if (result == TextToSpeech.LANG_MISSING_DATA
                    || result == TextToSpeech.LANG_NOT_SUPPORTED) {
                Log.e("TTS", "This Language is not supported");
            } else {
                speak.setEnabled(true);
                speakOut();
            }

        } else {
            Log.e("TTS", "Initilization Failed!");
        }

    }

    @Override
    public void onDestroy() {
        // Shuts Down TTS
        if (tts != null) {
            tts.stop();
            tts.shutdown();
        }
        super.onDestroy();
    }
}
