package com.bignerdranch.android.scanner.algorithms;

import com.bignerdranch.android.scanner.model.FA.State;
import java.util.Set;

public class Partition {
    private Set<State> states;

    Partition(Set<State> states) {
        this.states = states;
        for(State s : states) {
            s.setPartition(this);
        }
    }

    Set<State> getStates() {
        return states;
    }

    boolean containsAcceptingState() {
        for(State s : states) {
            if (s.isAcceptingState())
                return true;
        }
        return false;
    }

    @Override
    public boolean equals(Object o) {
        if(o instanceof Partition) {
            return this.states.equals(((Partition) o).states);
        }
        return false;
    }
}
