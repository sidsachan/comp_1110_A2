package comp1110.ass2;

import javax.swing.tree.TreeCellRenderer;
import java.util.*;

public class Metro {
    private static TreeMap<Integer,String>scorePlacementSequences=new TreeMap<>();
    private static TreeMap<Integer,String>allPlacementSequences=new TreeMap<>();
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

        if (piecePlacement.length() != 6) {
            return false;
        }

        //first four characters between 'a'(97 ASCII) and 'd' (100 ASCII)

        for (int i = 0; i < 4; i++) {
            int x = piecePlacement.charAt(i);
            if (!(x >= 97 && x <= 100)) {
                return false;
            }
        }

        //fifth and sixth character between '0'(48 ASCII) and '7'(55 ASCII)

        for (int i = 4; i < 6; i++) {
            int x = piecePlacement.charAt(i);
            if (!(x >= 48 && x <= 55)) {
                return false;
            }
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

        if (l % 6 != 0 || l > 360) {
            return false;
        }

        //check tile type valid
        if (!checkTileType(placement)) {
            return false;
        }

        Tile[] sorted = sortedOccurrence(placement);
        Tile[] allTiles = Tile.getStartingTiles();
        for (int i = 0; i < 24; i++) {
            if (sorted[i].getNumber() > allTiles[i].getNumber()) {
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
        //for creating a random number for selection
        int count = 0;
        Random rand = new Random();

        for (int i = 0; i < occurrence.length; ++i) {
            int difference = remaining[i].getNumber() - occurrence[i].getNumber();
            remaining[i].setNumber(difference);
            count = count + difference;
        }

        //getting rid of in hands tiles. doing normal search O(m*n)
        for (int i = 0; i < totalHands.length(); i = i + 4) {
            for (int j = 0; j < remaining.length; ++j) {
                if (totalHands.substring(i, i + 4).equals(remaining[j].getType())) {
                    int temp = remaining[j].getNumber() - 1;
                    remaining[j].setNumber(temp);
                    count = count - 1;
                    break;
                }
            }
        }

        if (count == 0) {
            return "";
    }

        int r = rand.nextInt(count) + 1;
        //i am doing this with more calculations/processing using less memory
        //alternate way is to create a string array of the remaining tile when we are
        //counting the remaining ones.
        count = 0;

        for (int i = 0; i < remaining.length; ++i) {
            for (int j = 0; j < remaining[i].getNumber(); j++) {
                count++;
                if (count == r) {
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

        int[][] tilePresent = new int[8][8];
        for (int i = 4; i < placementSequence.length(); i = i + 6) {
            int row = (int) placementSequence.charAt(i) - 48;
            int col = (int) placementSequence.charAt(i + 1) - 48;
            char a = placementSequence.charAt(i - 4);
            char b = placementSequence.charAt(i - 3);
            char c = placementSequence.charAt(i - 2);
            char d = placementSequence.charAt(i - 1);

            if (tilePresent[row][col] == 0) {
                tilePresent[row][col] = 1;
            } else {
                //overlap check
                return false;
            }

//central station positions check
            if ((row == 3 || row == 4) && (col == 3 || col == 4))
            {
                return false;
            }

            if (((row == 2 || row == 5) && (col == 3 || col == 4)) || ((row == 3 || row == 4) && (col == 2 || col == 5)) ){
                if(tilePresent[row-1][col] == 0 && tilePresent[row+1][col] == 0
                        &&tilePresent[row][col+1] == 0 && tilePresent[row][col-1] == 0) {
                    //next to central station check
                    return false;
                }
            }


            if (row == 0 && a == 'd'){
                if (b == 'd' && c == 'd' && d == 'd' && i == 4){
                    continue;
                }else{
                    if(!checkInnerBoard(tilePresent)) {
                        //check not edge place
                        return false;
                    }

                    for(int y=1;y<7;y++){
                        if(tilePresent[7][y]!=1&&c!='d'){
                            return false;
                        }
                    }

                    for(int x=1;x<7;x++){
                        if(tilePresent[x][0]!=1&&d!='d'){
                            return false;
                        }else if(tilePresent[x][7]!=1&&b!='d'){
                            return false;
                        }
                    }
                    //check edge place
                    boolean connerCheck=true;
                    if(tilePresent[7][0]!=1&&d!='c'&&c!='b'&&c!='d'&&d!='d'){
                        connerCheck = false;
                    }

                    if(tilePresent[7][7]!=1&&c!='c'&&b!='b'&&c!='d'&&b!='d'){
                        connerCheck = false;
                    }

                    if(!connerCheck)
                        //check conner place
                    {
                        return false;
                    }
                }
            }

            if (row == 7 && c == 'd'){
                if (b == 'd' && a == 'd' && d == 'd' && i == 4){
                    continue;
                }else{
                    if(!checkInnerBoard(tilePresent)) {
                        //check not edge place
                        return false;
                    }
                    for(int y=1;y<7;y++){
                        if(tilePresent[0][y]!=1&&a!='d'){
                            return false;
                        }
                    }
                    for(int x=1;x<7;x++){
                        if(tilePresent[x][0]!=1&&d!='d'){
                            return false;
                        }else if(tilePresent[x][7]!=1&&b!='d'){
                            return false;
                        }
                    }           //check edge place
                    boolean connerCheck=true;
                    if(tilePresent[0][0]!=1&&a!='c'&&d!='b'&&a!='d'&&d!='d'){
                        connerCheck = false;
                    }
                    if(tilePresent[0][7]!=1&&b!='c'&&a!='b'&&a!='d'&&b!='d'){
                        connerCheck = false;
                    }
                    if(!connerCheck) {
                        return false;
                    }
                }
            }

            if (col == 0 && d == 'd'){
                if (b == 'd' && c == 'd' && a == 'd' && i == 4){
                    continue;
                }else{
                    if(!checkInnerBoard(tilePresent))
                        return false;//check not edge place
                    for(int x=1;x<7;x++){
                        if(tilePresent[x][7]!=1&&b!='d'){
                            return false;
                        }
                    }

                    for(int y=1;y<7;y++){
                        if(tilePresent[0][y]!=1&&a!='d'){
                            return false;
                        }else if(tilePresent[7][y]!=1&&c!='d'){
                            return false;
                        }
                    }
                    //check edge place
                    boolean connerCheck=true;
                    if(tilePresent[7][7]!=1&&c!='c'&&b!='b'&&b!='d'&&c!='d'){
                        connerCheck = false;
                    }
                    if(tilePresent[0][7]!=1&&b!='c'&&a!='b'&&a!='d'&&b!='d'){
                        connerCheck = false;
                    }
                    if(!connerCheck) {
                        return false;
                    }
                }
            }
            if (col == 7 && b == 'd'){
                if (a == 'd' && c == 'd' && d == 'd' && i == 4){
                    continue;
                }else{
                    if(!checkInnerBoard(tilePresent)) {
                        return false;//check not edge place
                    }
                    for(int x=1;x<7;x++){
                        if(tilePresent[x][0]!=1&&d!='d'){
                            return false;
                        }
                    }

                    for(int y=1;y<7;y++){
                        if(tilePresent[0][y]!=1&&a!='d'){
                            return false;
                        }else if(tilePresent[7][y]!=1&&c!='d'){
                            return false;
                        }
                    }           //check edge place
                    boolean connerCheck=true;
                    if(tilePresent[0][0]!=1&&a!='c'&&d!='b'&&a!='d'&&d!='d'){
                        connerCheck = false;
                    }
                    if(tilePresent[7][0]!=1&&d!='c'&&c!='b'&&c!='d'&&d!='d'){
                        connerCheck = false;
                    }
                    if(!connerCheck)
                        return false;
                }
            }                //edge of the board check

            if (row == 0 && col == 0 &&(a=='c'||d=='b')){
                if(!checkInnerBoard(tilePresent))
                    return false;//check not edge place
                for(int x=1;x<7;x++){
                    if(tilePresent[x][7]!=1&&b!='d'){
                        return false;
                    }else if(tilePresent[x][0]!=1&&a!='d'){
                        return false;
                    }
                }

                for(int y=1;y<7;y++){
                    if(tilePresent[0][y]!=1&&a!='d'){
                        return false;
                    }else if(tilePresent[7][y]!=1&&c!='d'){
                        return false;
                    }
                }

                //check edge place
                boolean connerCheck=true;

                if(tilePresent[7][7]!=1&&c!='c'&&b!='b'&&b!='d'&&c!='d'){
                    connerCheck = false;
                }
                if(tilePresent[0][7]!=1&&b!='c'&&a!='b'&&a!='d'&&b!='d'){
                    connerCheck = false;
                }
                if(tilePresent[7][0]!=1&&d!='c'&&c!='b'&&d!='d'&&c!='d'){
                    connerCheck = false;
                }

                if(!connerCheck) {
                    return false;
                }
            }
            if (row == 7 && col == 0 &&(d=='c'||c=='b')){
                if(!checkInnerBoard(tilePresent))
                    return false;//check not edge place
                for(int x=1;x<7;x++){
                    if(tilePresent[x][7]!=1&&b!='d'){
                        return false;
                    }else if(tilePresent[x][0]!=1&&a!='d'){
                        return false;
                    }
                }
                for(int y=1;y<7;y++){
                    if(tilePresent[0][y]!=1&&a!='d'){
                        return false;
                    }else if(tilePresent[7][y]!=1&&c!='d'){
                        return false;
                    }
                }
                //check edge place
                boolean connerCheck=true;
                if(tilePresent[7][7]!=1&&c!='c'&&b!='b'&&b!='d'&&c!='d'){
                    connerCheck = false;
                }
                if(tilePresent[0][7]!=1&&b!='c'&&a!='b'&&a!='d'&&b!='d'){
                    connerCheck = false;
                }
                if(tilePresent[0][0]!=1&&a!='c'&&d!='b'&&a!='d'&&d!='d'){
                    connerCheck = false;
                }

                if(!connerCheck)
                    return false;
            }

            if (row == 0 && col == 7 &&(b=='c'||a=='b')){
                if(!checkInnerBoard(tilePresent))
                    return false;//check not edge place
                for(int x=1;x<7;x++){
                    if(tilePresent[x][7]!=1&&b!='d'){
                        return false;
                    }else if(tilePresent[x][0]!=1&&a!='d'){
                        return false;
                    }
                }
                for(int y=1;y<7;y++){
                    if(tilePresent[0][y]!=1&&a!='d'){
                        return false;
                    }else if(tilePresent[7][y]!=1&&c!='d'){
                        return false;
                    }
                }           //check edge place
                boolean connerCheck=true;

                if(tilePresent[7][7]!=1&&c!='c'&&b!='b'&&b!='d'&&c!='d'){
                    connerCheck = false;
                }

                if(tilePresent[0][0]!=1&&a!='c'&&d!='b'&&a!='d'&&d!='d'){
                    connerCheck = false;
                }
                if(tilePresent[7][0]!=1&&d!='c'&&c!='b'&&d!='d'&&c!='d'){
                    connerCheck = false;
                }

                if(!connerCheck)
                    return false;
            }
            if (row == 7 && col == 7 &&(c=='c'||b=='b')){
                if(!checkInnerBoard(tilePresent))
                    //check not edge place
                    return false;

                for(int x=1;x<7;x++){
                    if(tilePresent[x][7]!=1&&b!='d'){
                        return false;
                    }else if(tilePresent[x][0]!=1&&a!='d'){
                        return false;
                    }
                }

                for(int y=1;y<7;y++){
                    if(tilePresent[0][y]!=1&&a!='d'){
                        return false;
                    }else if(tilePresent[7][y]!=1&&c!='d'){
                        return false;
                    }
                }           //check edge place
                boolean connerCheck=true;
                if(tilePresent[0][0]!=1&&a!='c'&&d!='b'&&a!='d'&&d!='d'){
                    connerCheck = false;
                }
                if(tilePresent[0][7]!=1&&b!='c'&&a!='b'&&a!='d'&&b!='d'){
                    connerCheck = false;
                }
                if(tilePresent[7][0]!=1&&d!='c'&&c!='b'&&d!='d'&&c!='d'){
                    connerCheck = false;
                }

                if(!connerCheck) {
                    return false;
                }
            }              //conner of the board check

            if (row!=0&&row!=7&&col!=0&&col!=7){
                if(tilePresent[row-1][col]!=1&&tilePresent[row+1][col]!=1&&tilePresent[row][col-1]!=1&&tilePresent[row][col+1]!=1) {
                    return false;
                }
            }               //check if tile has next tile
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
    //获得分数
    public static int[] getScore(String placementSequence, int numberOfPlayers) {
        // FIXME Task 7: determine the current score for the game
        //System.out.println( "getScore~placementSequence: "+ placementSequence);
        int[] score = new int[numberOfPlayers];

        int stationPerPlayer = 32 / numberOfPlayers;

        int[][] arrangement = stationArrangement(numberOfPlayers);

        for (int i = 0; i < numberOfPlayers; i++) {
            for (int j = 0; j < stationPerPlayer; j++) {

                score[i] = score[i] + getScoreForStation(placementSequence, arrangement[i][j]);
            }
        }
        return score;
    }

    /**
     * Task 9
     * Given a placement sequence string, generate a valid next move.
     *
     * @param placementSequence a String representing the sequence of piece placements made so far in the game
     * @param piece             a four-character String representing the tile to be placed
     * @param numberOfPlayers   The number of players in the game
     * @return A valid placement of the given tile
     */

    public static String generateMove(String placementSequence, String piece, int numberOfPlayers) {
        // FIXME Task 9: generate a valid move
        Board[] startBoard = Board.getStartBoard();
        List<Board> board = new ArrayList<>(Arrays.asList(startBoard));

        boolean check = false;
        String result ="";

        for(int i=4;i<placementSequence.length();i+=6) {

            String position = placementSequence.substring(i, i + 2);

            for (int j = 0; j < board.size(); j++) {
                String name = board.get(j).getName();
                if (name.equals(position)) {
                    board.remove(j);
                }
            }
        }
//check if the sequence is valid. If not, remove the last move and create a new one
            while(!check){
                Random random = new Random();
                int num = random.nextInt(board.size());
                String place = board.get(num).getName();
                placementSequence = placementSequence.concat(piece);
                //add a random move
                placementSequence = placementSequence.concat(place);
                if(!isPlacementSequenceValid(placementSequence)){
                    board.remove(num);
                    placementSequence=placementSequence.substring(0,placementSequence.length()-6);
                }else {
                    result = piece.concat(place);
                    check=true;
                }
            }
        return result;
    }

    /* Update the given placement sequence string.
     *
     * @param placementSequence a String representing the sequence of piece placements made so far in the game
     * @param tilePlacement             a six-character String representing the piece to be placed
     * @return A new String shows updated placement sequence.
     */
//获取所有定义的顺序值
    public static TreeMap<Integer, String> getAllPlacementSequences() {
        if(scorePlacementSequences.size()>0){
            Integer lastScore=scorePlacementSequences.lastKey();
            String  lastPlacementSequences=scorePlacementSequences.get(lastScore);
            allPlacementSequences.put(lastScore,lastPlacementSequences);
        }

        return allPlacementSequences;
    }

    public static TreeMap<Integer, String> getScorePlacementSequences() {

        return scorePlacementSequences;
    }

    public static String updatePlacementSequence(String placementSequence, String tilePlacement) {

        return "";
    }

    /* Check if the board is full, if it is, game is over.
     *
     * @param placementSequence a String representing the sequence of piece placements made so far in the game
     * @return A boolean shows the game is over or not.
     */
    public static boolean checkGameOver(String placementSequence) {

        return false;
    }

    /**
     * Return sorted occurrence of tiles in the placement sequence
     *
     * @param placementSequence a String representing the sequence of tiles
     *                          that have already been played
     * @return a tile array containing sorted occurrence of each tile type
     */


    public static Tile[] sortedOccurrence(String placementSequence) {
        Tile[] sorted = Tile.getStartingTiles();
        if (placementSequence == null) {
            return null;
        }
        int l = placementSequence.length();

        /*
        //making all occurrences zero
        for (int i = 0; i < sorted.length; ++i) {
            sorted[i].setNumber(0);
        }

        //finding the corresponding type for each piece in the placementSequence
        for (int i = 0; i < l; i = i + 6) {
            String s = placementSequence.substring(i, i + 4);
            int low = 0;
            int high = sorted.length;
            while (high >= low) {
                int mid = (high + low) / 2;
                //if the two string are equal the add one to the number
                if (s.hashCode() == sorted[mid].getType().hashCode()) {
                    sorted[mid].setNumber(sorted[mid].getNumber() + 1);
                    break;
                }
                //if the substring occurs later in the sorted array, then new low is the current mid
                else if (s.hashCode() > sorted[mid].getType().hashCode())
                    low = mid;
                    //if the substring occurs earlier in the sorted array, then new high is the current mid
                else if (s.hashCode() < sorted[mid].getType().hashCode())
                    high = mid;
            }
        }
        */

        //Hash map implementation

        HashMap<String, Integer> occurrence = new HashMap<>();


        for (Tile value : sorted) {
            occurrence.put(value.getType(), 0);
        }


        for (int i = 0; i < l; i = i + 6) {
            String s = placementSequence.substring(i, i + 4);
            Integer old = occurrence.get(s);
            occurrence.put(s, old + 1);
        }


        for (Tile tile : sorted) {

            tile.setNumber(occurrence.get(tile.getType()));
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
    //检查 tile 类型
    public static boolean checkTileType(String placementSequence) {
        //System.out.println("checkTileType--placementSequence: "+placementSequence);
        Tile[] allTiles = Tile.getStartingTiles();
        boolean result = false;
        boolean check = true;
        for (int i = 0; i < placementSequence.length(); i += 6) {
            String type = placementSequence.substring(i, i + 4);
            for (int j = 0; j < 24; j++) {
                if (type.equals(allTiles[j].getType())) {
                    result = true;
                }
            }
            if (!result) {
                check = false;
            }
        }
        return check;
    }


    /**
     * A function to represent the station arrangement depending on number of players
     *
     * @param numberOfPlayers number of players int he game
     * @return a 2D array containing stations owned by respective players
     */
//根据玩家个数车站布置
    public static int[][] stationArrangement(int numberOfPlayers) {
        int[][] stationDistribution = new int[numberOfPlayers][32 / numberOfPlayers];
        switch (numberOfPlayers) {
            case 2:
                stationDistribution = new int[][]{
                        {1, 3, 5, 7, 9, 11, 13, 15, 17, 19, 21, 23, 25, 27, 29, 31},
                        {2, 4, 6, 8, 10, 12, 14, 16, 18, 20, 22, 24, 26, 28, 30, 32}
                };
                break;
            case 3:
                stationDistribution = new int[][]{
                        {1, 4, 6, 11, 15, 20, 23, 25, 28, 31},
                        {2, 7, 9, 12, 14, 19, 22, 27, 29, 32},
                        {3, 5, 8, 10, 13, 18, 21, 24, 26, 30}
                };
                break;
            case 4:
                stationDistribution = new int[][]{
                        {4, 7, 11, 16, 20, 23, 27, 32},
                        {3, 8, 12, 15, 19, 24, 28, 31},
                        {1, 6, 10, 13, 18, 21, 25, 30},
                        {2, 5, 9, 14, 17, 22, 26, 29}
                };
                break;
            case 5:
                stationDistribution = new int[][]{
                        {1, 5, 10, 14, 22, 28},
                        {6, 12, 18, 23, 27, 32},
                        {3, 7, 15, 19, 25, 29},
                        {2, 9, 13, 21, 26, 30},
                        {4, 8, 11, 20, 24, 31}
                };
                break;
            case 6:
                stationDistribution = new int[][]{
                        {1, 5, 10, 19, 27},
                        {2, 11, 18, 25, 29},
                        {4, 8, 14, 21, 26},
                        {6, 15, 20, 24, 31},
                        {3, 9, 13, 23, 30},
                        {7, 12, 22, 28, 32}

                };
                break;
        }
        return stationDistribution;
    }

    /**
     * function to return the score corresponding to a station number.
     *
     * @param placementSequence the board representation
     * @param stationNumber the station in consideration
     * @return score for a station.
     */

    public static int getScoreForStation(String placementSequence, int stationNumber) {

        int score = 0;
        int side = (stationNumber - 1) / 8;         //to get which side of board we are talking about (0-top, 1-left, 2-bottom, 3-right)
        int startIndex = -1;                //the index at which the station starts (0-top, 6-left, 4-bottom, 2-right)
        int exitIndex = 8;                   //Index are from 0 to 7 on a tile
        int row = -1;
        int col = -1;                            //(row,col) represents the location on board, both are from 0 to 7.
        //finding the startIndex corresponding to the edge of the board
        switch (side) {
            case 0:
                startIndex = 0;
                break;
            case 1:
                startIndex = 6;
                break;
            case 2:
                startIndex = 4;
                break;
            case 3:
                startIndex = 2;
                break;
        }
        //finding the starting coordinates

        switch (side) {
            case 0:
                row = 0;
                col = 8 - stationNumber;
                break;
            case 1:
                row = (stationNumber - 8) - 1;
                col = 0;
                break;
            case 2:
                row = 7;
                col = (stationNumber - 16) - 1;
                break;
            case 3:
                row = 8 - (stationNumber - 24);
                col = 7;
                break;
        }

        while (true){
            String z = row +String.valueOf(col);
            int index = placementSequence.indexOf(z);
            //if there is no tile at that place
            if(index==-1){
                score = 0;
                break;
            }
            //the tile at the position
            String tile = placementSequence.substring(index-4,index);
            //the type of exit at the start index
            char ch = tile.charAt(startIndex/2);
            /*now depending upon the start index and the type of exit, we get exit index
       startIndex->  0   |   2   |   4   |   6
                a|   5   |   7   |   1   |   3
                b|   3   |   5   |   7   |   1
                c|   7   |   1   |   3   |   5
                d|   1   |   3   |   5   |   7
             */
            switch (ch) {
                case 'a':
                    exitIndex = (startIndex + 5) % 8;
                    break;
                case 'b':
                    exitIndex = (startIndex + 3) % 8;
                    break;
                case 'c':
                    exitIndex = (startIndex + 7) % 8;
                    break;
                case 'd':
                    exitIndex = (startIndex + 1) % 8;
                    break;
            }

            //update the score
            score = score + 1;

            //check if we reached edge stations
            if(row==0 && exitIndex==1) {
                break;
            } else if(row==7 && exitIndex ==5) {
                break;
            }

            if (col==0 && exitIndex ==7) {
                break;
            } else if(col==7 && exitIndex==3) {
                break;
            }
            //finding the next coordinate and startIndex(index at this new coordinate) to look for, depending upon the index of exit
            switch (exitIndex) {
                case 1:
                    row = row - 1;
                    startIndex = 4;
                    break;
                case 3:
                    col = col + 1;
                    startIndex = 6;
                    break;
                case 5:
                    row = row + 1;
                    startIndex = 0;
                    break;
                case 7:
                    col = col - 1;
                    startIndex = 2;
                    break;
            }
            //check if we reached central stations
            if ((row==3||row==4)&&(col==3||col==4)){
                score = score*2;
                break;
            }
        }

        return score;
    }

    /**
     * A function to get a shuffled deck at the begining of the game
     *
     * @return a tile array containing randomised tiles, a shuffled deck for playing
     */
    //洗牌
    public static ArrayList<String> getFreshDeck() {
        Tile[] start = Tile.getStartingTiles();
        ArrayList<String > deck = new ArrayList<>();

        for (Tile tile : start) {
            for (int i = 0; i < tile.getNumber();++i) {
                String type = tile.getType();
                deck.add(type);
            }
        }

        Collections.shuffle(deck);
        return deck;
    }

    /**
     * A function to check if inner board has empty space
     *
     * @param tilePresent array shows board space
     * @return a boolean shows if inner board has empty space
     */
    public static boolean checkInnerBoard(int[][] tilePresent){

        for(int x = 1; x<7;x++){
            for(int y=1; y<7; y++){
                if(tilePresent[x][y]!=1&&(((x!=3)&&(x!=4))||((y!=3)&&(y!=4)))){
                    return false;
                }
            }
        }

        return true;
    }

    /**
     *function to update the scores of players
     * @param placementSequence the current state of the board
     * @param playerArrayList array list containing players info
     * @return array list with updated scores
     */
    //更新玩家分数
    public static ArrayList<Player> assignScore(String placementSequence, ArrayList<Player> playerArrayList){
        int[] scores = getScore(placementSequence,playerArrayList.size());
        for (int i=0;i<scores.length;i++){
            playerArrayList.get(i).updateScore(scores[i]);
            scorePlacementSequences.put(scores[i],placementSequence);
        }
        //System.out.println("scorePlacementSequences: "+scorePlacementSequences);
        //System.out.println("AllPlacementSequences: "+Metro.getAllPlacementSequences());
        return playerArrayList;
    }

    /**
     *  a function to decide whether to draw from deck if you(computer) have a piece in hand
     * @param placementSequence the board state
     * @param tileInHand the in hand tile type
     * @return true if you want to draw from deck, false otherwise
     */
    public static boolean shouldDraw(String placementSequence, String tileInHand){
        return true;
    }

    /**
     * a function to decide whether to keep a tile in hand or place it on the board
     * @param placementSequence the board state
     * @param tileOnDeck tile type on top of the deck
     * @return true if you want to place  it in hand, false if you want to play it on board
     */
    public static boolean shouldKeepInHand(String placementSequence, String tileOnDeck){
        return false;
    }

}
