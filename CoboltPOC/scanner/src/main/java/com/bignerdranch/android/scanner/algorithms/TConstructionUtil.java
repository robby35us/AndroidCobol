package com.bignerdranch.android.scanner.algorithms;

import com.bignerdranch.android.scanner.model.FA.FiniteAutomaton;
import com.bignerdranch.android.scanner.model.FA.State;
import com.bignerdranch.android.scanner.model.FA.Transition;
import com.bignerdranch.android.scanner.model.RE;

import java.util.List;

public class TConstructionUtil {
    private State currentState;
    private FiniteAutomaton nfa;

    TConstructionUtil() {
        State startingState = new State(false);
        currentState = startingState;
        nfa = new FiniteAutomaton(startingState);
    }

    FiniteAutomaton buildSimpleNFA(RE regex) {
        char character = regex.getNextChar();
        State tempState;
        while (character != '\n'){
            tempState = new State(false);
            Transition transition
                    = new Transition(character,
                                     currentState,
                                     tempState);
            nfa.addTransition(transition);
            currentState = tempState;
            character = regex.getNextChar();
        }
        currentState.setAcceptingState(true);
        nfa.updateAcceptingStates();
        return nfa;
    }



    public FiniteAutomaton applyAndToNFA(FiniteAutomaton nfa1,
                                         FiniteAutomaton nfa2) {
        return null;
    }

    public FiniteAutomaton buildClosureNFA(FiniteAutomaton nfa) {
        return null;
    }

}
