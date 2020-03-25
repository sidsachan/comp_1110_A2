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

    public static Tile[] tiles = {
            new Tile(TileType.aacb,TileState.D),
            new Tile(TileType.aacb,TileState.D),
            new Tile(TileType.aacb,TileState.D),
            new Tile(TileType.aacb,TileState.D),
            new Tile(TileType.cbaa,TileState.D),
            new Tile(TileType.cbaa,TileState.D),
            new Tile(TileType.cbaa,TileState.D),
            new Tile(TileType.cbaa,TileState.D),
            new Tile(TileType.acba,TileState.D),
            new Tile(TileType.acba,TileState.D),
            new Tile(TileType.acba,TileState.D),
            new Tile(TileType.acba,TileState.D),
            new Tile(TileType.baac,TileState.D),
            new Tile(TileType.baac,TileState.D),
            new Tile(TileType.baac,TileState.D),
            new Tile(TileType.baac,TileState.D),
            new Tile(TileType.aaaa,TileState.D),
            new Tile(TileType.aaaa,TileState.D),
            new Tile(TileType.aaaa,TileState.D),
            new Tile(TileType.aaaa,TileState.D),
            new Tile(TileType.cbcb,TileState.D),
            new Tile(TileType.cbcb,TileState.D),
            new Tile(TileType.cbcb,TileState.D),
            new Tile(TileType.bcbc,TileState.D),
            new Tile(TileType.bcbc,TileState.D),
            new Tile(TileType.bcbc,TileState.D),
            new Tile(TileType.cccc,TileState.D),
            new Tile(TileType.cccc,TileState.D),
            new Tile(TileType.bbbb,TileState.D),
            new Tile(TileType.bbbb,TileState.D),
            new Tile(TileType.dacc,TileState.D),
            new Tile(TileType.dacc,TileState.D),
            new Tile(TileType.cdac,TileState.D),
            new Tile(TileType.cdac,TileState.D),
            new Tile(TileType.ccda,TileState.D),
            new Tile(TileType.ccda,TileState.D),
            new Tile(TileType.accd,TileState.D),
            new Tile(TileType.accd,TileState.D),
            new Tile(TileType.dbba,TileState.D),
            new Tile(TileType.dbba,TileState.D),
            new Tile(TileType.adbb,TileState.D),
            new Tile(TileType.adbb,TileState.D),
            new Tile(TileType.badb,TileState.D),
            new Tile(TileType.badb,TileState.D),
            new Tile(TileType.bbad,TileState.D),
            new Tile(TileType.bbad,TileState.D),
            new Tile(TileType.ddbc,TileState.D),
            new Tile(TileType.ddbc,TileState.D),
            new Tile(TileType.cddb,TileState.D),
            new Tile(TileType.cddb,TileState.D),
            new Tile(TileType.bcdd,TileState.D),
            new Tile(TileType.bcdd,TileState.D),
            new Tile(TileType.dbcd,TileState.D),
            new Tile(TileType.dbcd,TileState.D),
            new Tile(TileType.adad,TileState.D),
            new Tile(TileType.adad,TileState.D),
            new Tile(TileType.dada,TileState.D),
            new Tile(TileType.dada,TileState.D),
            new Tile(TileType.dddd,TileState.D),
            new Tile(TileType.dddd,TileState.D),
    };


    /**
     * Update the tile state
     * @param newState A String representing the piece to be placed
     */
    public void updateTileState(TileState newState){
    }
}
