package com.example.chessp2p;

public enum Chess {
    WP, WR, WN, WB, WQ, WK,
    BP, BR, BN, BB, BQ, BK,
    EM;

    /**
     *
     * @param piece Chess piece to compare
     * @return true if the 2 pieces are the same color and not EM
     */
    public boolean sameColor(Chess piece) {
        // ^ : XOR
        if (this == Chess.EM || piece == Chess.EM)
            return false;
        return this.ordinal() < 6 ^ piece.ordinal() < 6;
    }
}