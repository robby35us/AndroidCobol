package com.bignerdranch.android.scanner.algorithms;

import com.bignerdranch.android.scanner.model.FA.FiniteAutomaton;
import com.bignerdranch.android.scanner.model.FA.State;
import com.bignerdranch.android.scanner.model.RE;

import org.junit.Assert;
import org.junit.Test;

public class ProduceIntermediateNFATests {

    @Test
    public void emptyStringProducesEmptyFA() {
        RE pattern = new RE("");
        FiniteAutomaton actual = ThompsonsConstruction.
                produceIntermediateNFA(pattern);
        Assert.assertEquals(new FiniteAutomaton(new State(true)), actual);
    }

    @Test
    public void singleCharProducesSimpleFA() {
        // Arrange
        RE pattern = new RE("a");

        FiniteAutomaton expected = ThompsonsConstruction.buildSimple('a');

        // Act
        FiniteAutomaton actual = ThompsonsConstruction.
                produceIntermediateNFA(pattern);

        //Assert
        Assert.assertEquals(expected, actual);
    }

    @Test
    public void twoCharsProducesAndFA() {
        // Arrange
        RE pattern = new RE("ab");

        FiniteAutomaton a = ThompsonsConstruction.buildSimple('a');
        FiniteAutomaton b = ThompsonsConstruction.buildSimple('b');
        FiniteAutomaton expected = ThompsonsConstruction.buildAnd(a, b);

        // Act
        FiniteAutomaton actual = ThompsonsConstruction.produceIntermediateNFA(pattern);

        // Assert
        Assert.assertEquals(expected, actual);
    }

    @Test
    public void threeCharsProducesAndAndFA() {
        // Arrange
        RE pattern = new RE("abc");

        FiniteAutomaton a = ThompsonsConstruction.buildSimple('a');
        FiniteAutomaton b = ThompsonsConstruction.buildSimple('b');
        FiniteAutomaton temp = ThompsonsConstruction.buildAnd(a, b);
        FiniteAutomaton c = ThompsonsConstruction.buildSimple('c');
        FiniteAutomaton expected = ThompsonsConstruction.buildAnd(temp, c);

        // Act
        FiniteAutomaton actual = ThompsonsConstruction.produceIntermediateNFA(pattern);

        // Assert
        Assert.assertEquals(expected, actual);
    }

    @Test
    public void charOrCharProducesOrFA() {
        // Arrange
        RE pattern = new RE("a|b");

        FiniteAutomaton a = ThompsonsConstruction.buildSimple('a');
        FiniteAutomaton b = ThompsonsConstruction.buildSimple('b');
        FiniteAutomaton expected = ThompsonsConstruction.buildOr(a, b);

        // Act
        FiniteAutomaton actual = ThompsonsConstruction.produceIntermediateNFA(pattern);

        // Assert
        Assert.assertEquals(expected, actual);
    }

    @Test
    public void charOrCharOrCharProducesOrOrFa() {
        // Arrange
        RE pattern = new RE("a|b|c");

        FiniteAutomaton a = ThompsonsConstruction.buildSimple('a');
        FiniteAutomaton b = ThompsonsConstruction.buildSimple('b');
        FiniteAutomaton c = ThompsonsConstruction.buildSimple('c');
        FiniteAutomaton temp = ThompsonsConstruction.buildOr(a, b);
        FiniteAutomaton expected = ThompsonsConstruction.buildOr(temp, c);

        // Act
        FiniteAutomaton actual = ThompsonsConstruction.produceIntermediateNFA(pattern);

        // Assert
        Assert.assertEquals(expected, actual);
    }

    @Test
    public void charAndCharOrCharProducesAndOrFa() {
        // Arrange
        RE pattern = new RE("ab|c");

        FiniteAutomaton a = ThompsonsConstruction.buildSimple('a');
        FiniteAutomaton b = ThompsonsConstruction.buildSimple('b');
        FiniteAutomaton c = ThompsonsConstruction.buildSimple('c');
        FiniteAutomaton temp = ThompsonsConstruction.buildAnd(a, b);
        FiniteAutomaton expected = ThompsonsConstruction.buildOr(temp, c);

        // Act
        FiniteAutomaton actual = ThompsonsConstruction.produceIntermediateNFA(pattern);

        // Assert
        Assert.assertEquals(expected, actual);
    }

    @Test
    public void charOrCharAndCharProducesAndOrFa() {
        // Arrange
        RE pattern = new RE("a|bc");

        FiniteAutomaton a = ThompsonsConstruction.buildSimple('a');
        FiniteAutomaton b = ThompsonsConstruction.buildSimple('b');
        FiniteAutomaton c = ThompsonsConstruction.buildSimple('c');
        FiniteAutomaton temp = ThompsonsConstruction.buildAnd(b, c);
        FiniteAutomaton expected = ThompsonsConstruction.buildOr(a, temp);

        // Act
        FiniteAutomaton actual = ThompsonsConstruction.produceIntermediateNFA(pattern);

        // Assert
        Assert.assertEquals(expected, actual);
    }

    @Test
    public void charOrCharAndCharOrCharProducesAndORORFA() {
        // Arrange
        RE pattern = new RE("a|bc|d");

        FiniteAutomaton a = ThompsonsConstruction.buildSimple('a');
        FiniteAutomaton b = ThompsonsConstruction.buildSimple('b');
        FiniteAutomaton c = ThompsonsConstruction.buildSimple('c');
        FiniteAutomaton d = ThompsonsConstruction.buildSimple('d');
        FiniteAutomaton temp1 = ThompsonsConstruction.buildAnd(b, c);
        FiniteAutomaton temp2 = ThompsonsConstruction.buildOr(a, temp1);
        FiniteAutomaton expected = ThompsonsConstruction.buildOr(temp2, d);

        // Act
        FiniteAutomaton actual = ThompsonsConstruction.produceIntermediateNFA(pattern);

        // Assert
        Assert.assertEquals(expected, actual);
    }

    @Test
    public void perCharPerProducesCharFA() {
        // Arrange
        RE pattern = new RE("(a)");

        FiniteAutomaton expected = ThompsonsConstruction.buildSimple('a');

        // Act
        FiniteAutomaton actual = ThompsonsConstruction.
                produceIntermediateNFA(pattern);

        //Assert
        Assert.assertEquals(expected, actual);

    }

    @Test
    public void perCharOrCharPerAndCharOrCharProducesORAndOr() {
        // Arrange
        RE pattern = new RE("(a|b)c|d");

        FiniteAutomaton a = ThompsonsConstruction.buildSimple('a');
        FiniteAutomaton b = ThompsonsConstruction.buildSimple('b');
        FiniteAutomaton c = ThompsonsConstruction.buildSimple('c');
        FiniteAutomaton d = ThompsonsConstruction.buildSimple('d');
        FiniteAutomaton temp1 = ThompsonsConstruction.buildOr(a, b);
        FiniteAutomaton temp2 = ThompsonsConstruction.buildAnd(temp1, c);
        FiniteAutomaton expected = ThompsonsConstruction.buildOr(temp2, d);

        // Act
        FiniteAutomaton actual = ThompsonsConstruction.produceIntermediateNFA(pattern);

        // Assert
        Assert.assertEquals(expected, actual);
    }

    @Test
    public void perCharOrCharPerAndCharOrCharProducesOrOrAnd() {
        // Arrange
        RE pattern = new RE("(a|b)(c|d)");

        FiniteAutomaton a = ThompsonsConstruction.buildSimple('a');
        FiniteAutomaton b = ThompsonsConstruction.buildSimple('b');
        FiniteAutomaton c = ThompsonsConstruction.buildSimple('c');
        FiniteAutomaton d = ThompsonsConstruction.buildSimple('d');
        FiniteAutomaton temp1 = ThompsonsConstruction.buildOr(a, b);
        FiniteAutomaton temp2 = ThompsonsConstruction.buildOr(c, d);
        FiniteAutomaton expected = ThompsonsConstruction.buildAnd(temp1, temp2);

        // Act
        FiniteAutomaton actual = ThompsonsConstruction.produceIntermediateNFA(pattern);

        // Assert
        Assert.assertEquals(expected, actual);
    }

    @Test
    public void CharOrCharAndPerCharOrCharPerProducesOrAndOr() {
        // Arrange
        RE pattern = new RE("a|b(c|d)");

        FiniteAutomaton a = ThompsonsConstruction.buildSimple('a');
        FiniteAutomaton b = ThompsonsConstruction.buildSimple('b');
        FiniteAutomaton c = ThompsonsConstruction.buildSimple('c');
        FiniteAutomaton d = ThompsonsConstruction.buildSimple('d');
        FiniteAutomaton temp1 = ThompsonsConstruction.buildOr(c, d);
        FiniteAutomaton temp2 = ThompsonsConstruction.buildAnd(b, temp1);
        FiniteAutomaton expected = ThompsonsConstruction.buildOr(a, temp2);

        // Act
        FiniteAutomaton actual = ThompsonsConstruction.produceIntermediateNFA(pattern);

        // Assert
        Assert.assertEquals(expected, actual);
    }
}
