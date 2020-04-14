package comp1110.ass2;

import java.util.Random;

public class Metro {

    private int numberOfPlayers;        //number of players in a particular game
    private String placementSequence;       //the string representing game board situation
    private Player turnOfPlayer;        //Player whose turn it is currently
    /**
     * Task 2
     * Determine whether a piece placement is well-formed. For a piece
     * placement to be well-formed, it must:
     * - contain exactly six characters;
     * - have as its first, second, third and fourth characters letters between
     * 'a' and 'd' inclusive (tracks); and
     * - have as its fifth and sixth characters digits between 0 and 7 inclusive
     * (column and row respectively).
     *
     * @param piecePlacement A String representing the piece to be placed
     * @return True if this string is well-formed
     */
    public static boolean isPiecePlacementWellFormed(String piecePlacement) {
        // FIXME Task 2: determine whether a piece placement is well-formed
        //checking length
        if (piecePlacement.length()!=6)
            return false;
        //first four characters between 'a'(97 ASCII) and 'd' (100 ASCII)
        for (int i=0;i<4;i++) {
            int x  = piecePlacement.charAt(i);
            if (!(x>= 97 && x<= 100))
                return false;
        }
        //fifth and sixth character between '0'(48 ASCII) and '7'(55 ASCII)
        for (int i=4;i<6;i++) {
            int x = piecePlacement.charAt(i);
            if (!(x>= 48 && x<= 55))
                return false;
        }
        return true;
    }

    /**
     * Task 3
     * Determine whether a placement sequence string is well-formed.
     * For a placement sequence to be well-formed, it must satisfy the
     * following criteria:
     * - It must be composed of well-formed tile placements.
     * - For any piece x, there can exist no more instances of x on the board
     * than instances of x in the deck.
     *
     * @param placement A String representing the placement of all tiles on the
     *                  board
     * @return true if this placement sequence is well-formed
     */
    public static boolean isPlacementSequenceWellFormed(String placement) {
        // FIXME Task 3: determine whether a placement sequence is well-formed
        //check length
        int l = placement.length();
        if(l%6!=0||l>360){
            return false;
        }
        //check tile type valid
        if(!checkTileType(placement))
            return false;

        Tile[] sorted = sortedOccurrence(placement);
        Tile[] allTiles = Tile.getStartingTiles();
        for(int i=0;i<24;i++){
            if(sorted[i].getNumber()>allTiles[i].getNumber()){
                return false;
            }
        }
        return true;
    }

    /**
     * Task 5
     * Draw a random tile from the deck.
     *
     * @param placementSequence a String representing the sequence of tiles
     *                          that have already been played
     * @param totalHands        a String representing all tiles (if any) in
     *                          all players' hands
     * @return a random tile from the deck
     */
    public static String drawFromDeck(String placementSequence, String totalHands) {
        // FIXME Task 5: draw a random tile from the deck
        Tile[] remaining = Tile.getStartingTiles();
        Tile[] occurrence = sortedOccurrence(placementSequence);
        int count = 0;          //for creating a random number for selection
        Random rand = new Random();

        for (int i = 0; i< occurrence.length;++i) {
            int difference = remaining[i].getNumber()-occurrence[i].getNumber();
            remaining[i].setNumber(difference);
            count = count + difference;
        }

        //getting rid of in hands tiles. doing normal search O(m*n)
        for (int i = 0; i<totalHands.length(); i=i+4){
            for (int j = 0; j<remaining.length;++j){
                if (totalHands.substring(i,i+4).equals(remaining[j].getType())){
                    int temp = remaining[j].getNumber()-1;
                    remaining[j].setNumber(temp);
                    count = count - 1;
                    break;
                }
            }
        }

        if (count==0)
            return "";
        int r = rand.nextInt(count) + 1;
        //i am doing this with more calculations/processing using less memory
        //alternate way is to create a string array of the remaining tile when we are
        //counting the remaining ones.
        count=0;
        for (int i =0; i<remaining.length;++i){
            for (int j = 0 ; j< remaining[i].getNumber();j++){
                count++;
                if (count==r){
                    return remaining[i].getType();
                }
            }
        }
        return "";
    }

    /**
     * Task 6
     * Determine if a given placement sequence follows the rules of the game.
     * These rules are:
     * - No tile can overlap another tile, or any of the central stations.
     * - A tile cannot be placed next to one of the central squares unless it
     * continues or completes an existing track.
     * - If a tile is on an edge of the board, it cannot contain a track that
     * results in a station looping back to itself, UNLESS that tile could not
     * have been placed elsewhere.
     * - If a tile is on a corner of the board, it cannot contain a track that
     * links the two stations adjacent to that corner, UNLESS that tile could
     * not have been placed elsewhere.
     *
     * @param placementSequence A sequence of placements on the board.
     * @return Whether this placement string is valid.
     */
    public static boolean isPlacementSequenceValid(String placementSequence) {
        // FIXME Task 6: determine whether a placement sequence is valid
        //overlap and central station check
        //make a 8x8 matrix and store 0 (default upon initialization) and 1 (if present in the placement sequence)
        int [][] tilePresent = new int[8][8];
        for(int i=4; i<placementSequence.length();i=i+6){
            int row = (int)placementSequence.charAt(i) - 48;
            int col = (int)placementSequence.charAt(i+1) - 48;

            if (tilePresent[row][col]==0)
                tilePresent[row][col]=1;
            else
                return false;           //overlap check

            if ((row==3 || row==4) && (col==3 || col==4))       //central station positions check
                return false;
        }

        return true;
    }

    /**
     * Task 7
     * Determine the current score for the game.
     *
     * @param placementSequence a String representing the sequence of piece placements made so far in the game
     * @param numberOfPlayers   The number of players in the game
     * @return an array containing the scores for all players
     */
    public static int[] getScore(String placementSequence, int numberOfPlayers) {
        // FIXME Task 7: determine the current score for the game
        return new int[0];
    }

    /**
     * Task 9
     * Given a placement sequence string, generate a valid next move.
     *
     * @param placementSequence a String representing the sequence of piece placements made so far in the game
     * @param piece             a four-character String representing the tile just drawn
     * @param hand              A tile in the player's hand, which they can choose to play instead of the drawn tile.
     *                          If the player does not currently hold a tile, this parameter will be null.
     * @param numberOfPlayers   The number of players in the game
     * @return A valid placement of other the drawn tile or the tile from the player's hand (if it is not empty).
     */
    public static String generateMove(String placementSequence, String piece, String hand, int numberOfPlayers) {
        // FIXME Task 9: generate a valid move
        return "";
    }

    /* Update the given placement sequence string.
     *
     * @param placementSequence a String representing the sequence of piece placements made so far in the game
     * @param tilePlacement             a six-character String representing the piece to be placed
     * @return A new String shows updated placement sequence.
     */
    public static String updatePlacementSequence(String placementSequence, String tilePlacement){
        return "";
    }

    /* Check if the board is full, if it is, game is over.
     *
     * @param placementSequence a String representing the sequence of piece placements made so far in the game
     * @return A boolean shows the game is over or not.
     */
    public static boolean checkGameOver(String placementSequence){
        return false;
    }

    /**
     * Return sorted occurrence of tiles in the placement sequence
     *
     * @param placementSequence a String representing the sequence of tiles
     *                          that have already been played
     * @return a tile array containing sorted occurrence of each tile type
     */
    public static Tile[] sortedOccurrence(String placementSequence){
        Tile[] sorted = Tile.getStartingTiles();
        //making all occurrences zero
        for (int i = 0; i<sorted.length; ++i) {
            sorted[i].setNumber(0);
        }
        //finding the corresponding type for each piece in the placementSequence
        int l = placementSequence.length();
        for (int i=0;i<l;i=i+6){
            String s = placementSequence.substring(i,i+4);
            int low = 0;
            int high = sorted.length;
            while (high>=low){
                int mid = (high+low)/2;
                //if the two string are equal the add one to the number
                if (s.hashCode()==sorted[mid].getType().hashCode()) {
                    sorted[mid].setNumber(sorted[mid].getNumber() + 1);
                    break;
                }
                //if the substring occurs later in the sorted array, then new low is the current mid
                else if (s.hashCode()>sorted[mid].getType().hashCode())
                    low = mid;
                //if the substring occurs earlier in the sorted array, then new high is the current mid
                else if (s.hashCode()<sorted[mid].getType().hashCode())
                    high = mid;
            }
        }
        return sorted;
    }

    /**
     * Return boolean represent if tiles' type in the placement sequence are valid
     *
     * @param placementSequence a String representing the sequence of tiles
     *                          that have already been played
     * @return boolean represent if tiles' type in the placement sequence are valid
     */

    public static boolean checkTileType(String placementSequence){
        Tile[] allTiles = Tile.getStartingTiles();
        boolean result = false;
        boolean check = true;
        for(int i=0;i<placementSequence.length();i+=6){
            String type = placementSequence.substring(i,i+4);
            for(int j=0;j<24;j++){
                if(type.equals(allTiles[j].getType())){
                    result = true;
                }
            }
            if(!result)
                check=false;
        }
        return check;
    }
    /**
     * A function to get a shuffled deck at the begining of the game
     *
     * @return a tile array containing randomised tiles, a shuffled deck for playing
     */
    public static Tile[] getFreshDeck(){
        Tile [] start = Tile.getStartingTiles();
        return start;
    }
}
