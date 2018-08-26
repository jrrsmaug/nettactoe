package net.sillipp.nettactoe.game;

import java.util.ArrayList;
import java.util.List;

import static net.sillipp.nettactoe.game.Placement.EMPTY;

public class Game {

    public static final Placement[][] EMPTY_BOARD = new Placement[][]{
            { EMPTY, EMPTY, EMPTY },
            { EMPTY, EMPTY, EMPTY },
            { EMPTY, EMPTY, EMPTY }
    };

    private final int maxX;
    private final int maxY;
    private final List<Placement> board;
    private Placement lastPlayer;

    public Game() {
        this(EMPTY_BOARD);
    }

    private Game(Placement[][] initial) {
        this.maxX = initial.length;
        this.maxY = initial[0].length;
        this.board = toList(initial);
        this.lastPlayer = null;
    }

    private List<Placement> toList(Placement[][] initial) {
        List<Placement> board = new ArrayList<>(maxX * maxY);
        for (int y = 0; y < maxY; y++) {
            for (int x = 0; x < maxX; x++) {
                board.add(initial[y][x]);
            }
        }
        return board;
    }

    public Placement[][] getBoard() {
        Placement[][] array = new Placement[maxY][maxX];
        for (int y = 0; y < maxY; y++) {
            for (int x = 0; x < maxX; x++) {
                array[y][x] = board.get(coords(x, y));
            }
        }
        return array;
    }

    private int coords(long x, long y) {
        if (x >= maxX || y >= maxY) {
            throw new IllegalArgumentException("Illegal coordinates");
        }
        return (int) (y * maxY + x);
    }

    public Game move(Placement player, long x, long y) {
        if (lastPlayer == player) {
            throw new IllegalArgumentException("Player took double turn");
        }
        if (board.get(coords(x, y)) != EMPTY) {
            throw new IllegalArgumentException("Field not empty");
        }
        board.set(coords(x, y), player);
        lastPlayer = player;
        return this;
    }

    private long count(Placement type) {
        return board.stream().filter(placement -> placement == type).count();
    }

}
