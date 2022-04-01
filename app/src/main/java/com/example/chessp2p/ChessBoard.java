package com.example.chessp2p;

import android.util.Log;

import java.util.Arrays;
import java.util.function.Function;

public class ChessBoard {
    static final Chess[][] startingBoard = {
            {Chess.BR, Chess.BN, Chess.BB, Chess.BQ, Chess.BK, Chess.BB, Chess.BN, Chess.BR},
            {Chess.BP, Chess.BP, Chess.BP, Chess.BP, Chess.BP, Chess.BP, Chess.BP, Chess.BP},
            {Chess.EM, Chess.EM, Chess.EM, Chess.EM, Chess.EM, Chess.EM, Chess.EM, Chess.EM},
            {Chess.EM, Chess.EM, Chess.EM, Chess.EM, Chess.EM, Chess.EM, Chess.EM, Chess.EM},
            {Chess.EM, Chess.EM, Chess.EM, Chess.EM, Chess.EM, Chess.EM, Chess.EM, Chess.EM},
            {Chess.EM, Chess.EM, Chess.EM, Chess.EM, Chess.EM, Chess.EM, Chess.EM, Chess.EM},
            {Chess.WP, Chess.WP, Chess.WP, Chess.WP, Chess.WP, Chess.WP, Chess.WP, Chess.WP},
            {Chess.WR, Chess.WN, Chess.WB, Chess.WQ, Chess.WK, Chess.WB, Chess.WN, Chess.WR}
    };

    static final int[][] knightMove = { {-2, -1}, {-2, 1}, {-1, 2}, {1, 2}, {2, 1}, {2, -1}, {1, -2}, {-1, -2} };
    static final int[][] kingMove = { {-1, 0}, {-1, 1}, {0, 1}, {1, 1}, {1, 0}, {1, -1}, {0, -1}, {-1, -1} };

    // Board states
    Chess[][] boardInfo;
    boolean[][] validMap = new boolean[8][8]; // An array that represent valid move of the chosen piece
    int chosenX, chosenY;
    boolean hasChosen = false;

    ChessBoard() {
        boardInfo = Arrays.copyOf(startingBoard, startingBoard.length);
    }

    Chess getPiece(int x, int y) {
        return boardInfo[x][y];
    }

    public boolean isValidMove(int x, int y) {
        return validMap[x][y];
    }

    void resetValidMap() {
        for (boolean[] ar : validMap)
            for (int i = 0; i < 8; ++i)
                ar[i] = false;
    }

    public boolean hasChosen() {
        return hasChosen && boardInfo[chosenX][chosenY] != Chess.EM;
    }

    /**
     * Remove chosen status.
     * Make hasChosen() return false
     */
    public void removeChosen() {
        resetValidMap();
        hasChosen = false;
    }

    public void setChosen(int x, int y) {
        if (boardInfo[x][y] == Chess.EM)
            return;

        chosenX = x;
        chosenY = y;
        hasChosen = true;

        Chess piece = boardInfo[x][y];
        switch (boardInfo[x][y]) {
            case BP:
                // TODO: Black en-passant
                if (y > 0 && x < 7 && piece.differentColor(boardInfo[x + 1][y - 1]))
                    validMap[x + 1][y - 1] = true;
                if (y < 7 && x < 7 && piece.differentColor(boardInfo[x + 1][y + 1]))
                    validMap[x + 1][y + 1] = true;
                if (x < 7 && boardInfo[x + 1][y] == Chess.EM)
                    validMap[x + 1][y] = true;
                if (x == 1 && boardInfo[x + 2][y] == Chess.EM)
                    validMap[x + 2][y] = true;
                break;
            case WP:
                // TODO: White en-passant
                if (y > 0 && x > 0 && piece.differentColor(boardInfo[x - 1][y - 1]))
                    validMap[x - 1][y - 1] = true;
                if (y < 7 && x > 0 && piece.differentColor(boardInfo[x - 1][y + 1]))
                    validMap[x - 1][y + 1] = true;
                if (x > 0 && boardInfo[x - 1][y] == Chess.EM)
                    validMap[x - 1][y] = true;
                if (x == 6 && boardInfo[x - 2][y] == Chess.EM)
                    validMap[x - 2][y] = true;
                break;
            case BR:
            case WR:
                setValidRook(x, y);
                break;
            case BN:
            case WN:
                for (int[] move : knightMove) {
                    int x2 = x + move[0], y2 = y + move[1];
                    if (x2 < 8 && x2 > -1 && y2 < 8 && y2 > -1 && !piece.sameColor(boardInfo[x2][y2]))
                        validMap[x2][y2] = true;
                }
                break;
            case BB:
            case WB:
                setValidBishop(x, y);
                break;
            case BK:
                // TODO: Add black castling
            case WK:
                // TODO: Add white castling
                for (int[] move : kingMove) {
                    int x2 = x + move[0], y2 = y + move[1];
                    if (x2 < 8 && x2 > -1 && y2 < 8 && y2 > -1 && !piece.sameColor(boardInfo[x2][y2]))
                        validMap[x2][y2] = true;
                }
                break;
            case BQ:
            case WQ:
                setValidRook(x, y);
                setValidBishop(x, y);
                break;
        }
    }

    void setValidRook(int x, int y) {
        // Up
        setValidStraightMove(n -> x - n, n -> y);
        // Down
        setValidStraightMove(n -> x + n, n -> y);
        // Left
        setValidStraightMove(n -> x, n -> y - n);
        // Right
        setValidStraightMove(n -> x, n -> y + n);
    }

    void setValidBishop(int x, int y) {
        // Up left
        setValidStraightMove(n -> x - n, n -> y - n);
        // Up right
        setValidStraightMove(n -> x - n, n -> y + n);
        // Down right
        setValidStraightMove(n -> x + n, n -> y + n);
        // Down left
        setValidStraightMove(n -> x + n, n -> y - n);
    }

    /**
     * Go along a path (functions) to set validMap for a chess piece,
     * aka Black magic fuckery
     * @param fx function to calculate row
     * @param fy function to calculate column
     */
    void setValidStraightMove(Function<Integer, Integer> fx, Function<Integer, Integer> fy) {
        int i = 1;
        int x = fx.apply(1), y = fy.apply(1);
        while (x < 8 && x > -1 && y < 8 && y > -1 && boardInfo[x][y] == Chess.EM) {
            validMap[x][y] = true;
            ++i;
            x = fx.apply(i);
            y = fy.apply(i);
        }

        Chess og = boardInfo[fx.apply(0)][fy.apply(0)];
        if (x < 8 && x > -1 && y < 8 && y > -1 && !og.sameColor(boardInfo[x][y]))
            validMap[x][y] = true;
    }

    /**
     * Move a piece from a non-empty square to a new valid position.
     * If successfully moved, remove the chosen status
     * @param x row of the new position
     * @param y column of the new position
     * @return true if the piece is successfully moved
     */
    public boolean moveChosenTo(int x, int y) {
        if (!isValidMove(x, y))
            return false;
        if (chosenX == x && chosenY == y)
            return false;
        if (boardInfo[chosenX][chosenY] == Chess.EM)
            return false;

        boardInfo[x][y] = boardInfo[chosenX][chosenY];
        boardInfo[chosenX][chosenY] = Chess.EM;
        removeChosen();
        return true;
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
        boardInfo[x][y] = boardInfo[x2][y2];
        boardInfo[x][x] = Chess.EM;
    }
}
