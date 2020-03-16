package comp1110.ass2;

import org.junit.Rule;
import org.junit.Test;
import org.junit.rules.Timeout;

import static org.junit.Assert.assertTrue;

public class IsPlacementSequenceValidTest {
    @Rule
    public Timeout globalTimeout = Timeout.millis(1000);

    @Test
    public void testEmptyBoard() {
        boolean emptyBoard = Metro.isPlacementSequenceValid("");
        assertTrue("Expected the empty board to be valid, but got " + emptyBoard, emptyBoard);
    }

    @Test
    public void testValid() {
        for (String validString : Utilities.VALID_SMALL_BOARDSTRINGS) {
            test(validString, true);
        }
        for (String validString : Utilities.VALID_MEDIUM_BOARDSTRINGS) {
            test(validString, true);
        }
    }

    @Test
    public void testInvalid() {
        testTrivial();
        for (String invalidString : Utilities.INVALID_SMALL_BOARDSTRINGS) {
            test(invalidString, false);
        }
        for (String invalidString : Utilities.INVALID_MEDIUM_BOARDSTRINGS) {
            test(invalidString, false);
        }
    }

    @Test
    public void testCompleteStrings() {
        for (String completeString : Utilities.COMPLETE_BOARDSTRINGS) {
            test(completeString, true);
        }
    }

    private void test(String boardString, boolean expected) {
        boolean result = Metro.isPlacementSequenceValid(boardString);
        assertTrue("Metro.isPlacementSequenceValid(\"" + boardString + "\") returned " + result + " but expected " + expected, expected == result);
    }

    private void testTrivial() {
        test("aacb01", true);
    }
}
