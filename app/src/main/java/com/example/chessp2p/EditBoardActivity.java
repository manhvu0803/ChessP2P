package com.example.chessp2p;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;

import com.example.chessp2p.gameplay.Chess;
import com.example.chessp2p.gameplay.EditableBoardView;

public class EditBoardActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_edit_board);

        EditableBoardView boardView = this.findViewById(R.id.editableBoardView);
        boardView.setAddPiece(Chess.WR);
    }
}