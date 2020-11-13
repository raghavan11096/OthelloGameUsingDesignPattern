package main.java.model.board;

import main.java.model.utility.Point;
import org.junit.jupiter.api.Test;

import java.util.HashSet;

import static org.junit.jupiter.api.Assertions.*;

class OthelloBoardTest {
    private final OthelloBoard othelloBoard = new OthelloBoard(8, 8);

    @Test
    void generateValidLocationsTest() {

        assertNotNull(othelloBoard.generateValidLocations('○', '●'));


    }

    @Test
    void ValidLocationsTest() {
        assertNotNull(othelloBoard.isValidMove(0, 0, 0));

    }

    @Test
    void scoreUpdateTest() {
        othelloBoard.scoreUpdate();

    }


    @Test
    void showBlackTest() {
        othelloBoard.showBlack();
    }

    @Test
    void validBlackPlayerTest() {
        othelloBoard.blackPlayer();
    }

    @Test
    void validWhitePlayerValidTest() {
        othelloBoard.whitePlayer("e3");
    }



    @Test
    void setColumnInvalid() {


        assertEquals(-1, othelloBoard.columnMatcher('z'));


    }

    @Test
    void setColumnValid() {


        assertEquals(0, othelloBoard.columnMatcher('a'));


    }

}