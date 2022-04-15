package com.example.chessp2p;

import androidx.appcompat.app.AppCompatActivity;

import android.graphics.Color;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.Spinner;

import com.example.chessp2p.gameplay.Chess;
import com.example.chessp2p.gameplay.EditableBoardView;

public class EditBoardActivity extends AppCompatActivity implements AdapterView.OnItemSelectedListener {
    static final String[] pieceStrings = {"Choose chess piece",
                            "♔ White King", "♕ White Queen", "♖ White Rook", "♗ White Bishop", "♘ White Knight", "♙ White Pawn",
                            "♚ Black King", "♛ Black Queen", "♜ Black Rook", "♝ Black Bishop", "♞ Black Knight", "♟ Black Pawn"};
    static final Chess[] pieces = {Chess.EM,
                            Chess.WK, Chess.WQ, Chess.WR, Chess.WB, Chess.WN, Chess.WP,
                            Chess.BK, Chess.BQ, Chess.BR, Chess.BB, Chess.BN, Chess.BP};

    EditableBoardView boardView;
    Button cancelButton;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_edit_board);

        boardView = this.findViewById(R.id.editableBoardView);
        boardView.setAddPiece(Chess.WR);

        Spinner spinner = this.findViewById(R.id.chessPieceSpinner);
        spinner.setAdapter(new ArrayAdapter<>(this, R.layout.spinner_item, pieceStrings));
        spinner.setOnItemSelectedListener(this);

        cancelButton = this.findViewById(R.id.cancelButton);

        cancelButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });
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