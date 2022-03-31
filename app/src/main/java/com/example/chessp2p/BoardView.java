package com.example.chessp2p;

import android.content.Context;
import android.content.res.Resources;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.graphics.Rect;
import android.util.AttributeSet;
import android.view.MotionEvent;
import android.view.View;

import androidx.annotation.NonNull;
import androidx.appcompat.content.res.AppCompatResources;

import java.util.HashMap;
import java.util.Map;


/**
 * TODO: document your custom view class.
 */
public class BoardView extends View implements View.OnTouchListener {
    public enum Chess {
        WP, WR, WN, WB, WQ, WK,
        BP, BR, BN, BB, BQ, BK,
        EM
    } 
    
    public static Chess[][] startingBoard = {
        {Chess.BR, Chess.BN, Chess.BB, Chess.BQ, Chess.BK, Chess.BB, Chess.BN, Chess.BR},
        {Chess.BP, Chess.BP, Chess.BP, Chess.BP, Chess.BP, Chess.BP, Chess.BP, Chess.BP},
        {Chess.EM, Chess.EM, Chess.EM, Chess.EM, Chess.EM, Chess.EM, Chess.EM, Chess.EM},
        {Chess.EM, Chess.EM, Chess.EM, Chess.EM, Chess.EM, Chess.EM, Chess.EM, Chess.EM},
        {Chess.EM, Chess.EM, Chess.EM, Chess.EM, Chess.EM, Chess.EM, Chess.EM, Chess.EM},
        {Chess.EM, Chess.EM, Chess.EM, Chess.EM, Chess.EM, Chess.EM, Chess.EM, Chess.EM},
        {Chess.WP, Chess.WP, Chess.WP, Chess.WP, Chess.WP, Chess.WP, Chess.WP, Chess.WP},
        {Chess.WR, Chess.WN, Chess.WB, Chess.WQ, Chess.WK, Chess.WB, Chess.WN, Chess.WR}
    };

    Map<Chess, Bitmap> iconMap;
    Bitmap grayCircle, hollowSquare;
    int canvasSize;

    // Destination for Canvas.drawBitmap()
    Rect dest = new Rect();
    Canvas canvas;

    // Board states
    Chess[][] boardInfo;
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

        boardInfo = startingBoard;

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
        for (int i = 0; i < 8; i++)
            for (int j = 0; j < 8; j++)
                if (Chess.EM != boardInfo[i][j]) {
                    // The canvas coordinate is column-row instead of row-column
                    draw(iconMap.get(boardInfo[i][j]), j, i, 0);
                }

        // Draw the square and circle
        if (cursorX != null) {
            draw(hollowSquare, cursorX, cursorY, -5);
            switch (boardInfo[cursorY][cursorX]) {
                case BP:
                    draw(grayCircle, cursorX, cursorY + 1, 50);
                    if (cursorY == 1)
                        draw(grayCircle, cursorX, cursorY + 2, 50);
                    break;
                case WP:
                    draw(grayCircle, cursorX, cursorY - 1, 50);
                    if (cursorY == 6)
                        draw(grayCircle, cursorX, cursorY - 2, 50);
                    break;
            }
        }
    }

    void draw(Bitmap bitmap, int x, int y, int pad) {
        x *= canvasSize;
        y *= canvasSize;
        dest.set(x + pad, y + pad, x + canvasSize - pad, y + canvasSize - pad);
        canvas.drawBitmap(bitmap, null, dest, null);
    }

    @Override
    public void onMeasure(int widthSpec, int heightSpec) {
        super.onMeasure(widthSpec, heightSpec);
        int size = Math.min(getMeasuredHeight(), getMeasuredWidth());
        setMeasuredDimension(size, size);
    }

    /**
     * Forcefully move a piece from a non-empty square to a new position. Any piece in the targeted square will be captured
     * @param row row of the moving piece
     * @param col column of the moving piece
     * @param newRow row of the new position
     * @param newCol column of the new position
     */
    public void movePiece(int row, int col, int newRow, int newCol) {
        if (Chess.EM == boardInfo[row][col] || (newRow == row && newCol == col))
            return;
        boardInfo[newRow][newCol] = boardInfo[row][col];
        boardInfo[row][col] = Chess.EM;
        invalidate();
    }

    @Override
    public boolean performClick() {
        return super.performClick();
    }

    @Override
    public boolean onTouch(View view, MotionEvent event) {
        if (event.getAction() == MotionEvent.ACTION_UP) {
            cursorX = (int)event.getX() / canvasSize;
            cursorY = (int)event.getY() / canvasSize;

            if (chosenX != null) {
                movePiece(chosenY, chosenX, cursorY, cursorX);
                chosenX = null;
                chosenY = null;
                cursorX = null;
                cursorY = null;
            }
            else if (boardInfo[cursorY][cursorX] != Chess.EM) {
                chosenX = cursorX;
                chosenY = cursorY;
            }

            invalidate();
        }

        return true;
    }
}