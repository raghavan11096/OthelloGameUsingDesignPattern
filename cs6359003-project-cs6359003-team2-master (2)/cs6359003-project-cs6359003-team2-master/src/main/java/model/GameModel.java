/**
 *
 */
package main.java.model;

import java.util.*;

import main.java.Othello.Team2.RandomMoveStrategy;
import main.java.gameIO.IModel;
import main.java.model.board.*;
import main.java.model.modelToView.*;
import main.java.model.move.IBoardStatusVisitor;
import main.java.model.move.ICheckMoveVisitor;
import main.java.model.move.INextMoveStrategy;
import main.java.model.player.*;
import main.java.model.viewToModel.*;

/**
 * based on the "design patterns for games" by Zung
 *
 *	Single game management class
 *	Represent a concrete game
 */


/**
 * A concrete model of a game.  For descriptions of the methods, see the IModel,
 * IModelAdmin, and IModelCombo interface documentation.
 * Except for the setPlayers() and getPlayers() methods, see below.
 */
public final class GameModel implements IModel {
    /**
     * An abstract factory to create APlayers.   Used privately by GameModel.
     */
    interface IMakePlayer {
        /**
         * Instantiates an APlayer object given the player's "player number".
         * Player number 0 plays first, player number 1 plays second.
         * @param playerNo The player number for the player to be instantiated.
         * @return An APlayer object
         */
		APlayer create(int playerNo);
    }

    /**
     * For player management.
     */
    private TurnControl turnControl;

    /**
     * Adapter to talk to the view to display/clear a game piece ("token") or
     * a String message.
     */
    private ICommand iCommand;

    /**
     * Adapter to talk to the view to announce winner, draw, or reset.
     */
    private IViewManager viewManager;

    /**
     * Adapter to talk to the view to tell that the human player needs to try a
     * move.
     */
    private ITurnManager turnManager;

    /**
     * The invariant, encapsulated rules and behaviors of a game.
     */
    private final IBoardModel boardModel;

    /**
     * The constructor for the game model.
     * @param nRows The number of rows in the board.
     * @param nCols The number of columns in the board.
     */
    public GameModel(int nRows, int nCols) {
//Othello Board is generated
        boardModel = new OthelloBoard(nRows, nCols);
    }

    public GameModel(IBoardModel boardModel) {
        this.boardModel = boardModel;
    }

    /**
     * The facade that does "everything"!
     * Use anonymous inner class to have access to everything in the outer object.
     */
    private final IRequestor requestor = getRequestor();
    //TODO: private IRequestor requestor = // = ???  FOR STUDENTS TO FILL OUT ...


    /**
     * @param command
     */
    public void setCommand(ICommand command) {
        iCommand = command;
    }

    public void reset() {
        System.out.println("Resetting");
        boardModel.reset();
        boardModel.redrawAll(iCommand);
        if (turnControl != null)
            turnControl.setHalt();
    }

    /**
     * Assumes that the players are IMakePlayer factory objects.
     * @param player0 an IMakePlayer factory
     * @param player1 an IMakePlayer factory
     */
    public void setPlayers(Object player0, Object player1) {
        // Last in/first play
        turnControl = new TurnControl(((IMakePlayer) player1).create(1));
        turnControl.addPlayer(((IMakePlayer) player0).create(0));
        turnControl.run();
    }

    public void setViewAdmin(IViewManager viewAdmin, ITurnManager turnAdmin) {
        this.viewManager = viewAdmin;
        this.turnManager = turnAdmin;
    }

    public IBoardModel getBoardModel() {
        return boardModel;
    }


    /**
     * Returns a List filled with IMakePlayer factory objects.
     * @return
     */

    public List<Object> getPlayers() {
        List<Object> playerList = new ArrayList<Object>();
//TODO: ADD COMPUTER PLAYER WITH RANDOM MOVE STRATEGY IN MILESTONE 1.
        //TODO: ADD COMPUTER PLAYER WITH OTHER NEXT MOVE STRATEGY IN MILESTONE 2.
        /**
         * Both the fuctionalities of RANDOM MOVE STRATEGY and RANDOM VALID MOVE STRATEGY are performed in RANDOM MOVE STRATEGY class.
         */

        playerList.add(new IMakePlayer() {
            public APlayer create(int playerNo) {
                return new BlackPlayer(requestor, playerNo, turnManager, new RandomMoveStrategy());
            }

            public String toString() {
                return "Computer Black Player";
            }
        });


        playerList.add(new IMakePlayer() {
            public APlayer create(int playerNo) {
                return new WhitePlayer(requestor, playerNo, turnManager);
            }

            public String toString() {
                return "Human White Player";
            }
        });

        return playerList;
    }

    public void exit() {
        reset();
    }

    public IRequestor getRequestor() {
        return new IRequestor() {
            /**
             * User makes a move with the help of IRequestor.
             */

            @Override
            public void setTokenAt(final int row, final int col, final
            int player, final IRejectCommand rejectCommand) {

                boardModel.makeMove(row, col, player,
                        new ICheckMoveVisitor() {


                            @Override
                            public void invalidMoveCase() {
                                rejectCommand.execute(); // Tell view
                            }

                            @Override
                            public void validMoveCase() { // Tell view

                                iCommand.setTokenAt(row, col, player);
                            }
                        },
                        new IBoardStatusVisitor() {

                            @Override
                            public Object player0WonCase(IBoardModel host,
                                                         Object param) {
                                viewManager.win(0);
                                return null; // Tell view
                            }

                            @Override
                            public Object player1WonCase(IBoardModel host,
                                                         Object param) {
                                viewManager.win(1);
                                return null; // Tell view
                            }

                            @Override
                            public Object drawCase(IBoardModel host,
                                                   Object param) {
                                viewManager.draw();
                                return null; // Tell view
                            }

                            @Override
                            public Object noWinnerCase(IBoardModel host,
                                                       Object param) {
                                //makeMove(player);
                                return null; //Computer’s turn
                            }
                        });
            }
        };
    }


    /**
     * Add a ComputerPlayer based using the INextMoveStrategy specified by the
     * fully qualified (i.e. included package name) String classname.
     * The strategy is assumed to have either a no-parameter constructor or a
     * constructor that takes an IModel as an input parameter.
     * An IMakePlayer factory is returned.
     * @param className Fully qualified class name for an INextMoveStrategy subclass.
     * @return IMakePlayer object that can construct a new Computer player with the supplied strategy.
     */
    public Object addPlayer(final String className) {
        final INextMoveStrategy strategy;
        try {
            java.lang.reflect.Constructor c = Class.forName(className).getConstructors()[0];
            Object[][] args = new Object[][]{new Object[]{}, new Object[]{this}};
            strategy = (INextMoveStrategy) c.newInstance(args[c.getParameterTypes().length]);
        } catch (Exception ex) {
            iCommand.setMessage(ex.toString());
            return null;
        }
        iCommand.setMessage("");
        return new IMakePlayer() {
            public APlayer create(int playerNo) {
                return new ComputerPlayer(requestor, playerNo, GameModel.this, strategy);
            }

            public String toString() {
                return className;
            }
        };
    }

    //    /**
    //     * Get the time left for the current player's turn in milliseconds.
    //     * Return value is undefined if no player is currently taking their turn.
    //     * @return
    //     */
    //    public long getTimeLeft() {
    //        return turnControl.getTimeLeft();
    //    }
    //

}


