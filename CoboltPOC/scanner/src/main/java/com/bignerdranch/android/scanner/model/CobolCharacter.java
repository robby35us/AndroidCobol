package com.bignerdranch.android.scanner.model;

import java.util.EnumSet;
import java.util.Set;
import java.util.regex.Pattern;

public enum CobolCharacter {
    DIGIT("[0-9]", new char[]{'0','1','2','3','4','5','6','7','8','9'}),
    UPPER("[A-Z]", new char[]{'A','B', 'C','D','E','F','G','H','I','J','K','L','M','N','O','P','Q','R','S','T','U','V','W','X','Y','Z'}),
    LOWER("[a-z]", new char[]{'a','b','c','d','e','f','g','h','i','j','k','l','m','n','o','p','q','r','s','t','u','v','w','x','y','z'}),
    PLUS("\\+", new char[]{'+'}),
    MINUS("-", new char[]{'-'}),
    ASTERISK("\\*", new char[]{'*'}),
    SLANT("/", new char[]{'/'}),
    EQUAL("=", new char[]{'='}),
    CURRENCY("\\$",new char[]{'$'}),
    COMMA(",", new char[]{','}),
    SEMICOLON(";", new char[]{';'}),
    PERIOD("\\.", new char[]{'.'}),
    QUOTATION("\\\"", new char[]{'\"'}),
    LEFT("\\(", new char[]{'('}),
    RIGHT("\\)",new char[]{')'}),
    GREATER(">", new char[]{'>'}),
    LESS("<", new char[]{'<'}),
    COLON(":", new char[]{':'}),
    UNDERSCORE("_",new  char[]{'_'}),
    INVALID(null, null),
    STRING("([^\"]|\\\\\")", null);

    public static final int COBOL_CHARACTER_SET_SIZE = 79;

    private Pattern pattern;
    private char[] matchingChars;



    CobolCharacter(String re, char[] matchingChars) {
        if(re != null) {
            pattern = Pattern.compile(re);
            if(matchingChars != null)
                this.matchingChars = matchingChars;
        } else {
            pattern = null;
        }
    }


    public static CobolCharacter matchCobolCharacter(Character c) {
        Set set = EnumSet.allOf(CobolCharacter.class);
        for (Object o: set) {
            CobolCharacter cc = (CobolCharacter) o;
            if (cc == INVALID || cc == STRING)
                continue;
            if (cc.pattern.matcher(c.toString()).matches()) {
                return cc;
            }
        }
        return INVALID;
    }

    public static boolean isValidStringChar(String seq) {
        return STRING.pattern.matcher(seq).matches();
    }

    public char[] getMatchingChars() {
        return matchingChars;
    }

    public boolean isCharThisType(Character character) {
        return pattern.matcher(character.toString()).matches();
    }

    public static char[] getCobolCharacterList() {
        //TODO - avoid duplicate work if called more than once

        char[] list = new char[COBOL_CHARACTER_SET_SIZE];
        int listIndex = 0;
        for(CobolCharacter cc: CobolCharacter.values()) {
            if( cc.matchingChars == null)
                continue;
            for(int ccIndex = 0; ccIndex < cc.matchingChars.length; ccIndex++, listIndex++) {
                list[listIndex] = cc.matchingChars[ccIndex];
            }
        }
        return list;
    }
}
