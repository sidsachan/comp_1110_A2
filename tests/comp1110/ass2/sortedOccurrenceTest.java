package comp1110.ass2;

import org.junit.Rule;
import org.junit.Test;
import org.junit.rules.Timeout;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.Random;

import static org.junit.Assert.assertTrue;

public class sortedOccurrenceTest {

    @Rule
    public Timeout globaltimeout = Timeout.millis(1000);

    @Test
    public void testEmptyBoardString(){
        Tile[] tilesExpected = Tile.getStartingTiles();
        //empty tiles array for comparision with return array
        for (Tile tile : tilesExpected) {
            tile.setNumber(0);
        }
        String boardString = "";

        Tile[] functionOutput = Metro.sortedOccurrence(boardString);
        //comparison of tile type and count
        for (int i = 0; i<tilesExpected.length; i++) {
            assertTrue("Expected: " + tilesExpected[i].getType() +"\nBut got: "+ functionOutput[i].getType(), tilesExpected[i].getType().equals(functionOutput[i].getType()));
            assertTrue("Expected: " + tilesExpected[i].getNumber() +"\nBut got: "+ functionOutput[i].getNumber(), tilesExpected[i].getType().equals(functionOutput[i].getType()));
        }
    }

    @Test
    public void testOnePieceBoardString(){
        Tile[] tilesExpected = Tile.getStartingTiles();
        //empty tiles array for comparision with return array
        for (Tile tile : tilesExpected) {
            tile.setNumber(0);
        }
        //making one of the tiles 1
        Random random = new Random();
        int index = random.nextInt(tilesExpected.length);
        String boardString = tilesExpected[index].getType() + "00";
        //set index 0 at 1
        tilesExpected[index].setNumber(1);
        Tile[] functionOutput = Metro.sortedOccurrence(boardString);
        //Comparison of tile type and count
        for (int i = 0; i<tilesExpected.length; i++) {
            assertTrue("Expected: " + tilesExpected[i].getType() +"\nBut got: "+ functionOutput[i].getType(), tilesExpected[i].getType().equals(functionOutput[i].getType()));
            assertTrue("Expected: " + tilesExpected[i].getNumber() +"\nBut got: "+ functionOutput[i].getNumber(), tilesExpected[i].getType().equals(functionOutput[i].getType()));
        }
    }

    @Test
    public void alreadySortedFullBoardString(){
        Tile[] tilesExpected = Tile.getStartingTiles();
        //forming full board string
        StringBuilder boardString = new StringBuilder();
        for (Tile tile : tilesExpected) {
            for (int i = 0; i < tile.getNumber();++i) {
                boardString.append(tile.getType()).append("00");
            }
        }
        Tile[] functionOutput = Metro.sortedOccurrence(String.valueOf(boardString));
        //Comparison of tile type and count
        for (int i = 0; i<tilesExpected.length; i++) {
            assertTrue("Expected: " + tilesExpected[i].getType() +"\nBut got: "+ functionOutput[i].getType(), tilesExpected[i].getType().equals(functionOutput[i].getType()));
            assertTrue("Expected: " + tilesExpected[i].getNumber() +"\nBut got: "+ functionOutput[i].getNumber(), tilesExpected[i].getType().equals(functionOutput[i].getType()));
        }
    }

    @Test
    public void backwardsFullBoardString(){
        Tile[] tilesExpected = Tile.getStartingTiles();
        //forming full board string
        StringBuilder boardString = new StringBuilder();
        for (int j = tilesExpected.length-1; j>=0;--j) {
            for (int i = 0; i < tilesExpected[j].getNumber();++i) {
                boardString.append(tilesExpected[j].getType()).append("00");
            }
        }
        Tile[] functionOutput = Metro.sortedOccurrence(String.valueOf(boardString));
        //Comparison of tile type and count
        for (int i = 0; i<tilesExpected.length; i++) {
            assertTrue("Expected: " + tilesExpected[i].getType() +"\nBut got: "+ functionOutput[i].getType(), tilesExpected[i].getType().equals(functionOutput[i].getType()));
            assertTrue("Expected: " + tilesExpected[i].getNumber() +"\nBut got: "+ functionOutput[i].getNumber(), tilesExpected[i].getType().equals(functionOutput[i].getType()));
        }
    }

    @Test
    public void randomFullBoardString(){
        Tile[] tilesExpected = Tile.getStartingTiles();
        //forming random full board string
        StringBuilder boardString = new StringBuilder();
        ArrayList<Tile> tileArrayList = new ArrayList<>();
        for (Tile tile : tilesExpected) {
            for (int i = 0; i < tile.getNumber();++i) {
                Tile t = new Tile(tile.getType(),1);
                tileArrayList.add(t);
            }
        }
        Collections.shuffle(tileArrayList);
        for (Tile tile:tileArrayList){
            boardString.append(tile.getType()).append("00");
        }
        Tile[] functionOutput = Metro.sortedOccurrence(String.valueOf(boardString));
        //Comparison of tile type and count
        for (int i = 0; i<tilesExpected.length; i++) {
            assertTrue("Expected: " + tilesExpected[i].getType() +"\nBut got: "+ functionOutput[i].getType(), tilesExpected[i].getType().equals(functionOutput[i].getType()));
            assertTrue("Expected: " + tilesExpected[i].getNumber() +"\nBut got: "+ functionOutput[i].getNumber(), tilesExpected[i].getType().equals(functionOutput[i].getType()));
            }
    }
    @Test
    public void randomBoardString(){
        Tile[] tilesExpected = Tile.getStartingTiles();
        //forming random board string comprising of one of the each type of tile
        StringBuilder boardString = new StringBuilder();
        //converting to array list to uses shuffle function
        ArrayList<Tile> tileArrayList = new ArrayList<>();
        for (Tile tile : tilesExpected) {
                Tile t = new Tile(tile.getType(),1);
                tileArrayList.add(t);
                tile.setNumber(1);
        }
        Collections.shuffle(tileArrayList);
        for (Tile tile:tileArrayList){
            boardString = boardString.append(tile.getType() + "00");
        }
        Tile[] functionOutput = Metro.sortedOccurrence(String.valueOf(boardString));
        //Comparison of tile type and count
        for (int i = 0; i<tilesExpected.length; i++) {
            assertTrue("Incorrect tile type at " + i +" th position in sorted array", tilesExpected[i].getType().equals(functionOutput[i].getType()));
            assertTrue("Incorrect tile count at " + i +" th position in sorted array", tilesExpected[i].getNumber()==(functionOutput[i].getNumber()));
        }
    }
}
