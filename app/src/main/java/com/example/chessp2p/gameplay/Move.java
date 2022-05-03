package com.example.chessp2p.gameplay;

import android.util.Log;

import androidx.annotation.NonNull;


public class Move {
    public static class Builder {
        private int x1 = 0, y1 = 0, x2 = 0, y2 = 0;

        public boolean check = false;
        public boolean haveDuplicate = false;
        public boolean duplicateSameColumn = false;

        public Chess capturedPiece;
        public Chess chosenPiece = Chess.BP;

        public void setPositions(int x1, int y1, int x2, int y2) {
            // TODO: Check positions validity
            this.x1 = x1;
            this.y1 = y1;
            this.x2 = x2;
            this.y2 = y2;
        }

        public Move build() {
            StringBuilder sBuilder = new StringBuilder(chosenPiece.str);
            if (haveDuplicate) {
                if (duplicateSameColumn)
                    sBuilder.append(toRow(x1));
                else
                    sBuilder.append(toCol(y1));
            }
            if (capturedPiece != Chess.EM && capturedPiece != null) {
                if (chosenPiece == Chess.BP || chosenPiece == Chess.WP)
                    sBuilder.append(toCol(y1));
                sBuilder.append('x');
            }
            sBuilder.append(toCol(y2));
            sBuilder.append(toRow(x2));
            if (check)
                sBuilder.append('+');

            return new Move(x1, y1, x2, y2, capturedPiece, sBuilder.toString());
        }
    }

    public static char toCol(int col) {
        return (char)(col + 'a');
    }

    public static char toRow(int row) {
        return (char)((8 - row) + '0');
    }

    public final int x1, y1;
    public final int x2, y2;

    public final Chess capturedPiece;

    public final String string;

    public Move(int x1, int y1, int x2, int y2, Chess capturedPiece, String moveString) {
        this.x1 = x1;
        this.y1 = y1;
        this.x2 = x2;
        this.y2 = y2;
        string = moveString;
        this.capturedPiece = capturedPiece;
    }

    @NonNull
    @Override
    public String toString() {
        return string;
    }
}
