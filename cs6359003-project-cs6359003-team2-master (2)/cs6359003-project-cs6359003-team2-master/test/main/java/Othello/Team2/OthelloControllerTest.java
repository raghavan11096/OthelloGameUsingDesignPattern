package main.java.Othello.Team2;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class OthelloControllerTest {
    OthelloController othelloController = new OthelloController();

    @Test
    void start() {
        othelloController.start(8, 8);

    }
}