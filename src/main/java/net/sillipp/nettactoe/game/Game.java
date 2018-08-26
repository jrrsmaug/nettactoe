package net.sillipp.nettactoe.game;

import static net.sillipp.nettactoe.game.Placement.EMPTY;

public class Game {

    public static final Placement[][] EMPTY_BOARD = new Placement[][]{
            { EMPTY, EMPTY, EMPTY },
            { EMPTY, EMPTY, EMPTY },
            { EMPTY, EMPTY, EMPTY }
    };

    private final Placement[][] board;

    public Game() {
        this(EMPTY_BOARD);
    }

    private Game(Placement[][] board) {
        this.board = board;
    }

    public Placement[][] getBoard() {
        return board;
    }
}
