package comp1110.ass2;

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
        return false;
    }

    /**
     * Task 5
     * Draw a random tile from the deck.
     *
     * @param placementSequence a String representing the sequence of tiles
     *                          that have already been played
     * @return a random tile from the deck
     */
    public static String drawFromDeck(String placementSequence) {
        // FIXME Task 5: draw a random tile from the deck
        return "";
    }

    /**
     * Task 6
     * Determine if a given placement sequence follows the rules of the game.
     * These rules are:
     * - All tracks on all placed pieces must eventually arrive at a station.
     * - No piece can overlap another piece, or any of the central stations.
     * - If a piece is on an edge of the board, it cannot contain a track that
     * results in a station looping back to itself, UNLESS it is unavoidable.
     * - If a piece is on a corner of the board, it cannot contain a track that
     * links the two stations adjacent to that corner, UNLESS it is
     * unavoidable.
     *
     * @param placementSequence A sequence of placements on the board.
     * @return Whether this placement string is valid.
     */
    public static boolean isPlacementSequenceValid(String placementSequence) {
        // FIXME Task 6: determine whether a placement sequence is valid
        return false;
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
}
