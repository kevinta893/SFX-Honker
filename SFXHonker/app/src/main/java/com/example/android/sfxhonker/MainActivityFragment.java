package com.example.android.sfxhonker;

import android.os.Environment;
import android.support.v4.app.Fragment;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ListView;

/**
 * A placeholder fragment containing a simple view.
 */
public class MainActivityFragment extends Fragment {

    private ListView fileList;


    public MainActivityFragment() {
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View rootView = inflater.inflate(R.layout.fragment_main, container, false);

        fileList = (ListView) rootView.findViewById(R.id.sfx_items);
        fileList.setAdapter(new DirectoryAdapter(getContext(), Environment.getExternalStorageDirectory().toString()));

        return rootView;
    }
}
