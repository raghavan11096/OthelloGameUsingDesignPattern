package main.java.Othello.Team2;

import main.java.gameIO.IView;
import main.java.model.board.OthelloBoard;
import main.java.model.modelToView.ICommand;
import main.java.model.modelToView.IRejectCommand;
import main.java.model.modelToView.IViewManager;
import main.java.model.player.APlayer;
import main.java.model.player.BlackPlayer;
import main.java.model.utility.Dimension;
import main.java.model.viewToModel.IModelManager;
import main.java.model.viewToModel.IViewRequestor;

import java.util.*;

public class OthelloUI implements IView, IViewManager, ICommand {
    APlayer player;
    List<Object> players;
    IModelManager iModelManager;
    static int player_id;
    Dimension size = new Dimension(8);
    private IViewRequestor iViewRequestor;
    static Scanner input = new Scanner(System.in);
    OthelloBoard othelloBoard = new OthelloBoard(8, 8);


    public final char[] columnAlphabets = new char[]{'A', 'B', 'C', 'D', 'E', 'F', 'G', 'H'};
    Scanner scan = new Scanner(System.in);

    @Override
    public ICommand getCommand() {
        return this;
    }


    public static void playerAt(int pid) {

        player_id = pid;

    }

    @Override
    public void setiViewRequestor(IViewRequestor iViewRequestor) {
        /**
         * User input is validated and taken to the board, if the player is white.
         */
        this.iViewRequestor = iViewRequestor;
        String userMoveValue;
        int colValue = 0;
        int rowValue = 0;
        if (player_id == 0) {
            othelloBoard.showBlack();
            othelloBoard.blackPlayer();
            othelloBoard.showWhite();
        } else {
            System.out.println("Enter the location of the white Ball to place (The format is the column alphabet followed by row value on the locations where the '?'  is present in the board. Example: a5): ");
            userMoveValue = scan.next();
            colValue = columnMatcher(userMoveValue.charAt(0));
            rowValue = (Integer.parseInt(userMoveValue.charAt(1) + "") - 1);
            othelloBoard.whitePlayer(userMoveValue);


        }


        this.iViewRequestor.setTokenAt(rowValue, colValue, new IRejectCommand() {


            @Override
            public void execute() {


                System.out.println("Invalid");
            }
        });
    }


    public void setiModelManager(IModelManager iModelManager) {
        this.iModelManager = iModelManager;
    }

    @Override
    public void setPlayers(List<Object> players) {
        /**
         * Assign human player(white player) and computer player(black player) in a list.
         */
        this.players = players;
        List<Object> l = this.iModelManager.getPlayers();
        this.iModelManager.setPlayers(l.get(0), l.get(1));

    }

    @Override
    public void setDimension(Dimension size) {
        this.size = size;


    }

    @Override
    public void setTokenAt(int row, int col, int player) {

if(player_id==0)
    othelloBoard.generateValidLocations('●','○');
else
    othelloBoard.generateValidLocations('○','●');
    }

    @Override
    public void clearTokenAt(int row, int col) {
        othelloBoard.generateValidLocations('_','_');
    }

    @Override
    public void setMessage(String s) {
        System.out.println("Message: " + s);

    }

    @Override
    public void draw() {

        getCommand().setMessage("Its a Draw!");
        this.iModelManager.reset();

    }

    @Override
    public void win(int player) {
        /**
         * Termination message after a player wins.
         */
        getCommand().setMessage("Player" + player_id + "has won!");
        this.iModelManager.reset();

    }

   public void exit() {
        this.iModelManager.exit();
    }

    @Override
    public void reset() {
        /**
         * Resetting is done.
         */
        getCommand().setMessage("Resetting the Game!");
        this.iModelManager.reset();
    }



    public int columnMatcher(char x) {
        /**
         * Matches the column value with alphabets for better understanding for the users.
         */
        for (int i = 0; i < 8; ++i)
            if (columnAlphabets[i] == Character.toLowerCase(x) || columnAlphabets[i] == Character.toUpperCase(x))
                return i;
        /**
         * Illegal move reached
         */
        return -1;
    }


}
