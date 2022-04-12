package com.example.chessp2p;

public enum Chess {
    WP(R.drawable.wpawn, ""),
    WR(R.drawable.wrook, "R"),
    WN(R.drawable.wknight, "N"),
    WB(R.drawable.wbishop, "B"),
    WQ(R.drawable.wqueen, "Q"),
    WK(R.drawable.wking, "K"),
    BP(R.drawable.bpawn, ""),
    BR(R.drawable.brook, "R"),
    BN(R.drawable.bknight, "N"),
    BB(R.drawable.bbishop, "B"),
    BQ(R.drawable.bqueen, "Q"),
    BK(R.drawable.bking, "K"),
    EM(-1, null);

    public final int bitmapId;
    public final String str;

    Chess(int id, String signature) {
        bitmapId = id;
        str = signature;
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
