package com.bignerdranch.android.scanner.model.FA;

import com.bignerdranch.android.scanner.model.CobolCharacter;

public class State {
    private boolean acceptingState;

    public State(boolean acceptingState) {
        //System.out.println("State");

        this.acceptingState = acceptingState;
    }

    public void setAcceptingState(boolean acceptingState) {
        this.acceptingState = acceptingState;
    }

    public boolean isAcceptingState() {
        return acceptingState;
    }
}
