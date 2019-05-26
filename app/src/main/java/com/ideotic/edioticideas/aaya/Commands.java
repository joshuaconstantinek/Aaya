package com.ideotic.edioticideas.aaya;

/**
 * Created by Mukul on 13-05-2016.
 * edited and continue the development by
 * joshua on may-2019
 */
public class Commands {
    //say my name commands
    public static final String sayModule = "my name";
    public static final String saysecret = "the reason you are born";

    //open additional apps
    public static final String viaApps = "open browser app";
    public static final String vpnApps = "open VPN app";
    public static final String sendMSG = "send message that I am busy";
    public static final String oMessage = "open message";
    public static final String oContacts = "view contact";
    public static final String oSetting = "open setting";
    public static final String oGallery = "open gallery";
    public static final String oCalendar = "open calendar";
    public static final String oGmail = "open Gmail";
    public static final String oCalculator = "I need calculator";
    public static final String oMusic = "open music";
    public static final String oDiscord = "open Discord";
    public static final String oGdrive = "open Google Drive";
    public static final String oInsta = "open Instagram";
    public static final String playstore = "open Play Store";
    public static final String oTwitter = "open Twitter";
    public static final String oFacebook = "open Facebook";
    public static final String oSnapchat = "open Snapchat";
    public static final String oTelegram = "open telegram";
    public static final String oYoutube = "open YouTube";
    public static final String oNetflix = "open Netflix";
    public static final String oTokopedia = "open tokopedia";
    public static final String oBukalapak = "open bukalapak";
    public static final String oBlibli = "open blibli";
    public static final String oGojek = "open gojek";
    public static final String oAmazon = "open Amazon";
    public static final String oGrab = "open grab";
    public static final String oYahoo = "open Yahoo mail";
    public static final String oTerminal = "open terminal";


    //Experimental Feature
    public static final String takeApic = "take a picture";
    public static final String takeAvid = "take a video";
    public static final String instantPicandPrev = "take a quick picture";
    public static final String lowscreenbright = "make it dark";
    public static final String maxscreenbright = "make it bright";
    public static final String balancescreenbright = "make it balance";
    public static final String javascript = "test JavaScript";

    //Open website
    public static final String facebook = "go to Facebook";
    public static final String twitter = "go to Twitter";
    public static final String youtube = "go to YouTube";
    public static final String tokopedia = "go to tokopedia";
    public static final String bukalapak = "go to bukalapak";
    public static final String blibli = "go to blibli";
    public static final String amazon = "go to Amazon";
    public static final String yahoo = "go to Yahoo";
    public static final String gmail = "go to Gmail";
    public static final String wikipedia = "go to Wikipedia";

    //Profiler
    public static final String Silent = "switch to silent mode";
    public static final String Normal = "switch to normal mode";

    //Network state
    public static final String wifioff = "turn off Wi-Fi";
    public static final String wifion = "turn on Wi-Fi";
    public static final String bluetoothOn = "turn on Bluetooth";
    public static final String bluetoothOff = "turn off Bluetooth";

    //root state shit
    public static final String reboot = "reboot phone";
    public static final String rebootRecovery = "reboot to recovery";
    public static final String killthemall = "Kill Them All";
    public static final String selfdestruction = "self destruction";


    //Main Activity Commands
    public static final String mailModule = "mail";
    public static final String callModule = "open phone";
    public static final String remmodule = "reminder";
    public static final String musicModule = "play music";
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
