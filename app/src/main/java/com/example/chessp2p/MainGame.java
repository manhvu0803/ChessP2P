package com.example.chessp2p;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.Intent;
import android.media.MediaPlayer;
import android.os.Bundle;
import android.os.Handler;
import android.os.Looper;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.chessp2p.gameplay.BoardView;
import com.example.chessp2p.gameplay.PlayingBoardView;

import java.util.Timer;
import java.util.TimerTask;


public class MainGame extends Activity {
    TextView timerText;
    PlayingBoardView boardView;

    MediaPlayer effect,background;
    int timeLeft = 3600;
    boolean isWhite = true;

    final Handler handler = new Handler(Looper.getMainLooper());
    final Runnable updateTimerText = new Runnable() {
        @Override
        public void run() {
            String str = timeLeft / 60 + ":" + timeLeft % 60;
            if (timeLeft <= 0)
                str = "Done";
            timerText.setText(str);
        }
    };

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main_game);
        effect = MediaPlayer.create(this, R.raw.move);
        background = MediaPlayer.create(this,R.raw.background);
        background.setLooping(true);
        background.start();


        timerText = this.findViewById(R.id.timerText);
        boardView = this.findViewById(R.id.playingBoardView);
        TextView logWhiteTextView = this.findViewById(R.id.logWhiteTextView);
        TextView logBlackTextView = this.findViewById(R.id.logBlackTextView);

        Button undoButton = this.findViewById(R.id.undoButton);
        undoButton.setOnClickListener((view) -> {
            isWhite = !isWhite;
            boardView.undo();
        });

        Button redoButton = this.findViewById(R.id.redoButton);
        redoButton.setOnClickListener((view) -> {
            isWhite = !isWhite;
            boardView.redo();
        });

        boardView.setGameEndListener((isWhiteWin, isCheckmate) -> {
            MainGame.this.EndGame(isWhiteWin);
        });

        Timer timer = new Timer();
        TimerTask task = new TimerTask() {
            @Override
            public void run() {
                if (timeLeft <= 0) {
                    timer.cancel();
                    return;
                }
                handler.post(updateTimerText);
                timeLeft -= 1;
            }
        };
        timer.scheduleAtFixedRate(task, 0, 1000);

        boardView.onMoveListener = (move, x1, y1, x2, y2) -> {
            effect.start();
            if (isWhite)
                logWhiteTextView.append("\n" + move);
            else
                logBlackTextView.append("\n" + move);
            isWhite = !isWhite;
        };
    }

    void EndGame(boolean isWhiteWin) {
        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.setMessage((isWhiteWin)? "White win" : "Black win");
        builder.setPositiveButton("OK", (dialog, id) -> {
            Intent intent = new Intent(this, MainActivity.class);
            MainGame.this.startActivity(intent);
        });
        builder.show();
    }

    @Override
    protected void onPause() {
        super.onPause();
        background.pause();
    }

    @Override
    protected void onResume(){
        super.onResume();
        background.start();
        background.seekTo(0);
    }

    @Override
    protected void onStop() {
        super.onStop();
        background.stop();
    }
}