package com.bignerdranch.android.scanner;

import com.bignerdranch.android.scanner.algorithms.DFAMinimization;
import com.bignerdranch.android.scanner.algorithms.SubsetConstruction;
import com.bignerdranch.android.scanner.algorithms.ThompsonsConstruction;
import com.bignerdranch.android.scanner.model.FA.FiniteAutomaton;
import com.bignerdranch.android.scanner.model.RE;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.Set;
import java.util.TreeSet;

public class ScannerGenerator {

    private Set<RE> patterns;

    public ScannerGenerator() {
        patterns = new TreeSet<>();
    }

    public void getKeywordsFromFile( InputStream sourceFile) {
        populatePatterns(sourceFile);

        // Apply Thompson's Construction
        FiniteAutomaton nfa = ThompsonsConstruction.apply(patterns);
        patterns = null;
        System.out.println(nfa);


        // Apply Subset Construction
        FiniteAutomaton dfa = SubsetConstruction.apply(nfa);
        nfa = null;
        System.out.println(dfa);

        // Apply DFAMinimization
        FiniteAutomaton mdfa = DFAMinimization.apply(dfa);
        dfa = null;
        System.out.println(mdfa);
    }

    private void populatePatterns(InputStream sourceFile) {
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
    }
}
