package com.example.user.myapplication.huntthewumpus;

import com.example.user.myapplication.R;

public enum Direction {
    NORTH("N", "TN", R.anim.slide_up_in, R.anim.slide_down_out),
    SOUTH("S", "TS", R.anim.slide_down_in, R.anim.slide_up_out),
    EAST("E", "TE", R.anim.slide_right_in, R.anim.slide_left_out),
    WEST("W", "TW", R.anim.slide_left_in, R.anim.slide_right_out);

    public String moveCommand;
    public String throwCommand;
    public int inAnimationId;
    public int outAnimationId;

    private Direction(String moveCommand, String throwCommand, int inAnimationId, int outAnimationId) {
        this.moveCommand = moveCommand;
        this.throwCommand = throwCommand;
        this.inAnimationId = inAnimationId;
        this.outAnimationId  = outAnimationId;
    }
}
