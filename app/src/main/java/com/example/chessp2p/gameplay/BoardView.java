package com.example.chessp2p.gameplay;

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
import androidx.annotation.Nullable;
import androidx.appcompat.content.res.AppCompatResources;

import com.example.chessp2p.R;

import java.util.HashMap;
import java.util.Map;


public class BoardView extends View implements View.OnTouchListener {
    ChessBoard board;

    Map<Chess, Bitmap> iconMap;
    Bitmap hollowSquare;

    int canvasSize;
    Canvas canvas;

    // Destination for Canvas.drawBitmap()
    Rect dest = new Rect();

    Integer cursorX = null, cursorY = null;

    public BoardView(Context context) {
        super(context);
        init(context);
    }

    public BoardView(Context context, AttributeSet attrSet) {
        super(context, attrSet);
        init(context);
    }

    public BoardView(Context context, AttributeSet attrSet, int styleId) {
        super(context, attrSet, styleId);
        init(context);
    }

    public BoardView(Context context, @Nullable AttributeSet attrs, int defStyleAttr, int defStyleRes) {
        super(context, attrs, defStyleAttr, defStyleRes);
        init(context);
    }

    private void init(@NonNull Context context) {
        setBoard();

        iconMap = new HashMap<>();
        Resources resource = context.getResources();
        for (Chess chessPiece : Chess.values())
            iconMap.put(chessPiece, BitmapFactory.decodeResource(resource, chessPiece.bitmapId));

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

        // Draw the square
        if (cursorX != null) {
            draw(hollowSquare, cursorX, cursorY, -5);
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
            // The canvas coordinates is column-row instead of row-column
            setCursor(event);
            invalidate();
        }

        return true;
    }

    protected void setBoard() {
        board = new ChessBoard();
    }

    protected void setCursor(MotionEvent event) {
        cursorY = (int)event.getX() / canvasSize;
        cursorX = (int)event.getY() / canvasSize;
    }
}