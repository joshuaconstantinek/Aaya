package com.ideotic.edioticideas.aaya;


import android.content.Context;
import android.media.AudioManager;

public class audioprofile {

    private AudioManager audioManager;


    public audioprofile(Context context){
        audioManager = (AudioManager) context.getSystemService(Context.AUDIO_SERVICE);
    }

    public String changeProfile(String profile){
        switch (profile){
            case Commands.Silent:
                audioManager.setRingerMode(AudioManager.RINGER_MODE_SILENT);
                break;
            case Commands.Normal:
                audioManager.setRingerMode(AudioManager.RINGER_MODE_NORMAL);
                break;
            case Commands.Vibrate:
                audioManager.setRingerMode(AudioManager.RINGER_MODE_VIBRATE);
                break;
            default:
                return "Invalid profile type.";

        }

        return "Profile changed to " + profile;
    }
}
