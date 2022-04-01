package com.example.chessp2p;

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

    public void setChosen(int x, int y) {
        resetValidMap();
        Chess piece = boardInfo[x][y];
        switch (boardInfo[x][y]) {
            case BP:
                // TODO: Black en-passant
                if (y > 0 && x < 7 && piece.sameColor(boardInfo[x + 1][y - 1]))
                    validMap[x + 1][y - 1] = true;
                if (y < 7 && x < 7 && piece.sameColor(boardInfo[x + 1][y + 1]))
                    validMap[x + 1][y + 1] = true;
                if (x < 7 && boardInfo[x + 1][y] == Chess.EM)
                    validMap[x + 1][y] = true;
                if (x == 1 && boardInfo[x + 2][y] == Chess.EM)
                    validMap[x + 2][y] = true;
                break;
            case WP:
                /*
                  TODO: White en-passant
                 */
                if (y > 0 && x > 0 && piece.sameColor(boardInfo[x - 1][y - 1]))
                    validMap[x - 1][y - 1] = true;
                if (y < 7 && x > 0 && piece.sameColor(boardInfo[x - 1][y + 1]))
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
                for (int[] ints : knightMove) {
                    int x2 = x + ints[0], y2 = y + ints[1];
                    if (x2 < 8 && x2 > -1 && y2 < 8 && y2 > -1 && !piece.sameColor(boardInfo[x2][y2]))
                        validMap[x2][y2] = true;
                }
                break;
            case BB:
            case WB:
                setValidBishop(x, y);
            case BK:
            case WK:
                for (int[] ints : kingMove) {
                    int x2 = x + ints[0], y2 = y + ints[1];
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
        Chess piece = boardInfo[x][y];
        int i;
        // Up
        for (i = x - 1; i > 0 && boardInfo[x][i] == Chess.EM; --i)
            validMap[i][y] = true;
        if (Chess.EM != boardInfo[x][i] && !piece.sameColor(boardInfo[i][y]))
            validMap[i][y] = true;
        // Down
        for (i = x + 1; i < 7 && boardInfo[x][i] == Chess.EM; ++i)
            validMap[i][y] = true;
        if (Chess.EM != boardInfo[x][i] && !piece.sameColor(boardInfo[i][y]))
            validMap[i][y] = true;
        // Left
        for (i = y - 1; i > 0 && boardInfo[x][i] == Chess.EM; --i)
            validMap[x][i] = true;
        if (Chess.EM != boardInfo[x][i] && !piece.sameColor(boardInfo[x][i]))
            validMap[x][i] = true;
        // Right
        for (i = y + 1; i < 7 && boardInfo[x][i] == Chess.EM; ++i)
            validMap[x][i] = true;
        if (Chess.EM != boardInfo[x][i] && !piece.sameColor(boardInfo[x][i]))
            validMap[x][i] = true;
    }

    void setValidBishop(int x, int y) {
        for (int i = -7; i <= 7; ++i) {
            int x2 = x - i, y2 = y - i;
            if (x2 > -1 && y2 > -1 && x2 < 8 && y2 < 8)
                validMap[x2][y2] = true;
            y2 = y + i;
            if (x2 > -1 && y2 > -1 && x2 < 8 && y2 < 8)
                validMap[x2][y2] = true;
        }
    }

    void setValidStraightMove(int x, int y, int dir, Function<Integer, Integer> calcX) {
        for (int i = 1; x + i * dir < 7 && x + i * dir > 0 && boardInfo[x][i] == Chess.EM; ++i) {
            validMap[calcX.apply(i)][calcX.apply(i)] = true;
        }
        if (Chess.EM != boardInfo[x][y] && !boardInfo[x][y].sameColor(boardInfo[x][y]));
    }

    /**
     * Forcefully move a piece from a non-empty square to a position.
     * Any piece in the targeted square will be captured.
     * Does not perform any check
     * @param row row of the moving piece
     * @param col column of the moving piece
     * @param newRow row of the new position
     * @param newCol column of the new position
     */
    public void movePiece(int row, int col, int newRow, int newCol) {
        boardInfo[newRow][newCol] = boardInfo[row][col];
        boardInfo[row][col] = Chess.EM;
    }
}
