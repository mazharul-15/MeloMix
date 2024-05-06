package com.example.melomix;

import androidx.appcompat.app.AppCompatActivity;

import android.Manifest;
import android.os.Bundle;
import android.os.Environment;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.Toast;

import com.karumi.dexter.Dexter;
import com.karumi.dexter.PermissionToken;
import com.karumi.dexter.listener.PermissionDeniedResponse;
import com.karumi.dexter.listener.PermissionGrantedResponse;
import com.karumi.dexter.listener.PermissionRequest;
import com.karumi.dexter.listener.single.PermissionListener;

import java.io.File;
import java.util.ArrayList;

public class MainActivity extends AppCompatActivity {

    ListView listView;
    String[] items;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        listView = findViewById(R.id.listView);
        runTimePermission();
        //displaySongs();

    }

    public void runTimePermission() {
        Dexter.withContext(this).withPermission(Manifest.permission.READ_EXTERNAL_STORAGE)
                .withListener(new PermissionListener() {
                    @Override
                    public void onPermissionGranted(PermissionGrantedResponse permissionGrantedResponse) {
                        displaySongs();
                    }

                    @Override
                    public void onPermissionDenied(PermissionDeniedResponse permissionDeniedResponse) {

                    }

                    @Override
                    public void onPermissionRationaleShouldBeShown(PermissionRequest permissionRequest, PermissionToken permissionToken) {
                        permissionToken.continuePermissionRequest();
                    }
                }).check();
    }

    // Accessing File from External Storage
    public ArrayList<File> findSongs(File file) {
        if(file.exists()) {
            ArrayList<File> arrayList = new ArrayList<>();
            File[] files = file.listFiles();

            if (files != null) {
                for (File singleFile : files) {

                    if (singleFile.isDirectory() && !singleFile.isHidden()) {
                        Toast.makeText(this, "Find Directorry", Toast.LENGTH_SHORT).show();
                        arrayList.addAll(findSongs(singleFile));
                    } else {
                        if (singleFile.getName().toLowerCase().endsWith(".mp3") || singleFile.getName().endsWith(".wav")) {
                            Toast.makeText(this, "find a mp3 file", Toast.LENGTH_SHORT).show();
                            arrayList.add(singleFile);
                        }
                    }
                }
            } else {
                Toast.makeText(this, "Directory Empty"+file.getPath().toString(), Toast.LENGTH_LONG).show();
            }
            return arrayList;
        }
        return null;
    }

    public void displaySongs() {

        final ArrayList<File> mySongs = findSongs(Environment.getExternalStorageDirectory());
        items = new String[mySongs.size()];

        for (int i = 0; i < mySongs.size(); i++) {
            items[i] = mySongs.get(i).getName().toString().replace(".mp3", "").replace(".wav", "");
        }

        ArrayAdapter<String> arrayAdapter = new ArrayAdapter<String>(this, android.R.layout.simple_list_item_1, items);
        listView.setAdapter(arrayAdapter);
    }
}