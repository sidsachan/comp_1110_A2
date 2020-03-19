package comp1110.ass2;

/**
 * This class represents the a tile. They have two properties:
 * TileType : type of the tile
 * TileState : state of the tile
 */
public class Tile {
    private TileType type;
    private TileState state;

    public Tile(TileType type, TileState state) {
        this.type = type;
        this.state = state;
    }

    /**
     * Update the tile state
     * @param newState A String representing the piece to be placed
     */
    public void updateTileState(TileState newState){
    }
}
