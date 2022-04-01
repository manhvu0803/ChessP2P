package com.example.chessp2p;

import android.content.Context;
import android.content.res.Resources;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.graphics.Rect;
import android.util.AttributeSet;
import android.util.Log;
import android.view.MotionEvent;
import android.view.View;

import androidx.annotation.NonNull;
import androidx.appcompat.content.res.AppCompatResources;

import java.util.Arrays;
import java.util.HashMap;
import java.util.Map;
import java.util.function.Function;


// TODO: document your custom view class
public class BoardView extends View implements View.OnTouchListener {
    ChessBoard board;

    Map<Chess, Bitmap> iconMap;
    Bitmap grayCircle, hollowSquare;

    int canvasSize;
    Canvas canvas;

    // Destination for Canvas.drawBitmap()
    Rect dest = new Rect();

    Integer chosenX = null, chosenY = null;
    Integer cursorX = null, cursorY = null;

    public BoardView(Context context) {
        super(context);
        init(context);
    }

    public BoardView(Context context, AttributeSet attrSet) {
        super(context, attrSet);

        init(context);
    }

    void init(@NonNull Context context) {
        //TypedArray tArr = context.getTheme().obtainStyledAttributes(attrSet, R.styleable.BoardView, Chess.EM, 0);
        //tArr.recycle();

        iconMap = new HashMap<>();
        Resources resource = context.getResources();
        iconMap.put(Chess.WP, BitmapFactory.decodeResource(resource, R.drawable.wpawn));
        iconMap.put(Chess.WR, BitmapFactory.decodeResource(resource, R.drawable.wrook));
        iconMap.put(Chess.WN, BitmapFactory.decodeResource(resource, R.drawable.wknight));
        iconMap.put(Chess.WB, BitmapFactory.decodeResource(resource, R.drawable.wbishop));
        iconMap.put(Chess.WQ, BitmapFactory.decodeResource(resource, R.drawable.wqueen));
        iconMap.put(Chess.WK, BitmapFactory.decodeResource(resource, R.drawable.wking));
        iconMap.put(Chess.BP, BitmapFactory.decodeResource(resource, R.drawable.bpawn));
        iconMap.put(Chess.BR, BitmapFactory.decodeResource(resource, R.drawable.brook));
        iconMap.put(Chess.BN, BitmapFactory.decodeResource(resource, R.drawable.bknight));
        iconMap.put(Chess.BB, BitmapFactory.decodeResource(resource, R.drawable.bbishop));
        iconMap.put(Chess.BQ, BitmapFactory.decodeResource(resource, R.drawable.bqueen));
        iconMap.put(Chess.BK, BitmapFactory.decodeResource(resource, R.drawable.bking));

        grayCircle = BitmapFactory.decodeResource(resource, R.drawable.gray_circle);
        hollowSquare = BitmapFactory.decodeResource(resource, R.drawable.hollow_square);

        setBackground(AppCompatResources.getDrawable(context, R.drawable.board));

        this.setOnTouchListener(this);
    }

    @Override
    public void onDraw(Canvas canvas) {
        super.onDraw(canvas);

        // Update canvas info
        canvasSize = getWidth() / 8;
        this.canvas = canvas;

        // Draw the chess pieces;
        for (int i = 0; i < 8; ++i)
            for (int j = 0; j < 8; ++j)
                if (Chess.EM != board.getPiece(i, j)) {
                    // The canvas coordinate is column-row instead of row-column
                    draw(iconMap.get(board.getPiece(i, j)), i, j, 0);
                }

        // Draw the square and circle
        if (cursorX != null) {
            draw(hollowSquare, cursorX, cursorY, -5);
        }
        if (chosenX != null) {
            for (int i = 0; i < 8; ++i)
                for (int j = 0; j < 8; ++j)
                    if (board.isValidMove(i, j)) {
                        // The canvas coordinate is column-row instead of row-column
                        draw(grayCircle, i, j, 40);
                    }
        }
    }

    void draw(Bitmap bitmap, int x, int y, int pad) {
        x *= canvasSize;
        y *= canvasSize;
        // The canvas coordinate is column-row instead of row-column
        dest.set(y + pad, x + pad, y + canvasSize - pad, x + canvasSize - pad);
        canvas.drawBitmap(bitmap, null, dest, null);
    }

    @Override
    public void onMeasure(int widthSpec, int heightSpec) {
        super.onMeasure(widthSpec, heightSpec);
        int size = Math.min(getMeasuredHeight(), getMeasuredWidth());
        setMeasuredDimension(size, size);
    }

    @Override
    public boolean performClick() {
        return super.performClick();
    }

    @Override
    public boolean onTouch(View view, MotionEvent event) {
        if (event.getAction() == MotionEvent.ACTION_UP) {
            // The canvas coordinate system is column-row instead of row-column
            cursorY = (int)event.getX() / canvasSize;
            cursorX = (int)event.getY() / canvasSize;

            if (chosenX != null) {
                // Cast Integer to int to compare values instead of addresses
                if ((chosenX != (int)cursorX || chosenY != (int)cursorY) && board.isValidMove(cursorX, cursorY)) {
                    board.movePiece(chosenX, chosenY, cursorX, cursorY);
                    invalidate();
                }
                chosenX = null;
                chosenY = null;
            }
            else if (board.getPiece(cursorX, cursorY) != Chess.EM) {
                chosenX = cursorX;
                chosenY = cursorY;
                board.setChosen(cursorX, cursorY);
            }
            invalidate();
        }

        return true;
    }
}