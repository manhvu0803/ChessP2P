package com.example.chessp2p;

import android.app.Activity;
import android.os.Bundle;
import android.os.Handler;
import android.os.Looper;
import android.widget.TextView;

import com.example.chessp2p.gameplay.BoardView;

import java.util.Timer;
import java.util.TimerTask;


public class MainGame extends Activity {
    TextView timerText;
    BoardView boardView;

    int timeLeft = 20;

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

        timerText = this.findViewById(R.id.timerText);
        boardView = this.findViewById(R.id.playingBoardView);
        TextView logTextView = this.findViewById(R.id.logTextView);

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

        boardView.onMoveListener = move -> logTextView.append(" " + move);
    }
}