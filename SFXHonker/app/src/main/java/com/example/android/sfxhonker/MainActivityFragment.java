package com.example.android.sfxhonker;

import android.media.MediaPlayer;
import android.os.Environment;
import android.support.v4.app.Fragment;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ListView;
import android.widget.Toast;

import java.io.File;
import java.io.IOException;

/**
 * A placeholder fragment containing a simple view.
 */
public class MainActivityFragment extends Fragment {

    private ListView fileList;

    public static String[] audioFileExtensions = new String[]{
            "3gp",
            "m4a",
            "aac",
            "flac",
            "mp3",
            "ogg",
            "mid",
            "wav"
    };

    public MainActivityFragment() {
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View rootView = inflater.inflate(R.layout.fragment_main, container, false);

        fileList = (ListView) rootView.findViewById(R.id.sfx_items);
        fileList.setAdapter(new DirectoryAdapter(getContext(), Environment.getExternalStorageDirectory().toString(), audioFileExtensions));
        fileList.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                File f = (File) adapterView.getAdapter().getItem(i);


                //if file doesnt exist anymore
                if (f.exists() == false){
                    Toast.makeText(getContext(), R.string.error_file_not_exist, Toast.LENGTH_LONG).show();
                    return;
                }


                if (f.isDirectory()){
                    //directory, navigate to that directory
                }
                else{
                    //play the sound
                    playSound(f.getPath());
                }
            }
        });

        return rootView;
    }



    private void playSound(String filePath){
        MediaPlayer mp = new MediaPlayer();

        try{
            mp.setDataSource(filePath);
            mp.prepare();
            mp.start();
        } catch (IOException e) {
            e.printStackTrace();
            Toast.makeText(getContext(), R.string.error_cannot_playback, Toast.LENGTH_LONG).show();
        }

    }
}
