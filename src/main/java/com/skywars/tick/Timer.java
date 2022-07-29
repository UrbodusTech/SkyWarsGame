package com.skywars.tick;

import lombok.Getter;
import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
@Getter
public class Timer {

    private final int seconds;

    private int time = 0;
    private int repeats = 0;
    private boolean finished = false;

    public void start() {
        time = seconds;
        repeats = 0;
    }

    public void down() {
        if (finished) {
            return;
        }

        time--;
        if (time == 0) {
            finished = true;
        }
    }

    public void restart() {
        time = seconds;
        finished = false;
        repeats++;
    }

    public String format() {
        int mDivide = time / 60;
        int minutes = (int) Math.floor(mDivide);

        int sRest = time % 60;
        int seconds = (int) Math.floor(sRest);

        String firstPart = (minutes < 10 ? "0" : "") + minutes;
        String secondPart = (seconds < 10 ? "0" : "") + seconds;

        return firstPart + ":" + secondPart;
    }
}
