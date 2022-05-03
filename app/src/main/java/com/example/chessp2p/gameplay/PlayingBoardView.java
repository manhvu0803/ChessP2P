package com.example.chessp2p.gameplay;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.util.AttributeSet;
import android.view.MotionEvent;
import android.view.View;

import androidx.annotation.NonNull;

import com.example.chessp2p.R;

public class PlayingBoardView extends BoardView {
    public interface OnMoveListener {
        void OnMove(String move, int x1, int y1, int x2, int y2);
    }

    public OnMoveListener onMoveListener;

    Bitmap grayCircle;

    public PlayingBoardView(Context context) {
        super(context);
        init(context);
    }

    public PlayingBoardView(Context context, AttributeSet attrSet) {
        super(context, attrSet);
        init(context);
    }

    public PlayingBoardView(Context context, AttributeSet attrSet, int defStyleAttr) {
        super(context, attrSet, defStyleAttr);
        init(context);
    }

    public PlayingBoardView(Context context, AttributeSet attrs, int defStyleAttr, int defStyleRes) {
        super(context, attrs, defStyleAttr, defStyleRes);
        init(context);
    }

    void init(@NonNull Context context) {
        grayCircle = BitmapFactory.decodeResource(context.getResources(), R.drawable.gray_circle);
    }

    @Override
    public void onDraw(Canvas canvas) {
        super.onDraw(canvas);

        // Draw the possible moves
        if (board.getChosen() != Chess.EM && board.getChosen() != null) {
            for (int i = 0; i < 8; ++i)
                for (int j = 0; j < 8; ++j)
                    if (board.isValidMove(i, j))
                        draw(grayCircle, i, j, 40);
        }
    }

    @Override
    public boolean onTouch(View view, MotionEvent event) {
        // super.onTouch() call invalidate() so we shouldn't use it

        if (event.getAction() == MotionEvent.ACTION_UP) {
            // The canvas coordinates is column-row instead of row-column
            Integer cy = (int)event.getX() / canvasSize;
            Integer cx = (int)event.getY() / canvasSize;

            if (board.getChosen() != Chess.EM && board.getChosen() != null) {
                if (!board.moveChosenTo(cx, cy))
                    board.removeChosen();
                else
                    onMoveListener.OnMove(board.getLastMove().string, 0, 0, 0, 0);
                cx = null;
                cy = null;
            }
            else if (board.getPiece(cx, cy) != Chess.EM)
                board.setChosen(cx, cy);

            super.cursorX = cx;
            super.cursorY = cy;

            invalidate();
        }

        return true;
    }
}
