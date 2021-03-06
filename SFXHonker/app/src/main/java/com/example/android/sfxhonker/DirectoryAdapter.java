package com.example.android.sfxhonker;

import android.content.Context;
import android.os.Environment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import java.io.File;
import java.io.FileFilter;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Comparator;
import java.util.List;

/**
 * Directory adapter creates an adapter that allows users to browse through a directory
 */
public class DirectoryAdapter extends BaseAdapter {

    private Context context;

    private List<File> fileList = new ArrayList<File>();
    private String rootPath;
    private boolean showParentNode = true;

    private List<String> extensionFilter = new ArrayList<String>();

    public DirectoryAdapter(Context c, String startPath, String[] fileFilter){

        this.context = c;
        this.rootPath = startPath;

        this.extensionFilter = Arrays.asList(fileFilter);

        FileFilter audioFileFilter = new FileFilter() {
            @Override
            public boolean accept(File file) {

                if(file.isDirectory()){
                    //directory, keep in list
                    return true;
                }


                //file, keep if one of the accepted extensions
                String extension = getFileExtension(file);
                if (extensionFilter.indexOf(extension) >= 0){
                    return true;
                }

                return false;
            }

        };

        final String state = Environment.getExternalStorageState();
        File[] files = new File(startPath).listFiles(audioFileFilter);

        if(files != null){
            //sort the files
            Arrays.sort(files, new FileSorter());
            this.fileList.addAll(Arrays.asList(files));
        }


        //inject the parent directory
        String parentDirPath = new File(startPath).getParent();
        if (parentDirPath != null){
            //parent exists, add to list
            this.fileList.add(0, new File(parentDirPath));          //parent directory
        }

        if (Environment.MEDIA_MOUNTED.equals(state)) {
            //TODO
        }
        else {

        }
    }

    /**
     * Gets the file extension for the given file.
     * Returns empty string on no file extension, does not imply
     * a file is a directory however.
     * @param f
     * @return "happy.mp3" returns "mp3"
     */
    public static String getFileExtension(File f){
        String fileName = f.getPath();
        int i = fileName.lastIndexOf('.');
        if (i > 0) {
            return fileName.substring(i+1);
        }
        return "";
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
        return fileList.size();
    }

    @Override
    public Object getItem(int i) {
        return fileList.get(i);
    }

    @Override
    public long getItemId(int i) {
        return 0;
    }

    @Override
    public View getView(int i, View view, ViewGroup viewGroup) {

        File selection = fileList.get(i);
        if(view == null){
            LayoutInflater li = LayoutInflater.from(context);

            if (i == 0){
                //first one is the root directory
                view = li.inflate(R.layout.folder_item, null);
                TextView text = (TextView) view.findViewById(R.id.directory_title);
                text.setText("..");
                if (showParentNode == false){
                    ImageView icon = (ImageView) view.findViewById(R.id.directory_icon);
                    view.setVisibility(View.GONE);
                    icon.setVisibility(View.GONE);
                    text.setVisibility(View.GONE);
                    text.setHeight(0);
                }
            }
            else if(selection.isDirectory()){
                //inflate a directory layout item
                view = li.inflate(R.layout.folder_item, null);
                TextView text = (TextView) view.findViewById(R.id.directory_title);
                text.setText(selection.getName());
            }
            else{
                //inflate a file layout item
                view = li.inflate(R.layout.file_item, null);
                TextView text = (TextView) view.findViewById(R.id.file_title);
                text.setText(selection.getName());
            }
        }



        return view;
    }

    public void setShowParentNode(boolean show){
        this.showParentNode = show;
    }

    public String getRootPath(){
        return rootPath;
    }

    public Context getContext(){
        return context;
    }
}
