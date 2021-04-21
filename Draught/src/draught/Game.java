package draught;

import java.util.ArrayList;
import java.util.Arrays;
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
        this.printBoard();
        playRound();
    }

    public void playRound() {
        System.out.println(this.player + "'s move");
//        int[] startPosition;
        int[] endPosition;
//        boolean isValid = false;
//        while (startPosition == null || endPosition == null) {
        while (!checkForWinner(this.board.fields, this.player)) {
            System.out.println("Enter starting coordinate :");
            String start = input.nextLine();
            while (!inputCheck(start)) {
                System.out.println("Please choose valid starting coordinate: ");
                start = input.nextLine();
            }
            System.out.println("Enter target coordinate: ");
            String target = input.nextLine();
            while (!inputCheck(target)) {
                System.out.println("Enter a valid target coordinate: ");
                target = input.nextLine();
            }
            endPosition = convertPlacement(target);
            //check if move is valid,
//        }
            board.movePawn(this.startPosition[0], startPosition[1], endPosition[0], endPosition[1]);
            if (captureChecker(startPosition[0], startPosition[1], endPosition[0], endPosition[1])) {
                this.board.fields[this.capturedTile[0]][this.capturedTile[1]] = null;
            }
            this.printBoard();
//        checkForWinner();
            swapPlayer();
        }

        //TODO: b3 -> 1, 2  [i][j]   ||  a2 -> 0,1 -- || a4 -> 0,3 -+  || c2 -> 2, 1 +-   || c4 -> 2,3 ++
        //TODO: m4 -> 12, 3 [i][j]   ||  l3 -> 11, 2 -- || l5 -> 11, 4 -+ || n3 -> 13, 2 +- || n5 -> 13, 4 ++ 14, 5 ++++
        //TODO:
    }

    public void removeCaptured() {

    }

    public boolean captureChecker(int fromX, int fromY, int toX, int toY) {
        //TODO: check if target coordinate distance is 2 diagonal from starting coordinate && check if the middle spot has an enemy pawn there
        int[] capturedTile = getInts(fromX, fromY, toX, toY);
        if (((this.player == 1) && this.board.fields[capturedTile[0]][capturedTile[1]].isWhite) && this.board.fields[capturedTile[0]][capturedTile[1]] != null
                && Math.abs(fromX - toX) == 2 && Math.abs(fromY - toY) == 2) {
                return true;
            } else if (((this.player == 2) && !this.board.fields[capturedTile[0]][capturedTile[1]].isWhite) && this.board.fields[capturedTile[0]][capturedTile[1]] != null
                && Math.abs(fromX - toX) == 2 && Math.abs(fromY - toY) == 2) {
                return true;
            }
        return false;

    }

    private int[] getInts(int fromX, int fromY, int toX, int toY) {
        if (fromX > toX) {
            this.capturedTile[0] = fromX - 1;
        } else {
            this.capturedTile[0] = fromX + 1;
        }
        if (fromY > toY) {
            this.capturedTile[1] = fromY - 1;
        } else {
            this.capturedTile[1] = fromY + 1;
        }
        return this.capturedTile;
    }

    private boolean inputCheck(String input) {
        return input.length() > 1 && input.matches("\\D\\d");
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

    public void highlightPawn(int[] position) {
        String symbol;
        if (this.player == 1) {
            symbol = " X ";
        } else {
            symbol = " O ";
        }
        this.highlightedPawn = Color.CYAN_BRIGHT + symbol + Color.RESET;

    }

    public int[] convertPlacement(String coordinate) {
        try {
            char[] alphabet = "abcdefghijklmnopqrstuvwxyz".toCharArray();
            String letter = coordinate.substring(0, 1);
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
                if (col != null) {
                    if (player == 1 && col.isWhite) {
                        counter++;
                    } else if (player == 2 && !col.isWhite) {
                        counter++;
                    }
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
//        System.out.println(game.validMoves(game.getBoard().fields, game.player).size());

//        while (!checkForWinner(fields, game.player)) {
//            game.playRound();
//
    }

}
