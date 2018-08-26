package net.sillipp.nettactoe.game;

import org.junit.Test;

import static net.sillipp.nettactoe.game.Game.EMPTY_BOARD;
import static net.sillipp.nettactoe.game.Placement.EMPTY;
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
}
