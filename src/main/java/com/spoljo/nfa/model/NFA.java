package com.spoljo.nfa.model;

import java.util.ArrayList;
import java.util.List;

public class NFA {
    private static final int SPLIT = 257;
    private static final int MATCH = 256;
    private static final State matchstate = new State(MATCH);
    private static int listId;

    private static List<State> list1(State outp) {
        List<State> list = new ArrayList<>();
        list.add(outp);
        return list;
    }

    private static void patch(List<State> l, State s) {
        for (State ls : l) {
            ls.copyFrom(s);
        }
    }

    private static List<State> append(List<State> l1, List<State> l2) {
        List<State> list = new ArrayList<>();
        list.addAll(l1);
        list.addAll(l2);
        return list;
    }

    private static void push(List<Fragment> stack, Fragment f) {
        stack.add(0, f);
    }

    private static Fragment pop(List<Fragment> stack) {
        return stack.remove(0);
    }

    public static State constructNFA(String postfix) {
        if (postfix == null) {
            return null;
        }
        List<Fragment> stack = new ArrayList<>();
        for (char p : postfix.toCharArray()) {
            switch (p) {
                case ',':
                case '.': {
                    Fragment e2 = pop(stack);
                    Fragment e1 = pop(stack);
                    patch(e1.getOut(), e2.getStart());
                    push(stack, new Fragment(e1.getStart(), e2.getOut()));
                    break;
                }
                case '|': {
                    Fragment e2 = pop(stack);
                    Fragment e1 = pop(stack);
                    State s = new State(SPLIT, e1.getStart(), e2.getStart());
                    push(stack, new Fragment(s, append(e1.getOut(), e2.getOut())));
                    break;
                }
                case '?': {
                    Fragment e = pop(stack);
                    State s = new State(SPLIT, e.getStart(), new State());
                    push(stack, new Fragment(s, append(e.getOut(), list1(s.getOut1()))));
                    break;
                }
                case '*': {
                    Fragment e = pop(stack);
                    State s = new State(SPLIT, e.getStart(), new State());
                    patch(e.getOut(), s);
                    push(stack, new Fragment(s, list1(s.getOut1())));
                    break;
                }
                case '+': {
                    Fragment e = pop(stack);
                    State s = new State(SPLIT, e.getStart(), new State());
                    patch(e.getOut(), s);
                    push(stack, new Fragment(e.getStart(), list1(s.getOut1())));
                    break;
                }
                default: {
                    State s = new State(p, new State(), new State());
                    push(stack, new Fragment(s, list1(s.getOut())));
                    break;
                }
            }
        }
        Fragment e = pop(stack);

        patch(e.getOut(), matchstate);
        return e.getStart();
    }

    private static List<State> startlist(State start) {
        listId++;
        List<State> l = new ArrayList<>();
        addstate(l, start);
        return l;
    }

    private static boolean ismatch(List<State> l) {
        for (State s : l) {
            if (s.getC() == MATCH) {
                return true;
            }
        }
        return false;
    }

    private static void addstate(List<State> l, State s) {
        if (s == null || s.getC() == -1 || s.getLastlist() == listId) {
            return;
        }
        s.setLastlist(listId);
        if (s.getC() == SPLIT) {
            addstate(l, s.getOut());
            addstate(l, s.getOut1());
            return;
        }
        l.add(s);
    }

    /* step the nfa from the states in clist past the char c, to
     * create the next nfa state index nlist */
    private static void step(List<State> clist, int c, List<State> nlist) {
        listId++;
        for (State s : clist) {
            if (s.getC() == c) {
                addstate(nlist, s.getOut());
            }
        }
    }

    public static boolean match(State start, String s) {
        List<State> clist = startlist(start);
        List<State> nlist = new ArrayList<>();
        for (char c : s.toCharArray()) {
            step(clist, c, nlist);
            clist = nlist;
            nlist = new ArrayList<>();
        }
        return ismatch(clist);
    }

    public static String regexToPostfix(String regex) {
        int natom = 0, nalt = 0;
        StringBuilder buf = new StringBuilder();
        List<Paren> pl = new ArrayList<>();
        for (char c : regex.toCharArray()) {
            switch (c) {
                case '(': {
                    if (natom > 1) {
                        --natom;
                        buf.append('.');
                    }
                    pl.add(new Paren(nalt, natom));
                    nalt = 0;
                    natom = 0;
                    break;
                }
                case '|': {
                    if (natom == 0) {
                        return null; // what?
                    }
                    while (--natom > 0) {
                        buf.append('.');
                    }
                    ++nalt;
                    break;
                }
                case ')': {
                    assert !pl.isEmpty();
                    assert natom != 0;
                    while (--natom > 0) buf.append('.');
                    for (; nalt > 0; --nalt) buf.append('|');
                    Paren p = pl.remove(pl.size() - 1);
                    nalt = p.nalt;
                    natom = p.natom;
                    ++natom;
                    break;
                }
                case '*':
                case '+':
                case '?': {
                    if (natom == 0) return null;
                    buf.append(c);
                    break;
                }
                default: {
                    if (natom > 1) {
                        --natom;
                        buf.append('.');
                    }
                    buf.append(c);
                    ++natom;
                    break;
                }
            }
        }
        assert pl.isEmpty();
        while (--natom > 0) buf.append('.');
        for (; nalt > 0; --nalt) buf.append('|');
        return buf.toString();
    }
}
