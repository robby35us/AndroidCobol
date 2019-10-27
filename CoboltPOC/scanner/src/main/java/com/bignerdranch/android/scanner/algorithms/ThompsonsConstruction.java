package com.bignerdranch.android.scanner.algorithms;

import com.bignerdranch.android.scanner.model.FA.FiniteAutomaton;
import com.bignerdranch.android.scanner.model.RE;

import java.util.LinkedList;
import java.util.List;
import java.util.Set;

public class ThompsonsConstruction {

    public static FiniteAutomaton apply(Set<RE> patterns) {
        List<FiniteAutomaton> simpleAutomata = new LinkedList<>();
        for (RE re : patterns) {
            simpleAutomata.add(
                    new TConstructionUtil().buildSimpleNFA(re));
        }
        return new TConstructionUtil().applyOrToNFA(simpleAutomata);
    }
}
