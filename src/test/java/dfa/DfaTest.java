package dfa;

import com.spoljo.nfa.dfa.DFA;
import org.junit.Assert;
import org.junit.Test;

import java.util.ArrayList;
import java.util.List;

public class DfaTest {

    @Test
    public void DfaTest1(){
        int[] input = {1,0,0,0,1,0,1,1,1,0,1};
        int[][] transactionTable = {{0, 1},
                                    {2, 0},
                                    {1, 2}
        };
        List<Integer> acceptingStates = new ArrayList<>();
        acceptingStates.add(0);
        acceptingStates.add(2);

        DFA dfa = new DFA(input, transactionTable, 0, acceptingStates);

        Assert.assertTrue(dfa.validate());
    }

    @Test
    public void DfaTest2(){
    }
}
