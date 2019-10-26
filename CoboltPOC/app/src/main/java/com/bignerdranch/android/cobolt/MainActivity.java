 package com.bignerdranch.android.cobolt;

import androidx.appcompat.app.AppCompatActivity;

import android.content.res.AssetManager;
import android.os.Bundle;

import com.bignerdranch.android.scanner.ScannerGenerator;

import java.io.IOException;
import java.io.InputStream;

 public class MainActivity extends AppCompatActivity {

    AssetManager am;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        am = this.getAssets();
        ScannerGenerator scannerGenerator = new ScannerGenerator();
        try {
            InputStream source = am.open("keywords.txt");
            scannerGenerator.getKeywordsFromFile(source);
        } catch (IOException e) {
            e.printStackTrace();
        }


    }
}
