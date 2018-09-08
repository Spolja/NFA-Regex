package com.spoljo.nfa.model;

public class State {

    private int c;
    private State out;
    private State out1;
    private int lastlist;

    public State() {
        c = -1;
    }

    public State(int c) {
        this.c = c;
        out = new State();
        out1 = new State();
    }

    public State(int c, State out, State out1) {
        this.c = c;
        this.out = out;
        this.out1 = out1;
    }

    public void copyFrom(State s) {
        c = s.c;
        out = s.out;
        out1 = s.out1;
    }

    public int getC() {
        return c;
    }

    public void setC(int c) {
        this.c = c;
    }

    public State getOut() {
        return out;
    }

    public void setOut(State out) {
        this.out = out;
    }

    public State getOut1() {
        return out1;
    }

    public void setOut1(State out1) {
        this.out1 = out1;
    }

    public int getLastlist() {
        return lastlist;
    }

    public void setLastlist(int lastlist) {
        this.lastlist = lastlist;
    }
}
