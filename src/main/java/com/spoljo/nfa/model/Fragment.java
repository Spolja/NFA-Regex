package com.spoljo.nfa.model;

import java.util.List;

public class Fragment {
    private State start;
    private List<State> out;

    public Fragment(State start, List<State> out) {
        this.start = start;
        this.out = out;
    }

    public State getStart() {
        return start;
    }

    public void setStart(State start) {
        this.start = start;
    }

    public List<State> getOut() {
        return out;
    }

    public void setOut(List<State> out) {
        this.out = out;
    }
}
