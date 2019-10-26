package com.bignerdranch.android.scanner;

import com.bignerdranch.android.scanner.model.CobolCharacter;
import com.bignerdranch.android.scanner.model.FA.Configuration;
import com.bignerdranch.android.scanner.model.FA.FiniteAutomaton;
import com.bignerdranch.android.scanner.model.FA.State;
import com.bignerdranch.android.scanner.model.FA.Transition;
import com.bignerdranch.android.scanner.model.RE;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;
import java.util.Set;
import java.util.TreeSet;

public class ScannerGenerator {

    private Set<RE> patterns;

    public ScannerGenerator() {
        patterns = new TreeSet<>();
    }

    public void getKeywordsFromFile( InputStream sourceFile) {
        BufferedReader br = null;
        try {
            br = new BufferedReader(new InputStreamReader(sourceFile));

            String line;

            while ((line = br.readLine()) != null) {
                patterns.add(new RE(line.trim()));
            }

        } catch (IOException e) {
            System.out.println("Exception while reading input " + e);
        } finally {
            try {
                if (br != null) {
                    br.close();
                }
            } catch (IOException e) {
                System.out.println("Error while closing stream: " + e);
            }
        }
        // Apply Thompson's Construction
        List<FiniteAutomaton> simpleAutomata = new LinkedList<>();
        for (RE re : patterns) {
            simpleAutomata.add(
                    new NFABuilder().buildSimpleNFA(re));
        }
        FiniteAutomaton nfa = new NFABuilder().buildOrNFA(simpleAutomata);

        System.out.println(nfa);

        // Apply Subset Construction

        List<State> stateList = new ArrayList<>();
        stateList.add(nfa.getStartingState());

        List<Configuration> workList = new LinkedList<>();
        workList.add(nfa.getEpsilonClosure(stateList));

        FiniteAutomaton dfa = new FiniteAutomaton(
                CobolCharacter.getCobolCharacterList(),
                workList.get(0).getResultingState());

        List<Configuration> configs = new ArrayList<>();
        configs.add(workList.get(0));

        while(!workList.isEmpty()){
            //System.out.println(workList.size());
            Configuration workItem = workList.remove(0);
            for(Character c : CobolCharacter.getCobolCharacterList()) {
                Configuration newWorkItem = nfa.getEpsilonClosure(nfa.getDelta(workItem, c));
                if(newWorkItem == null) {
                    continue;
                }

                boolean likeTransitionFound = false;
                List<Transition> dfaTransitions = dfa.getOutTransitionsFromState(workItem.getResultingState());
                for(Transition t : dfaTransitions) {
                    if(t.getCharacter() == c) {
                        likeTransitionFound = true;
                        break;
                    }
                }
                if(!likeTransitionFound && newWorkItem.getStates().size() != 0) {
                    dfa.addTransition(
                            new Transition(c, workItem.getResultingState(),
                                    newWorkItem.getResultingState()));
                }
                if(!configs.contains(newWorkItem) && newWorkItem.getStates().size() != 0) {
                    workList.add(newWorkItem);
                    configs.add(newWorkItem);
                }
            }
        }
        System.out.println(dfa);
    }
}
