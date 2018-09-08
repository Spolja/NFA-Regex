import com.spoljo.nfa.model.NFA;
import com.spoljo.nfa.model.State;
import org.junit.Assert;
import org.junit.Test;

public class NFATest {

    @Test
    public void NFATest1(){
        String postfix = NFA.regexToPostfix("(A*B|AC)D");
        String value = "AAAABD";
        State start = NFA.constructNFA(postfix);

        Assert.assertTrue(NFA.match(start, value));
    }

    @Test
    public void NFATest2(){
        String postfix = NFA.regexToPostfix("(A*B|AC)D");
        String value = "AAAAC";
        State start = NFA.constructNFA(postfix);

        Assert.assertFalse(NFA.match(start, value));
    }

    @Test
    public void NFATest3(){
        String postfix = NFA.regexToPostfix("(a|(bc)*d)*");
        String value = "abcbcd";
        State start = NFA.constructNFA(postfix);

        Assert.assertTrue(NFA.match(start, value));
    }

    @Test
    public void NFATest4(){
        String postfix = NFA.regexToPostfix("(a|(bc)*d)*");
        String value = "abcbcbcdaaaabcbcdaaaddd";

        State start = NFA.constructNFA(postfix);

        Assert.assertTrue(NFA.match(start, value));
    }
}
