package comp1110.ass2;

import org.junit.Rule;
import org.junit.Test;
import org.junit.rules.Timeout;

import java.util.Random;

import static org.junit.Assert.*;

public class GenerateMoveTest {

    @Rule
    public Timeout globalTimeout = Timeout.millis(1000);

    @Test
    public void testEmptyBoard() {
        String move = Metro.generateMove("", "aaaa", 2);
        assertNotNull("Metro.generateMove(\"\", \"aaaa\", 2) returned null", move);
        assertFalse("Metro.generateMove(\"\", \"aaaa\", 2) returned empty string", move.isEmpty());
        boolean rowCond = move.charAt(4) == '0' || move.charAt(4) == '7';
        boolean colCond = move.charAt(5) == '0' || move.charAt(5) == '7';
        assertTrue("Piece aaaa is invalidly placed at " + move, rowCond || colCond);

        move = Metro.generateMove("", "cccc", 2);
        assertNotNull("Metro.generateMove(\"\", \"cccc\", 2) returned null", move);
        assertFalse("Metro.generateMove(\"\", \"cccc\", 2) returned empty string", move.isEmpty());
        rowCond = move.charAt(4) == '0' || move.charAt(4) == '7';
        colCond = move.charAt(5) == '0' || move.charAt(5) == '7';
        boolean cornerCond = !(rowCond && colCond);
        assertTrue("Piece cccc is invalidly placed at " + move, (rowCond || colCond) && cornerCond);

        move = Metro.generateMove("", "dada", 2);
        assertNotNull("Metro.generateMove(\"\", \"dada\", 2) returned null", move);
        assertFalse("Metro.generateMove(\"\", \"dada\", 2) returned empty string", move.isEmpty());
        colCond = move.charAt(5) == '0' || move.charAt(5) == '7';
        assertTrue("Piece dada is invalidly placed at " + move, colCond);

    }

    @Test
    public void testMiscellaneous() {
        for (int i = 0; i < Utilities.MISCELLANEOUS_MOVES.length; i++) {
            String moveSet = Utilities.MISCELLANEOUS_MOVES[i];
            String boardString = moveSet.substring(0, moveSet.length() - 4);
            String piece = moveSet.substring(moveSet.length() - 4);
            int numPlayers = i + 2;
            String move = Metro.generateMove(boardString, piece, numPlayers);

            assertTrue("Metro.generateMove(\"" + boardString + "\", \"" + piece + "\", " + numPlayers + ") returned invalid move \"" + move + "\"",
                    Utilities.POSSIBLE_MOVES.get(i).contains(move));
        }
    }

    @Test
    public void testNearCompleteBoards() {
        Random randPlayer = new Random();
        for (String completeBoard : Utilities.COMPLETE_BOARDSTRINGS) {
            String nearCompleteBoard = completeBoard.substring(0, completeBoard.length() - 6);
            String expected = completeBoard.substring(completeBoard.length() - 2);
            String piece = completeBoard.substring(completeBoard.length() - 6, completeBoard.length() - 2);
            int numPlayers = randPlayer.nextInt(5) + 2;
            String move = Metro.generateMove(nearCompleteBoard, piece, numPlayers);

            assertNotNull("Metro.generateMove(\"" + nearCompleteBoard + "\", \"" + piece + "\", " + numPlayers + ") returned null", move);
            assertFalse("Metro.generateMove(\"" + nearCompleteBoard + "\", \"" + piece + "\", " + numPlayers + ") returned empty string", move.isEmpty());
            assertEquals("Metro.generateMove(\"" + nearCompleteBoard + "\", \"" + piece + "\", " + numPlayers + ") expected placement (" + expected.charAt(0) + ", " + expected.charAt(1) +
                    "), got (" + move.charAt(4) + ", " + move.charAt(5) + ")", piece + expected, move);
        }
    }

}
