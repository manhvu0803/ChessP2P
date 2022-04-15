package com.example.chessp2p;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Spinner;

import com.example.chessp2p.gameplay.Chess;
import com.example.chessp2p.gameplay.EditableBoardView;

public class EditBoardActivity extends AppCompatActivity implements AdapterView.OnItemSelectedListener {
    static final String[] pieceStrings = {"None",
                            "White King", "White Rook", "White Knight", "White Bishop", "White Queen", "White Pawn",
                            "Black King", "Black Rook", "Black Knight", "Black Bishop", "Black Queen", "Black Pawn"};
    static final Chess[] pieces = {Chess.EM,
                            Chess.WK, Chess.WR, Chess.WN, Chess.WB, Chess.WQ, Chess.WP,
                            Chess.BK, Chess.BR, Chess.BN, Chess.BB, Chess.BQ, Chess.BP};

    EditableBoardView boardView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_edit_board);

        boardView = this.findViewById(R.id.editableBoardView);
        boardView.setAddPiece(Chess.WR);

        Spinner spinner = this.findViewById(R.id.chessPieceSpinner);
        spinner.setAdapter(new ArrayAdapter<>(this, R.layout.spinner_item, pieceStrings));
        spinner.setOnItemSelectedListener(this);
    }

    @Override
    public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {
        boardView.setAddPiece(pieces[i]);
    }

    @Override
    public void onNothingSelected(AdapterView<?> adapterView) {
        boardView.setAddPiece(Chess.EM);
    }
}