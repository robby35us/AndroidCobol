package com.bignerdranch.android.scanner.model.FA;

import com.bignerdranch.android.scanner.algorithms.Partition;

public class State {
    private boolean acceptingState;
    private Partition partition;
    private boolean marked = false;

    public State(boolean acceptingState) {
        this.acceptingState = acceptingState;
    }

    public void setPartition(Partition p) {
        partition = p;
    }

    public Partition getPartition() {
        return partition;
    }

    public boolean isMarked() {
        return marked;
    }

    public void setMarked(boolean marked) {
        this.marked = marked;
    }


    public void setAcceptingState(boolean acceptingState) {
        this.acceptingState = acceptingState;
    }

    public boolean isAcceptingState() {
        return acceptingState;
    }
}
