package com.bignerdranch.android.scanner.algorithms;

import com.bignerdranch.android.scanner.model.FA.FiniteAutomaton;
import com.bignerdranch.android.scanner.model.FA.State;
import com.bignerdranch.android.scanner.model.FA.Transition;
import com.bignerdranch.android.scanner.model.RE;

import java.util.List;

public class NFABuilder {
    private State startingState;
    private State currentState;
    private FiniteAutomaton nfa;

    public NFABuilder() {
        startingState = new State(false);
        currentState = startingState;
        nfa = new FiniteAutomaton(startingState);
    }

    public FiniteAutomaton buildSimpleNFA(RE regex) {
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

    public FiniteAutomaton buildOrNFA(List<FiniteAutomaton> nfas) {
        State resultStartingState = new State(false);
        State resultAcceptingState = new State(true);

        FiniteAutomaton resultFA = new FiniteAutomaton(resultStartingState);

        for(FiniteAutomaton nfa : nfas) {
            resultFA.addTransition(new Transition('~',
                    resultStartingState,
                    nfa.getStartingState()));
            for (State as : nfa.getAcceptingStates()) {
                resultFA.addTransition(new Transition('~', as,
                        resultAcceptingState));
                as.setAcceptingState(false);
            }
            resultFA.addTransitions(nfa.getTransitions());
        }
        return resultFA;
    }

    public FiniteAutomaton buildAndNFA(FiniteAutomaton nfa1,
                                       FiniteAutomaton nfa2) {
        return null;
    }

    public FiniteAutomaton buildClosureNFA(FiniteAutomaton nfa) {
        return null;
    }

}
