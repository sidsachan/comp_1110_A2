package comp1110.ass2;

import org.junit.Rule;
import org.junit.Test;
import org.junit.rules.Timeout;

import java.util.ArrayList;

import static org.junit.Assert.assertTrue;

public class DrawFromDeckTest {

    @Rule
    public Timeout globalTimeout = Timeout.millis(1000);

    @Test
    public void testWithMissingPieces() {
        for (int i = 0; i < 3; i++) {
            String boardString = Utilities.BOARDSTRINGS_FOR_DECK[i];
            String draw = Metro.drawFromDeck(boardString, "");
            boolean expected = Utilities.DECK_CHOICES.get(i).contains(draw);
            assertTrue("Drew invalid piece \"" + draw + "\" from board string " + boardString, expected);
        }
    }

    @Test
    public void testEmptyBoardString() {
        String draw = Metro.drawFromDeck("", "");
        boolean expected = Utilities.ALL_UNIQUE_PIECES.contains(draw);
        assertTrue("Drew invalid piece \"" + draw + "\" from empty board string", expected);
    }

    @Test
    public void testAlmostCompleteBoardStrings() {
        for (String completeString : Utilities.COMPLETE_BOARDSTRINGS) {
            String boardString = completeString.substring(0, completeString.length() - 6);
            String expected = completeString.substring(completeString.length() - 6, completeString.length() - 2);
            String draw = Metro.drawFromDeck(boardString, "");
            assertTrue("Metro.drawFromDeck(\"" + boardString + "\") returned \"" + draw + "\" but expected \"" + expected + "\"", expected.equals(draw));
        }
    }

    @Test
    public void testWithPiecesInPlayersHands() {
        for (int i = 0; i < Utilities.VALID_SMALL_BOARDSTRINGS.length; i++) {
            String boardString = Utilities.VALID_SMALL_BOARDSTRINGS[i];
            ArrayList<String> expected = Utilities.VALID_SMALL_BOARDSTRING_DRAWS.get(i);
            String hand = Utilities.CORRESPONDING_HANDS[i];
            String draw = Metro.drawFromDeck(boardString, hand);
            assertTrue("Metro.drawFromDeck(\"" + boardString + "\") with hand (\"" + hand + "\") returned \"" + draw + "\" but expected \"" + expected + "\"", expected.contains(draw));
        }
        for (int i = 0; i < Utilities.COMPLETE_BOARDSTRINGS.length; i++) {
            String complete = Utilities.COMPLETE_BOARDSTRINGS[i];
            int length = complete.length();
            String almostComplete = complete.substring(0, length - 24);
            String hand = complete.substring(length - 24, length - 20) + complete.substring(length - 18, length - 14) + complete.substring(length - 12, length - 8);
            String draw = Metro.drawFromDeck(almostComplete, hand);
            String expected = complete.substring(length - 6, length - 2);
            assertTrue("Metro.drawFromDeck(\"" + almostComplete + "\") with hand (\"" + hand + "\") returned \"" + draw + "\" but expected \"" + expected + "\"", expected.equals(draw));
        }
    }
}
