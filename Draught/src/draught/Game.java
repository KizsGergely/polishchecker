package draught;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Scanner;

public class Game {
    public Scanner input = new Scanner(System.in);
    public int player;
    private Board board;
    final String alphabet = "ABCDEFGHIJKLMONPQRSTUVWXYZ";
    public int[] capturedTile = new int[2];
    public String highlightedPawn;
    public int[] startPosition = new int[2];

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

    public String choosePawn() {
        System.out.println("Player " + this.player + ", choose your pawn to move :");
        String start = input.nextLine();
        while (!inputCheck(start) || !pieceCheck(start)) {
            System.out.println("Please choose valid starting coordinate: ");
            start = input.nextLine();
        } return start;
    }

    public String chooseTarget(String start) {
        System.out.println("Player " + this.player + ", choose your target coordinate: ");
        String target = input.nextLine();
        while (!inputCheck(target) && !pieceCheck(start)) {
            System.out.println("Please enter a valid target coordinate: ");
            target = input.nextLine();
        } return target;
    }

    private int[] stepValidator() {
        String start = this.choosePawn();
        this.startPosition = convertPlacement(start);
        this.highlightPawn(this.startPosition);
        this.printBoard();
        String target = this.chooseTarget(start);
        int [] endPosition = convertPlacement(target);
        this.highlightedPawn = null;
        while (!this.board.isItEmpty(endPosition[0], endPosition[1])) {
            printBoard();
            System.out.println("Impossible step. Please revise your decision. ");
            start = this.choosePawn();
            this.startPosition = convertPlacement(start);
            this.highlightPawn(this.startPosition);
            printBoard();
            target = this.chooseTarget(start);
            endPosition = convertPlacement(target);
            this.highlightedPawn = null;
        } return endPosition;
    }

    private int stepDistance(int[] start, int[] end) {
        System.out.println("start:" + Arrays.toString(start));
        System.out.println("end: " + Arrays.toString(end));
        return Math.abs(start[0] - end[0]);
    }

    public void playRound() {
        int[] endPosition;
        System.out.println(this.player + "'s move");
        while (true) {
            endPosition = this.stepValidator();
            //check if move is valid,
//        }
            this.board.movePawn(this.startPosition[0], this.startPosition[1], endPosition[0], endPosition[1]);
            int distance = this.stepDistance(this.startPosition, endPosition);
            if (distance > 1) {
                System.out.println("distance: " + distance);
                if (captureChecker(this.startPosition[0], this.startPosition[1], endPosition[0], endPosition[1])) {
                    removeCaptured();
                }
            }
            this.printBoard();
            if (checkForWinner(this.board.fields, this.player)) {
                System.out.println("Player " + this.player + " has won! But life is meaningless anyway. Enjoy yourself.");
                break;
            }
            swapPlayer();
        }
    }

    public void removeCaptured() {
        this.board.fields[this.capturedTile[0]][this.capturedTile[1]] = null;
    }

    public boolean captureChecker(int fromX, int fromY, int toX, int toY) {
        //check if target coordinate's distance is 2 diagonal from starting coordinate && check if the middle spot has an enemy pawn there
        int[] capturedTile = getInts(fromX, fromY, toX, toY);
        if (((this.player == 1) && this.board.fields[capturedTile[0]][capturedTile[1]].isWhite) && this.board.fields[capturedTile[0]][capturedTile[1]] != null
                && Math.abs(fromX - toX) == 2 && Math.abs(fromY - toY) == 2) {
            return true;
        } else return ((this.player == 2) && !this.board.fields[capturedTile[0]][capturedTile[1]].isWhite) && this.board.fields[capturedTile[0]][capturedTile[1]] != null
                && Math.abs(fromX - toX) == 2 && Math.abs(fromY - toY) == 2;

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

    private boolean pieceCheck(String input) {
        try {
            int row = convertPlacement(input)[0];
            int col = convertPlacement(input)[1];
            if (board.fields[row][col] != null )  {
                return board.fields[row][col].player != this.player;}
        } catch (ArrayIndexOutOfBoundsException e) {
            return false;
        } return false;
    }

    private boolean inputCheck(String input) {
        if (input.length() == 2) {
        return input.matches("\\D\\d");}
        else if (input.length() == 3) {
            return input.matches("\\D\\d\\d");
        } return false;
        }

    private void swapPlayer() {
        if (this.player == 1){
            this.player = 2;
        }
        else {
            this.player = 1;
        }
    }

        public void printBoard() {
        for (int i = 0; i < board.fields[0].length + 1; i++) {
            if (i == 0) {
                System.out.print("   ");
            } else {
                if (i > 9) {
                    System.out.print(i + " ");
                } else {
                    System.out.print(i + "  ");
                }
            }
        }
        System.out.println();
        for (int i = 0; i < board.fields.length; i++) {
            System.out.print(alphabet.charAt(i) + " ");
            for (int j = 0; j < board.fields[i].length; j++) {
                if (board.fields[i][j] == null) { //empty white & black tiles
                    if ((i + j) % 2 == 0) {
                        System.out.print(" - ");
                    } else {
                        System.out.print("   ");
                    }
                } else { //this is the place where Pawn objects got printed
                    if (i == this.startPosition[0] && j == this.startPosition[1]) {
                        if (this.highlightedPawn != null) {
                            System.out.print(this.highlightedPawn);
                        } else {
                            System.out.print(" O "); //TODO: hard coded, need to fix this!
                        }
                    }
                    else {
                        if (board.fields[i][j].isWhite && !board.fields[i][j].isCrowned && !isItHighlighted(i, j)) {
                            System.out.print(" X ");
                        } else if (!board.fields[i][j].isWhite && !board.fields[i][j].isCrowned && !isItHighlighted(i, j)) {
                            System.out.print(" O ");
                        }}
                }
            }
            System.out.println();
        }
    }

    public void tryToMakeMove() {

    }

    public boolean isItHighlighted(int i, int j) {
        return i == this.startPosition[0] && j == this.startPosition[1];
    }

    public void highlightPawn(int[] position) {
        String symbol;
        if (this.player == 1) {
            symbol = " O ";
        } else {
            symbol = " X ";
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
        //loop over each player's pawns
        //check the 4 diagonal squares for each pawn (if on board)
        //TODO: check for possible capture moves
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
                    }if (isItOnBoard(i-2,j-2) != null) {
                        valid.add(isItOnBoard(i-2,j-2));
                    } if (isItOnBoard(i-2,j+2) != null) {
                        valid.add(isItOnBoard(i-2,j+2));
                    } if (isItOnBoard(i+1,j-2) != null) {
                        valid.add(isItOnBoard(i+2,j-2));
                    } if (isItOnBoard(i+1,j+2) != null) {
                        valid.add(isItOnBoard(i+2,j+2));
                    }
                }
            }
        }
        return valid; //returning with an arraylist indicating possible CAPTURE & NON-CAPTURE moves count
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
