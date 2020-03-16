package comp1110.ass2;

import org.junit.Rule;
import org.junit.Test;
import org.junit.rules.Timeout;

import static org.junit.Assert.assertTrue;

public class IsPlacementSequenceWellFormedTest {


    @Rule
    public Timeout globalTimeout = Timeout.millis(1000);

    private void test(String placement, Boolean expected) {
        boolean out = Metro.isPlacementSequenceWellFormed(placement);
        assertTrue("expected " + expected + " for piece placement string: " + placement +
                ", but got " + out, out == expected);
    }


    @Test
    public void testWellFormed() {
        testTrivial();
        for (int i = 0; i < Utilities.COMPLETE_BOARDSTRINGS.length; i++) {
            String boardString = Utilities.COMPLETE_BOARDSTRINGS[i];
            for (int j = 0; j < Utilities.COMPLETE_BOARDSTRINGS[i].length() / 6; j++) {
                String placement = boardString.substring(j * 6 + 6);
                test(placement, true);
            }
        }
    }

    @Test
    public void testExtraTiles() {
        testTrivial();
        for (int i = 0; i < Utilities.COMPLETE_BOARDSTRINGS.length; i++) {
            String boardString = Utilities.COMPLETE_BOARDSTRINGS[i];
            String badTiles = boardString.replace("cccc", "adad");
            badTiles = badTiles.replace("dbba", "aaaa");
            test(badTiles, false);
        }

    }


    @Test
    public void testBadLength() {
        testTrivial();
        String boardString = Utilities.COMPLETE_BOARDSTRINGS[0];
        for (int i = 0; i < boardString.length() / 6; i++) {
            String piece ='a'+ boardString.substring(i * 6 + 6);
            test(piece, false);
        }
    }

    private void testTrivial() {
        test("aaaa00bbbb01", true);
        test("zzzz99xxxx99", false);
    }
}


