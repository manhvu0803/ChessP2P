package com.example.chessp2p.gameplay;

import android.util.Log;

import java.util.Arrays;
import java.util.function.Function;

public class ChessBoard {
    static final int[][] knightMove = { {-2, -1}, {-2, 1}, {-1, 2}, {1, 2}, {2, 1}, {2, -1}, {1, -2}, {-1, -2} };
    static final int[][] kingMove = { {-1, 0}, {-1, 1}, {0, 1}, {1, 1}, {1, 0}, {1, -1}, {0, -1}, {-1, -1} };

    // Board states
    Chess[][] board;
    boolean[][] validMap = new boolean[8][8]; // An array that represent valid move of the chosen piece
    int chosenX, chosenY;
    boolean hasChosen = false;
    Chess turnPlayer = Chess.WK; // Representing turn player with the king because it's convenient
    String lastMove;

    ChessBoard() {
        board = new Chess[][] {
            {Chess.BR, Chess.BN, Chess.BB, Chess.BQ, Chess.BK, Chess.BB, Chess.BN, Chess.BR},
            {Chess.BP, Chess.BP, Chess.BP, Chess.BP, Chess.BP, Chess.BP, Chess.BP, Chess.BP},
            {Chess.EM, Chess.EM, Chess.EM, Chess.EM, Chess.EM, Chess.EM, Chess.EM, Chess.EM},
            {Chess.EM, Chess.EM, Chess.EM, Chess.EM, Chess.EM, Chess.EM, Chess.EM, Chess.EM},
            {Chess.EM, Chess.EM, Chess.EM, Chess.EM, Chess.EM, Chess.EM, Chess.EM, Chess.EM},
            {Chess.EM, Chess.EM, Chess.EM, Chess.EM, Chess.EM, Chess.EM, Chess.EM, Chess.EM},
            {Chess.WP, Chess.WP, Chess.WP, Chess.WP, Chess.WP, Chess.WP, Chess.WP, Chess.WP},
            {Chess.WR, Chess.WN, Chess.WB, Chess.WQ, Chess.WK, Chess.WB, Chess.WN, Chess.WR}
        };
    }

    ChessBoard(Chess[][] board) {
        if (board.length != 8 || board[0].length != 8)
            throw new IllegalArgumentException("Input chess board size must be 8 by 8");
        this.board = board;
    }

    Chess getPiece(int x, int y) {
        return board[x][y];
    }

    public String getLastMove() {
        return lastMove;
    }

    /**
     *
     * @return true if it's white turn
     */
    public boolean isWhiteTurn() {
        return Chess.WK == turnPlayer;
    }

    public boolean isValidMove(int x, int y) {
        return validMap[x][y];
    }

    public boolean hasChosen() {
        return hasChosen && board[chosenX][chosenY] != Chess.EM;
    }

    /**
     * Remove chosen status.
     * Make hasChosen() return false
     */
    public void removeChosen() {
        resetValidMap();
        hasChosen = false;
    }

    // TODO: Checkmate check
    /**
     * Does not reset validMap
     * @param x row
     * @param y column
     */
    public void setChosen(int x, int y) {
        if (turnPlayer.differentColor(board[x][y]))
            return;

        chosenX = x;
        chosenY = y;
        hasChosen = true;
        setValidMap(x, y, true);
    }

    void resetValidMap() {
        for (boolean[] ar : validMap)
            for (int i = 0; i < 8; ++i)
                ar[i] = false;
    }

    void setValidMap(int x, int y, boolean checkSameColor) {
        Chess piece = board[x][y];
        switch (board[x][y]) {
            case BP:
                // TODO: Black en-passant
                if (y > 0 && x < 7 && piece.differentColor(board[x + 1][y - 1]))
                    validMap[x + 1][y - 1] = true;
                if (y < 7 && x < 7 && piece.differentColor(board[x + 1][y + 1]))
                    validMap[x + 1][y + 1] = true;
                if (x < 7 && board[x + 1][y] == Chess.EM)
                    validMap[x + 1][y] = true;
                if (x == 1 && board[x + 2][y] == Chess.EM)
                    validMap[x + 2][y] = true;
                break;
            case WP:
                // TODO: White en-passant
                if (y > 0 && x > 0 && piece.differentColor(board[x - 1][y - 1]))
                    validMap[x - 1][y - 1] = true;
                if (y < 7 && x > 0 && piece.differentColor(board[x - 1][y + 1]))
                    validMap[x - 1][y + 1] = true;
                if (x > 0 && board[x - 1][y] == Chess.EM)
                    validMap[x - 1][y] = true;
                if (x == 6 && board[x - 2][y] == Chess.EM)
                    validMap[x - 2][y] = true;
                break;
            case BR:
            case WR:
                setValidRook(x, y, checkSameColor);
                break;
            case BN:
            case WN:
                for (int[] move : knightMove) {
                    int x2 = x + move[0], y2 = y + move[1];
                    if (x2 < 8 && x2 > -1 && y2 < 8 && y2 > -1 && (!piece.sameColor(board[x2][y2]) || !checkSameColor))
                        validMap[x2][y2] = true;
                }
                break;
            case BB:
            case WB:
                setValidBishop(x, y, checkSameColor);
                break;
            case BK:
                // TODO: Add black castling
            case WK:
                // TODO: Add white castling
                for (int[] move : kingMove) {
                    int x2 = x + move[0], y2 = y + move[1];
                    if (x2 < 8 && x2 > -1 && y2 < 8 && y2 > -1 && (!piece.sameColor(board[x2][y2]) || !checkSameColor))
                        validMap[x2][y2] = true;
                }
                break;
            case BQ:
            case WQ:
                setValidRook(x, y, checkSameColor);
                setValidBishop(x, y, checkSameColor);
                break;
        }
    }

    void setValidRook(int x, int y, boolean checkSameColor) {
        // Up
        setValidStraightMove(n -> x - n, n -> y, checkSameColor);
        // Down
        setValidStraightMove(n -> x + n, n -> y, checkSameColor);
        // Left
        setValidStraightMove(n -> x, n -> y - n, checkSameColor);
        // Right
        setValidStraightMove(n -> x, n -> y + n, checkSameColor);
    }

    void setValidBishop(int x, int y, boolean checkSameColor) {
        // Up left
        setValidStraightMove(n -> x - n, n -> y - n, checkSameColor);
        // Up right
        setValidStraightMove(n -> x - n, n -> y + n, checkSameColor);
        // Down right
        setValidStraightMove(n -> x + n, n -> y + n, checkSameColor);
        // Down left
        setValidStraightMove(n -> x + n, n -> y - n, checkSameColor);
    }

    /**
     * Go along a path (functions) to set validMap for a chess piece,
     * aka Black magic fuckery
     * @param fx function to calculate row
     * @param fy function to calculate column
     */
    void setValidStraightMove(Function<Integer, Integer> fx, Function<Integer, Integer> fy, boolean checkSameColor) {
        int i = 1;
        int x = fx.apply(1), y = fy.apply(1);
        while (x < 8 && x > -1 && y < 8 && y > -1 && board[x][y] == Chess.EM) {
            validMap[x][y] = true;
            ++i;
            x = fx.apply(i);
            y = fy.apply(i);
        }

        Chess og = board[fx.apply(0)][fy.apply(0)];
        if (x < 8 && x > -1 && y < 8 && y > -1 && (!og.sameColor(board[x][y]) || !checkSameColor))
            validMap[x][y] = true;
    }

    char toCol(int y) {
        if (y < 0 || y > 7)
            throw new IllegalArgumentException("");
        return (char)(y + 'a');
    }

    char toRow(int x) {
        if (x < 0 || x > 7)
            throw new IllegalArgumentException("");
        return (char)(8 - x + '0');
    }

    /**
     * Move a piece from a non-empty square to a new valid position.
     * If successfully moved, remove the chosen status
     * @param x row of the new position
     * @param y column of the new position
     * @return true if the piece is successfully moved
     */
    public boolean moveChosenTo(int x, int y) {
        if (!hasChosen() || !isValidMove(x, y) || (chosenX == x && chosenY == y))
            return false;

        Chess chosen = board[chosenX][chosenY];
        if (chosen == Chess.EM)
            return false;

        boolean capture = board[x][y] != Chess.EM;
        board[x][y] = chosen;
        board[chosenX][chosenY] = Chess.EM;

        // Check for pieces that can reach the same position and king check
        // Fuck you chess notation creator
        // TODO: Checkmate notation
        boolean check = false, dup = false, sameColumn = false;
        resetValidMap();
        setValidMap(x, y, false);
        for (int i = 0; i < 8; ++i)
            for (int j = 0; j < 8; ++j)
                if (validMap[i][j]) {
                    Chess p = board[i][j];
                    if (p == chosen) {
                        dup = true;
                        if (j == chosenY)
                            sameColumn = true;
                    }
                    else if (p.differentColor(chosen) && (p == Chess.WK || p == Chess.BK))
                        check = true;
                }

        // Update lastMove
        lastMove = chosen.str;
        if (dup && !sameColumn)
            lastMove += toCol(chosenY);
        else if (sameColumn)
            lastMove += toRow(chosenX);
        if (capture) {
            if (chosen == Chess.BP || chosen == Chess.WP)
                lastMove += toCol(chosenY);
            lastMove += 'x';
        }
        lastMove += toCol(y) + "" + toRow(x);
        if (check)
            lastMove += '+';

        removeChosen();

        // swap turn player
        if (turnPlayer == Chess.WK)
            turnPlayer = Chess.BK;
        else
            turnPlayer = Chess.WK;

        return true;
    }

    /**
     * Forcefully overwrite a position
     * @param x row
     * @param y col
     * @param piece Chess piece to set
     */
    public void setPiece(int x, int y, Chess piece) {
        board[x][y] = piece;
    }

    /**
     * Forcefully move a piece to a position (including empty squares).
     * Does not perform any check
     * @param x old row
     * @param y old column
     * @param x2 new row
     * @param y2 new column
     */
    public void move(int x, int y, int x2, int y2) {
        board[x][y] = board[x2][y2];
        board[x][x] = Chess.EM;
    }
}
