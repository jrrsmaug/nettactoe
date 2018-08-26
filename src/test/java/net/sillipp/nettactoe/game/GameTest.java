package net.sillipp.nettactoe.game;

import org.junit.Test;

import static net.sillipp.nettactoe.game.Game.EMPTY_BOARD;
import static net.sillipp.nettactoe.game.Placement.EMPTY;
import static net.sillipp.nettactoe.game.Placement.O;
import static net.sillipp.nettactoe.game.Placement.X;
import static org.hamcrest.CoreMatchers.is;
import static org.hamcrest.CoreMatchers.notNullValue;
import static org.junit.Assert.assertThat;

public class GameTest {

    @Test
    public void shouldInitGame() {
        assertThat(new Game(), is(notNullValue()));
    }

    @Test
    public void shouldSetEmptyBoardOnInit() {
        assertThat(new Game().getBoard(), is(EMPTY_BOARD));
    }

    @Test
    public void shouldMove() {
        assertThat(new Game().move(X, 1, 1).getBoard(), is(new Placement[][]{
                { EMPTY, EMPTY, EMPTY },
                { EMPTY, X, EMPTY },
                { EMPTY, EMPTY, EMPTY }
        }));
    }

    @Test
    public void shouldPlayRemis() {
        Game game = new Game()
                .move(X, 1, 1)
                .move(O, 0, 0)
                .move(X, 2, 0)
                .move(O, 0, 2)
                .move(X, 0, 1)
                .move(O, 2, 1)
                .move(X, 1, 0)
                .move(O, 1, 2)
                .move(X, 2, 2);
        assertThat(game.getBoard(), is(new Placement[][]{
                { O, X, X },
                { X, X, O },
                { O, O, X }
        }));
    }

    @Test(expected = IllegalArgumentException.class)
    public void shouldNotOverwritePastMoves() {
        Game game = new Game().move(X, 1, 1);
        game.move(O, 1, 1);
    }

    @Test(expected = IllegalArgumentException.class)
    public void shouldNotAllowMovesOutsideXOfBoard() {
        new Game().move(X, 3, 0);
    }

    @Test(expected = IllegalArgumentException.class)
    public void shouldNotAllowMovesOutsideYOfBoard() {
        new Game().move(X, 0, 3);
    }

    @Test(expected = IllegalArgumentException.class)
    public void shouldNotAllowDoubleMove() {
        Game game = new Game().move(X, 1, 1);
        game.move(X, 2, 1);
    }

    @Test(expected = IllegalArgumentException.class)
    public void shouldNotAllowDoubleMoveOfSecondPlayer() {
        Game game = new Game().move(X, 1, 1).move(O, 0, 0);
        game.move(O, 2, 1);
    }

    @Test
    public void shouldNotChangeBoardFromOutside() {
        Game game = new Game();
        Placement[][] board = game.getBoard();
        board[1][1] = Placement.X;
        assertThat(game.getBoard(), is(EMPTY_BOARD));
    }

}
