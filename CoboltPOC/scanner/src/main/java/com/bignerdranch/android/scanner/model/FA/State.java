package com.bignerdranch.android.scanner.model.FA;

public class State {
    private boolean acceptingState;

    public State(boolean acceptingState) {
        this.acceptingState = acceptingState;
    }

    public void setAcceptingState(boolean acceptingState) {
        this.acceptingState = acceptingState;
    }

    public boolean isAcceptingState() {
        return acceptingState;
    }
}
