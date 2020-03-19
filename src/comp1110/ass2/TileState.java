package comp1110.ass2;

/**
 * The enum represents the states a tile can be in. These are:
 * InDeck = in the deck,
 * InHand = in a player's hand,
 * OnBoard = on the board,
 */
public enum TileState {
    D("InDeck"), H("InHand"), B("OnBoard");

    private final String state;

    TileState(String state) {
        this.state = state;
    }
}
