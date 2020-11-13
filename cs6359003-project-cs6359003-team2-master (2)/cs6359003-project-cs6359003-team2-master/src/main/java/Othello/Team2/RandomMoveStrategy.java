package main.java.Othello.Team2;

import main.java.gameIO.IModel;
import main.java.model.move.INextMoveStrategy;
import main.java.model.utility.Point;

import java.util.Random;

public class RandomMoveStrategy implements INextMoveStrategy {

    @Override
    public Point getNextMove(IModel context, int player) {
        return null;
    }

    public String nextMove(Object[] myArr) {
        // Generates a Valid move using random function from the list of valid moves.
        int rnd = new Random().nextInt(myArr.length);
        //Decoding the random value to string
        String input = myArr[rnd].toString().replace("[", "").replace("]", "").replace(",", "").replace(" ", "");
        return input;
    }

}
