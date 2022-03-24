package com.example.chessp2p;

import android.content.Context;
import android.content.res.Resources;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.graphics.Rect;
import android.util.AttributeSet;
import android.util.Log;
import android.view.View;

import java.util.HashMap;
import java.util.Map;


/**
 * TODO: document your custom view class.
 */
public class BoardView extends View {
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

    Chess[][] boardInfo;
    Map<Chess, Bitmap> iconMap;

    public BoardView(Context context) {
        super(context);

        init(context);
    }

    public BoardView(Context context, AttributeSet attrSet) {
        super(context, attrSet);

        init(context);
    }

    void init(Context context) {
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


        setBackground(context.getDrawable(R.drawable.board));
    }

    @Override
    public void onDraw(Canvas canvas) {
        super.onDraw(canvas);

        int size = canvas.getWidth() / 8;
        for (int x = 0; x < 8; x++)
            for (int y = 0; y < 8; y++)
                if (Chess.EM != boardInfo[x][y]) {
                    // The canvas coordinate is YX instead of XY
                    Rect dest = new Rect(y * size, x * size, (y + 1) * size, (x + 1) * size);
                    canvas.drawBitmap(iconMap.get(boardInfo[x][y]), null, dest, null);
                }
    }

    @Override
    public void onMeasure(int widthSpec, int heightSpec) {
        super.onMeasure(widthSpec, heightSpec);
        int size = Math.min(getMeasuredHeight(), getMeasuredWidth());
        setMeasuredDimension(size, size);
    }
}