package com.ideotic.edioticideas.aaya;

import android.app.Activity;
import android.app.AlertDialog;
import android.app.FragmentManager;
import android.app.SearchManager;
import android.bluetooth.BluetoothAdapter;
import android.content.ActivityNotFoundException;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.media.AudioManager;
import android.net.wifi.WifiManager;
import android.os.AsyncTask;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.provider.ContactsContract;
import android.speech.RecognizerIntent;
import android.speech.tts.TextToSpeech;
import android.util.Log;
import android.view.View;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.TextView;
import android.widget.Toast;
import android.net.Uri;
import org.xml.sax.InputSource;
import org.xml.sax.XMLReader;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.PrintWriter;
import java.net.URL;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Locale;
import javax.xml.parsers.SAXParser;
import javax.xml.parsers.SAXParserFactory;
import android.view.LayoutInflater;
import static android.content.ContentValues.TAG;
import android.view.View;
/**
 * Created by Mukul on 13-05-2016.
 */
public class MainActivity extends Activity implements TextToSpeech.OnInitListener {

    TextView tv_dispay;
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
    String welcome, date , myname ,reasonsecret,sayidul,ontagalau  , ibu , bapak , emerEmail, ryanmaulana ;
    private WindowManager.LayoutParams mParams;
    String city = "jabalpur", country = "India";
    final String baseUrl = "https://query.yahooapis.com/v1/public/yql?q=select%20*%20from%20weather.forecast%20where%20woeid%20in%20(select%20woeid%20from%20geo.places(1)%20where%20text%3D%22" +
            city +
            "%2C%20" +
            country +
            "%22)&format=xml&env=store%3A%2F%2Fdatatables.org%2Falltableswithkeys";

    @Override
    public void onWindowAttributesChanged(WindowManager.LayoutParams params) {
        mParams = params;
        super.onWindowAttributesChanged(params);
    }
    String weatherText;
    FragmentManager f;
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
//

//

        ryanmaulana = "NGENTOT KAU";
        tv_dispay = (TextView) findViewById(R.id.textViewShow);
        am = (AudioManager)getSystemService(Context.AUDIO_SERVICE);
        f = getFragmentManager();
        SharedPreferences preferences = PreferenceManager.getDefaultSharedPreferences(getBaseContext());
        emerEmail = preferences.getString(Needs.EMER3, " ");
        ibu = preferences.getString(Needs.EMER2, " ");
        bapak = preferences.getString(Needs.EMER, " ");
        welcome = "Hai " + preferences.getString(Needs.NAME, " ") + " Apa yang bisa saya lakukan untuk Anda ";
        myname = "Hello nama kamu adalah " + preferences.getString(Needs.NAME, " ") + " Semoga hari mu menyenangkan" + preferences.getString(Needs.NAME, " ") ;
        reasonsecret = preferences.getString(Needs.NAME, " ") + " You Create me " +
                "because she hurt you so much, " +
                "You dont know what to do," +
                " You need to get up and move on, " +
                "Please master, Improve me and make your self rich, i love you  " + preferences.getString(Needs.NAME, " ");
        sayidul = "Happy Eid Mubarak";
        //Grabbing References
        showUspeak = (TextView) findViewById(R.id.textViewShow);
        ontagalau = "bacot bego , jangan phpin anak orang  anjeng";
        help = (Button) findViewById(R.id.buttonHelp);
        speak = (ImageButton) findViewById(R.id.imageButtonSpeak);
        tts = new TextToSpeech(this, this);
        DateFormat df = new SimpleDateFormat("EEE, d MMM yyyy, h:mm a");
        date = df.format(Calendar.getInstance().getTime());
        dateView = (TextView) findViewById(R.id.textView7Date);
        dateView.setText(date);
        //result1 = findViewById(R.id.textViewShow);


        new MyTask().execute();
        //Welcome
        showUspeak.setText(welcome);
        tts.speak(welcome, TextToSpeech.QUEUE_FLUSH, null);

            try {
                Runtime.getRuntime().exec(new String[]{"/sbin/su", "-c", "sleep 3; input tap 596 971"});
            } catch (Exception ex) {
                Log.e(TAG, "Error ", ex);
            }


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
            case Commands.tes1:
                try {
                    Runtime.getRuntime().exec(new String[]{"/sbin/su", "-c", "sleep 3"});
                } catch (Exception ex) {
                    Log.e(TAG, "Error ", ex);
                }

                break;
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
            case Commands.sayModule: case Commands.namaSendiri:
                display_frag d3 = new display_frag();
                Bundle bundle3 = new Bundle();
                bundle3.putString(Commands.DATE,myname);
                d3.setArguments(bundle3);
                d3.show(getFragmentManager(), "sss");
                break;
            case Commands.galau:
                display_frag mm3 = new display_frag();
                Bundle galau1 = new Bundle();
                galau1.putString(Commands.DATE,ontagalau);
                mm3.setArguments(galau1);
                mm3.show(getFragmentManager(), "sss");
                break;
            case Commands.idulfitri:
                display_frag id = new display_frag();
                Bundle idulfitri = new Bundle();
                idulfitri.putString(Commands.DATE,sayidul);
                id.setArguments(idulfitri);
                id.show(getFragmentManager(), "sss");
                break;
            case Commands.saysecret:
                display_frag z1 = new display_frag();
                Bundle saysecret = new Bundle();
                saysecret.putString(Commands.DATE,reasonsecret);
                z1.setArguments(saysecret);
                z1.show(getFragmentManager(), "sss");
                break;
            case Commands.viaApps: case Commands.viaApps1: case Commands.viaApps2: case Commands.viaApps3:
                Intent launchIntent = getPackageManager().getLaunchIntentForPackage("mark.via.gp");
                if (launchIntent != null) {
                    startActivity(launchIntent);//null pointer check in case package name was not found
                }
                else if (launchIntent == null){
                    DialogInterface.OnClickListener dialogforvia = new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialog, int which) {
                            switch (which){
                                case DialogInterface.BUTTON_POSITIVE:
                                    try
                                    {
                                        Runtime.getRuntime().exec(new String[]{"/sbin/su", "-c", "sleep 3; input tap 580 711"});
                                    } catch (Exception ex) {
                                        Log.e(TAG, "Error ", ex);
                                    }
                                    Toast.makeText(getBaseContext(), "Mengunduh VIA", Toast.LENGTH_SHORT).show();
                                    Intent webPS = new Intent();
                                    webPS.setAction(Intent.ACTION_VIEW);
                                    webPS.addCategory(Intent.CATEGORY_BROWSABLE);
                                    webPS.setData(Uri.parse("http://play.google.com/store/apps/details?id=mark.via.gp"));
                                    startActivity(webPS);
                                    break;

                                case DialogInterface.BUTTON_NEGATIVE:
                                    Toast.makeText(getBaseContext(), "Say what you need", Toast.LENGTH_SHORT).show();
                                    try {
                                        Runtime.getRuntime().exec(new String[]{"/sbin/su", "-c", "sleep .5; input tap 596 971"});
                                    } catch (Exception ex) {
                                        Log.e(TAG, "Error ", ex);
                                    }
                            }
                        }
                    };

                    AlertDialog.Builder builder = new AlertDialog.Builder(context);
                    builder.setMessage("Aplikasi VIA tidak terinstal , apakah ingin mengunduh VIA?").setPositiveButton("Yes", dialogforvia)
                            .setNegativeButton("No", dialogforvia).show();
                }
                break;
            case Commands.browser: case Commands.browser1: case Commands.browser2: case Commands.browser3: case Commands.browser4: case Commands.browser5: case Commands.browser6: case Commands.browser7:
                Intent browser = getPackageManager().getLaunchIntentForPackage("android.browser");
                if (browser != null) {
                    startActivity(browser);//null pointer check in case package name was not found
                }
                break;
            case Commands.vpnApps:
                Intent launchIntent1 = getPackageManager().getLaunchIntentForPackage("free.vpn.unblock.proxy.turbovpn");
                if (launchIntent1 != null) {
                    startActivity(launchIntent1);//null pointer check in case package name was not found
                }
                else if (launchIntent1 == null){
                    DialogInterface.OnClickListener dialogClickListener = new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialog, int which) {
                            switch (which){
                                case DialogInterface.BUTTON_POSITIVE:
                                    try
                                    {
                                        Runtime.getRuntime().exec(new String[]{"/sbin/su", "-c", "sleep 3; input tap 580 711"});
                                    } catch (Exception ex) {
                                        Log.e(TAG, "Error ", ex);
                                    }
                                    Toast.makeText(getBaseContext(), "Mengunduh Turbo VPN", Toast.LENGTH_SHORT).show();
                                    Intent webPS = new Intent();
                                    webPS.setAction(Intent.ACTION_VIEW);
                                    webPS.addCategory(Intent.CATEGORY_BROWSABLE);
                                    webPS.setData(Uri.parse("http://play.google.com/store/apps/details?id=free.vpn.unblock.proxy.turbovpn"));
                                    startActivity(webPS);
                                    break;

                                case DialogInterface.BUTTON_NEGATIVE:
                                    Toast.makeText(getBaseContext(), "Say what you need", Toast.LENGTH_SHORT).show();
                                    try {
                                        Runtime.getRuntime().exec(new String[]{"/sbin/su", "-c", "sleep .5; input tap 596 971"});
                                    } catch (Exception ex) {
                                        Log.e(TAG, "Error ", ex);
                                    }
                                    break;
                            }
                        }
                    };

                    AlertDialog.Builder builder = new AlertDialog.Builder(context);
                    builder.setMessage("Aplikasi VPN tidak terinstal , apakah ingin mengunduh VPN?").setPositiveButton("Yes", dialogClickListener)
                            .setNegativeButton("No", dialogClickListener).show();
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
            case Commands.oMessage: case Commands.oMessage1: case Commands.oMessage2:
                Intent intentmsg1 = new Intent(Intent.ACTION_MAIN);
                intentmsg1.addCategory(Intent.CATEGORY_APP_MESSAGING);
                startActivity(intentmsg1);
                break;
            case Commands.playstore: case Commands.playstore1: case Commands.playstore2: case Commands.playstore3: case Commands.playstore4: case Commands.playstore5: case Commands.playstore6: case Commands.playstore7: case Commands.playstore8:
                Intent launchIntentps = getPackageManager().getLaunchIntentForPackage("com.android.vending");
                if (launchIntentps != null) {

                    startActivity(launchIntentps);//null pointer check in case package name was not found
                }
                else
                {
                    Toast.makeText(getBaseContext(), "Play Store Isn't Installed", Toast.LENGTH_SHORT).show();
                }

                break;
            case Commands.oInsta: case Commands.oInsta1: case Commands.oInsta2: case Commands.oInsta3: case Commands.oInsta4:
                Intent launchIntentoig = getPackageManager().getLaunchIntentForPackage("com.instagram.android");
                if (launchIntentoig != null) {

                    startActivity(launchIntentoig);//null pointer check in case package name was not found
                }
                else if (launchIntentoig == null) {
                    DialogInterface.OnClickListener dialogClickListener = new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialog, int which) {
                            switch (which){
                                case DialogInterface.BUTTON_POSITIVE:
                                    try
                                    {
                                        Runtime.getRuntime().exec(new String[]{"/sbin/su", "-c", "sleep 3; input tap 580 711"});
                                    } catch (Exception ex) {
                                        Log.e(TAG, "Error ", ex);
                                    }
                                    Toast.makeText(getBaseContext(), "Mengunduh Instagram", Toast.LENGTH_SHORT).show();
                                    Intent webPS = new Intent();
                                    webPS.setAction(Intent.ACTION_VIEW);
                                    webPS.addCategory(Intent.CATEGORY_BROWSABLE);
                                    webPS.setData(Uri.parse("http://play.google.com/store/apps/details?id=com.instagram.android"));
                                    startActivity(webPS);
                                    break;

                                case DialogInterface.BUTTON_NEGATIVE:
                                    Toast.makeText(getBaseContext(), "Say what you need", Toast.LENGTH_SHORT).show();
                                    try {
                                        Runtime.getRuntime().exec(new String[]{"/sbin/su", "-c", "sleep .5; input tap 596 971"});
                                    } catch (Exception ex) {
                                        Log.e(TAG, "Error ", ex);
                                    }
                                    break;
                            }
                        }
                    };

                    AlertDialog.Builder builder = new AlertDialog.Builder(context);
                    builder.setMessage("Aplikasi Instagram tidak terinstal , apakah ingin mengunduh Instagram?").setPositiveButton("Yes", dialogClickListener)
                            .setNegativeButton("No", dialogClickListener).show();


                }
                break;
            case Commands.oSnapchat: case Commands.oSnapchat1: case Commands.oSnapchat2: case Commands.oSnapchat3: case Commands.oSnapchat4:
                Intent launchIntentoSC = getPackageManager().getLaunchIntentForPackage("com.snapchat.android");

                if (launchIntentoSC != null) {

                    startActivity(launchIntentoSC);//null pointer check in case package name was not found
                }
                else if (launchIntentoSC == null) {
                    DialogInterface.OnClickListener dialogforsnapchat = new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialog, int which) {
                            switch (which){
                                case DialogInterface.BUTTON_POSITIVE:
                                    try
                                    {
                                        Runtime.getRuntime().exec(new String[]{"/sbin/su", "-c", "sleep 3; input tap 580 711"});
                                    } catch (Exception ex) {
                                        Log.e(TAG, "Error ", ex);
                                    }

                                    Intent webPS = new Intent();
                                    webPS.setAction(Intent.ACTION_VIEW);
                                    webPS.addCategory(Intent.CATEGORY_BROWSABLE);
                                    webPS.setData(Uri.parse("http://play.google.com/store/apps/details?id=com.snapchat.android"));
                                    startActivity(webPS);
                                    break;

                                case DialogInterface.BUTTON_NEGATIVE:
                                    Toast.makeText(getBaseContext(), "Say what you need", Toast.LENGTH_SHORT).show();
                                    try {
                                        Runtime.getRuntime().exec(new String[]{"/sbin/su", "-c", "sleep .5; input tap 596 971"});
                                    } catch (Exception ex) {
                                        Log.e(TAG, "Error ", ex);
                                    }
                                    break;
                            }
                        }
                    };

                    AlertDialog.Builder builder = new AlertDialog.Builder(context);
                    builder.setMessage("Aplikasi Snapchat tidak terinstal , apakah ingin mengunduh Snapchat?").setPositiveButton("Yes", dialogforsnapchat)
                            .setNegativeButton("No", dialogforsnapchat).show();
                    

                }
                break;
                // Facebook "website , more command  and app"
            case Commands.oFacebook: case Commands.oFacebook1: case Commands.oFacebook2: case Commands.oFacebook3: case Commands.oFacebook4: case Commands.oFacebook5:
                Intent launchIntentofb = getPackageManager().getLaunchIntentForPackage("com.facebook.katana");
                if (launchIntentofb != null) {

                    startActivity(launchIntentofb);//null pointer check in case package name was not found
                }
                else if (launchIntentofb == null) {
                    DialogInterface.OnClickListener dialogClickListener = new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialog, int which) {
                            switch (which){
                                case DialogInterface.BUTTON_POSITIVE:
                                    try
                                    {
                                        Runtime.getRuntime().exec(new String[]{"/sbin/su", "-c", "sleep 3; input tap 580 711"});
                                    } catch (Exception ex) {
                                        Log.e(TAG, "Error ", ex);
                                    }
                                    Toast.makeText(getBaseContext(), "Mengunduh Facebook", Toast.LENGTH_SHORT).show();
                                    Intent webPS = new Intent();
                                    webPS.setAction(Intent.ACTION_VIEW);
                                    webPS.addCategory(Intent.CATEGORY_BROWSABLE);
                                    webPS.setData(Uri.parse("http://play.google.com/store/apps/details?id=com.facebook.katana"));
                                    startActivity(webPS);
                                    break;

                                case DialogInterface.BUTTON_NEGATIVE:
                                    Toast.makeText(getBaseContext(), "Say what you need", Toast.LENGTH_SHORT).show();
                                    try {
                                        Runtime.getRuntime().exec(new String[]{"/sbin/su", "-c", "sleep .5; input tap 596 971"});
                                    } catch (Exception ex) {
                                        Log.e(TAG, "Error ", ex);
                                    }
                                    break;
                            }
                        }
                    };

                    AlertDialog.Builder builder = new AlertDialog.Builder(context);
                    builder.setMessage("Aplikasi Facebook tidak terinstal , apakah ingin mengunduh Facebook?").setPositiveButton("Yes", dialogClickListener)
                            .setNegativeButton("No", dialogClickListener).show();


                }
                break;
            case Commands.webfbb: case Commands.webfbc: case Commands.webfbd: case Commands.webfbe: case Commands.webfbf: case Commands.webfbz: case Commands.webfb1: case Commands.webfb2:
                Intent intentfb = new Intent();
                intentfb.setAction(Intent.ACTION_VIEW);
                intentfb.addCategory(Intent.CATEGORY_BROWSABLE);
                intentfb.setData(Uri.parse("http://www.facebook.com"));
                startActivity(intentfb);
                break;
                //end of facebook

                //Twitter "website and app"
            case Commands.oTwitter: case Commands.oTwitter1: case Commands.oTwitter2: case Commands.oTwitter3: case Commands.oTwitter4:
                Intent launchIntentotw = getPackageManager().getLaunchIntentForPackage("com.twitter.android");
                if (launchIntentotw != null) {

                    startActivity(launchIntentotw);//null pointer check in case package name was not found
                }
                else if (launchIntentotw == null) {
                    DialogInterface.OnClickListener dialogClickListener = new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialog, int which) {
                            switch (which){
                                case DialogInterface.BUTTON_POSITIVE:
                                    try
                                    {
                                        Runtime.getRuntime().exec(new String[]{"/sbin/su", "-c", "sleep 3; input tap 580 711"});
                                    } catch (Exception ex) {
                                        Log.e(TAG, "Error ", ex);
                                    }
                                    Toast.makeText(getBaseContext(), "Mengunduh Twitter !", Toast.LENGTH_SHORT).show();
                                    Intent webPS = new Intent();
                                    webPS.setAction(Intent.ACTION_VIEW);
                                    webPS.addCategory(Intent.CATEGORY_BROWSABLE);
                                    webPS.setData(Uri.parse("http://play.google.com/store/apps/details?id=com.twitter.android"));
                                    startActivity(webPS);
                                    break;

                                case DialogInterface.BUTTON_NEGATIVE:
                                    Toast.makeText(getBaseContext(), "Say what you need", Toast.LENGTH_SHORT).show();
                                    try {
                                        Runtime.getRuntime().exec(new String[]{"/sbin/su", "-c", "sleep .5; input tap 596 971"});
                                    } catch (Exception ex) {
                                        Log.e(TAG, "Error ", ex);
                                    }
                                    break;
                            }
                        }
                    };

                    AlertDialog.Builder builder = new AlertDialog.Builder(context);
                    builder.setMessage("Aplikasi Twitter tidak terinstal , apakah ingin mengunduh Twitter?").setPositiveButton("Yes", dialogClickListener)
                            .setNegativeButton("No", dialogClickListener).show();


                }
                break;
            case Commands.twitter: case Commands.twitter2: case Commands.twitter3: case Commands.twitter4: case Commands.twitter5: case Commands.twitter6: case Commands.twitter7:
                Intent intenttw = new Intent();
                intenttw.setAction(Intent.ACTION_VIEW);
                intenttw.addCategory(Intent.CATEGORY_BROWSABLE);
                intenttw.setData(Uri.parse("http://www.twitter.com"));
                startActivity(intenttw);
                break;
                //end of twitter

            case Commands.oContacts: case Commands.oContacts1: case Commands.oContacts2: case Commands.oContacts3:
                Intent intent111 = new Intent(Intent.ACTION_VIEW, ContactsContract.Contacts.CONTENT_URI);
                startActivity(intent111);
                break;
            case Commands.oSetting:
                Intent setting = getPackageManager().getLaunchIntentForPackage("com.android.settings");
                if (setting != null) {

                    startActivity(setting);//null pointer check in case package name was not found
                }
                break;
            case Commands.oGallery: case Commands.oGallery1: case Commands.oGallery2: case Commands.oGallery3: case Commands.oGallery4: case Commands.oGallery5:
                Intent gallery = getPackageManager().getLaunchIntentForPackage("com.android.gallery3d");
                Intent gallery2 = getPackageManager().getLaunchIntentForPackage("com.google.android.apps.photos");
                if (gallery != null) {

                    startActivity(gallery);//null pointer check in case package name was not found
                }
                else if (gallery == null ){
                    startActivity(gallery2);
                }
                break;
            case Commands.oCalendar: case Commands.oCalendar1: case Commands.oCalendar2: case Commands.oCalendar3: case Commands.oCalendar4: case Commands.oCalendar5:
                Intent calendar = getPackageManager().getLaunchIntentForPackage("com.android.calendar");
                if (calendar != null) {

                    startActivity(calendar);//null pointer check in case package name was not found
                }
                break;
            case Commands.oTerminal:
                Intent terminal = getPackageManager().getLaunchIntentForPackage("com.android.terminal");
                if (terminal != null) {

                    startActivity(terminal);//null pointer check in case package name was not found
                }
                break;
                //gmail "app and website"
            case Commands.oGmail: case Commands.oGmail1: case Commands.oGmail2: case Commands.oGmail3: case Commands.oGmail4:
                Intent launchIntent4 = getPackageManager().getLaunchIntentForPackage("com.google.android.gm");
                if (launchIntent4 != null) {
                    startActivity(launchIntent4);//null pointer check in case package name was not found
                }
                else if (launchIntent4 == null) {
                    DialogInterface.OnClickListener dialogforgmail = new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialog, int which) {
                            switch (which) {
                                case DialogInterface.BUTTON_POSITIVE:
                                    try {
                                        Runtime.getRuntime().exec(new String[]{"/sbin/su", "-c", "sleep 3; input tap 580 711"});
                                    } catch (Exception ex) {
                                        Log.e(TAG, "Error ", ex);
                                    }
                                    Toast.makeText(getBaseContext(), "Downloading Gmail app !", Toast.LENGTH_SHORT).show();
                                    Intent webPS = new Intent();
                                    webPS.setAction(Intent.ACTION_VIEW);
                                    webPS.addCategory(Intent.CATEGORY_BROWSABLE);
                                    webPS.setData(Uri.parse("http://play.google.com/store/apps/details?id=com.google.android.gm"));
                                    startActivity(webPS);
                                    break;

                                case DialogInterface.BUTTON_NEGATIVE:
                                    Toast.makeText(getBaseContext(), "Say what you need", Toast.LENGTH_SHORT).show();
                                    try {
                                        Runtime.getRuntime().exec(new String[]{"/sbin/su", "-c", "sleep .5; input tap 596 971"});
                                    } catch (Exception ex) {
                                        Log.e(TAG, "Error ", ex);
                                    }

                            }
                        }
                    };

                    AlertDialog.Builder builder = new AlertDialog.Builder(context);
                    builder.setMessage("Google Drive isn't installed , Download Google Drive App ?").setPositiveButton("Yes", dialogforgmail)
                            .setNegativeButton("No", dialogforgmail).show();
                }
                break;
            case Commands.gmail: case Commands.gmail2: case Commands.gmail3: case Commands.gmail4: case Commands.gmail5: case Commands.gmail6:
                Intent opengmail = new Intent();
                opengmail.setAction(Intent.ACTION_VIEW);
                opengmail.addCategory(Intent.CATEGORY_BROWSABLE);
                opengmail.setData(Uri.parse("http://www.gmail.com"));
                startActivity(opengmail);
                break;
                //end of gmail
            case Commands.oCalculator: case Commands.oCalculator1: case Commands.oCalculator2: case Commands.oCalculator3: case Commands.oCalculator4:
                Intent launchIntent5 = getPackageManager().getLaunchIntentForPackage("com.android.calculator2");
                if (launchIntent5 != null) {
                    startActivity(launchIntent5);//null pointer check in case package name was not found
                }
                break;
            case Commands.oMusic: case Commands.oMusic1: case Commands.oMusic2: case Commands.oMusic3: case Commands.oMusic4: case Commands.oMusic5: case Commands.oMusic6: case Commands.oMusic7:
                try {
                    Intent intent6 = Intent.makeMainSelectorActivity(Intent.ACTION_MAIN, Intent.CATEGORY_APP_MUSIC);
                    startActivity(intent6);
                } catch (Exception e) {
                    Log.d(TAG, "Exception for launching music player "+e);
                }
                break;
            case Commands.oDiscord: case Commands.oDiscord1: case Commands.oDiscord2: case Commands.oDiscord3: case Commands.oDiscord4:
                Intent launchIntent7 = getPackageManager().getLaunchIntentForPackage("com.discord");
                if (launchIntent7 != null) {
                    startActivity(launchIntent7);//null pointer check in case package name was not found
                }
                else if(launchIntent7 == null){
                    DialogInterface.OnClickListener dialogClickListener = new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialog, int which) {
                            switch (which){
                                case DialogInterface.BUTTON_POSITIVE:
                                    try
                                    {
                                        Runtime.getRuntime().exec(new String[]{"/sbin/su", "-c", "sleep 3; input tap 580 711"});
                                    } catch (Exception ex) {
                                        Log.e(TAG, "Error ", ex);
                                    }
                                    Toast.makeText(getBaseContext(), "Mengunduh Discord ", Toast.LENGTH_SHORT).show();
                                    Intent webPS = new Intent();
                                    webPS.setAction(Intent.ACTION_VIEW);
                                    webPS.addCategory(Intent.CATEGORY_BROWSABLE);
                                    webPS.setData(Uri.parse("http://play.google.com/store/apps/details?id=com.discord"));
                                    startActivity(webPS);
                                    break;

                                case DialogInterface.BUTTON_NEGATIVE:
                                    Toast.makeText(getBaseContext(), "Say what you need", Toast.LENGTH_SHORT).show();
                                    try {
                                        Runtime.getRuntime().exec(new String[]{"/sbin/su", "-c", "sleep .5; input tap 596 971"});
                                    } catch (Exception ex) {
                                        Log.e(TAG, "Error ", ex);
                                    }
                            }
                        }
                    };

                    AlertDialog.Builder builder = new AlertDialog.Builder(context);
                    builder.setMessage("Aplikasi Discord tidak terinstal , apakah ingin mengunduh Discord?").setPositiveButton("Yes", dialogClickListener)
                            .setNegativeButton("No", dialogClickListener).show();
                }
                break;
            case Commands.oGdrive: case Commands.oGdrive1: case Commands.oGdrive2: case Commands.oGdrive3: case Commands.oGdrive4:
                Intent launchIntent8 = getPackageManager().getLaunchIntentForPackage("com.google.android.apps.docs");
                if (launchIntent8 != null) {
                    startActivity(launchIntent8);//null pointer check in case package name was not found
                }
                else if(launchIntent8 == null){
                    DialogInterface.OnClickListener dialogforgdrive = new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialog, int which) {
                            switch (which){
                                case DialogInterface.BUTTON_POSITIVE:
                                    try
                                    {
                                        Runtime.getRuntime().exec(new String[]{"/sbin/su", "-c", "sleep 3; input tap 580 711"});
                                    } catch (Exception ex) {
                                        Log.e(TAG, "Error ", ex);
                                    }
                                    Toast.makeText(getBaseContext(), "Mengunduh Google Drive", Toast.LENGTH_SHORT).show();
                                    Intent webPS = new Intent();
                                    webPS.setAction(Intent.ACTION_VIEW);
                                    webPS.addCategory(Intent.CATEGORY_BROWSABLE);
                                    webPS.setData(Uri.parse("http://play.google.com/store/apps/details?id=com.google.android.apps.docs"));
                                    startActivity(webPS);
                                    break;

                                case DialogInterface.BUTTON_NEGATIVE:
                                    Toast.makeText(getBaseContext(), "Say what you need", Toast.LENGTH_SHORT).show();
                                    try {
                                        Runtime.getRuntime().exec(new String[]{"/sbin/su", "-c", "sleep .5; input tap 596 971"});
                                    } catch (Exception ex) {
                                        Log.e(TAG, "Error ", ex);
                                    }
                            }
                        }
                    };

                    AlertDialog.Builder builder = new AlertDialog.Builder(context);
                    builder.setMessage("Aplikasi Google Drive tidak terinstal , apakah ingin mengunduh Google drive?").setPositiveButton("Yes", dialogforgdrive)
                            .setNegativeButton("No", dialogforgdrive).show();


                }
                break;
            case Commands.oTelegram: case Commands.oTelegram1: case Commands.oTelegram2: case Commands.oTelegram3: case Commands.oTelegram4:
                Intent launchIntentotg = getPackageManager().getLaunchIntentForPackage("org.telegram.messenger");
                if (launchIntentotg != null) {

                    startActivity(launchIntentotg);//null pointer check in case package name was not found
                }
                else if (launchIntentotg == null) {
                    DialogInterface.OnClickListener dialogfortelegram = new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialog, int which) {
                            switch (which){
                                case DialogInterface.BUTTON_POSITIVE:
                                    try
                                    {
                                        Runtime.getRuntime().exec(new String[]{"/sbin/su", "-c", "sleep 3; input tap 580 711"});
                                    } catch (Exception ex) {
                                        Log.e(TAG, "Error ", ex);
                                    }
                                    Toast.makeText(getBaseContext(), "Mengunduh Telegram", Toast.LENGTH_SHORT).show();
                                    Intent webPS = new Intent();
                                    webPS.setAction(Intent.ACTION_VIEW);
                                    webPS.addCategory(Intent.CATEGORY_BROWSABLE);
                                    webPS.setData(Uri.parse("http://play.google.com/store/apps/details?id=org.telegram.messenger"));
                                    startActivity(webPS);
                                    break;

                                case DialogInterface.BUTTON_NEGATIVE:
                                    Toast.makeText(getBaseContext(), "Say what you need", Toast.LENGTH_SHORT).show();
                                    try {
                                        Runtime.getRuntime().exec(new String[]{"/sbin/su", "-c", "sleep .5; input tap 596 971"});
                                    } catch (Exception ex) {
                                        Log.e(TAG, "Error ", ex);
                                    }
                                    break;
                            }
                        }
                    };

                    AlertDialog.Builder builder = new AlertDialog.Builder(context);
                    builder.setMessage("Aplikasi Telegram tidak terinstal , apakah ingin mengunduh Telegram?").setPositiveButton("Yes", dialogfortelegram)
                            .setNegativeButton("No", dialogfortelegram).show();
                    

                }
                break;

                //youtube "website and app"
            case Commands.oYoutube: case Commands.oYoutube1: case Commands.oYoutube2: case Commands.oYoutube3: case Commands.oYoutube4:
                Intent launchIntentoyt = getPackageManager().getLaunchIntentForPackage("com.google.android.youtube");
                if (launchIntentoyt != null) {

                    startActivity(launchIntentoyt);//null pointer check in case package name was not found
                }
                else if (launchIntentoyt == null) {
                    DialogInterface.OnClickListener dialogforyoutube = new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialog, int which) {
                            switch (which){
                                case DialogInterface.BUTTON_POSITIVE:
                                    try
                                    {
                                        Runtime.getRuntime().exec(new String[]{"/sbin/su", "-c", "sleep 3; input tap 580 711"});
                                    } catch (Exception ex) {
                                        Log.e(TAG, "Error ", ex);
                                    }

                                    Intent webPS = new Intent();
                                    webPS.setAction(Intent.ACTION_VIEW);
                                    webPS.addCategory(Intent.CATEGORY_BROWSABLE);
                                    webPS.setData(Uri.parse("http://play.google.com/store/apps/details?id=com.google.android.youtube"));
                                    startActivity(webPS);
                                    break;

                                case DialogInterface.BUTTON_NEGATIVE:
                                    Toast.makeText(getBaseContext(), "Say what you need", Toast.LENGTH_SHORT).show();
                                    try {
                                        Runtime.getRuntime().exec(new String[]{"/sbin/su", "-c", "sleep .5; input tap 596 971"});
                                    } catch (Exception ex) {
                                        Log.e(TAG, "Error ", ex);
                                    }
                                    break;
                            }
                        }
                    };

                    AlertDialog.Builder builder = new AlertDialog.Builder(context);
                    builder.setMessage("Aplikasi Youtube tidak terinstal , apakah ingin mengunduh Youtube?").setPositiveButton("Yes", dialogforyoutube)
                            .setNegativeButton("No", dialogforyoutube).show();
                    

                }
                break;
            case Commands.youtube: case Commands.youtube2: case Commands.youtube3: case Commands.youtube4: case Commands.youtube5: case Commands.youtube6: case Commands.youtube1:
                Intent openyoutube = new Intent();
                openyoutube.setAction(Intent.ACTION_VIEW);
                openyoutube.addCategory(Intent.CATEGORY_BROWSABLE);
                openyoutube.setData(Uri.parse("http://www.youtube.com"));
                startActivity(openyoutube);
                break;
                //end of youtube

            case Commands.oNetflix: case Commands.oNetflix1: case Commands.oNetflix2: case Commands.oNetflix3: case Commands.oNetflix4:
                Intent launchIntentonf = getPackageManager().getLaunchIntentForPackage("com.netflix.mediaclient");
                if (launchIntentonf != null) {

                    startActivity(launchIntentonf);//null pointer check in case package name was not found
                }
                else if (launchIntentonf == null) {
                    DialogInterface.OnClickListener dialogfornetflix = new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialog, int which) {
                            switch (which){
                                case DialogInterface.BUTTON_POSITIVE:
                                    try
                                    {
                                        Runtime.getRuntime().exec(new String[]{"/sbin/su", "-c", "sleep 3; input tap 580 711"});
                                    } catch (Exception ex) {
                                        Log.e(TAG, "Error ", ex);
                                    }
                                    Toast.makeText(getBaseContext(), "Mengunduh Netflix", Toast.LENGTH_SHORT).show();
                                    Intent webPS = new Intent();
                                    webPS.setAction(Intent.ACTION_VIEW);
                                    webPS.addCategory(Intent.CATEGORY_BROWSABLE);
                                    webPS.setData(Uri.parse("http://play.google.com/store/apps/details?id=com.netflix.mediaclient"));
                                    startActivity(webPS);

                                    break;

                                case DialogInterface.BUTTON_NEGATIVE:
                                    Toast.makeText(getBaseContext(), "Say what you need", Toast.LENGTH_SHORT).show();
                                    try {
                                        Runtime.getRuntime().exec(new String[]{"/sbin/su", "-c", "sleep .5; input tap 596 971"});
                                    } catch (Exception ex) {
                                        Log.e(TAG, "Error ", ex);
                                    }
                                    break;
                            }
                        }
                    };

                    AlertDialog.Builder builder = new AlertDialog.Builder(context);
                    builder.setMessage("Aplikasi Netflix tidak terinstal , apakah ingin mengunduh Netflix?").setPositiveButton("Yes", dialogfornetflix)
                            .setNegativeButton("No", dialogfornetflix).show();

                }
                break;
                //Tokopedia "website and app"
            case Commands.oTokopedia: case Commands.oTokopedia1: case Commands.oTokopedia2: case Commands.oTokopedia3: case Commands.oTokopedia4: case Commands.oTokopedia5:
                Intent tokopedia = getPackageManager().getLaunchIntentForPackage("com.tokopedia.tkpd");
                if (tokopedia != null) {

                    startActivity(tokopedia);//null pointer check in case package name was not found
                }
                else if (tokopedia == null) {
                    DialogInterface.OnClickListener dialogfortokopedia = new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialog, int which) {
                            switch (which){
                                case DialogInterface.BUTTON_POSITIVE:
                                    try
                                    {
                                        Runtime.getRuntime().exec(new String[]{"/sbin/su", "-c", "sleep 3; input tap 580 711"});
                                    } catch (Exception ex) {
                                        Log.e(TAG, "Error ", ex);
                                    }
                                    Toast.makeText(getBaseContext(), "Mengunduh Tokopedia", Toast.LENGTH_SHORT).show();
                                    Intent webPS = new Intent();
                                    webPS.setAction(Intent.ACTION_VIEW);
                                    webPS.addCategory(Intent.CATEGORY_BROWSABLE);
                                    webPS.setData(Uri.parse("http://play.google.com/store/apps/details?id=com.tokopedia.tkpd"));
                                    startActivity(webPS);
                                    break;

                                case DialogInterface.BUTTON_NEGATIVE:
                                    Toast.makeText(getBaseContext(), "Say what you need", Toast.LENGTH_SHORT).show();
                                    try {
                                        Runtime.getRuntime().exec(new String[]{"/sbin/su", "-c", "sleep .5; input tap 596 971"});
                                    } catch (Exception ex) {
                                        Log.e(TAG, "Error ", ex);
                                    }
                                    break;
                            }
                        }
                    };

                    AlertDialog.Builder builder = new AlertDialog.Builder(context);
                    builder.setMessage("Aplikasi Tokopedia tidak terinstal , apakah ingin mengunduh Tokopedia?").setPositiveButton("Yes", dialogfortokopedia)
                            .setNegativeButton("No", dialogfortokopedia).show();


                }
                break;
            case Commands.tokopedia: case Commands.tokopedia2: case Commands.tokopedia3: case Commands.tokopedia4: case Commands.tokopedia6: case Commands.tokopedia7:
                Intent opentokopedia = new Intent();
                opentokopedia.setAction(Intent.ACTION_VIEW);
                opentokopedia.addCategory(Intent.CATEGORY_BROWSABLE);
                opentokopedia.setData(Uri.parse("http://www.tokopedia.com"));
                startActivity(opentokopedia);
                break;
                //end of tokopedia

                //bukalapak "website and app"
            case Commands.oBukalapak: case Commands.oBukalapak1: case Commands.oBukalapak2: case Commands.oBukalapak3: case Commands.oBukalapak4: case Commands.oBukalapak5:
                Intent bukalapak = getPackageManager().getLaunchIntentForPackage("com.bukalapak.android");
                if (bukalapak != null) {

                    startActivity(bukalapak);//null pointer check in case package name was not found
                }
                else if (bukalapak == null) {
                    DialogInterface.OnClickListener dialogClickListener = new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialog, int which) {
                            switch (which){
                                case DialogInterface.BUTTON_POSITIVE:
                                    try
                                    {
                                        Runtime.getRuntime().exec(new String[]{"/sbin/su", "-c", "sleep 3; input tap 580 711"});
                                    } catch (Exception ex) {
                                        Log.e(TAG, "Error ", ex);
                                    }
                                    Toast.makeText(getBaseContext(), "Mengunduh BukaLapak", Toast.LENGTH_SHORT).show();
                                    Intent webPS = new Intent();
                                    webPS.setAction(Intent.ACTION_VIEW);
                                    webPS.addCategory(Intent.CATEGORY_BROWSABLE);
                                    webPS.setData(Uri.parse("http://play.google.com/store/apps/details?id=com.bukalapak.android"));
                                    startActivity(webPS);
                                    break;

                                case DialogInterface.BUTTON_NEGATIVE:
                                    Toast.makeText(getBaseContext(), "Say what you need", Toast.LENGTH_SHORT).show();
                                    try {
                                        Runtime.getRuntime().exec(new String[]{"/sbin/su", "-c", "sleep .5; input tap 596 971"});
                                    } catch (Exception ex) {
                                        Log.e(TAG, "Error ", ex);
                                    }
                                    break;
                            }
                        }
                    };

                    AlertDialog.Builder builder = new AlertDialog.Builder(context);
                    builder.setMessage("Aplikasi Bukalapak tidak terinstal , apakah ingin mengunduh Bukalapak?").setPositiveButton("Yes", dialogClickListener)
                            .setNegativeButton("No", dialogClickListener).show();

                }
                break;
            case Commands.bukalapak: case Commands.bukalapak2: case Commands.bukalapak3:
                Intent openbukalapak = new Intent();
                openbukalapak.setAction(Intent.ACTION_VIEW);
                openbukalapak.addCategory(Intent.CATEGORY_BROWSABLE);
                openbukalapak.setData(Uri.parse("http://www.bukalapak.com"));
                startActivity(openbukalapak);
                break;
                //end of bukalapak

                //bli bli "website and app"
            case Commands.oBlibli: case Commands.oBlibli1: case Commands.oBlibli2: case Commands.oBlibli3: case Commands.oBlibli4: case Commands.oBlibli5:
                Intent blibli = getPackageManager().getLaunchIntentForPackage("blibli.mobile.commerce");
                if (blibli != null) {

                    startActivity(blibli);//null pointer check in case package name was not found
                }
                else if (blibli == null) {
                    DialogInterface.OnClickListener dialogforblibli = new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialog, int which) {
                            switch (which){
                                case DialogInterface.BUTTON_POSITIVE:
                                    try
                                    {
                                        Runtime.getRuntime().exec(new String[]{"/sbin/su", "-c", "sleep 3; input tap 580 711"});
                                    } catch (Exception ex) {
                                        Log.e(TAG, "Error ", ex);
                                    }
                                    Toast.makeText(getBaseContext(), "Mengunduh Bli Bli", Toast.LENGTH_SHORT).show();
                                    Intent webPS = new Intent();
                                    webPS.setAction(Intent.ACTION_VIEW);
                                    webPS.addCategory(Intent.CATEGORY_BROWSABLE);
                                    webPS.setData(Uri.parse("http://play.google.com/store/apps/details?id=blibli.mobile.commerce"));
                                    startActivity(webPS);

                                    break;

                                case DialogInterface.BUTTON_NEGATIVE:
                                    Toast.makeText(getBaseContext(), "Say what you need", Toast.LENGTH_SHORT).show();
                                    try {
                                        Runtime.getRuntime().exec(new String[]{"/sbin/su", "-c", "sleep .5; input tap 596 971"});
                                    } catch (Exception ex) {
                                        Log.e(TAG, "Error ", ex);
                                    }
                                    break;
                            }
                        }
                    };

                    AlertDialog.Builder builder = new AlertDialog.Builder(context);
                    builder.setMessage("Aplikasi Bli Bli tidak terinstal , apakah ingin mengunduh Bli Bli?").setPositiveButton("Yes", dialogforblibli)
                            .setNegativeButton("No", dialogforblibli).show();

                }
                break;
            case Commands.blibli: case Commands.blibli2: case Commands.blibli3: case Commands.blibli5:
                Intent openblibli = new Intent();
                openblibli.setAction(Intent.ACTION_VIEW);
                openblibli.addCategory(Intent.CATEGORY_BROWSABLE);
                openblibli.setData(Uri.parse("http://www.blibli.com"));
                startActivity(openblibli);
                break;
                //end of blibli

            case Commands.oGojek: case Commands.oGojek1: case Commands.oGojek2: case Commands.oGojek3: case Commands.oGojek4: case Commands.oGojek5:
                Intent gojek = getPackageManager().getLaunchIntentForPackage("com.gojek.app");
                if (gojek != null) {

                    startActivity(gojek);//null pointer check in case package name was not found
                }
                else if (gojek == null) {
                    DialogInterface.OnClickListener dialogforgojek = new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialog, int which) {
                            switch (which){
                                case DialogInterface.BUTTON_POSITIVE:
                                    try
                                    {
                                        Runtime.getRuntime().exec(new String[]{"/sbin/su", "-c", "sleep 3; input tap 580 711"});
                                    } catch (Exception ex) {
                                        Log.e(TAG, "Error ", ex);
                                    }
                                    Toast.makeText(getBaseContext(), "Mengunduh Gojek", Toast.LENGTH_SHORT).show();
                                    Intent webPS = new Intent();
                                    webPS.setAction(Intent.ACTION_VIEW);
                                    webPS.addCategory(Intent.CATEGORY_BROWSABLE);
                                    webPS.setData(Uri.parse("http://play.google.com/store/apps/details?id=com.gojek.app"));
                                    startActivity(webPS);
                                    break;

                                case DialogInterface.BUTTON_NEGATIVE:
                                    Toast.makeText(getBaseContext(), "Say what you need", Toast.LENGTH_SHORT).show();
                                    try {
                                        Runtime.getRuntime().exec(new String[]{"/sbin/su", "-c", "sleep .5; input tap 596 971"});
                                    } catch (Exception ex) {
                                        Log.e(TAG, "Error ", ex);
                                    }
                                    break;
                            }
                        }
                    };

                    AlertDialog.Builder builder = new AlertDialog.Builder(context);
                    builder.setMessage("Aplikasi Gojek tidak terinstal , apakah ingin mengunduh Gojek?").setPositiveButton("Yes", dialogforgojek)
                            .setNegativeButton("No", dialogforgojek).show();

                }
                break;

                //Amazon "website and app"
            case Commands.oAmazon: case Commands.oAmazon1: case Commands.oAmazon2: case Commands.oAmazon3:
                Intent Amazon = getPackageManager().getLaunchIntentForPackage("com.amazon.mShop.android.shopping");
                if (Amazon != null) {

                    startActivity(Amazon);//null pointer check in case package name was not found
                }
                else if (Amazon == null) {
                    DialogInterface.OnClickListener dialogforamazon = new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialog, int which) {
                            switch (which){
                                case DialogInterface.BUTTON_POSITIVE:
                                    try
                                    {
                                        Runtime.getRuntime().exec(new String[]{"/sbin/su", "-c", "sleep 3; input tap 580 711"});
                                    } catch (Exception ex) {
                                        Log.e(TAG, "Error ", ex);
                                    }
                                    Toast.makeText(getBaseContext(), "Mengunduh Amazon", Toast.LENGTH_SHORT).show();
                                    Intent webPS = new Intent();
                                    webPS.setAction(Intent.ACTION_VIEW);
                                    webPS.addCategory(Intent.CATEGORY_BROWSABLE);
                                    webPS.setData(Uri.parse("http://play.google.com/store/apps/details?id=com.amazon.mShop.android.shopping"));
                                    startActivity(webPS);

                                    break;

                                case DialogInterface.BUTTON_NEGATIVE:
                                    Toast.makeText(getBaseContext(), "Say what you need", Toast.LENGTH_SHORT).show();
                                    try {
                                        Runtime.getRuntime().exec(new String[]{"/sbin/su", "-c", "sleep .5; input tap 596 971"});
                                    } catch (Exception ex) {
                                        Log.e(TAG, "Error ", ex);
                                    }
                                    break;
                            }
                        }
                    };

                    AlertDialog.Builder builder = new AlertDialog.Builder(context);
                    builder.setMessage("Aplikasi Amazon tidak terinstal , apakah ingin mengunduh Amazon?").setPositiveButton("Yes", dialogforamazon)
                            .setNegativeButton("No", dialogforamazon).show();

                }
                break;
            case Commands.amazon: case Commands.amazon1: case Commands.amazon2: case Commands.amazon3: case Commands.amazon5:
                Intent openamazon = new Intent();
                openamazon.setAction(Intent.ACTION_VIEW);
                openamazon.addCategory(Intent.CATEGORY_BROWSABLE);
                openamazon.setData(Uri.parse("http://www.amazon.com"));
                startActivity(openamazon);
                break;
                //end of Amazon

            //Yahoo "website and app"
            case Commands.oYahoo: case Commands.oYahoo1: case Commands.oYahoo2: case Commands.oYahoo3: case Commands.oYahoo4:
                Intent yahoo = getPackageManager().getLaunchIntentForPackage("com.yahoo.mobile.client.android.mail");
                if (yahoo != null) {

                    startActivity(yahoo);//null pointer check in case package name was not found
                }
                else if (yahoo == null) {
                    DialogInterface.OnClickListener dialogforyahoo = new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialog, int which) {
                            switch (which){
                                case DialogInterface.BUTTON_POSITIVE:
                                    try
                                    {
                                        Runtime.getRuntime().exec(new String[]{"/sbin/su", "-c", "sleep 3; input tap 580 711"});
                                    } catch (Exception ex) {
                                        Log.e(TAG, "Error ", ex);
                                    }
                                    Toast.makeText(getBaseContext(), "Mengumduh Yahoo Mail", Toast.LENGTH_SHORT).show();
                                    Intent webPS = new Intent();
                                    webPS.setAction(Intent.ACTION_VIEW);
                                    webPS.addCategory(Intent.CATEGORY_BROWSABLE);
                                    webPS.setData(Uri.parse("http://play.google.com/store/apps/details?id=com.yahoo.mobile.client.android.mail"));
                                    startActivity(webPS);
                                    break;

                                case DialogInterface.BUTTON_NEGATIVE:
                                    Toast.makeText(getBaseContext(), "Say what you need", Toast.LENGTH_SHORT).show();
                                    try {
                                        Runtime.getRuntime().exec(new String[]{"/sbin/su", "-c", "sleep .5; input tap 596 971"});
                                    } catch (Exception ex) {
                                        Log.e(TAG, "Error ", ex);
                                    }
                                    break;
                            }
                        }
                    };
                    AlertDialog.Builder builder = new AlertDialog.Builder(context);
                    builder.setMessage("Aplikasi Yahoo Mail tidak terinstal , apakah ingin mengunduh Yahoo Mail?").setPositiveButton("Yes", dialogforyahoo)
                            .setNegativeButton("No", dialogforyahoo).show();



                }
                break;
            case Commands.yahoo: case Commands.yahoo1: case Commands.yahoo2: case Commands.yahoo3: case Commands.yahoo4: case Commands.yahoo5: case Commands.yahoo6: case Commands.yahoo7:
                Intent openyahoo = new Intent();
                openyahoo.setAction(Intent.ACTION_VIEW);
                openyahoo.addCategory(Intent.CATEGORY_BROWSABLE);
                openyahoo.setData(Uri.parse("http://www.yahoo.com"));
                startActivity(openyahoo);
                break;
                //end of yahoo

                //Iflix
            case Commands.oIflix: case Commands.oIflix1: case Commands.oIflix2: case Commands.oIflix3: case Commands.oIflix4:
                Intent iflix = getPackageManager().getLaunchIntentForPackage("iflix.play");
                if (iflix != null) {

                    startActivity(iflix);//null pointer check in case package name was not found
                }
                else if (iflix == null) {
                    DialogInterface.OnClickListener dialogforiflix = new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialog, int which) {
                            switch (which){
                                case DialogInterface.BUTTON_POSITIVE:
                                    try
                                    {
                                        Runtime.getRuntime().exec(new String[]{"/sbin/su", "-c", "sleep 3; input tap 580 711"});
                                    } catch (Exception ex) {
                                        Log.e(TAG, "Error ", ex);
                                    }
                                    Toast.makeText(getBaseContext(), "Mengunduh Iflix", Toast.LENGTH_SHORT).show();
                                    Intent webPS = new Intent();
                                    webPS.setAction(Intent.ACTION_VIEW);
                                    webPS.addCategory(Intent.CATEGORY_BROWSABLE);
                                    webPS.setData(Uri.parse("https://play.google.com/store/apps/details?id=iflix.play&hl=in"));
                                    startActivity(webPS);
                                    break;

                                case DialogInterface.BUTTON_NEGATIVE:
                                    Toast.makeText(getBaseContext(), "Say what you need", Toast.LENGTH_SHORT).show();
                                    try {
                                        Runtime.getRuntime().exec(new String[]{"/sbin/su", "-c", "sleep .5; input tap 596 971"});
                                    } catch (Exception ex) {
                                        Log.e(TAG, "Error ", ex);
                                    }
                                    break;
                            }
                        }
                    };
                    AlertDialog.Builder builder = new AlertDialog.Builder(context);
                    builder.setMessage("Aplikasi Iflix tidak terinstal , apakah ingin mengunduh Iflix?").setPositiveButton("Yes", dialogforiflix)
                            .setNegativeButton("No", dialogforiflix).show();



                }
                break;
            case Commands.iflix5: case Commands.iflix6: case Commands.iflix7:
                Intent openiflix = new Intent();
                openiflix.setAction(Intent.ACTION_VIEW);
                openiflix.addCategory(Intent.CATEGORY_BROWSABLE);
                openiflix.setData(Uri.parse("http://www.iflix.com"));
                startActivity(openiflix);
                break;
            //end of iflix


            case Commands.oGrab: case Commands.oGrab1: case Commands.oGrab2: case Commands.oGrab3: case Commands.oGrab4: case Commands.oGrab5: case Commands.oGrab6:
                Intent Grab1 = getPackageManager().getLaunchIntentForPackage("com.grabtaxi.passenger");
                if (Grab1 != null) {

                    startActivity(Grab1);//null pointer check in case package name was not found
                }
                else if (Grab1 == null) {
                    DialogInterface.OnClickListener dialogClickListener = new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialog, int which) {
                            switch (which){
                                case DialogInterface.BUTTON_POSITIVE:
                                    try
                                    {
                                        Runtime.getRuntime().exec(new String[]{"/sbin/su", "-c", "sleep 3; input tap 580 711"});
                                    } catch (Exception ex) {
                                        Log.e(TAG, "Error ", ex);
                                    }
                                    Toast.makeText(getBaseContext(), "Mengunduh Grab", Toast.LENGTH_SHORT).show();
                                    Intent webPS = new Intent();
                                    webPS.setAction(Intent.ACTION_VIEW);
                                    webPS.addCategory(Intent.CATEGORY_BROWSABLE);
                                    webPS.setData(Uri.parse("http://play.google.com/store/apps/details?id=com.grabtaxi.passenger"));
                                    startActivity(webPS);
                                    break;

                                case DialogInterface.BUTTON_NEGATIVE:
                                    Toast.makeText(getBaseContext(), "Say what you need", Toast.LENGTH_SHORT).show();
                                    try {
                                        Runtime.getRuntime().exec(new String[]{"/sbin/su", "-c", "sleep .5; input tap 596 971"});
                                    } catch (Exception ex) {
                                        Log.e(TAG, "Error ", ex);
                                    }
                                    break;
                            }
                        }
                    };

                    AlertDialog.Builder builder = new AlertDialog.Builder(context);
                    builder.setMessage("Aplikasi Grab tidak terinstal , apakah ingin mengunduh Grab?").setPositiveButton("Yes", dialogClickListener)
                            .setNegativeButton("No", dialogClickListener).show();


                }
                break;
            case Commands.remmodule:
                Toast.makeText(getBaseContext(), "Reminder Module", Toast.LENGTH_SHORT).show();
                Intent intentr = new Intent(MainActivity.this, ReminderModule.class);
                startActivity(intentr);
                break;
            case Commands.Silent: case Commands.Silent1: case Commands.Silent2: case Commands.Silent3:
                Toast.makeText(getBaseContext(), "Silent Mode !!!", Toast.LENGTH_SHORT).show();
                am.setStreamMute(AudioManager.STREAM_NOTIFICATION, true);
                am.setStreamMute(AudioManager.STREAM_ALARM, true);
                am.setStreamMute(AudioManager.STREAM_MUSIC, true);
                am.setStreamMute(AudioManager.STREAM_RING, true);
                am.setStreamMute(AudioManager.STREAM_SYSTEM, true);
                Toast.makeText(getBaseContext(), "Say what you need", Toast.LENGTH_SHORT).show();
                try {
                    Runtime.getRuntime().exec(new String[]{"/sbin/su", "-c", "sleep .5; input tap 596 971"});
                } catch (Exception ex) {
                    Log.e(TAG, "Error ", ex);
                }
                break;
            case Commands.Normal: case Commands.Normal1: case Commands.Normal2:
                Toast.makeText(getBaseContext(), "Normal Mode !!!", Toast.LENGTH_SHORT).show();
                am.setStreamMute(AudioManager.STREAM_NOTIFICATION, false);
                am.setStreamMute(AudioManager.STREAM_ALARM, false);
                am.setStreamMute(AudioManager.STREAM_MUSIC, false);
                am.setStreamMute(AudioManager.STREAM_RING, false);
                am.setStreamMute(AudioManager.STREAM_SYSTEM, false);
                Toast.makeText(getBaseContext(), "Say what you need", Toast.LENGTH_SHORT).show();
                try {
                    Runtime.getRuntime().exec(new String[]{"/sbin/su", "-c", "sleep .5; input tap 596 971"});
                } catch (Exception ex) {
                    Log.e(TAG, "Error ", ex);
                }
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
            case Commands.wifioff: case Commands.wifioff1: case Commands.wifioff2: case Commands.wifioff3:
                Toast.makeText(getBaseContext(), "Wi-Fi is turn off", Toast.LENGTH_SHORT).show();
                WifiManager wifiManageroff = (WifiManager)this.context.getSystemService(Context.WIFI_SERVICE);
                wifiManageroff.setWifiEnabled(false);
                Toast.makeText(getBaseContext(), "Say what you need", Toast.LENGTH_SHORT).show();
                try {
                    Runtime.getRuntime().exec(new String[]{"/sbin/su", "-c", "sleep .5; input tap 596 971"});
                } catch (Exception ex) {
                    Log.e(TAG, "Error ", ex);
                }
                break;
            case Commands.wifion: case Commands.wifion1: case Commands.wifion2: case Commands.wifion3:
                Toast.makeText(getBaseContext(), "Wi-Fi is turn on", Toast.LENGTH_SHORT).show();
                WifiManager wifiManageron = (WifiManager)this.context.getSystemService(Context.WIFI_SERVICE);
                wifiManageron.setWifiEnabled(true);
                Toast.makeText(getBaseContext(), "Say what you need", Toast.LENGTH_SHORT).show();
                try {
                    Runtime.getRuntime().exec(new String[]{"/sbin/su", "-c", "sleep .5; input tap 596 971"});
                } catch (Exception ex) {
                    Log.e(TAG, "Error ", ex);
                }
                break;
                // wifi state end here

                //Bluetooth state on and off
            case Commands.bluetoothOff: case Commands.bluetoothOff1: case Commands.bluetoothOff2: case Commands.bluetoothOff3: case Commands.bluetoothOff4:
                Toast.makeText(getBaseContext(), "Bluetooth is turn off", Toast.LENGTH_SHORT).show();
                BluetoothAdapter mBluetoothAdapteroff = BluetoothAdapter.getDefaultAdapter();
                if (mBluetoothAdapteroff.isEnabled()) {
                    mBluetoothAdapteroff.disable();
                    Toast.makeText(getBaseContext(), "Say what you need", Toast.LENGTH_SHORT).show();
                    try {
                        Runtime.getRuntime().exec(new String[]{"/sbin/su", "-c", "sleep .5; input tap 596 971"});
                    } catch (Exception ex) {
                        Log.e(TAG, "Error ", ex);
                    }
                }
                break;
            case Commands.bluetoothOn: case Commands.bluetoothOn1: case Commands.bluetoothOn2: case Commands.bluetoothOn3: case Commands.bluetoothOn4:
                Toast.makeText(getBaseContext(), "Bluetooth is turn on", Toast.LENGTH_SHORT).show();
                BluetoothAdapter mBluetoothAdapteron = BluetoothAdapter.getDefaultAdapter();
                if (!mBluetoothAdapteron.isEnabled()) {
                    mBluetoothAdapteron.enable();
                    Toast.makeText(getBaseContext(), "Say what you need", Toast.LENGTH_SHORT).show();
                    try {
                        Runtime.getRuntime().exec(new String[]{"/sbin/su", "-c", "sleep .5; input tap 596 971"});
                    } catch (Exception ex) {
                        Log.e(TAG, "Error ", ex);
                    }
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
                    Runtime.getRuntime().exec(new String[]{"/sbin/su", "-c", "sleep 1; am force-stop com.ideotic.edioticideas.aaya"});
                } catch (Exception ex) {
                    Log.e(TAG, "Error ", ex);
                }
                break;
            case Commands.airplane: case Commands.airplane1:
                try {
                    Toast.makeText(getBaseContext(), "Mode Pesawat !!", Toast.LENGTH_SHORT).show();
                    Runtime.getRuntime().exec(new String[]{"/sbin/su", "-c", "settings put global airplane_mode_on 1"});
                    Runtime.getRuntime().exec(new String[]{"/sbin/su", "-c", "am broadcast -a android.intent.action.AIRPLANE_MODE"});
                } catch (Exception ex) {
                    Log.e(TAG, "Error ", ex);
                }
                break;
            case Commands.airplaneoff: case Commands.airplaneoff1: case Commands.airplaneoff2:
                try {
                    Toast.makeText(getBaseContext(), "Mode Pesawat !!", Toast.LENGTH_SHORT).show();
                    Runtime.getRuntime().exec(new String[]{"/sbin/su", "-c", "settings put global airplane_mode_on 0"});
                    Runtime.getRuntime().exec(new String[]{"/sbin/su", "-c", "am broadcast -a android.intent.action.AIRPLANE_MODE"});
                } catch (Exception ex) {
                    Log.e(TAG, "Error ", ex);
                }
                break;
            case Commands.killthemall:
                try {
                    Toast.makeText(getBaseContext(), "ALL OF THEM HAS BEEN KILLED !!", Toast.LENGTH_SHORT).show();
                    Runtime.getRuntime().exec(new String[]{"/sbin/su", "-c", "am kill-all"});
                    Runtime.getRuntime().exec(new String[]{"/sbin/su", "-c", "sleep 1; input tap 596 971"});
                } catch (Exception ex) {
                    Log.e(TAG, "Error ", ex);
                }
                break;
            case Commands.rebootRecovery:
                try {
                    Toast.makeText(getBaseContext(), "Rebooting !!", Toast.LENGTH_SHORT).show();
                    Runtime.getRuntime().exec(new String[]{"/sbin/su", "-c", "sleep .5; reboot recovery"});
                } catch (Exception ex) {
                    Log.e(TAG, "Error ", ex);
                }
                break;

                //end of root state
            case Commands.wikipedia: case Commands.wikipedia1: case Commands.wikipedia2: case Commands.wikipedia3: case Commands.wikipedia4: case Commands.wikipedia5: case Commands.wikipedia6:
                Intent openwiki = new Intent();
                openwiki.setAction(Intent.ACTION_VIEW);
                openwiki.addCategory(Intent.CATEGORY_BROWSABLE);
                openwiki.setData(Uri.parse("http://www.wikipedia.com"));
                startActivity(openwiki);
            break;
            //start of experimental feature
            case Commands.instantPicandPrev:
                Intent quckpic = getPackageManager().getLaunchIntentForPackage("org.cyanogenmod.snap");
                if (quckpic != null) {

                    startActivity(quckpic);//null pointer check in case package name was not found
                    try {
                        Runtime.getRuntime().exec(new String[]{"/sbin/su", "-c", "sleep 1; input tap 606 1664; sleep 1; input tap 180 1653; sleep .5; input tap 590 957;"});
                    } catch (Exception ex) {
                        Log.e(TAG, "Error ", ex);
                    }

                }
                break;
            case Commands.mlbb: case Commands.mlbb1: case Commands.mlbb2: case Commands.mlbb3: case Commands.mlbb4:
                Intent mlbb = getPackageManager().getLaunchIntentForPackage("com.mobile.legends");

                if (mlbb != null) {
                    startActivity(mlbb);
                }
                    else
                    {
                        tts.speak(ryanmaulana, TextToSpeech.QUEUE_FLUSH, null);
                    }


                break;
            case Commands.takeApic: case Commands.takeApic1: case Commands.takeApic2: case Commands.takeApic3:
                Intent takeApic = getPackageManager().getLaunchIntentForPackage("org.cyanogenmod.snap");

                if (takeApic != null) {

                    startActivity(takeApic);//null pointer check in case package name was not found
                  try {
                   Runtime.getRuntime().exec(new String[]{"/sbin/su", "-c", "sleep 2; input tap 1052 1675; input tap 1050 1569; sleep .5; input tap 606 1664;"});
               } catch (Exception ex) {
                Log.e(TAG, "Error ", ex);
            }

                }
                break;
            case Commands.takeAvid: case Commands.takeAvid1: case Commands.takeAvid2: case Commands.takeAvid3:
                Intent takeAvid = getPackageManager().getLaunchIntentForPackage("org.cyanogenmod.snap");
                if (takeAvid != null) {

                    startActivity(takeAvid);//null pointer check in case package name was not found
                    try {
                        Runtime.getRuntime().exec(new String[]{"/sbin/su", "-c", "sleep 1; input tap 1059 1675; input tap 1047 1435; sleep .5; input tap 606 1664;"});
                    } catch (Exception ex) {
                        Log.e(TAG, "Error ", ex);
                    }

                }
                break;
            case Commands.lowscreenbright: case Commands.lowscreenbright1: case Commands.lowscreenbright2: case Commands.lowscreenbright3: case Commands.lowscreenbright4:
                WindowManager.LayoutParams lp = getWindow().getAttributes();
                lp.screenBrightness = 1 / 100.0f;
                getWindow().setAttributes(lp);
                break;
            case Commands.maxscreenbright: case Commands.maxscreenbright1: case Commands.maxscreenbright2: case Commands.maxscreenbright3: case Commands.maxscreenbright4:

                try {
                    Runtime.getRuntime().exec(new String[]{"/sbin/su", "-c", "input swipe 0 0 550 1743; input swipe 0 0 665 1069; input tap 950 224; input tap 1126 1433;"});
                    Runtime.getRuntime().exec(new String[]{"/sbin/su", "-c", "sleep 3.5; input tap 596 971"});
                } catch (Exception ex) {
                    Log.e(TAG, "Error ", ex);
                }
                break;
            case Commands.balancescreenbright: case Commands.balancescreenbright1: case Commands.balancescreenbright2:
                try {
                    Runtime.getRuntime().exec(new String[]{"/sbin/su", "-c", "input swipe 0 0 550 1743; sleep .5; input swipe 0 0 665 1069; input tap 612 212; input tap 1126 1433;"});
                    Runtime.getRuntime().exec(new String[]{"/sbin/su", "-c", "sleep 3.5; input tap 596 971"});
                } catch (Exception ex) {
                    Log.e(TAG, "Error ", ex);
                }
                break;
            case Commands.RecordVoice: case Commands.RecordVoice1: case Commands.RecordVoice2:
                    Intent voicerecordlineage = getPackageManager().getLaunchIntentForPackage("org.lineageos.recorder");
                    Intent voicerecordsamsung = getPackageManager().getLaunchIntentForPackage("com.sec.android.app.voicenote");
                    if (voicerecordlineage != null) {

                        startActivity(voicerecordlineage);//null pointer check in case package name was not found
                        try {
                            Runtime.getRuntime().exec(new String[]{"/sbin/su", "-c", "sleep 1; input tap 592 1381;"});
                        } catch (Exception ex) {
                            Log.e(TAG, "Error ", ex);
                        }
                        //currently only support local recorder for auto record function
                    }
                    else if (voicerecordlineage == null){
                        startActivity(voicerecordsamsung);
                    }
                break;
            case Commands.recordscreen: case Commands.recordscreen1: case Commands.recordscreen2: case Commands.recordscreen3: case Commands.recordscreen4:
                        Intent screenrecord = getPackageManager().getLaunchIntentForPackage("org.lineageos.recorder");

                        if (screenrecord != null) {

                            startActivity(screenrecord);//null pointer check in case package name was not found
                            try {
                                Runtime.getRuntime().exec(new String[]{"/sbin/su", "-c", "sleep 1; input tap 599 488;"});
                            } catch (Exception ex) {
                                Log.e(TAG, "Error ", ex);
                            }
                            //currently only support local recorder for auto record screen function
                        }
                    break;
            case Commands.turnoffscreen:
                    try {
                        Runtime.getRuntime().exec(new String[]{"/sbin/su", "-c", "input tap 959 11"});
                        Runtime.getRuntime().exec(new String[]{"/sbin/su", "-c", "sleep .1; input tap 959 11"});
                    } catch (Exception ex) {
                        Log.e(TAG, "Error ", ex);
                    }
                break;
                //end of experimental feature
            case Commands.movies: case Commands.movies1: case Commands.movies2: case Commands.movies3: case Commands.movies4: case Commands.movies5: case Commands.movies6:
                Intent openmovies = new Intent();
                openmovies.setAction(Intent.ACTION_VIEW);
                openmovies.addCategory(Intent.CATEGORY_BROWSABLE);
                openmovies.setData(Uri.parse("http://www.indoxxi.tube.com"));
                startActivity(openmovies);
                break;
            case Commands.composeE:
                Intent composeE=new Intent(Intent.ACTION_SEND);
                String[] recipients={""+emerEmail};
                composeE.putExtra(composeE.EXTRA_EMAIL, recipients);
                composeE.putExtra(composeE.EXTRA_SUBJECT," ");
                composeE.putExtra(composeE.EXTRA_TEXT,"Isi Email..");
                composeE.putExtra(composeE.EXTRA_CC,"yoshua@mail");
                composeE.setType("text/html");
                composeE.setPackage("com.google.android.gm");
                startActivity(Intent.createChooser(composeE, "Send mail"));
                break;
            case Commands.callMom:
                startActivity(new Intent(Intent.ACTION_DIAL, Uri.fromParts("tel", ibu, null)));
                break;
            case Commands.callDad:
                startActivity(new Intent(Intent.ACTION_DIAL, Uri.fromParts("tel", bapak, null)));

                break;
            case Commands.oLine: case Commands.oLine1: case Commands.oLine2: case Commands.oLine3: case Commands.oLine4: case Commands.oLine5: case Commands.oLine6: case Commands.oLine7: case Commands.oLine8: case Commands.oLine9: case Commands.oLine10: case Commands.oLine11: case Commands.oLine12:
                Intent Line1 = getPackageManager().getLaunchIntentForPackage("jp.naver.line.android");
                if (Line1 != null) {

                    startActivity(Line1);//null pointer check in case package name was not found
                }
                else if (Line1 == null) {
                    DialogInterface.OnClickListener dialogClickListener = new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialog, int which) {
                            switch (which){
                                case DialogInterface.BUTTON_POSITIVE:
                                    try
                                    {
                                        Runtime.getRuntime().exec(new String[]{"/sbin/su", "-c", "sleep 3; input tap 580 711"});
                                    } catch (Exception ex) {
                                        Log.e(TAG, "Error ", ex);
                                    }
                                    Toast.makeText(getBaseContext(), "Mengunduh Line", Toast.LENGTH_SHORT).show();
                                    Intent webPS = new Intent();
                                    webPS.setAction(Intent.ACTION_VIEW);
                                    webPS.addCategory(Intent.CATEGORY_BROWSABLE);
                                    webPS.setData(Uri.parse("http://play.google.com/store/apps/details?id=jp.naver.line"));
                                    startActivity(webPS);
                                    break;

                                case DialogInterface.BUTTON_NEGATIVE:
                                    Toast.makeText(getBaseContext(), "Say what you need", Toast.LENGTH_SHORT).show();
                                    try {
                                        Runtime.getRuntime().exec(new String[]{"/sbin/su", "-c", "sleep .5; input tap 596 971"});
                                    } catch (Exception ex) {
                                        Log.e(TAG, "Error ", ex);
                                    }
                                    break;
                            }
                        }
                    };

                    AlertDialog.Builder builder = new AlertDialog.Builder(context);
                    builder.setMessage("Aplikasi Line tidak terinstal , apakah ingin mengunduh Line?").setPositiveButton("Yes", dialogClickListener)
                            .setNegativeButton("No", dialogClickListener).show();


                }
                break;
            case Commands.oWhatsapp: case Commands.oWhatsapp1: case Commands.oWhatsapp2: case Commands.oWhatsapp3: case Commands.oWhatsapp4: case Commands.oWhatsapp5: case Commands.oWhatsapp6: case Commands.oWhatsapp7: case Commands.oWhatsapp8: case Commands.oWhatsapp9: case Commands.oWhatsapp10: case Commands.oWhatsapp11:
                Intent WA1 = getPackageManager().getLaunchIntentForPackage("com.whatsapp");
                if (WA1 != null) {

                    startActivity(WA1);//null pointer check in case package name was not found
                }
                else if (WA1 == null) {
                    DialogInterface.OnClickListener dialogClickListener = new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialog, int which) {
                            switch (which){
                                case DialogInterface.BUTTON_POSITIVE:
                                    try
                                    {
                                        Runtime.getRuntime().exec(new String[]{"/sbin/su", "-c", "sleep 3; input tap 580 711"});
                                    } catch (Exception ex) {
                                        Log.e(TAG, "Error ", ex);
                                    }
                                    Toast.makeText(getBaseContext(), "Mengunduh Whatsapp", Toast.LENGTH_SHORT).show();
                                    Intent webPS = new Intent();
                                    webPS.setAction(Intent.ACTION_VIEW);
                                    webPS.addCategory(Intent.CATEGORY_BROWSABLE);
                                    webPS.setData(Uri.parse("http://play.google.com/store/apps/details?id=com.whatsapp"));
                                    startActivity(webPS);
                                    break;

                                case DialogInterface.BUTTON_NEGATIVE:
                                    Toast.makeText(getBaseContext(), "Say what you need", Toast.LENGTH_SHORT).show();
                                    try {
                                        Runtime.getRuntime().exec(new String[]{"/sbin/su", "-c", "sleep .5; input tap 596 971"});
                                    } catch (Exception ex) {
                                        Log.e(TAG, "Error ", ex);
                                    }
                                    break;
                            }
                        }
                    };

                    AlertDialog.Builder builder = new AlertDialog.Builder(context);
                    builder.setMessage("Aplikasi Whatsapp tidak terinstal , apakah ingin mengunduh Whatsapp?").setPositiveButton("Yes", dialogClickListener)
                            .setNegativeButton("No", dialogClickListener).show();


                }
                break;
             
            default:
                DialogInterface.OnClickListener dialogClickListener = new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        switch (which){
                            case DialogInterface.BUTTON_POSITIVE:
                                try {
                                    Toast.makeText(getBaseContext(), "Error, Redirect to Google", Toast.LENGTH_SHORT).show();
                                    Intent intents = new Intent(Intent.ACTION_WEB_SEARCH);
                                    intents.putExtra(SearchManager.QUERY, command);
                                    startActivity(intents);
                                } catch (Exception e) {
                                    // TODO: handle exception
                                }
                                break;

                            case DialogInterface.BUTTON_NEGATIVE:
                                Toast.makeText(getBaseContext(), "Mengubah kata menjadi file !!", Toast.LENGTH_SHORT).show();
                                File root = android.os.Environment.getExternalStorageDirectory();

                                File dir = new File (root.getAbsolutePath() + "/HasilVoiceCommand");
                                dir.mkdirs();
                                File file = new File(dir, "Hasil.txt");
                                try {
                                    FileOutputStream f = new FileOutputStream(file);
                                    PrintWriter pw = new PrintWriter(f);
                                    pw.println(command);
                                    pw.flush();
                                    pw.close();
                                    f.close();
                                } catch (FileNotFoundException e) {
                                    e.printStackTrace();
                                } catch (IOException e) {
                                    e.printStackTrace();
                                }


                                break;
                        }
                    }
                };

                AlertDialog.Builder builder = new AlertDialog.Builder(context);
                builder.setMessage("Apa anda ingin melanjutkan pencarian ke Google ? tekan tidak untuk mengubak kata menjadi file").setPositiveButton("Yes", dialogClickListener)
                        .setNegativeButton("No", dialogClickListener).show();



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
