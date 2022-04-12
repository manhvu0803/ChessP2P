package com.example.chessp2p;

public enum Chess {
    WP(R.drawable.wpawn),
    WR(R.drawable.wrook),
    WN(R.drawable.wknight),
    WB(R.drawable.wbishop),
    WQ(R.drawable.wqueen),
    WK(R.drawable.wking),
    BP(R.drawable.bpawn),
    BR(R.drawable.brook),
    BN(R.drawable.bknight),
    BB(R.drawable.bbishop),
    BQ(R.drawable.bqueen),
    BK(R.drawable.bking),
    EM(-1);

    public final int bitmapId;

    Chess(int id) {
        bitmapId = id;
    }

    /**
     *
     * @param piece Chess piece to compare
     * @return true if the 2 pieces are the same color and not EM
     */
    public boolean sameColor(Chess piece) {
        if (this == Chess.EM || piece == Chess.EM)
            return false;
        return this.ordinal() < 6 == piece.ordinal() < 6;
    }

    /**
     *
     * @param piece Chess piece to compare
     * @return true if the 2 pieces are different colors and not EM
     */
    public boolean differentColor(Chess piece) {
        return this != Chess.EM && piece != Chess.EM && !this.sameColor(piece);
    }
}
