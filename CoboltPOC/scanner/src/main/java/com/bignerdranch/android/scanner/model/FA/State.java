package com.bignerdranch.android.scanner.model.FA;

import com.bignerdranch.android.scanner.algorithms.Partition;

public class State {
    private boolean acceptingState;
    private Partition partition;

    public State(boolean acceptingState) {
        this.acceptingState = acceptingState;
    }

    public void setPartition(Partition p) {
        partition = p;
    }

    public Partition getPartition() {
        return partition;
    }

    public void setAcceptingState(boolean acceptingState) {
        this.acceptingState = acceptingState;
    }

    public boolean isAcceptingState() {
        return acceptingState;
    }
}
