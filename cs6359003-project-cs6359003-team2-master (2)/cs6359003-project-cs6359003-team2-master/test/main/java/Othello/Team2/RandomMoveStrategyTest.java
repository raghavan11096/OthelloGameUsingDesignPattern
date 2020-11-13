package main.java.Othello.Team2;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class RandomMoveStrategyTest {
    RandomMoveStrategy randomMoveStrategy = new RandomMoveStrategy();

    @Test
    void nextMove() {
        Object[][] myArr;
        // Values of the first moves
        myArr = new Integer[][]{{2, 3}, {3, 2}, {5, 4}, {4, 5}};
        assertNotNull(randomMoveStrategy.nextMove(myArr));

    }
}