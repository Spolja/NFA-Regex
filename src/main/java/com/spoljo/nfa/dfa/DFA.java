package com.spoljo.nfa.dfa;

import java.util.Arrays;
import java.util.List;

public class DFA {
    private int[] input;
    private int table[][];
    private int startingState;
    private List<Integer> acceptingStates;
    private int state = startingState;
    private String stateHistory;
    private int minimizationMatrix[][];
    private Minimizer minimizer = new Minimizer();

    public DFA(int[] input, int[][] table, int startingState, List<Integer> acceptingStates) {
        this.input = input;
        this.table = table;
        this.startingState = startingState;
        this.acceptingStates = acceptingStates;
        this.stateHistory = "S" + startingState;

        //TODO: Minimize DFA;
        this.minimizationMatrix = new int[table.length][table.length];
        for (int i = 0; i < minimizationMatrix.length; ++i) {
            for (int j = i; j < minimizationMatrix.length; ++j) {
                if (true)
                    minimizationMatrix[i][j] = 1;
                else
                    minimizationMatrix[i][j] = 0;
            }
        }
    }
    public void minimize(){
        minimizer.minimize(this);
    }

    public boolean validate() {
        for (int i = 0; i < input.length; ++i) {
            state = table[state][input[i]];
            stateHistory = stateHistory.concat(", S" + state);
        }

        System.out.println("State History: " + stateHistory);
        System.out.println("DFA ended in state: S" + state);
        System.out.println("Accepted states are: S:" + Arrays.toString(acceptingStates.toArray()));
        if (acceptingStates.contains(state))
            return true;
        else
            return false;
    }
}
