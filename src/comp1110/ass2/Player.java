package comp1110.ass2;

public class Player {
    private String name;
    private int score;
    private Tile tileInHand;

    public Player(String name) {
        this.name = name;
        this.score = 0;
        this.tileInHand = null;
    }

    /**
     * Update the tile player holding in hand
     * May need to add a tile (if player decides to keep it)
     * Or reset it  to null, if player decides to play it on the board
     * @param tile A String representing the piece to be placed
     */
    public void updateTileHolding(Tile tile){
    }

    public String getName(){
        return this.name;
    }
}
