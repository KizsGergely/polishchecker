package draught;

import java.util.ArrayList;
import java.sql.ClientInfoStatus;
import java.util.Scanner;

public class Game {
    public Scanner input = new Scanner(System.in);
    public int player;
    private Board board;
    final String alphabet = "ABCDEFGHIJKLMONPQRSTUVWXYZ";

    public Game() {
        this.player = 1;
    }

    public Board getBoard(){
        return this.board;
    }
    public void start() {
        board = new Board();
        board.printBoard();
    }

    public void playRound() {
        System.out.println(this.player + "'s move");
        int[] startPosition = null;
        int[] endPosition = null;
        boolean isValid = false;
        while (startPosition == null || endPosition == null || !isValid) {
            System.out.println("Enter starting coordinates:");
            String start = input.nextLine();
            System.out.println("Enter target coordinates:");
            String target = input.nextLine();
            startPosition = convertPlacement(start);
            endPosition = convertPlacement(target);
            //check if move is valid,
        }
        board.movePawn(startPosition[0], startPosition[1], endPosition[0],endPosition[1]);
//        checkForWinner();
        swapPlayer();
    }

    private void swapPlayer() {
        if (this.player == 1){
            this.player = 2;
        }
        else {
            this.player = 1;
        }
    }

    public void tryToMakeMove() {

    }

    public int[] choosePawn() {
        System.out.println("Choose your pawn to move: ");
        String coordinate = input.nextLine();
        int row = convertPlacement(coordinate)[0];
        int col = convertPlacement(coordinate)[1];
        return new int[]{row, col};
    }

    public int[] convertPlacement(String coordinate) {
        try {
            char[] alphabet = "abcdefghijklmnopqrstuvwxyz".toCharArray();
            String letter = coordinate.substring(0, 1).toLowerCase();
            int row = new String(alphabet).indexOf(letter);
            int col = Integer.parseInt(coordinate.substring(1)) - 1;
            return new int[]{row, col};
        }
        catch (NumberFormatException e){
            return null;
        }
    }

    public boolean checkForWinner(Pawn[][] fields, int player) {
        int counter = 0;
        for (Pawn[] row : fields) {
            for (Pawn col : row) {
                if (player == 1 && col.isWhite) {
                    counter++;
                } else if (player == 2 && !col.isWhite) {
                    counter++;
                }
            }
        }  //counter only checks for number of pieces, return true is there is none
        return counter == 0 || validMoves(fields, player).size() == 0; //validMove's size indicates the number of possible steps
    }

    public int[] isItOnBoard(int x, int y) {
        int[] res = new int[2];
        try {
            if (board.fields[x][y] == null) {
                res[0] = x;
                res[1] = y;
                return res;}
        } catch (IndexOutOfBoundsException e) {
            return null;
        }  return null;
    }

    public ArrayList validMoves(Pawn[][] fields, int player) {
        //TODO: loop over each player's pawns
        //check the 4 diagonal squares for each pawn
        //check for possible capture moves
        ArrayList<int[]> valid = new ArrayList<>();

        for (int i = 0; i < fields.length; i++) {
            for (int j = 0; j < fields[i].length; j++) {

                if (fields[i][j] != null && fields[i][j].whichPlayer() == player) {
                   if (isItOnBoard(i-1,j-1) != null) {
                       valid.add(isItOnBoard(i-1,j-1));
                    } if (isItOnBoard(i-1,j+1) != null) {
                        valid.add(isItOnBoard(i-1,j+1));
                    } if (isItOnBoard(i+1,j-1) != null) {
                        valid.add(isItOnBoard(i+1,j-1));
                    } if (isItOnBoard(i+1,j+1) != null) {
                        valid.add(isItOnBoard(i+1,j+1));
                    }
                }
            }
        }
        return valid;
    }


    public static void main(String[] args) {

        Game game = new Game();
        game.start();
        System.out.println(game.validMoves(game.getBoard().fields, game.player).size());

//        while (!checkForWinner(fields, game.player)) {
//            game.playRound();
//
    }

}
