package com.bignerdranch.android.scanner.algorithms;

import com.bignerdranch.android.scanner.model.FA.FiniteAutomaton;
import com.bignerdranch.android.scanner.model.FA.State;
import com.bignerdranch.android.scanner.model.FA.Transition;
import com.bignerdranch.android.scanner.model.RE;

import java.util.HashSet;
import java.util.Set;

public class ThompsonsConstruction {

    private RegexParser parser = new RegexParser();

    public FiniteAutomaton apply(Set<RE> patterns) {
        Set<FiniteAutomaton> intermediateNFAs
                = produceIntermediateNFAs(patterns);
        return produceFinalNFA(intermediateNFAs);
    }

    private Set<FiniteAutomaton> produceIntermediateNFAs(Set<RE> patterns) {
        Set<FiniteAutomaton> intermediateNFAs = new HashSet<>();
        for (RE pattern : patterns) {
            intermediateNFAs.add(parser.parseExpression(pattern));
        }
        return intermediateNFAs;
    }

    private static FiniteAutomaton produceFinalNFA(Set<FiniteAutomaton> nfas) {
        State resultStartingState = new State(false);
        State resultAcceptingState = new State(true);

        FiniteAutomaton resultFA = new FiniteAutomaton(resultStartingState);

        for (FiniteAutomaton nfa : nfas) {
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
        resultFA.updateAcceptingStates();
        return resultFA;
    }


}
