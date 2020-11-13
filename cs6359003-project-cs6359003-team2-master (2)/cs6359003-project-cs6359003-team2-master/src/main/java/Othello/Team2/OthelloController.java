package main.java.Othello.Team2;

import main.java.model.GameModel;
import main.java.model.viewToModel.ITurnManager;
import main.java.model.viewToModel.IViewRequestor;


public class OthelloController {

    public static void main(String[] args) {
        OthelloController othello = new OthelloController();

        othello.start(8, 8);


    }

    public void start(int row, int col) {
        GameModel gameModel = new GameModel(row, col);

        final OthelloUI view = new OthelloUI();
/**
 * Dimension of the board is set.
 */
        view.setDimension(gameModel.getBoardModel().getDimension());
        gameModel.setCommand(view.getCommand());
        gameModel.setViewAdmin(view, new ITurnManager() {
            /**
             * Take turn helps in switching the players after every valid move.
             */
            @Override
            public void takeTurn(IViewRequestor iViewRequestor) {
                view.setiViewRequestor(iViewRequestor);
            }
        });
        view.setiModelManager(gameModel);
        view.setPlayers(gameModel.getPlayers());

    }
}

