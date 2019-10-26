package com.bignerdranch.android.scanner.model.FA;

import java.util.ArrayList;
import java.util.List;

public class Configuration {
    private List<State> states;
    private State resultingState;

    public Configuration(State state) {
        //System.out.println("Configuration");

        states = new ArrayList<>();
        states.add(state);
        resultingState = new State(false);
    }

    public Configuration(List<State> states){
        //System.out.println("Configuration");

        this.states = states;
        boolean markAsAccepting = false;
        for(State s: states) {
            if (s.isAcceptingState()) {
                markAsAccepting = true;
                break;
            }
        }
        resultingState = new State(markAsAccepting);
    }

    public List<State> getStates() {
        return states;
    }

    public State getResultingState() {
        return resultingState;
    }

    public void dropStatesList() {
        states = null;
    }

    @Override
    public boolean equals(Object o) {
        if(o instanceof Configuration) {
            this.states.containsAll(((Configuration) o).states);
        }
        return false;
    }
}