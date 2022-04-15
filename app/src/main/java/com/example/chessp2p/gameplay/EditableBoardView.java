package com.example.chessp2p.gameplay;

import android.content.Context;
import android.util.AttributeSet;
import android.util.Log;
import android.view.GestureDetector;
import android.view.MotionEvent;
import android.view.View;

public class EditableBoardView extends BoardView implements GestureDetector.OnDoubleTapListener {
    Chess addPiece = Chess.WK;

    GestureDetector gestureDetector = new GestureDetector(getContext(), new GestureDetector.SimpleOnGestureListener() {
        @Override
        public boolean onDown(MotionEvent event) {
            return true;
        }
    });

    public EditableBoardView(Context context) {
        super(context);
        gestureDetector.setOnDoubleTapListener(this);
    }

    public EditableBoardView(Context context, AttributeSet attrSet) {
        super(context, attrSet);
        gestureDetector.setOnDoubleTapListener(this);
    }

    public void setAddPiece(Chess piece) {
        addPiece = piece;
    }

    @Override
    protected void setBoard() {
        super.board = new ChessBoard(new Chess[][] {
            {Chess.EM, Chess.EM, Chess.EM, Chess.EM, Chess.EM, Chess.EM, Chess.EM, Chess.EM},
            {Chess.EM, Chess.EM, Chess.EM, Chess.EM, Chess.EM, Chess.EM, Chess.EM, Chess.EM},
            {Chess.EM, Chess.EM, Chess.EM, Chess.EM, Chess.EM, Chess.EM, Chess.EM, Chess.EM},
            {Chess.EM, Chess.EM, Chess.EM, Chess.EM, Chess.EM, Chess.EM, Chess.EM, Chess.EM},
            {Chess.EM, Chess.EM, Chess.EM, Chess.EM, Chess.EM, Chess.EM, Chess.EM, Chess.EM},
            {Chess.EM, Chess.EM, Chess.EM, Chess.EM, Chess.EM, Chess.EM, Chess.EM, Chess.EM},
            {Chess.EM, Chess.EM, Chess.EM, Chess.EM, Chess.EM, Chess.EM, Chess.EM, Chess.EM},
            {Chess.EM, Chess.EM, Chess.EM, Chess.EM, Chess.EM, Chess.EM, Chess.EM, Chess.EM},
        });
    }

    @Override
    public boolean performClick() {
        return super.performClick();
    }

    @Override
    public boolean onTouch(View view, MotionEvent event) {
        return gestureDetector.onTouchEvent(event);
    }

    @Override
    public boolean onSingleTapConfirmed(MotionEvent event) {
        Log.d("Edit", "single");
        super.setCursor(event);

        Chess chosenPiece = board.getPiece(cursorX, cursorY);
        if (chosenPiece == Chess.EM && addPiece != Chess.EM)
            board.setPiece(cursorX, cursorY, addPiece);

        invalidate();
        return true;
    }

    @Override
    public boolean onDoubleTap(MotionEvent event) {
        Log.d("Edit", "double");
        super.setCursor(event);
        board.setPiece(cursorX, cursorY, Chess.EM);
        cursorX = null;
        cursorY = null;
        invalidate();
        return true;
    }

    @Override
    public boolean onDoubleTapEvent(MotionEvent event) {
        return false;
    }
}
