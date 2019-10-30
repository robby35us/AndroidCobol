package com.bignerdranch.android.scanner.algorithms;

import com.bignerdranch.android.scanner.model.FA.FiniteAutomaton;
import com.bignerdranch.android.scanner.model.FA.State;
import com.bignerdranch.android.scanner.model.FA.Transition;
import com.bignerdranch.android.scanner.model.RE;

import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;

import java.util.HashSet;
import java.util.Set;

public class TConstructionTest {
    private Set<RE> regexSet;
    private FiniteAutomaton comparisonNFA;
    private FiniteAutomaton expectedNFA;


    @Before
    public void initializeResources() {
        regexSet = new HashSet<>();
        comparisonNFA = new FiniteAutomaton(new State(false));
    }

    @Test
    public void testEmpty() {
        expectedNFA = ThompsonsConstruction.apply(regexSet);

        Assert.assertEquals(comparisonNFA, expectedNFA);
    }

    @Test
    public void testSingleNode() {
        regexSet.add(new RE("a"));
        expectedNFA = ThompsonsConstruction.apply(regexSet);

        Transition t = new Transition('a',
                comparisonNFA.getStartingState(), new State(false));
        comparisonNFA.addTransition(t);

        Assert.assertEquals(comparisonNFA, expectedNFA);
    }
}
