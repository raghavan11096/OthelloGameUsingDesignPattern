package main.java.model.player;

import main.java.Othello.Team2.OthelloUI;
import main.java.model.modelToView.IRejectCommand;
import main.java.model.viewToModel.IRequestor;
import main.java.model.viewToModel.ITurnManager;
import main.java.model.viewToModel.IViewRequestor;

public class WhitePlayer extends HumanPlayer {
    ITurnManager turnManager;

    public WhitePlayer(IRequestor iRequestor, int player, ITurnManager turnManager) {
        super(iRequestor, player, turnManager);
        this.turnManager = turnManager;
    }


    @Override
    public void takeTurn() {
        System.out.println("White player " + getPlayer() + " takes turn.");
        /**
         * Passes the player ID to the Othello UI
         */
        OthelloUI.playerAt(getPlayer());


        turnManager.takeTurn(new IViewRequestor() {

            @Override
            public void setTokenAt(int row, int col, final IRejectCommand rejectCommand) {
                getRequestor().setTokenAt(row, col, getPlayer(), new IRejectCommand() {
                    @Override
                    public void execute() {
                        rejectCommand.execute();
                        takeTurn();
                    }
                });
            }
        });
    }

}





















