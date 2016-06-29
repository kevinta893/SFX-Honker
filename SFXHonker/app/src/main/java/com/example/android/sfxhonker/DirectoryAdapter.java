package com.example.android.sfxhonker;

import android.content.Context;
import android.os.Environment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.LinearLayout;
import android.widget.TextView;

import java.io.File;
import java.util.Arrays;
import java.util.Comparator;

/**
 * Directory adapter creates an adapter that allows users to browse through a directory
 */
public class DirectoryAdapter extends BaseAdapter {

    private Context context;

    private File[] fileList;
    private String rootPath;

    public DirectoryAdapter(Context c, String startPath){

        this.context = c;
        this.rootPath = startPath;

        final String state = Environment.getExternalStorageState();
        File[] files = new File(startPath).listFiles();

        //sort the files
        Arrays.sort(files, new FileSorter());
        this.fileList = files;
        if (Environment.MEDIA_MOUNTED.equals(state)) {

        }
        else {

        }
    }


    /**
     * Sorts by folder first, then files after
     */
    private class FileSorter implements Comparator<File> {

        @Override
        public int compare(File file, File t1) {

            if(file.isDirectory()){
                //is directory, sort by name
                if (t1.isDirectory()){
                    return file.compareTo(t1);
                }
                else{
                    return -1;
                }
            }
            else{
                //is file, sort by name
                if (t1.isDirectory() == false){
                    return file.compareTo(t1);
                }
                else{
                    return 1;
                }
            }

        }
    }

    @Override
    public int getCount() {
        return fileList.length;
    }

    @Override
    public Object getItem(int i) {
        return fileList[i];
    }

    @Override
    public long getItemId(int i) {
        return 0;
    }

    @Override
    public View getView(int i, View view, ViewGroup viewGroup) {

        File selection = fileList[i];
        if(view == null){
            LayoutInflater li = LayoutInflater.from(context);

            if(selection.isDirectory()){
                //inflate a directory layout item
                view = li.inflate(R.layout.folder_item, null);
                TextView text = (TextView) view.findViewById(R.id.directory_title);
                text.setText(fileList[i].getName());
            }
            else{
                //inflate a file layout item
                view = li.inflate(R.layout.file_item, null);
                TextView text = (TextView) view.findViewById(R.id.file_title);
                text.setText(fileList[i].getName());
            }
        }



        return view;
    }

    public Context getContext(){
        return context;
    }
}
