package com.bignerdranch.android.scanner.algorithms;

import com.bignerdranch.android.scanner.model.FA.FiniteAutomaton;
import com.bignerdranch.android.scanner.model.FA.State;
import com.bignerdranch.android.scanner.model.FA.Transition;

 class AutomataBuilder {
    static FiniteAutomaton buildSimple(char c) {
        State inState = new State(false);
        FiniteAutomaton result = new FiniteAutomaton(inState);
        result.addTransition(new Transition(c, inState, new State(true)));
        result.updateAcceptingStates();
        return result;
    }

    static FiniteAutomaton buildAnd(
            FiniteAutomaton a, FiniteAutomaton b) {
        State aEnd = a.getAcceptingStates().iterator().next();
        State bStart = b.getStartingState();

        aEnd.setAcceptingState(false);

        FiniteAutomaton result = new FiniteAutomaton(a.getStartingState());
        result.addTransitions(a.getTransitions());
        result.addTransition(new Transition('~', aEnd, bStart));
        result.addTransitions(b.getTransitions());
        result.updateAcceptingStates();

        return result;
    }

    static FiniteAutomaton buildOr(
            FiniteAutomaton a, FiniteAutomaton b) {
        State start = new State(false);
        State aStart = a.getStartingState();
        State aEnd = a.getAcceptingStates().iterator().next();
        State bStart = b.getStartingState();
        State bEnd = b.getAcceptingStates().iterator().next();
        State end = new State(true);

        aEnd.setAcceptingState(false);
        bEnd.setAcceptingState(false);

        FiniteAutomaton result = new FiniteAutomaton(start);
        result.addTransition(new Transition('~', start, aStart));
        result.addTransitions(a.getTransitions());
        result.addTransition(new Transition('~', aEnd, end));
        result.addTransition(new Transition('~', start, bStart));
        result.addTransitions(b.getTransitions());
        result.addTransition(new Transition('~', bEnd, end));
        result.updateAcceptingStates();

        return result;
    }

    static FiniteAutomaton buildClosure(FiniteAutomaton a) {
        State start = new State(false);
        State aStart = a.getStartingState();
        State aEnd = a.getAcceptingStates().iterator().next();
        State end = new State(true);

        aEnd.setAcceptingState(false);

        FiniteAutomaton result = new FiniteAutomaton(start);
        result.addTransition(new Transition('~', start, aStart));
        result.addTransition(new Transition('~', start, end));
        result.addTransitions(a.getTransitions());
        result.addTransition(new Transition('~', aEnd, end));
        result.addTransition(new Transition('~', aEnd, aStart));

        result.updateAcceptingStates();
        return result;
    }
}
