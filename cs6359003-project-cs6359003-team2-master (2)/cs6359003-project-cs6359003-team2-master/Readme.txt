“As a Comet, I pledge honesty, integrity, and service in all that I do.”
-------------------------------------------------------------------------
CS6359.003 Object-Oriented Analysis and Design
------------------------------------------------------------
                NAME                             NET ID
PROJECT TEAM 2- RAGHAVAN SIVAKUMAR               RXS190048
                SHUBHAM DHINGRA                  SXD190070
                VAISHNAVI REDDY JUNNUTULA        VXJ190000
---------------------------------------------------------------

DESIGN PATTERN FOR BOARD GAMES- OTHELLO
-----------------------------------------

Othello is an 8 X 8-dimensional board game, 64 pieces of white discs on one side, and black discs on the other side.

Before the game starts, four discs are arranged in the center, diagonal to each other. 

The discs can be moved vertically, horizontally, and diagonally. 

The game rules are well explained in the following link which was used as a reference to design the game.
(https://bonaludo.com/2016/02/18/reversi-and-othello-two-different-games-do-you-know-their-different-rules/)

HOW TO RUN THE APPLICATION
-----------------------------

Clone or Download the project from the following link,
https://github.com/UTDClassroom/cs6359003-project-cs6359003-team2

Open the command prompt and type the following command:- javac OthelloController.java

HOW TO PLAY THE GAME
-----------------------

* Once the application is started, the computer player i.e Black player generates its first move,
  the black colored balls/discs are populated on the updated board.

* The '?' symbol is shown on the board, where the user i.e White player can perform his moves.

* Those are the locations which are the places where the user can place, 
  if placed not in those locations, the system throws an error message and facilitates the user to undo its previous move.

* The moves are performed by entering the column's alphabet row number together to make a move, otherwise, the system doesn't recognize.
  For Example. To perform a move on the first column and 3rd row, the user has to give the value like "a3" or "A3".

* The score is updated and printed after each move, updating the user.

* When there is no '?' printed, which means the user can skip the move just by giving any value in a valid format.

* The winner is finally displayed and the game terminates.

DESIGN DECISIONS/ASSUMPTIONS
-------------------------------

* The two-player game involves gameplay between a computer player and a human player, the computer player is assumed to play the first move in the application.

* The computer player is named as a Black player which is written separately by extending APlayer.java and similarly, the White player is written in a separate class,
  by extending the HumanPlayer.java.

* OthelloController.java is created as a controller/facilitator which is the driver class that interacts with the UI and the Othello Board.

* The functionalities of the Othello Board and game rules are encapsulated in OthelloBoard.java, where the makeMove method is used to perform moves.

* Othello UI is developed for displaying the board to the user, the setIViewRequestor method is used for accepting the input from White Player i.e Human player.

* Random move generation is done in the RandomMoveStrategy.java where the moves are generated based on the placeable locations for the computer player,
  thus performing the random move as well as a random valid move. 

* Most of the functions do not return anything, thus writing the test code for them has been restricted.








