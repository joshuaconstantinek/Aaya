package com.ideotic.edioticideas.aaya;


import android.app.Activity;
import android.content.SharedPreferences;
import android.preference.PreferenceManager;
import android.speech.tts.TextToSpeech;
import android.view.View;


public abstract class sayModule extends Activity implements TextToSpeech.OnInitListener  {
   String nama;



    f = getFragmentManager();
    SharedPreferences preferences = PreferenceManager.getDefaultSharedPreferences(getBaseContext());
    nama = "Hi " + preferences.getString(Needs.NAME, " ");
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
}