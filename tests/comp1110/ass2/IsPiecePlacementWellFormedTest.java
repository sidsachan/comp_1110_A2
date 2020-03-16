package comp1110.ass2;

import org.junit.Rule;
import org.junit.Test;
import org.junit.rules.Timeout;


import static org.junit.Assert.assertTrue;

public class IsPiecePlacementWellFormedTest {

    @Rule
    public Timeout globalTimeout = Timeout.millis(1000);

    private void test(String piecePlacement, Boolean expected) {
        boolean out = Metro.isPiecePlacementWellFormed(piecePlacement);
        assertTrue("expected " + expected + " for piece placement: " + piecePlacement +
                ", but got " + out, out == expected);
    }


    @Test
    public void testWellFormed() {
        testTrivial();
        for (int i = 0; i < Utilities.COMPLETE_BOARDSTRINGS.length; i++) {
            String boardString = Utilities.COMPLETE_BOARDSTRINGS[i];
            for (int j = 0; j < Utilities.COMPLETE_BOARDSTRINGS[i].length() / 6; j++) {
                String piece = boardString.substring(j * 6, j * 6 + 6);
                test(piece, true);
            }
        }
    }

    @Test
    public void testBadTile() {
        testTrivial();
        String boardString = Utilities.COMPLETE_BOARDSTRINGS[0];
        String replace = boardString.replace('b', 'p');
        replace = replace.replace('a', 'o');
        for (int i = 0; i < 8; i++) {
            test(replace.substring(i * 6, i * 6 + 6), false);
        }

    }


    @Test
    public void testBadLocation() {
        testTrivial();
        String boardString = Utilities.COMPLETE_BOARDSTRINGS[0];
        String replace = boardString.replace('0', '9');
        replace = replace.replace('7', '8');
        replace = replace.replace('6', '9');
        for (int i = 0; i < 13; i++) {
            test(replace.substring(i * 6, i * 6 + 6), false);

        }

    }

    @Test
    public void testBadLength() {
        testTrivial();
        String boardString = Utilities.COMPLETE_BOARDSTRINGS[0];
        for (int i = 0; i < boardString.length() / 6; i++) {
            String piece = boardString.substring(i * 6, i * 6 + 6) + 'a';
            test(piece, false);
        }
    }

    private void testTrivial() {
        test("aaaa00", true);
        test("zzzz99", false);
    }
}