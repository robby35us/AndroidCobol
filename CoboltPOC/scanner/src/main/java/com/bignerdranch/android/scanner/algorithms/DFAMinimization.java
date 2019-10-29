package com.bignerdranch.android.scanner.algorithms;

import com.bignerdranch.android.scanner.model.CobolCharacter;
import com.bignerdranch.android.scanner.model.FA.FiniteAutomaton;
import com.bignerdranch.android.scanner.model.FA.State;
import com.bignerdranch.android.scanner.model.FA.Transition;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;

public class DFAMinimization {
    private static FiniteAutomaton startingDFA;
    private static Set<Partition> tempPSet;
    private static Set<Partition> partitionSet;
    private static Set<Partition> finalPSet;

    public static FiniteAutomaton apply(FiniteAutomaton dfa) {
        initializeTempSet(dfa);
        while(!partitionSet.isEmpty()){
            partitionSet = tempPSet;
            tempPSet = new HashSet<>();
            for(Partition p : partitionSet) {
                Set<Partition> resultOfSplit = split(p);
                if(resultOfSplit.size() > 1) {
                    tempPSet.addAll(resultOfSplit);
                } else {
                    finalPSet.addAll(resultOfSplit);
                }
            }
        }
        int sum = 0;
        for(Partition p : finalPSet) {
            sum += p.getStates().size();
        }
        System.out.println(sum);
        return buildResultingDFA(dfa);
    }

    private static void initializeTempSet(FiniteAutomaton dfa) {
        startingDFA = dfa;

        tempPSet = new HashSet<>();
        tempPSet.add(new Partition(dfa.getNonAcceptingStates()));
        tempPSet.add(new Partition(dfa.getAcceptingStates()));

        partitionSet = tempPSet;
        finalPSet = new HashSet<>();
    }


    private static Set<Partition> split(Partition p) {
        Set<Partition> splitOnC = null;
        for(char c : CobolCharacter.getCobolCharacterList()) {
            splitOnC = getSplitOnChar(c, p);
            if(splitOnC.size() > 1) {
                return splitOnC;
            }
        }
        splitOnC = new HashSet<>();
        splitOnC.add(p);
        return splitOnC;
    }

    private static Set<Partition> getSplitOnChar(char c, Partition p) {
        Partition primaryPartition = null;
        Set<State> primaryPartitionStates = new HashSet<>();
        Set<State> otherPartitionStates = new HashSet<>();
        Set<State> remainderStates = new HashSet<>(p.getStates());

        Set<Transition> fromTransitionsOnChar = getFromTransitionsOnChar(c, p.getStates());
        for(Transition t : fromTransitionsOnChar) {
            if (primaryPartition == null) {
                primaryPartition = t.getOutState().getPartition();
            }

            if (t.getOutState().getPartition() == primaryPartition) {
                primaryPartitionStates.add(t.getInState());
            } else {
                otherPartitionStates.add(t.getInState());
            }
            remainderStates.remove(t.getInState());
        }
        if(!remainderStates.isEmpty()) {
            otherPartitionStates.addAll(remainderStates);
        }

        Set<Partition> newPartitions = new HashSet<>();
        if(!primaryPartitionStates.isEmpty())
            newPartitions.add(new Partition(primaryPartitionStates));
        if(!otherPartitionStates.isEmpty()) {
            newPartitions.add(new Partition(otherPartitionStates));
        }
        return newPartitions;
    }

    private static Set<Transition> getFromTransitionsOnChar(char c, Set<State> states) {
        Set<Transition> outTransitions = new HashSet<>();
        for(State s : states) {
            outTransitions.addAll(startingDFA.getOutTransitionsFromState(s));
        }
        Set<Transition> outTransitionsOnChar = new HashSet<>();
        for(Transition t : outTransitions) {
            if(t.getCharacter() == c) {
                outTransitionsOnChar.add(t);
            }
        }
        return outTransitionsOnChar;
    }




    private static FiniteAutomaton buildResultingDFA(FiniteAutomaton dfa) {
        Map<Partition, State> resultingDFAStates = new HashMap<>();
        State resultingStartingState = null;

        for (Partition p : finalPSet) {
            resultingDFAStates.put(p, new State(p.containsAcceptingState()));
            if (p.getStates().contains(dfa.getStartingState())) {
                resultingStartingState = resultingDFAStates.get(p);
            }
        }

        FiniteAutomaton resultingDFA = new FiniteAutomaton(resultingStartingState);
        List<Transition> newTransitions = new ArrayList<>();
        for (Partition p : finalPSet) {
            Set<Character> chars = new HashSet<>();
            List<Transition> oldTransitions = null;
            for (State s : p.getStates()) {
                oldTransitions = dfa.getOutTransitionsFromState(s);
                for (Transition t : oldTransitions) {
                    chars.add(t.getCharacter());
                }
            }

            State inState = resultingDFAStates.get(p);
            State outState = null;

            if(oldTransitions.size() == 0)
                continue;
            Transition temp = oldTransitions.get(0);
            for (Partition q : finalPSet) {
                if (q.getStates().contains(temp.getOutState())) {
                    outState = resultingDFAStates.get(q);
                    break;
                }
            }
            newTransitions.add(new Transition(chars, inState, outState));
        }
        resultingDFA.addTransitions(newTransitions);
        return resultingDFA;
    }


}
