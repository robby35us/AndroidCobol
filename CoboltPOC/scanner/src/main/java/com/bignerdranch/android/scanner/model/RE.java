package com.bignerdranch.android.scanner.model;

import java.util.regex.Pattern;

public class RE implements Comparable<RE> {
    private Pattern pattern;
    private int position = 0;

    public RE(String regexString) {
        pattern = Pattern.compile(regexString);
    }

    public String toString() {
        return pattern.pattern();
    }

    public char getNextChar() {
        if(position < pattern.toString().length())
            return pattern.toString().charAt(position++);
        else
            return '\n';
    }

    public boolean hasNextChar() {
        return position < pattern.pattern().length();
    }

    @Override
    public int compareTo(RE re) {
        return pattern.toString().compareTo(re.toString());
    }
}
