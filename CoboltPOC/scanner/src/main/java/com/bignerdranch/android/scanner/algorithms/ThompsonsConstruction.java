package com.bignerdranch.android.scanner.algorithms;

import com.bignerdranch.android.scanner.model.FA.FiniteAutomaton;
import com.bignerdranch.android.scanner.model.FA.State;
import com.bignerdranch.android.scanner.model.FA.Transition;
import com.bignerdranch.android.scanner.model.RE;

import java.util.HashSet;
import java.util.Set;
import java.util.Stack;

public class ThompsonsConstruction {

    public static FiniteAutomaton apply(Set<RE> patterns) {
        Set<FiniteAutomaton> intermediateNFAs
                = produceIntermediateNFAs(patterns);
        return produceFinalNFA(intermediateNFAs);
    }

    private static Set<FiniteAutomaton> produceIntermediateNFAs(Set<RE> patterns) {
        Set<FiniteAutomaton> intermediateNFAs = new HashSet<>();
        for (RE pattern : patterns) {
            intermediateNFAs.add(produceIntermediateNFA(pattern));
        }
        return intermediateNFAs;
    }

    static FiniteAutomaton produceIntermediateNFA(RE pattern) {
        Stack<Character> symbols = new Stack<>();
        Stack<FiniteAutomaton> nfas = new Stack<>();

        while (pattern.hasNextChar())
            processChar(pattern.getNextChar(), symbols, nfas);
        while(!symbols.empty())
            processSymbolFromStack(symbols.pop(), symbols, nfas);

        return !nfas.empty() ? nfas.pop() :
                new FiniteAutomaton(new State(true));
    }

    private static void processChar(char c,
                            Stack<Character> symbols,
                            Stack<FiniteAutomaton> nfas) {
        if (c == '(') {
            symbols.push(c);
        } else if (isSymbol(c)) {
            processSymbol(c, symbols, nfas);
        } else {
            nfas.push(buildSimple(c));
        }

        if (symbols.size() < nfas.size() - 1){
            symbols.push(' ');
        }

    }

    private static boolean isSymbol(char c) {
        switch (c) {
            case '(':
            case ')':
            case '|':
            case '*':
            case ' ':
                return true;
            default:
                return false;
        }
    }

    private static void processSymbol(char newSymbol,
                                         Stack<Character> symbols,
                                         Stack<FiniteAutomaton> nfas) {
        while(!symbols.empty() && precedenceOf(newSymbol) <= precedenceOf(symbols.peek())) {
            processSymbolFromStack(symbols.pop(), symbols, nfas);
        }
        if(newSymbol != ')')
            symbols.push(newSymbol);
    }

    private static void processSymbolFromStack(char symbol,
                                              Stack<Character> symbols,
                                              Stack<FiniteAutomaton> nfas) {
        switch (symbol) {
            case '(': // not sure about this!!!!
            case ' ':
                FiniteAutomaton andTemp = nfas.pop();
                if(!nfas.empty())
                    nfas.push(buildAnd(nfas.pop(), andTemp));
                else
                    nfas.push(andTemp);
                break;
            case '|':
                FiniteAutomaton orTemp = nfas.pop();
                nfas.push(buildOr(nfas.pop(), orTemp));
                break;
            case ')':
                break;
        }
    }

    private static int precedenceOf(char symbol) {
        switch (symbol) {
            case '*':
                return 3;
            case ' ':
                return 2;
            case '|':
                return 1;
            default:
                return 0;
        }
    }

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
