package com.example.chessp2p;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.Intent;
import android.content.res.Configuration;
import android.media.MediaPlayer;
import android.os.Bundle;
import android.os.Handler;
import android.os.Looper;
import android.text.InputType;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.example.chessp2p.gameplay.PlayingBoardView;

import java.util.Locale;
import java.util.Timer;
import java.util.TimerTask;


public class MainGame extends Activity {
    TextView timerText;
    PlayingBoardView boardView;

    MediaPlayer effect,background;
    int whiteTimeLeft = 0;
    int blackTimeLeft = 0;
    boolean isTimerSet = false;
    boolean isWhite = true;

    final Handler handler = new Handler(Looper.getMainLooper());
    final Runnable updateTimerText = new Runnable() {
        @Override
        public void run() {
            if (isWhite)
                timerText.setText(String.format(Locale.US, "%02d:%02d", whiteTimeLeft / 60, whiteTimeLeft % 60));
            else
                timerText.setText(String.format(Locale.US, "%02d:%02d", blackTimeLeft / 60, blackTimeLeft % 60));
            if (whiteTimeLeft <= 0 || blackTimeLeft <= 0)
                timerText.setText(R.string.finish_timer);
        }
    };

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main_game);
        effect = MediaPlayer.create(this, R.raw.move);
        background = MediaPlayer.create(this,R.raw.background);
        if(MainActivity.setting.getBackgroundMusic().equals("on"))
        {
            background.setLooping(true);
            background.start();
        }
        else{
            background.stop();
        }

        AlertDialog.Builder alert = new AlertDialog.Builder(this);
        alert.setTitle("Set time for each player (in seconds)");
        final EditText input = new EditText(this);
        input.setInputType(InputType.TYPE_CLASS_NUMBER);
        input.setRawInputType(Configuration.KEYBOARD_12KEY);
        alert.setView(input);
        alert.setPositiveButton("Set", (dialog, whichButton) -> {
            String value = input.getText().toString();
            if(value.equals(""))
            {
                Toast.makeText(getApplicationContext(), "Please enter a number", Toast.LENGTH_SHORT).show();
                return;
            }
            whiteTimeLeft = blackTimeLeft = Integer.parseInt(value) + 1;
            isTimerSet = true;
            Toast.makeText(getApplicationContext(), "Game start!", Toast.LENGTH_SHORT).show();
        });
        alert.setNegativeButton("Cancel", (dialog, whichButton) -> finish());
        alert.show();

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

        boardView.setGameEndListener((isWhiteWin, isCheckmate) -> MainGame.this.EndGame(isWhiteWin));

        Timer timer = new Timer();
        TimerTask task = new TimerTask() {
            @Override
            public void run() {
                if (whiteTimeLeft <= 0 || blackTimeLeft <= 0) {
                    timer.cancel();
                    return;
                }
                handler.post(updateTimerText);
                if (isWhite && isTimerSet) {
                    whiteTimeLeft--;
                } else if (isTimerSet){
                    blackTimeLeft--;
                }
            }
        };
        timer.scheduleAtFixedRate(task, 0, 1000);

        boardView.onMoveListener = (move, x1, y1, x2, y2) -> {
            if(MainActivity.setting.getSoundEffect().equals("on"))
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
        if(MainActivity.setting.getBackgroundMusic().equals("on"))
            background.pause();
    }

    @Override
    protected void onResume(){
        super.onResume();
        if(MainActivity.setting.getBackgroundMusic().equals("on"))
        {
            background.start();
            background.seekTo(0);
        }
    }

    @Override
    protected void onStop() {
        super.onStop();
        if(MainActivity.setting.getBackgroundMusic().equals("on"))
            background.stop();
    }
}