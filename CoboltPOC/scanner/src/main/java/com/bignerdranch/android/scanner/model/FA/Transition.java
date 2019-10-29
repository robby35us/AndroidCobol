package com.bignerdranch.android.scanner.model.FA;


import java.util.Set;

public class Transition {

    private Set<Character> chars;
    private char character;
    private State inState;
    private State outState;

    public Transition(char cc, State in, State out) {
        character = cc;
        inState = in;
        outState = out;
    }

    public Transition(Set<Character> chars, State in, State out) {
        this.chars = chars;
        this.inState = in;
        this.outState = out;
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

    public Set<Character> getChars() {
        return chars;
    }
}
