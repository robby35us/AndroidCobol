package com.bignerdranch.android.scanner.model.FA;


public class Transition {

    private char character;
    private State inState;
    private State outState;

    public Transition(char cc, State in, State out) {
        //System.out.println("Transition");

        character = cc;
        inState = in;
        outState = out;
    }

    public State getInState() {
        return inState;
    }

    public State getOutState() {
        return outState;
    }

    public char getCharacter() {
        return character;
    }
}
