package comp1110.ass2;

import org.junit.Rule;
import org.junit.Test;
import org.junit.rules.Timeout;

import java.util.Arrays;

import static org.junit.Assert.assertTrue;

public class checkInnerBoardTest {

    @Rule
    public Timeout globalTimeout = Timeout.millis(1000);

    private void test(int[][] tilePresent, Boolean expected) {
        boolean out = Metro.checkInnerBoard(tilePresent);
        String board = "";
        for(int i=0;i<tilePresent.length;i++){
            board+="[";
            for(int j=0;j<tilePresent[i].length;j++){
                board+=tilePresent[i][j];
                board+=", ";
            }
            board = board.substring(0,board.length()-1);
            board+="] ,";
        }
        board = board.substring(0,board.length()-2);

        assertTrue("expected " + expected + " for piece placement: " +board  +
                ", but got " + out, out == expected);
    }

    @Test
    public void testEmptyBoard(){
        int[][] emptyBoard = Utilities.EMPTY_BOARD;
        test(emptyBoard,false);
    }

    @Test
    public void testFullBoard(){
        int[][] fullInnerBoard = Utilities.FULL_INNER_BOARD;
        test(fullInnerBoard,true);
    }

    @Test
    public void testStation(){
        int[][] fullBoardWithEmptyStation = Utilities.FULL_BOARD_WITH_EMPTY_STATION;
        test(fullBoardWithEmptyStation,true);
    }

    @Test
    public void testAlmostFullInnerBoard(){
        int[][] almostFullInnerBoard = Utilities.ALMOST_FULL_INNER_BOARD;
        test(almostFullInnerBoard,false);
    }
}
