package comp1110.ass2;

/**
 * This class represents the a tile. They have two properties:
 * TileType : type of the tile
 * TileState : state of the tile
 */
public class Tile {
    private String type;
    private int number;

    public Tile(String type, int number) {
        this.type = type;
        this.number = number;
    }

    public static Tile[] startingTiles = {
            new Tile("aaaa",4),
            new Tile("aacb",4),
            new Tile("acba",4),
            new Tile("accd",2),
            new Tile("adad",2),
            new Tile("adbb",2),
            new Tile("baac",4),
            new Tile("badb",2),
            new Tile("bbad",2),
            new Tile("bbbb",2),
            new Tile("bcbc",3),
            new Tile("bcdd",2),
            new Tile("cbaa",4),
            new Tile("cbcb",3),
            new Tile("cccc",2),
            new Tile("ccda",2),
            new Tile("cdac",2),
            new Tile("cddb",2),
            new Tile("dacc",2),
            new Tile("dada",2),
            new Tile("dbba",2),
            new Tile("dbcd",2),
            new Tile("ddbc",2),
            new Tile("dddd",2),
    };


}
