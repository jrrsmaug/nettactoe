package net.sillipp.nettactoe.game;

import java.util.ArrayList;
import java.util.List;
import java.util.function.Function;

import static net.sillipp.nettactoe.game.Placement.EMPTY;
import static net.sillipp.nettactoe.game.Placement.O;
import static net.sillipp.nettactoe.game.Placement.X;

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
    private State state;

    public Game() {
        this(EMPTY_BOARD);
    }

    private Game(Placement[][] initial) {
        this.maxX = initial.length;
        this.maxY = initial[0].length;
        this.board = toList(initial);
        this.lastPlayer = null;
        this.state = State.WAITING;
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
        updateMeta(player);
        return this;
    }

    private void updateMeta(Placement player) {
        lastPlayer = player;
        state = checkWin();
        if (state == null) {
            state = otherPlayerMoves(player);
        }
    }

    private State checkWin() {
        if (winCondition(Placement.X)) {
            return State.X_WON;
        }
        if (winCondition(Placement.O)) {
            return State.O_WON;
        }
        if (count(board, Placement.EMPTY) == 0) {
            return State.REMIS;
        }
        return null;
    }

    private boolean winCondition(Placement player) {
        return checkRows(player)
                || checkColumns(player)
                || checkDiagonals(player);
    }

    private boolean checkDiagonals(Placement player) {
        return checkDiagonal(player, (x) -> coords(x, x))
                || checkDiagonal(player, (x) -> coords(x, maxX - x - 1));
    }

    private boolean checkDiagonal(Placement player, Function<Integer, Integer> coord) {
        List<Placement> diagonal = new ArrayList<>(maxY);
        for (int x = 0; x < maxX; x++) {
            diagonal.add(board.get(coord.apply(x)));
        }
        return count(diagonal, player) == maxX;
    }

    private boolean checkColumns(Placement player) {
        for (int x = 0; x < maxX; x++) {
            if (countColumn(x, player) == maxX) {
                return true;
            }
        }
        return false;
    }

    private boolean checkRows(Placement player) {
        for (int y = 0; y < maxY; y++) {
            if (countRow(y, player) == maxX) {
                return true;
            }
        }
        return false;
    }

    private long countColumn(int x, Placement player) {
        List<Placement> column = new ArrayList<>(maxY);
        for (int y = 0; y < maxY; y++) {
            column.add(board.get(coords(x, y)));
        }
        return count(column, player);
    }

    private long countRow(int y, Placement player) {
        List<Placement> row = board.subList(y * maxY, y * maxY + maxX);
        return count(row, player);
    }

    private State otherPlayerMoves(Placement player) {
        return player == X ? State.O_MOVES
                : player == O ? State.X_MOVES
                : null;
    }

    private long count(List<Placement> board, Placement type) {
        return board.stream().filter(placement -> placement == type).count();
    }

    public State getState() {
        return state;
    }

}
