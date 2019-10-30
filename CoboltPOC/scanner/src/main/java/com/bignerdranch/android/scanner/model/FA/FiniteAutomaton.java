package com.bignerdranch.android.scanner.model.FA;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;
import java.util.Set;

public class FiniteAutomaton {

    private Map<State, List<Transition>> stateTransitionMap;
    private List<Transition> transitions;
    private State startingState;
    private Set<State> acceptingStates;
    private List<Path> pathList;

    public FiniteAutomaton (State startingState) {
        pathList = new ArrayList<>();
        this.stateTransitionMap = new HashMap<>();
        this.transitions = new ArrayList<>();
        this.startingState = startingState;
        this.acceptingStates = new HashSet<>();
        stateTransitionMap.put(this.startingState, new ArrayList<Transition>());
    }

    public void addTransition (Transition transition) {
        putState(transition.getInState(), transition);
        putState(transition.getOutState(), null);
        if(transition.getOutState().isAcceptingState()){
            acceptingStates.add(transition.getOutState());
        }
        transitions.add(transition);
    }

    public void addTransitions(List<Transition> transitions) {
        for (Transition t : transitions) {
            putState(t.getInState(), t);
            putState(t.getOutState(), null);
            this.transitions.add(t);
        }
    }

    private void putState(State state, Transition t) {
        List<Transition> temp;
        if(stateTransitionMap.containsKey(state)) {
            temp = stateTransitionMap.get(state);
        } else {
            temp = new ArrayList<>();
        }
        if(t != null)
            temp.add(t);
        stateTransitionMap.put(state, temp);
    }

    public void updateAcceptingStates() {
        acceptingStates.clear();
        for (State s : stateTransitionMap.keySet()) {
            if(s.isAcceptingState()) {
                acceptingStates.add(s);
            }
        }
    }

    public State getStartingState() {
        return startingState;
    }

    public Set<State> getAcceptingStates() {
        return acceptingStates;
    }

    public Set<State> getNonAcceptingStates() {
        Set<State> nonAcceptingStates = new HashSet<>();
        for(State s: stateTransitionMap.keySet()) {
            if(!acceptingStates.contains(s)) {
                nonAcceptingStates.add(s);
            }
        }
        return nonAcceptingStates;
    }


    public List<Transition> getTransitions() {
        return transitions;
    }

    public List<Transition> getOutTransitionsFromState(State state) {
        //System.out.println(stateTransitionMap.get(state).size());
        return stateTransitionMap.get(state);
    }

    public List<State> getDelta(Configuration q, char c) {
        List<State> delta = new ArrayList<>();
        List<Transition> transitions = new LinkedList<>();
        for (State s : q.getStates()) {
            transitions.addAll(getOutTransitionsFromState(s));
        }
        Transition t;
        while(transitions.size() > 0 && (t = transitions.remove(0)) != null) {
            if(t.getCharacter() == '~') {
                transitions.addAll(getOutTransitionsFromState(t.getOutState()));
            } else if(t.getCharacter() == c) {
                delta.add(t.getOutState());
            }
        }
        return delta;
    }


    public Configuration getEpsilonClosure(List<State> states) {
        if(states.size() == 0) {
            return null;
        }
        List<State> epsilonClosure = new ArrayList<>(states);
        List<Transition> transitions = new LinkedList<>();
        for(State s : states) {
            transitions.addAll(getOutTransitionsFromState(s));
        }
        Transition t;
        while(transitions.size() > 0 && (t = transitions.remove(0)) != null) {
            if(t.getCharacter() == '~') {
                epsilonClosure.add(t.getOutState());
                transitions.addAll(getOutTransitionsFromState(t.getOutState()));
            }
        }
        return new Configuration(epsilonClosure);
    }

    @Override
    public String toString() {
        StringBuilder stringBuilder = new StringBuilder();
        for (Transition t: transitions) {
            if(t.getChars() == null)
                stringBuilder.append(t.getCharacter());
            else
                for(Character c : t.getChars()) {
                    stringBuilder.append(c);
                }
        }
        return stringBuilder.toString();
    }

    @Override
    public boolean equals(Object o) {
        return o instanceof FiniteAutomaton
                //&& this.acceptingStates.equals(((FiniteAutomaton) o).acceptingStates)
                //&& this.getNonAcceptingStates().equals(((FiniteAutomaton) o).getNonAcceptingStates())
                && this.generatePathList().equals(((FiniteAutomaton )o).generatePathList());
    }

    private List<Path> generatePathList() {
        explorePaths(startingState, "");
        return pathList;
    }

    private void explorePaths(State currentState, String currentString) {
        List<Transition> transitions = this.getOutTransitionsFromState(currentState);
        if(transitions.isEmpty()) {
            pathList.add(new Path(currentString));
            return;
        }
        for ( Transition t: transitions) {
            explorePaths(t.getOutState(), currentString + t.getCharacter());
        }
    }

}
