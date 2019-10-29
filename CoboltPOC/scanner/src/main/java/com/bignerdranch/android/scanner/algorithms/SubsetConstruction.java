package com.bignerdranch.android.scanner.algorithms;

import com.bignerdranch.android.scanner.model.CobolCharacter;
import com.bignerdranch.android.scanner.model.FA.Configuration;
import com.bignerdranch.android.scanner.model.FA.Transition;
import com.bignerdranch.android.scanner.model.FA.FiniteAutomaton;
import com.bignerdranch.android.scanner.model.FA.State;

import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;

public class SubsetConstruction {

    private static FiniteAutomaton dfa;
    private static FiniteAutomaton nfa;
    private static List<Configuration> workList;
    private static List<Configuration> configs;
    private static Configuration workItem;
    private static Configuration newWorkItem;

    public static FiniteAutomaton apply(FiniteAutomaton nfa) {
        SubsetConstruction.nfa = nfa;
        initialize();
        while(!workList.isEmpty()) {
            workItem = workList.remove(0);
            processConfiguration();
        }
        dfa.updateAcceptingStates();
        FiniteAutomaton tempResult = dfa;
        cleanup();
        return tempResult;
    }

    private static void initialize() {
        List<State> stateList = new ArrayList<>();
        stateList.add(nfa.getStartingState());

        workList = new LinkedList<>();
        workList.add(nfa.getEpsilonClosure(stateList));

        dfa = new FiniteAutomaton(workList.get(0).getResultingState());

        configs = new ArrayList<>();
        configs.add(workList.get(0));
    }

    private static void cleanup() {
        dfa = null;
        nfa = null;
        workList = null;
        configs = null;
        workItem = null;
        newWorkItem = null;
    }

    private static void processConfiguration() {
        boolean isAcceptingConfiguration = true;
        for(Character c : CobolCharacter.getCobolCharacterList()) {
            newWorkItem = getEClosureOFDeltaOf(c);
            if(newWorkItem == null) {
                continue;
            }
            manageTransitions(c);
            updateConfigurationLists();
            isAcceptingConfiguration = false;
        }
        if(isAcceptingConfiguration) {
            workItem.getResultingState().setAcceptingState(true);
        }
    }

    private static Configuration getEClosureOFDeltaOf(char c) {
        return nfa.getEpsilonClosure(nfa.getDelta(workItem, c));
    }

    private static void manageTransitions(char c) {
        boolean likeTransitionFound = dfaHasLikeTransition(c);
        if (!likeTransitionFound && newWorkItem.getStates().size() != 0) {
            addTransitionToDFA(c);
        }
    }

    private static boolean dfaHasLikeTransition(char c) {
        boolean likeTransitionFound = false;
        List<Transition> dfaTransitions = dfa.getOutTransitionsFromState(workItem.getResultingState());
        for (Transition t : dfaTransitions) {
            if (t.getCharacter() == c) {
                likeTransitionFound = true;
                break;
            }
        }
        return likeTransitionFound;
    }

    private static void addTransitionToDFA(char c) {
        dfa.addTransition(new Transition(c, workItem.getResultingState(),
                          newWorkItem.getResultingState()));
    }


    private static void updateConfigurationLists() {
        if (!configs.contains(newWorkItem) && newWorkItem.getStates().size() != 0) {
            workList.add(newWorkItem);
            configs.add(newWorkItem);
        }
    }
}
