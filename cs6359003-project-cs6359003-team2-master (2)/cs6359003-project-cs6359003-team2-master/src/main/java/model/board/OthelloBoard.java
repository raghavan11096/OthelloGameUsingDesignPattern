package main.java.model.board;

import main.java.Othello.Team2.RandomMoveStrategy;
import main.java.model.move.IBoardStatusVisitor;
import main.java.model.move.ICheckMoveVisitor;
import main.java.model.move.IUndoMove;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.Scanner;
import java.util.Set;

public class OthelloBoard extends ABoardModel {
    RandomMoveStrategy randomMoveStrategy = new RandomMoveStrategy();
    ArrayList<int[]> star_positions;
    Point move = new Point(-1, -1);

    OthelloBoard(char[][] cells) {
        super(8, 8);
        this.cells = cells;
    }


    public class Point extends main.java.model.utility.Point {
        int row, col;

        Point(int row, int col) {
            super(row, col);
            this.row = row;
            this.col = col;
        }

        @Override
        public String toString() {
            return "[" + row + ", " + col + "]";
        }

        @Override
        public boolean equals(Object o) {
            return o.hashCode() == this.hashCode();
        }

        //        @Override
        public int hashCode() {
            return Integer.parseInt(row + "" + col);
        }
    }

    public final char[][] cells;
    int whiteScore, blackScore, movesRemaining;
    public final char[] columnAlphabets = new char[]{'A', 'B', 'C', 'D', 'E', 'F', 'G', 'H'};

    public OthelloBoard(int row, int col) {
        super(8, 8);
        /**
         * The initial board values are already set in the cells in a 8X8 Othello board with two black and white balls diagonally
         * placed to each other.
         */
        cells = new char[][]{
                {'_', '_', '_', '_', '_', '_', '_', '_'},
                {'_', '_', '_', '_', '_', '_', '_', '_'},
                {'_', '_', '_', '_', '_', '_', '_', '_'},
                {'_', '_', '_', '○', '●', '_', '_', '_'},
                {'_', '_', '_', '●', '○', '_', '_', '_'},
                {'_', '_', '_', '_', '_', '_', '_', '_'},
                {'_', '_', '_', '_', '_', '_', '_', '_'},
                {'_', '_', '_', '_', '_', '_', '_', '_'}
        };
    }

    private void ValidLocations(char player, char opponent, HashSet<Point> validPositions) {
        /**
         * Checks for locations where the moves can be placed pertaining to the game rules
         */

        for (int row = 0; row < 8; ++row) {
            for (int column = 0; column < 8; ++column) {
                if (cells[row][column] == opponent) {
                    int player_RowValue = row, player_ColumnValue = column;
                    if (row - 1 >= 0 && column - 1 >= 0 && cells[row - 1][column - 1] == '_') {
                        row = row + 1;
                        column = column + 1;
                        while (row < 7 && column < 7 && cells[row][column] == opponent) {
                            row++;
                            column++;
                        }
                        if (row <= 7 && column <= 7 && cells[row][column] == player)
                            validPositions.add(new Point(player_RowValue - 1, player_ColumnValue - 1));
                        /**
                         * Valid Positions are added after every move is performed by the opponent player.
                         */
                    }
                    row = player_RowValue;
                    column = player_ColumnValue;
                    if (row - 1 >= 0 && cells[row - 1][column] == '_') {
                        row = row + 1;
                        while (row < 7 && cells[row][column] == opponent) row++;
                        if (row <= 7 && cells[row][column] == player)
                            validPositions.add(new Point(player_RowValue - 1, player_ColumnValue));
                    }
                    row = player_RowValue;
                    if (row - 1 >= 0 && column + 1 <= 7 && cells[row - 1][column + 1] == '_') {
                        row = row + 1;
                        column = column - 1;
                        while (row < 7 && column > 0 && cells[row][column] == opponent) {
                            row++;
                            column--;
                        }
                        if (row <= 7 && column >= 0 && cells[row][column] == player)
                            validPositions.add(new Point(player_RowValue - 1, player_ColumnValue + 1));
                    }
                    row = player_RowValue;
                    column = player_ColumnValue;
                    if (column - 1 >= 0 && cells[row][column - 1] == '_') {
                        column = column + 1;
                        while (column < 7 && cells[row][column] == opponent) column++;
                        if (column <= 7 && cells[row][column] == player)
                            validPositions.add(new Point(player_RowValue, player_ColumnValue - 1));
                    }
                    column = player_ColumnValue;
                    if (column + 1 <= 7 && cells[row][column + 1] == '_') {
                        column = column - 1;
                        while (column > 0 && cells[row][column] == opponent) column--;
                        if (column >= 0 && cells[row][column] == player)
                            validPositions.add(new Point(player_RowValue, player_ColumnValue + 1));
                    }
                    column = player_ColumnValue;
                    if (row + 1 <= 7 && column - 1 >= 0 && cells[row + 1][column - 1] == '_') {
                        row = row - 1;
                        column = column + 1;
                        while (row > 0 && column < 7 && cells[row][column] == opponent) {
                            row--;
                            column++;
                        }
                        if (row >= 0 && column <= 7 && cells[row][column] == player)
                            validPositions.add(new Point(player_RowValue + 1, player_ColumnValue - 1));
                    }
                    row = player_RowValue;
                    column = player_ColumnValue;
                    if (row + 1 <= 7 && cells[row + 1][column] == '_') {
                        row = row - 1;
                        while (row > 0 && cells[row][column] == opponent) row--;
                        if (row >= 0 && cells[row][column] == player)
                            validPositions.add(new Point(player_RowValue + 1, player_ColumnValue));
                    }
                    row = player_RowValue;
                    if (row + 1 <= 7 && column + 1 <= 7 && cells[row + 1][column + 1] == '_') {
                        row = row - 1;
                        column = column - 1;
                        while (row > 0 && column > 0 && cells[row][column] == opponent) {
                            row--;
                            column--;
                        }
                        if (row >= 0 && column >= 0 && cells[row][column] == player)
                            validPositions.add(new Point(player_RowValue + 1, player_ColumnValue + 1));
                    }
                    row = player_RowValue;
                    column = player_ColumnValue;
                }
            }
        }
    }

    public void showOthelloBoard(OthelloBoard b) {
        /**
         * Othello Board is displayed after every move is performed.
         */
        System.out.print("\n  ");
        for (int row = 0; row < 8; ++row) System.out.print(columnAlphabets[row] + " ");
        System.out.println();
        for (int row = 0; row < 8; ++row) {
            System.out.print((row + 1) + " ");
            for (int column = 0; column < 8; ++column)
                System.out.print(b.cells[row][column] + " ");
            System.out.println();
        }
        System.out.println();
    }

    public int gameResult(Set<Point> whiteValidLocations, Set<Point> blackValidLocations) {
        /**
         * Final result is populated and is checked after every move in game is performed
         */
        scoreUpdate();

        if (movesRemaining == 0) {
            if (whiteScore > blackScore) return 1;
            else if (blackScore > whiteScore) return -1;
            else return 0;
        }
        if (whiteScore == 0 || blackScore == 0) {
            if (whiteScore > 0) return 1;
            else if (blackScore > 0) return -1;
        }
        if (whiteValidLocations.isEmpty() && blackValidLocations.isEmpty()) {
            if (whiteScore > blackScore) return 1;
            else if (blackScore > whiteScore) return -1;
            else return 0; //Draw
        }
        return -2;
    }

    public HashSet<Point> generateValidLocations(char player, char opponent) {
        /**
         * Valid locations are generated, where the user/computer can place a move.
         */
        HashSet<Point> validPositions = new HashSet<>();
        ValidLocations(player, opponent, validPositions);
        return validPositions;
    }


    public void showValidLocations(HashSet<Point> locations, char player, char opponent) {
        /**
         * Displaying Valid Locations where user can place a move.
         */
        for (Point p : locations)
            cells[p.row][p.col] = '?';
        showOthelloBoard(this);
        for (Point p : locations)
            cells[p.row][p.col] = '_';
    }


    public void makeMove(Point p, char player, char opponent) {
        /**
         * The computation is performed for every move, where it is checked whether every move performed are legal are not
         * and the legal moves are added to the board.
         */
        int row = p.row, column = p.col;
        cells[row][column] = player;
        int player_RowValue = row, player_ColumnValue = column;

        if (row - 1 >= 0 && column - 1 >= 0 && cells[row - 1][column - 1] == opponent) {
            row = row - 1;
            column = column - 1;
            while (row > 0 && column > 0 && cells[row][column] == opponent) {
                row--;
                column--;
            }
            if (row >= 0 && column >= 0 && cells[row][column] == player) {
                while (row != player_RowValue - 1 && column != player_ColumnValue - 1) cells[++row][++column] = player;
            }
        }
        row = player_RowValue;
        column = player_ColumnValue;
        if (row - 1 >= 0 && cells[row - 1][column] == opponent) {
            row = row - 1;
            while (row > 0 && cells[row][column] == opponent) row--;
            if (row >= 0 && cells[row][column] == player) {
                while (row != player_RowValue - 1) cells[++row][column] = player;
            }
        }
        row = player_RowValue;
        if (row - 1 >= 0 && column + 1 <= 7 && cells[row - 1][column + 1] == opponent) {
            row = row - 1;
            column = column + 1;
            while (row > 0 && column < 7 && cells[row][column] == opponent) {
                row--;
                column++;
            }
            if (row >= 0 && column <= 7 && cells[row][column] == player) {
                while (row != player_RowValue - 1 && column != player_ColumnValue + 1) cells[++row][--column] = player;
            }
        }
        row = player_RowValue;
        column = player_ColumnValue;
        if (column - 1 >= 0 && cells[row][column - 1] == opponent) {
            column = column - 1;
            while (column > 0 && cells[row][column] == opponent) column--;
            if (column >= 0 && cells[row][column] == player) {
                while (column != player_ColumnValue - 1) cells[row][++column] = player;
            }
        }
        column = player_ColumnValue;
        if (column + 1 <= 7 && cells[row][column + 1] == opponent) {
            column = column + 1;
            while (column < 7 && cells[row][column] == opponent) column++;
            if (column <= 7 && cells[row][column] == player) {
                while (column != player_ColumnValue + 1) cells[row][--column] = player;
            }
        }
        column = player_ColumnValue;
        if (row + 1 <= 7 && column - 1 >= 0 && cells[row + 1][column - 1] == opponent) {
            row = row + 1;
            column = column - 1;
            while (row < 7 && column > 0 && cells[row][column] == opponent) {
                row++;
                column--;
            }
            if (row <= 7 && column >= 0 && cells[row][column] == player) {
                while (row != player_RowValue + 1 && column != player_ColumnValue - 1) cells[--row][++column] = player;
            }
        }
        row = player_RowValue;
        column = player_ColumnValue;
        if (row + 1 <= 7 && cells[row + 1][column] == opponent) {
            row = row + 1;
            while (row < 7 && cells[row][column] == opponent) row++;
            if (row <= 7 && cells[row][column] == player) {
                while (row != player_RowValue + 1) cells[--row][column] = player;
            }
        }
        row = player_RowValue;

        if (row + 1 <= 7 && column + 1 <= 7 && cells[row + 1][column + 1] == opponent) {
            row = row + 1;
            column = column + 1;
            while (row < 7 && column < 7 && cells[row][column] == opponent) {
                row++;
                column++;
            }
            if (row <= 7 && column <= 7 && cells[row][column] == player)
                while (row != player_RowValue + 1 && column != player_ColumnValue + 1) cells[--row][--column] = player;
        }
    }

    @Override
    public IUndoMove makeMove(int p0, int p1, int p2, ICheckMoveVisitor p3, IBoardStatusVisitor p4) {

        return null;
    }

    @Override
    public boolean isValidMove(int p0, int p1, int p2) {
        return false;
    }

    public void scoreUpdate() {
        /**
         * Live score is displayed after every move.
         */
        whiteScore = 0;
        blackScore = 0;
        movesRemaining = 0;
        for (int row = 0; row < 8; ++row) {
            for (int column = 0; column < 8; ++column) {
                if (cells[row][column] == '○') whiteScore++;
                else if (cells[row][column] == '●') blackScore++;
                else movesRemaining++;
            }
        }
    }

    public int columnMatcher(char val) {
        /**
         * Matches the column value with alphabets with better user understanding.
         */

        for (int row = 0; row < 8; ++row)
            if (columnAlphabets[row] == Character.toLowerCase(row) || columnAlphabets[row] == Character.toUpperCase(val))
                return row;
        /**
         * Illegal move.
         */
        return -1;

    }


    public void showWhite() {
        /**
         * Shows the list of white's placeable moves.
         */

        HashSet<Point> whiteValidLocations = generateValidLocations('○', '●');
        showValidLocations(whiteValidLocations, '○', '●');
    }

    public void showBlack() {
        /**
         * Shows the list of black's placeable moves
         */
        HashSet<Point> blackValidLocations = generateValidLocations('●', '○');
        showValidLocations(blackValidLocations, '●', '○');


    }

    public void blackPlayer() {
        /**
         * black player moves are initialized and passed to make move function.
         */
        Scanner scan = new Scanner(System.in);
        int result;
        Boolean skip;
        String input;


        skip = false;
//while(row==0){
        HashSet<Point> blackValidLocations = generateValidLocations('●', '○');
        HashSet<Point> whiteValidLocations = generateValidLocations('○', '●');

        showValidLocations(blackValidLocations, '●', '○');
        result = gameResult(whiteValidLocations, blackValidLocations);

        if (result == 0) {
            System.out.println("It is a draw.");
            chgState(0);
            System.exit(0);
        } else if (result == 1) {
            System.out.println("White wins: " + whiteScore + ":" + blackScore);
            chgState(1);
            System.exit(0);
        } else if (result == -1) {
            System.out.println("Black wins: " + blackScore + ":" + whiteScore);
            chgState(-1);
            System.exit(0);
        }

        if (blackValidLocations.isEmpty()) {
            System.out.println("Black needs to skip... Passing to white");
            skip = true;
        }

        if (!skip) {
            Object[] array_BlackLocations = blackValidLocations.toArray();


            /**
             * Random move strategy is used.
             */
            String randomGeneratedValue = randomMoveStrategy.nextMove(array_BlackLocations);
            final char[] cells = new char[]{'a', 'b', 'c', 'd', 'e', 'f', 'g', 'h'};


            int integralColumnValue = Character.getNumericValue(randomGeneratedValue.charAt(1)) + 1;
            String col = "" + integralColumnValue;
            int value = Character.getNumericValue(randomGeneratedValue.charAt(0));


            input = cells[value] + col;

            move.col = columnMatcher(input.charAt(0));
            move.row = (Integer.parseInt(input.charAt(1) + "") - 1);

            makeMove(move, '●', '○');
            scoreUpdate();


            System.out.println("\nBlack: " + blackScore + " White: " + whiteScore);
        }

    }


    public void whitePlayer(String input) {
        Scanner scan = new Scanner(System.in);


        int result;
        Boolean skip;



        skip = false;

        HashSet<Point> whiteValidLocations = generateValidLocations('○', '●');
        HashSet<Point> blackValidLocations = generateValidLocations('●', '○');

        result = gameResult(whiteValidLocations, blackValidLocations);

        if (result == 0) {
            System.out.println("It is a draw.");
            chgState(0);
            System.exit(0);
        } else if (result == 1) {
            System.out.println("White wins: " + whiteScore + ":" + blackScore);
            chgState(1);
            System.exit(0);
        } else if (result == -1) {
            System.out.println("Black wins: " + blackScore + ":" + whiteScore);
            chgState(-1);
            System.exit(0);
        }

        if (whiteValidLocations.isEmpty()) {
            System.out.println("Skip Performed");
            skip = true;
        }

        if (!skip) {

            move.col =
                    columnMatcher(input.charAt(0));
            move.row =
                    (Integer.parseInt(input.charAt(1) + "") - 1);

            showValidLocations(whiteValidLocations, '○', '●');
            while (!whiteValidLocations.contains(move)) {
                System.out.println("Invalid!!!Please select the valid location to perform the move!\n\nEnter the location of the white Ball to place (The format is the column alphabet followed by row value on the locations where the '?'  is present in the board. Example: a5): ");
                input = scan.next();
                move.col = columnMatcher(input.charAt(0));
                move.row = (Integer.parseInt(input.charAt(1) + "") - 1);
            }

            makeMove(move, '○', '●');
            scoreUpdate();
            System.out.println("\nWhite: " + whiteScore + " Black: " + blackScore);
        }

    }


}
