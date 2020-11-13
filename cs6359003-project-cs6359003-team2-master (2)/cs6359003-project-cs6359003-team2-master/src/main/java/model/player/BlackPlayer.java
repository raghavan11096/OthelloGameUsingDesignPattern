package main.java.model.player;

import main.java.Othello.Team2.OthelloUI;
import main.java.Othello.Team2.RandomMoveStrategy;
import main.java.model.modelToView.IRejectCommand;
import main.java.model.move.INextMoveStrategy;
import main.java.model.viewToModel.IRequestor;
import main.java.model.viewToModel.ITurnManager;
import main.java.model.viewToModel.IViewRequestor;

public class BlackPlayer extends APlayer {
    /**
     * Extended from APlayer, mimicks the functionality of Computer player class.
     */
    private final Object RandomMoveStrategy = null;
    private INextMoveStrategy iNextMoveStrategy;
    private final ITurnManager turnManager;
    private final RandomMoveStrategy randomMoveStrategy;

    public BlackPlayer(IRequestor iRequestor, int player, ITurnManager turnManager, RandomMoveStrategy randomMoveStrategy) {
        super(iRequestor, player);
        this.turnManager = turnManager;
        this.randomMoveStrategy = (main.java.Othello.Team2.RandomMoveStrategy) RandomMoveStrategy;

    }


    @Override
    public void takeTurn() {
        System.out.println("Black player " + getPlayer() + " takes turn.");
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