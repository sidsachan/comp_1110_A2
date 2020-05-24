package comp1110.ass2;

public class Player {

    private String name;
    private int score;
    private String tileInHand;

    public Player() {
    }

    public Player(String name) {
        this.name = name;
        this.score = 0;
        this.tileInHand = "";
    }

    /**
     * Update the tile player holding in hand
     * May need to add a tile (if player decides to keep it)
     * Or reset it  to null, if player decides to play it on the board
     * @param tile A String representing the piece to be placed
     */

    public void updateTileHolding(String tile){
        this.tileInHand = tile;
    }

    /**
     * to get the name of player for game
     * @return a string name of the particular player
     */
    public String getName(){
        return this.name;
    }

    /**
     * function to update the score of the player
     * @param score an int variable containing the updated score of the player
     */

    public void updateScore(int score){
        this.score = score;
    }

    /**
     * function to get score of a player
     * @return int representing the score of a player
     */
    public int getScore(){
        return this.score;
    }

    /**
     * function to get the tile in hand
     * @return tile type of the tile in hand
     */
    public String getTileInHand(){return this.tileInHand; }

    /**
     * function to determine if tile is in hand
     * @return true if player has a tile  in hand, false otherwise
     */

    public  boolean isHolding(){
        if("".equals(this.tileInHand)) {
            return false;
        }
        return true;
    }
}
