package com.spoljo.nfa.dfa;

public class Minimizer {
    public DFA minimize(DFA dfa){
        this.removeUnreachableStates(dfa);
        this.removeNonDistinguishableStates(dfa);


        return dfa;
    }

    private void removeUnreachableStates(DFA dfa) {

    }

    private void removeNonDistinguishableStates(DFA dfa) {

    }
}
