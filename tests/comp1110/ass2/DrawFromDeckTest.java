package comp1110.ass2;

import org.junit.Rule;
import org.junit.Test;
import org.junit.rules.Timeout;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;

public class DrawFromDeckTest {

    @Rule
    public Timeout globalTimeout = Timeout.millis(1000);

    @Test
    public void testWithMissingPieces() {
        for (int i = 0; i < 3; i++) {
            String boardString = Utilities.BOARDSTRINGS_FOR_DECK[i];
            String draw = Metro.drawFromDeck(boardString);
            boolean expected = Utilities.DECK_CHOICES.get(i).contains(draw);
            assertTrue("Drew invalid piece \"" + draw + "\" from board string " + boardString, expected);
        }
    }

    @Test
    public void testEmptyBoardString() {
        String draw = Metro.drawFromDeck("");
        boolean expected = Utilities.ALL_UNIQUE_PIECES.contains(draw);
        assertTrue("Drew invalid piece \"" + draw + "\" from empty board string", expected);
    }

    @Test
    public void testAlmostCompleteBoardStrings() {
        for (String completeString : Utilities.COMPLETE_BOARDSTRINGS) {
            String boardString = completeString.substring(0, completeString.length() - 6);
            String expected = completeString.substring(completeString.length() - 6, completeString.length() - 2);
            String draw = Metro.drawFromDeck(boardString);
            assertTrue("Metro.drawFromDeck(\"" + boardString + "\") returned \"" + draw + "\" but expected \"" + expected + "\"", expected.equals(draw));
        }
    }
}
