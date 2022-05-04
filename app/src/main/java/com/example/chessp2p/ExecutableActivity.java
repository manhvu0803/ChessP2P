package com.example.chessp2p;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Context;
import android.os.Bundle;
import android.util.Log;

import java.io.BufferedOutputStream;
import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileOutputStream;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.io.OutputStreamWriter;
import java.util.stream.Collectors;

public class ExecutableActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState)  {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_executable);

        Context context = getApplicationContext();

        File file = context.getFileStreamPath("stockfish");
        Log.d("exe", file.getAbsolutePath());

        if (!file.exists()) {
           // createExeFile();
        }

        boolean res = file.setExecutable(true);
        if (!res) {
            Log.e("exe", "setExecutable false");
            return;
        }

        try {
            Process process = Runtime.getRuntime().exec(file.getCanonicalPath());
            try (BufferedWriter writer = new BufferedWriter(new OutputStreamWriter(process.getOutputStream()));
                 BufferedReader reader = new BufferedReader(new InputStreamReader(process.getInputStream()));
                 BufferedReader errorReader = new BufferedReader(new InputStreamReader(process.getErrorStream())))
            {
                String data = reader.lines().collect(Collectors.joining());
                Log.d("exe", "Data: " + data);

                String error = errorReader.lines().collect(Collectors.joining());
                Log.e("exe", "Error: " + error);
            }
            process.waitFor();
            Log.d("exe", "Done");
        }
        catch (Exception e) {
            Log.e("exe", "Process: " + e.getMessage());
        }
    }

    void createExeFile() {
        Context context = getApplicationContext();
        try (InputStream inputStream = context.getResources().openRawResource(R.raw.stockfish);
             FileOutputStream fileStream = context.openFileOutput("stockfish", Context.MODE_PRIVATE)) {
            byte[] buffer = new byte[inputStream.available()];
            int res = inputStream.read(buffer);
            fileStream.write(buffer);
            Log.d("exe", "created exe");
        }
        catch (Exception e) {
            Log.e("exe", "Create: " + e.getLocalizedMessage());
        }
    }
}