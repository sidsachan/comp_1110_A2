package comp1110.ass2;

public class Player {
    private String name;
    private int score;
    private Tiles tileInHand;

    public Player(String name) {
        this.name = name;
        this.score = 0;
        this.tileInHand = null;
    }

    /**
     * Update the tile player holding in hand
     * @param tile A String representing the piece to be placed
     */
    public void updateTileHolding(Tiles tile){

    }
}
