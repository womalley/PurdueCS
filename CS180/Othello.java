
/**
 * Project 2
 *
 * @author William O'Malley, womalley@purdue.edu
 * @version 1.0
 * Created by bomal_000 on 7/8/2016.
 */
public class Othello {

    public static final int NONE = 0;
    public static final int BLACK = 1;
    public static final int WHITE = 2;

    public int[][] board;

    /**
     * The Constructor for BlackJack
     * @param length for the width and height of the game table
     */
    public Othello(int length) {

        this.board = new int[length][length];

        //Fills board with NONE
        for (int i = 0; i < board.length; i++) {
            for (int k = 0; k < board[i].length; k++) {
                this.board[i][k] = NONE;
            }
        }

        //top left disk
        this.board[(length / 2) - 1][(length / 2) - 1] = WHITE;

        //top right disk
        this.board[(length / 2) - 1][(length / 2)] = BLACK;

        //bottom left disk
        this.board[(length / 2)][(length / 2) - 1] = BLACK;

        //bottom right disk
        this.board[(length / 2)][(length / 2)] = WHITE;
    }

    /**
     * Checks to see if the move is valid
     * @param row holds the value for row
     * @param col holds the value for the column
     * @param player holds the value that determines which player it is
     * @return returns whether the spot is valid
     */
    public boolean isValid(int row, int col, int player) {

        int opp = (player == 1 ? 2 : 1);


        //returns false if the specified row or column is off of the array (out of bounds)
        if (row >= board.length || col >= board.length) {
            return false;
        }

        //returns false if the board position has a disk already on it
        if (board[row][col] == WHITE || board[row][col] == BLACK) {
            return false;
        }

        boolean valid = false;
        boolean piece = false;

        //ORIGINAL TEST CODE
//        int opp = (player == 1 ? 2 : 1);
//        int i; //x direction
//        int k; //y direction
//        int horizPosition; //horizontal position
//        int vertPosition; //vertical position
//
//        //returns false if the specified row or column is off of the array (out of bounds)
//        if (row > board.length || col > board.length) {
//            return false;
//        }
//
//        //returns false if the board position has a disk already on it
//        if (board[row][col] == WHITE || board[row][col] == BLACK) {
//            return false;
//        }
//
//
//        //Test code
//        for (i = -1; i < 2; i++) {
//            for (k = -1; k < 2; k++) {
//
//                horizPosition = i + row;
//                vertPosition = k + col;
//
//
//
//                if(horizPosition >= 0 && horizPosition < board.length
//                        && vertPosition >= 0 && vertPosition < board.length){
//
//                    //check if position is opponent
//                    if (board[horizPosition][vertPosition] == opp){
//                        System.out.println("For position: Board[" +row+"][" +col+"], an opponent is located at " +
//                                "Board[" + horizPosition + "][" + vertPosition+"]" );
//                        return true;
//                    }
//                }
//            }
//        }
//        return false;

        //END ORIGINAL TEST CODE

        int nextRow = 0;
        int nextCol = 0;

        //left check
        if (col > 0) {
            piece = false;
            nextCol = col - 1;
            while (board[row][nextCol] == opp) {
                if (nextCol != 0) {
                    nextCol--;
                    piece = true;
                } else {
                    break;
                }
            }
            if (board[row][nextCol] == player && piece) {
                valid = true;
            }
        }

        //right check
        if (col >= 0 && col + 1 < board.length) {
            piece = false;
            nextCol = col + 1;
            while (board[row][nextCol] == opp) {
                if (nextCol != board.length - 1) {
                    nextCol++;
                    piece = true;
                } else {
                    break;
                }
            }
            if (board[row][nextCol] == player && piece) {
                valid = true;
            }
        }

        //up check
        if (row != 0) {
            piece = false;
            nextRow = row - 1;
            while (board[nextRow][col] == opp) {
                if (nextRow != 0) {
                    nextRow--;
                    piece = true;
                } else {
                    break;
                }

                if (board[nextRow][col] == player && piece) {
                    valid = true;
                }
            }
        }

        //down check
        if (row + 1 < board.length) {
            piece = false;
            nextRow = row + 1;
            while (board[nextRow][col] == opp) {
                if (nextRow + 1 != board.length) {
                    nextRow++;
                    piece = true;
                } else {
                    break;
                }
            }
            if (board[nextRow][col] == player && piece) {
                valid = true;
            }
        }

        //upper left check
        if (col != 0 && row != 0) {
            piece = false;
            nextRow = row - 1;
            nextCol = col - 1;
            while (board[nextRow][nextCol] == opp) {
                if (nextRow != 0 && nextCol != 0) {
                    nextRow--;
                    nextCol--;
                    piece = true;
                } else {
                    break;
                }
            }
            if (board[nextRow][nextCol] == player && piece) {
                valid = true;
            }
        }

        //upper right check
        if (col + 1 < board.length && row != 0) {
            piece = false;
            nextRow = row - 1;
            nextCol = col + 1;
            while (board[nextRow][nextCol] == opp) {
                if (nextRow != 0 && nextCol + 1 != board.length) {
                    nextRow--;
                    nextCol++;
                    piece = true;
                } else {
                    break;
                }
            }
            if (board[nextRow][nextCol] == player && piece) {
                valid = true;
            }
        }

        //lower right check
        if (col + 1 < board.length && row + 1 < board.length) {
            piece = false;
            nextRow = row + 1;
            nextCol = col + 1;
            while (board[nextRow][nextCol] == opp) {
                if (nextRow != board.length - 1 && nextCol != board.length - 1) {
                    nextRow++;
                    nextCol++;
                    piece = true;
                } else {
                    break;
                }
            }
            if (board[nextRow][nextCol] == player && piece) {
                valid = true;
            }
        }

        //lower left check
        if (col != 0 && row + 1 < board.length) {
            piece = false;
            nextRow = row + 1;
            nextCol = col - 1;
            while (board[nextRow][nextCol] == opp) {
                if (nextRow != board.length - 1 && nextCol != 0) {
                    nextRow++;
                    nextCol--;
                    piece = true;
                } else {
                    break;
                }
            }
            if (board[nextRow][nextCol] == player && piece) {
                valid = true;
            }
        }
        return valid;
    }

    /**
     * Used to swap the disks that are captured
     * @param originalRow holds the original row
     * @param originalCol holds the original column
     * @param currentRow holds the current row
     * @param currentCol holds the current column
     * @param rowChange holds the row directional value
     * @param colChange holds the column directional value
     * @param player holds the value for what player it currently is
     */
    public void swap(int originalRow, int originalCol, int currentRow, int currentCol, int rowChange, int colChange,
                     int player) {

        board[currentRow][currentCol] = player;
        while (currentRow != originalRow || currentCol != originalCol) {
            currentRow = currentRow + rowChange;
            currentCol = currentCol + colChange;
            if (board[currentRow][currentCol] != player) {
                board[currentRow][currentCol] = player;
            }
        }
    }

    /**
     * Checks to see if the player has a valid move
     * @param player holds the value that determines the player
     * @return returns whether the player has a valid move or not
     */
    public boolean hasValidMoves(int player) {
    //boolean valid = false;
        for (int i = 0; i < board.length; i++) {
            for (int k = 0; k < board[i].length; k++) {
                if (board[i][k] == NONE) {

                    if (player == WHITE) {
                        if (isValid(i, k, player)) {
                            return true;
                        }

                    }

                    if (player == BLACK) {
                        if (isValid(i, k, player)) {
                            return true;
                        }
                    }
                }
            }
        }
        return false;
    }

    /**
     * Allows the player to make a move and change disks that are captured
     * @param row holds the value for row
     * @param col holds the value for the col
     * @param player holds the value that determines player
     */
    public void makeMove(int row, int col, int player) {

        int opp = (player == 1 ? 2 : 1);

        int nextRow = 0;
        int nextCol = 0;

        boolean valid = false;
        boolean piece = false;

        //left check
        if (col > 0) {
            piece = false;
            nextCol = col - 1;
            while (board[row][nextCol] == opp) {
                if (nextCol != 0) {
                    nextCol--;
                    piece = true;
                } else {
                    break;
                }
            }
            if (board[row][nextCol] == player && piece) {
                swap(row, nextCol, row, col, 0, -1, player);
            }
        }

        //right check
        if (col >= 0 && col + 1 < board.length) {
            piece = false;
            nextCol = col + 1;
            while (board[row][nextCol] == opp) {
                if (nextCol != board.length - 1) {
                    nextCol++;
                    piece = true;
                } else {
                    break;
                }
            }
            if (board[row][nextCol] == player && piece) {
                swap(row, nextCol, row, col, 0, 1, player);
            }
        }

        //up check
        if (row != 0) {
            piece = false;
            nextRow = row - 1;
            while (board[nextRow][col] == opp) {
                if (nextRow != 0) {
                    nextRow--;
                    piece = true;
                } else {
                    break;
                }
            }
            if (board[nextRow][col] == player && piece) {
                swap(nextRow, col, row, col, -1, 0, player);
            }
        }

        //down check
        if (row + 1 < board.length) {
            piece = false;
            nextRow = row + 1;
            while (board[nextRow][col] == opp) {
                if (nextRow + 1 != board.length) {
                    nextRow++;
                    piece = true;
                } else {
                    break;
                }
            }
            if (board[nextRow][col] == player && piece) {
                swap(nextRow, col, row, col, 1, 0, player);
            }
        }

        //upper left check
        if (col != 0 && row != 0) {
            piece = false;
            nextRow = row - 1;
            nextCol = col - 1;
            while (board[nextRow][nextCol] == opp) {
                if (nextRow != 0 && nextCol != 0) {
                    nextRow--;
                    nextCol--;
                    piece = true;
                } else {
                    break;
                }
            }
            if (board[nextRow][nextCol] == player && piece) {
                swap(nextRow, nextCol, row, col, -1, -1, player);
            }
        }

        //upper right check
        if (col + 1 < board.length && row != 0) {
            piece = false;
            nextRow = row - 1;
            nextCol = col + 1;
            while (board[nextRow][nextCol] == opp) {
                if (nextRow != 0 && nextCol + 1 != board.length) {
                    nextRow--;
                    nextCol++;
                    piece = true;
                } else {
                    break;
                }
            }
            if (board[nextRow][nextCol] == player && piece) {
                swap(nextRow, nextCol, row, col, -1, 1, player);
            }
        }

        //lower right check
        if (col + 1 < board.length && row + 1 < board.length) {
            piece = false;
            nextRow = row + 1;
            nextCol = col + 1;
            while (board[nextRow][nextCol] == opp) {
                if (nextRow != board.length - 1 && nextCol != board.length - 1) {
                    nextRow++;
                    nextCol++;
                    piece = true;
                } else {
                    break;
                }
            }
            if (board[nextRow][nextCol] == player && piece) {
                swap(nextRow, nextCol, row, col, 1, 1, player);
            }
        }

        //lower left check
        if (col != 0 && row + 1 < board.length) {
            piece = false;
            nextRow = row + 1;
            nextCol = col - 1;
            while (board[nextRow][nextCol] == opp) {
                if (nextRow != board.length - 1 && nextCol != 0) {
                    nextRow++;
                    nextCol--;
                    piece = true;
                } else {
                    break;
                }
            }
            if (board[nextRow][nextCol] == player && piece) {
                swap(nextRow, nextCol, row, col, 1, -1, player);
            }
        }
    }


    /**
     * Displays the game board and updates the game
     */
    public void playGame() {

        //Point counter
        int blackPoints = 0;
        int whitePoints = 0;

        for (int i = 0; i < board.length; i++) {
            for (int k = 0; k < board[i].length; k++) {
                if (board[i][k] == BLACK) {
                    blackPoints++;
                }

                if (board[i][k] == WHITE) {
                    whitePoints++;
                }
            }
        }

        //Determines winner
        if (blackPoints > whitePoints) {
            System.out.println("Color X WINS!");
        }
        else if (whitePoints > blackPoints) {
            System.out.println("Color O WINS!");
        }
        else if (whitePoints == blackPoints) {
            System.out.println("TIE GAME!");
        }
        else {
            System.out.println("ERROR: something went wrong!");
        }

    }

    /**
     * This method prints the board to the console
     * @param turn current turn
     */
    public void printBoard(int turn) {
        int numBlacks = 0;
        int numWhites = 0;
        System.out.println();
        System.out.printf("   ");
        for (int i = 0; i < this.board.length; i++) {
            System.out.printf(" " + i + "  ");
        }
        System.out.printf("\n  ");
        for (int i = 0; i < this.board.length; i++) {
            System.out.printf("----");
        }
        System.out.println();
        for (int i = 0; i < this.board.length; i++) {
            System.out.printf(i + " |");
            for (int j = 0; j < this.board.length; j++) {
                if (this.board[i][j] == WHITE) {
                    System.out.printf(" O |");
                    numWhites++;
                } else if (this.board[i][j] == BLACK) {
                    System.out.printf(" X |");
                    numBlacks++;
                } else if (isValid(i, j, turn)) {
                    System.out.printf(" * |");
                } else {
                    System.out.printf("   |");
                }
            }
            System.out.println();
            System.out.printf("  ");
            for (int j = 0; j < this.board.length; j++) {
                System.out.printf("----");
            }
            System.out.println();

        }
        System.out.println("Black: " + numBlacks + " - " + "White: " + numWhites);
        System.out.println();
    }

    /**
     * Main method to run a round of othello
     * @param args command line parameters
     */
    public static void main(String[] args) {
        // you may change the input to experiment with other boards
        Othello game = new Othello(4);
        game.playGame();
    }


}