package com.bignerdranch.android.scanner.algorithms;

import com.bignerdranch.android.scanner.model.FA.FiniteAutomaton;
import com.bignerdranch.android.scanner.model.FA.State;
import com.bignerdranch.android.scanner.model.RE;

import org.junit.Assert;
import org.junit.Test;


public class ProduceIntermediateNFATests {

    private RegexParser parser = new RegexParser();
    
    @Test
    public void emptyStringProducesEmptyFA() {
        RE pattern = new RE("");
        FiniteAutomaton actual = parser.parseExpression(pattern);
        Assert.assertEquals(new FiniteAutomaton(new State(true)), actual);
    }

    @Test
    public void singleCharProducesSimpleFA() {
        // Arrange
        RE pattern = new RE("a");

        FiniteAutomaton expected = AutomataBuilder.buildSimple('a');

        // Act
        FiniteAutomaton actual = parser.parseExpression(pattern);

        //Assert
        Assert.assertEquals(expected, actual);
    }

    @Test
    public void twoCharsProducesAndFA() {
        // Arrange
        RE pattern = new RE("ab");

        FiniteAutomaton a = AutomataBuilder.buildSimple('a');
        FiniteAutomaton b = AutomataBuilder.buildSimple('b');
        FiniteAutomaton expected = AutomataBuilder.buildAnd(a, b);

        // Act
        FiniteAutomaton actual = parser.parseExpression(pattern);

        // Assert
        Assert.assertEquals(expected, actual);
    }

    @Test
    public void threeCharsProducesAndAndFA() {
        // Arrange
        RE pattern = new RE("abc");

        FiniteAutomaton a = AutomataBuilder.buildSimple('a');
        FiniteAutomaton b = AutomataBuilder.buildSimple('b');
        FiniteAutomaton temp = AutomataBuilder.buildAnd(a, b);
        FiniteAutomaton c = AutomataBuilder.buildSimple('c');
        FiniteAutomaton expected = AutomataBuilder.buildAnd(temp, c);

        // Act
        FiniteAutomaton actual = parser.parseExpression(pattern);

        // Assert
        Assert.assertEquals(expected, actual);
    }

    @Test
    public void charOrCharProducesOrFA() {
        // Arrange
        RE pattern = new RE("a|b");

        FiniteAutomaton a = AutomataBuilder.buildSimple('a');
        FiniteAutomaton b = AutomataBuilder.buildSimple('b');
        FiniteAutomaton expected = AutomataBuilder.buildOr(a, b);

        // Act
        FiniteAutomaton actual = parser.parseExpression(pattern);

        // Assert
        Assert.assertEquals(expected, actual);
    }

    @Test
    public void charOrCharOrCharProducesOrOrFa() {
        // Arrange
        RE pattern = new RE("a|b|c");

        FiniteAutomaton a = AutomataBuilder.buildSimple('a');
        FiniteAutomaton b = AutomataBuilder.buildSimple('b');
        FiniteAutomaton c = AutomataBuilder.buildSimple('c');
        FiniteAutomaton temp = AutomataBuilder.buildOr(a, b);
        FiniteAutomaton expected = AutomataBuilder.buildOr(temp, c);

        // Act
        FiniteAutomaton actual = parser.parseExpression(pattern);

        // Assert
        Assert.assertEquals(expected, actual);
    }

    @Test
    public void charAndCharOrCharProducesAndOrFa() {
        // Arrange
        RE pattern = new RE("ab|c");

        FiniteAutomaton a = AutomataBuilder.buildSimple('a');
        FiniteAutomaton b = AutomataBuilder.buildSimple('b');
        FiniteAutomaton c = AutomataBuilder.buildSimple('c');
        FiniteAutomaton temp = AutomataBuilder.buildAnd(a, b);
        FiniteAutomaton expected = AutomataBuilder.buildOr(temp, c);

        // Act
        FiniteAutomaton actual = parser.parseExpression(pattern);

        // Assert
        Assert.assertEquals(expected, actual);
    }

    @Test
    public void charOrCharAndCharProducesAndOrFa() {
        // Arrange
        RE pattern = new RE("a|bc");

        FiniteAutomaton a = AutomataBuilder.buildSimple('a');
        FiniteAutomaton b = AutomataBuilder.buildSimple('b');
        FiniteAutomaton c = AutomataBuilder.buildSimple('c');
        FiniteAutomaton temp = AutomataBuilder.buildAnd(b, c);
        FiniteAutomaton expected = AutomataBuilder.buildOr(a, temp);

        // Act
        FiniteAutomaton actual = parser.parseExpression(pattern);

        // Assert
        Assert.assertEquals(expected, actual);
    }

    @Test
    public void charOrCharAndCharOrCharProducesAndORORFA() {
        // Arrange
        RE pattern = new RE("a|bc|d");

        FiniteAutomaton a = AutomataBuilder.buildSimple('a');
        FiniteAutomaton b = AutomataBuilder.buildSimple('b');
        FiniteAutomaton c = AutomataBuilder.buildSimple('c');
        FiniteAutomaton d = AutomataBuilder.buildSimple('d');
        FiniteAutomaton temp1 = AutomataBuilder.buildAnd(b, c);
        FiniteAutomaton temp2 = AutomataBuilder.buildOr(a, temp1);
        FiniteAutomaton expected = AutomataBuilder.buildOr(temp2, d);

        // Act
        FiniteAutomaton actual = parser.parseExpression(pattern);

        // Assert
        Assert.assertEquals(expected, actual);
    }

    @Test
    public void perCharPerProducesCharFA() {
        // Arrange
        RE pattern = new RE("(a)");

        FiniteAutomaton expected = AutomataBuilder.buildSimple('a');

        // Act
        FiniteAutomaton actual = parser.parseExpression(pattern);

        //Assert
        Assert.assertEquals(expected, actual);

    }

    @Test
    public void perCharOrCharPerAndCharOrCharProducesORAndOr() {
        // Arrange
        RE pattern = new RE("(a|b)c|d");

        FiniteAutomaton a = AutomataBuilder.buildSimple('a');
        FiniteAutomaton b = AutomataBuilder.buildSimple('b');
        FiniteAutomaton c = AutomataBuilder.buildSimple('c');
        FiniteAutomaton d = AutomataBuilder.buildSimple('d');
        FiniteAutomaton temp1 = AutomataBuilder.buildOr(a, b);
        FiniteAutomaton temp2 = AutomataBuilder.buildAnd(temp1, c);
        FiniteAutomaton expected = AutomataBuilder.buildOr(temp2, d);

        // Act
        FiniteAutomaton actual = parser.parseExpression(pattern);

        // Assert
        Assert.assertEquals(expected, actual);
    }

    @Test
    public void perCharOrCharPerAndCharOrCharProducesOrOrAnd() {
        // Arrange
        RE pattern = new RE("(a|b)(c|d)");
        FiniteAutomaton a = AutomataBuilder.buildSimple('a');
        FiniteAutomaton b = AutomataBuilder.buildSimple('b');
        FiniteAutomaton c = AutomataBuilder.buildSimple('c');
        FiniteAutomaton d = AutomataBuilder.buildSimple('d');
        FiniteAutomaton temp1 = AutomataBuilder.buildOr(a, b);
        FiniteAutomaton temp2 = AutomataBuilder.buildOr(c, d);
        FiniteAutomaton expected = AutomataBuilder.buildAnd(temp1, temp2);

        // Act
        FiniteAutomaton actual = parser.parseExpression(pattern);

        // Assert
        Assert.assertEquals(expected, actual);
    }

    @Test
    public void CharOrCharAndPerCharOrCharPerProducesOrAndOr() {
        // Arrange
        RE pattern = new RE("a|b(c|d)");

        FiniteAutomaton a = AutomataBuilder.buildSimple('a');
        FiniteAutomaton b = AutomataBuilder.buildSimple('b');
        FiniteAutomaton c = AutomataBuilder.buildSimple('c');
        FiniteAutomaton d = AutomataBuilder.buildSimple('d');
        FiniteAutomaton temp1 = AutomataBuilder.buildOr(c, d);
        FiniteAutomaton temp2 = AutomataBuilder.buildAnd(b, temp1);
        FiniteAutomaton expected = AutomataBuilder.buildOr(a, temp2);

        // Act
        FiniteAutomaton actual = parser.parseExpression(pattern);

        // Assert
        Assert.assertEquals(expected, actual);
    }

    @Test
    public void charStarProducesStarFa() {
        // Arrange
        RE pattern = new RE("a*");

        FiniteAutomaton a = AutomataBuilder.buildSimple('a');
        FiniteAutomaton expected = AutomataBuilder.buildClosure(a);

        // Act
        FiniteAutomaton actual = parser.parseExpression(pattern);


        // Assert
        Assert.assertEquals(expected, actual);
    }

    @Test
    public void orInClosure() {
        // Arrange
        RE pattern = new RE("(a|b)*");

        FiniteAutomaton a = AutomataBuilder.buildSimple('a');
        FiniteAutomaton b = AutomataBuilder.buildSimple('b');
        FiniteAutomaton temp = AutomataBuilder.buildOr(a, b);
        FiniteAutomaton expected = AutomataBuilder.buildClosure(temp);

        // Act
        FiniteAutomaton actual = parser.parseExpression(pattern);

        // Assert
        Assert.assertEquals(expected, actual);
    }

    @Test
    public void integrationTest() {
        // Arrange
        RE pattern = new RE("a(bc|d*(e|f))(g|h)ij");

        FiniteAutomaton a = AutomataBuilder.buildSimple('a');
        FiniteAutomaton b = AutomataBuilder.buildSimple('b');
        FiniteAutomaton c = AutomataBuilder.buildSimple('c');
        FiniteAutomaton d = AutomataBuilder.buildSimple('d');
        FiniteAutomaton e = AutomataBuilder.buildSimple('e');
        FiniteAutomaton f = AutomataBuilder.buildSimple('f');
        FiniteAutomaton g = AutomataBuilder.buildSimple('g');
        FiniteAutomaton h = AutomataBuilder.buildSimple('h');
        FiniteAutomaton i = AutomataBuilder.buildSimple('i');
        FiniteAutomaton j = AutomataBuilder.buildSimple('j');
        FiniteAutomaton temp1 = AutomataBuilder.buildAnd(b, c);
        FiniteAutomaton temp2 = AutomataBuilder.buildClosure(d);
        FiniteAutomaton temp3 = AutomataBuilder.buildOr(e,f);
        FiniteAutomaton temp4 = AutomataBuilder.buildAnd(temp2, temp3);
        FiniteAutomaton temp5 = AutomataBuilder.buildOr(temp1, temp4);
        FiniteAutomaton temp6 = AutomataBuilder.buildAnd(a, temp5);
        FiniteAutomaton temp7 = AutomataBuilder.buildOr(g, h);
        FiniteAutomaton temp8 = AutomataBuilder.buildAnd(temp6, temp7);
        FiniteAutomaton temp9 = AutomataBuilder.buildAnd(temp8, i);
        FiniteAutomaton expected = AutomataBuilder.buildAnd(temp9, j);

        // Act
        FiniteAutomaton actual = parser.parseExpression(pattern);

        // Assert
        Assert.assertEquals(expected, actual);
    }
}
