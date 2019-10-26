package com.bignerdranch.android.scanner.model;

import org.junit.Test;

public class CCTest {
    @Test
    public void char0IsDigit() {
        CobolCharacter cc = CobolCharacter.matchCobolCharacter('0');
        assert (cc == CobolCharacter.DIGIT);
    }

    @Test
    public void char7IsDigit() {
        CobolCharacter cc = CobolCharacter.matchCobolCharacter('7');
        assert (cc == CobolCharacter.DIGIT);
    }

    @Test
    public void char9IsDigit() {
        CobolCharacter cc = CobolCharacter.matchCobolCharacter('9');
        assert (cc == CobolCharacter.DIGIT);
    }

    @Test
    public void charAIsUpper() {
        CobolCharacter cc = CobolCharacter.matchCobolCharacter('A');
        assert (cc == CobolCharacter.UPPER);
    }

    @Test
    public void charMIsUpper() {
        CobolCharacter cc = CobolCharacter.matchCobolCharacter('M');
        assert (cc == CobolCharacter.UPPER);
    }

    @Test
    public void charZIsUpper() {
        CobolCharacter cc = CobolCharacter.matchCobolCharacter('Z');
        assert (cc == CobolCharacter.UPPER);
    }

    @Test
    public void charaIsLower() {
        CobolCharacter cc = CobolCharacter.matchCobolCharacter('a');
        assert (cc == CobolCharacter.LOWER);
    }

    @Test
    public void chartIsLower() {
        CobolCharacter cc = CobolCharacter.matchCobolCharacter('t');
        assert (cc == CobolCharacter.LOWER);
    }

    @Test
    public void charzIsLower() {
        CobolCharacter cc = CobolCharacter.matchCobolCharacter('z');
        assert (cc == CobolCharacter.LOWER);
    }

    @Test
    public void charSpaceIsSpace() {
        CobolCharacter cc = CobolCharacter.matchCobolCharacter(' ');
        assert (cc == CobolCharacter.SPACE);
    }

    @Test
    public void charPlusIsPlus() {
        CobolCharacter cc = CobolCharacter.matchCobolCharacter('+');
        assert (cc == CobolCharacter.PLUS);
    }

    @Test
    public void charMinusIsMinus() {
        CobolCharacter cc = CobolCharacter.matchCobolCharacter('-');
        assert (cc == CobolCharacter.MINUS);
    }

    @Test
    public void charAsteriskIsAsterisk() {
        CobolCharacter cc = CobolCharacter.matchCobolCharacter('*');
        assert (cc == CobolCharacter.ASTERISK);
    }

    @Test
    public void charSlantIsSlant() {
        CobolCharacter cc = CobolCharacter.matchCobolCharacter('/');
        assert (cc == CobolCharacter.SLANT);
    }

    @Test
    public void charEqualIsEqual() {
        CobolCharacter cc = CobolCharacter.matchCobolCharacter('=');
        assert (cc == CobolCharacter.EQUAL);
    }

    @Test
    public void charCurrencyIsCurrency() {
        CobolCharacter cc = CobolCharacter.matchCobolCharacter('$');
        assert (cc == CobolCharacter.CURRENCY);
    }

    @Test
    public void charCommaIsComma() {
        CobolCharacter cc = CobolCharacter.matchCobolCharacter(',');
        assert (cc == CobolCharacter.COMMA);
    }

    @Test
    public void charSemicolonIsSemicolon() {
        CobolCharacter cc = CobolCharacter.matchCobolCharacter(';');
        assert (cc == CobolCharacter.SEMICOLON);
    }

    @Test
    public void charPeriodIsPeriod() {
        CobolCharacter cc = CobolCharacter.matchCobolCharacter('.');
        assert (cc == CobolCharacter.PERIOD);
    }

    @Test
    public void charQuoteIsQuote() {
        CobolCharacter cc = CobolCharacter.matchCobolCharacter('\"');
        assert (cc == CobolCharacter.QUOTATION);
    }

    @Test
    public void charLeftIsLeft() {
        CobolCharacter cc = CobolCharacter.matchCobolCharacter('(');
        assert (cc == CobolCharacter.LEFT);
    }

    @Test
    public void charRightIsRight() {
        CobolCharacter cc = CobolCharacter.matchCobolCharacter(')');
        assert (cc == CobolCharacter.RIGHT);
    }

    @Test
    public void charGreaterIsGreater() {
        CobolCharacter cc = CobolCharacter.matchCobolCharacter('>');
        assert (cc == CobolCharacter.GREATER);
    }

    @Test
    public void charLessIsLess() {
        CobolCharacter cc = CobolCharacter.matchCobolCharacter('<');
        assert (cc == CobolCharacter.LESS);
    }

    @Test
    public void charColonIsColon() {
        CobolCharacter cc = CobolCharacter.matchCobolCharacter(':');
        assert (cc == CobolCharacter.COLON);
    }

    @Test
    public void charUnderIsUnder() {
        CobolCharacter cc = CobolCharacter.matchCobolCharacter('_');
        assert (cc == CobolCharacter.UNDERSCORE);
    }

    @Test
    public void charQuestionIsInvalid() {
        CobolCharacter cc = CobolCharacter.matchCobolCharacter('?');
        assert  (cc == CobolCharacter.INVALID);
    }

    @Test
    public void charQuoteIsNotValidStringChar() {
        assert(!CobolCharacter.isValidStringChar("\""));
    }

    @Test
    public void charBackslashQuoteIsValidStringChar() {
        assert (CobolCharacter.isValidStringChar("\\\""));
    }

    @Test
    public void questionMarkIsvalidStringChar() {
        assert  (CobolCharacter.isValidStringChar("?"));
    }

    @Test
    public void twoCharsNotValidStringChar() {
        assert (!CobolCharacter.isValidStringChar("ab"));
    }
}
