package com.example.chessp2p;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.ImageButton;
import android.widget.ListView;
import android.widget.Spinner;

public class Puzzle extends Activity {

    ListView listView;
    ImageButton back;

    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.puzzle_view);

        listView = (ListView) findViewById(R.id.round);
        back = (ImageButton) findViewById(R.id.puzzleBack);

        ArrayAdapter<String> myAdapter = new ArrayAdapter<>(Puzzle.this,
                android.R.layout.simple_list_item_1,getResources().getStringArray(R.array.num));
        listView.setAdapter(myAdapter);

        back.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View view) {
                finish();
            }
        });
    }
}
