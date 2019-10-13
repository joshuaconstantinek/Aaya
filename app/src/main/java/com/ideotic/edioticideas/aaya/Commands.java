package com.ideotic.edioticideas.aaya;

/**
 * Created by Mukul on 13-05-2016.
 * edited and continue the development by
 * joshua on may-2019
 * *13/10/19 convert all shit to indonesia*
 */
public class Commands {
    //say my name commands
    public static final String namaSendiri = "Nama saya";
    public static final String sayModule = "my name";
    public static final String saysecret = "the reason you are born";
    public static final String idulfitri = "say something";

    //open additional apps
    public static final String viaApps = "buka aplikasi via";
    public static final String browser = "buka aplikasi seluncur";
    public static final String vpnApps = "buka aplikasi VPN";
    public static final String sendMSG = "noatavailiable";
    public static final String oMessage = "buka pesan";
    public static final String oContacts = "lihat kontak";
    public static final String oSetting = "buka pengaturan";
    public static final String oGallery = "buka Gallery";
    public static final String oCalendar = "buka Kalendar";
    public static final String oGmail = "buka Gmail";
    public static final String oCalculator = "Buka Kalkulator";
    public static final String oMusic = "buka music";
    public static final String oDiscord = "buka Discord";
    public static final String oGdrive = "buka Google Drive";
    public static final String oInsta = "buka Instagram";
    public static final String playstore = "Buka Play Store";
    public static final String oTwitter = "buka Twitter";
    public static final String oFacebook = "buka Facebook";
    public static final String oSnapchat = "buka Snapchat";
    public static final String oTelegram = "buka Telegram";
    public static final String oYoutube = "buka YouTube";
    public static final String oNetflix = "buka netflix";
    public static final String oTokopedia = "buka Tokopedia";
    public static final String oBukalapak = "buka Bukalapak";
    public static final String oBlibli = "buka Blibli";
    public static final String oGojek = "Buka go-jek";
    public static final String oAmazon = "Buka Amazon";
    public static final String oGrab = "Buka grab";
    public static final String oYahoo = "buka Yahoo Mail";
    public static final String oTerminal = "Buka Terminal";


    //Experimental Feature
    public static final String takeApic = "ambil gambar";
    public static final String takeAvid = "ambil video";
    public static final String instantPicandPrev = "ambil gambar cepat";
    public static final String lowscreenbright = "gelapkan layar";
    public static final String maxscreenbright = "terangkan layar";
    public static final String balancescreenbright = "cerahkan sedikit layar";
    public static final String RecordVoice = "rekam suara sekarang";
    public static final String recordscreen = "rekam layar sekarang";



    //Open website
    public static final String facebook = "beralih ke Facebook";
    public static final String twitter = "beralih ke Twitter";
    public static final String youtube = "beralih ke YouTube";
    public static final String tokopedia = "beralih ke Tokopedia";
    public static final String bukalapak = "beralih ke Bukalapak";
    public static final String blibli = "beralih ke Blibli";
    public static final String amazon = "beralih ke Amazon";
    public static final String yahoo = "beralih ke Yahoo";
    public static final String gmail = "beralih ke Gmail";
    public static final String wikipedia = "beralih ke Wikipedia";
    public static final String movies = "beralih ke web film";

    //Profiler
    public static final String Silent = "mode diam";
    public static final String Normal = "mode normal";

    //Network state
    public static final String wifioff = "matikan wifi";
    public static final String wifion = "Nyalakan Wi-Fi";
    public static final String bluetoothOn = "nyalakan Bluetooth";
    public static final String bluetoothOff = "Matikan bluetooth";

    //root state shit
    public static final String reboot = "reset ulang Android";
    public static final String rebootRecovery = "reboot to recovery";
    public static final String killthemall = "Bunuh itu semua";
    public static final String selfdestruction = "penghancuran diri sendiri";
    public static final String turnoffscreen = "matikan layar";
    public static final String galau = "cara menghibur teman yang lagi galau";


    //Main Activity Commands
    public static final String mailModule = "mail";
    public static final String callModule = "open phone";
    public static final String remmodule = "reminder";
    public static final String musicModule = "putar musik";
    public static final String emergencyModule = "emergency";
    public static final String noteModule = "note";
    public static final String helpModule = "help";
    public static final String EMERGENCY = "emergency activity";
    public static final String TIME = "time";

    //Gmail Module Commands
    //search base commands
    public static final String MAIL_FETCH_MAILS = "get mails";
    public static final String MAIL_FEtCH_LABELS = "get labels";
    public static final String MAIL_SEARCH_SUBJECT = "search subject";
    public static final String MAIL_SEARCH_LABELS = "search labels";
    public static final String MAIL_NEXT = "next";
    public static final String MAIL_CONFIRM = "do you want to change any field?";


    //compose based commands
    public static final String MAIL_COMPOSE_MAIL = "compose mail";

    //Weather Module
    public static final String TEMP = "s";
    public static final String WEEK = "w";
    public static final String weather = "how is the weather";

    //Support commands
    public static final String MAIL_SEND = "send";
    public static final String MAIL_SUBJECT = "subject";
    public static final String MAIL_TO = "send to";
    public static final String MAIL_BODY = "body";

    //Phone Module Commands
    public static final String CALL_LOG = "call logs";
    public static final String SMS = "messages";
    public static final String CALL = "call";
    public static final String CONTACTS = "search contact";
    public static final String SMS_SEND = "send message";
    public static final String PHONE_HELP = "help";

    //Reminder Module Commands
    public static final String DATE = "date";
    public static final String MONTH = "month";
    public static final String YEAR = "year";
    public static final String HOUR = "hour";
    public static final String MINUTES = "minutes";
    public static final String TITLE = "title";
    public static final String SET = "setting";

    //Notes Module in Utility
    public static final String TITLEn = "title";
    public static final String BODY = "body";
    public static final String PRIORITY = "priority";
    public static final String SAVE = "saving";
    public static final String MAKE = "make";
    public static final String READ = "read";
    public static final String YES = "yes";
    public static final String NO = "no";

    public static String[] filterCommands(String commandToFilter) {
        String words[] = commandToFilter.split(" ");
        return switchCommands(words);

    }


    public static String[] switchCommands(String[] commandToSwitch) {
        String[] a = new String[4];
        String as = "";
        if (commandToSwitch[0].equals(CALL)) {
            if (commandToSwitch.length == 4) {
                switch (commandToSwitch[0]) {
                    case CALL:
                        if (commandToSwitch[1] != null) {
                            a[0] = commandToSwitch[0];
                            a[1] = commandToSwitch[1] + commandToSwitch[2] + commandToSwitch[3];
                            return a;
                        }
                        break;
                }
            } else if (commandToSwitch[1].equals("logs")) {
                a[0] = commandToSwitch[0] + " " + commandToSwitch[1];
            } else {
                a[0] = commandToSwitch[0];
                a[1] = commandToSwitch[1];
                a[2] = "check";
            }
        } else if (commandToSwitch[0].equals(Commands.SMS)) {
            a[0] = commandToSwitch[0];
        } else if (commandToSwitch[0].equals(Commands.helpModule)) {
            a[0] = commandToSwitch[0];
        } else if (commandToSwitch[0].equals(Commands.PHONE_HELP)) {
            a[0] = commandToSwitch[0];
            a[1] = "";
        } else if (commandToSwitch.length == 1) {
            a[0] = "";
        } else {
            switch (commandToSwitch[0] + " " + commandToSwitch[1]) {
                case MAIL_SEARCH_SUBJECT:
                    a[0] = commandToSwitch[0] + " " + commandToSwitch[1];
                    if (commandToSwitch[2] != null) {
                        a[1] = commandToSwitch[2];
                    }
                    break;
                case MAIL_FETCH_MAILS:
                    a[0] = commandToSwitch[0] + " " + commandToSwitch[1];
                    try {
                        a[1] = commandToSwitch[2];
                    } catch (IndexOutOfBoundsException e) {
                        a[1] = "";
                    }
                    break;
                case CONTACTS:
                    a[0] = commandToSwitch[0] + " " + commandToSwitch[1];
                    if (commandToSwitch[2] != null) {
                        a[1] = commandToSwitch[2];
                    }
                    break;
                default:
                    for (String ab : commandToSwitch)
                        as = as + ab + " ";
                    a[0] = as.trim();
            }
        }
        return a;
    }


}
