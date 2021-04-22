package draught;

import java.util.Scanner;

public class Board {
    private int n;
    public Pawn[][] fields;
    private final Scanner scanner;
    public static int[] lastMove = new int[2];
    public Board() {
        scanner = new Scanner(System.in);
        System.out.println("Enter board size between 10 and 20: ");
        String input = scanner.nextLine();
        this.n = validN(input);
        if (this.n == 4) {//Win check shortcut for 0 valid steps
            this.fields = new Pawn[n][n];
            fields[3][0] = new Pawn(3, 0, true, 1);
            fields[2][1] = new Pawn(2, 1, false, 2);
            fields[0][3] = new Pawn(0, 3, false, 2);
        } else {
            while (this.n > 20 || this.n < 10) {
                System.out.println("Between 10 and 20: ");
                input = scanner.nextLine();
                this.n = validN(input);
            }
            this.fields = new Pawn[n][n];

//      place black pawns on board
            int count = 0;
            myBreakLabelBlack:
            for (int i = 0; i < fields.length; i++) {
                for (int j = 0; j < fields[0].length; j++) {
                    if ((i + j) % 2 != 0) {
                        fields[i][j] = new Pawn(i, j, false, 2);
                        count++;
                        if (count == n * 2) {
                            break myBreakLabelBlack;
                        }
                    }
                }
            }


//      place white pawns on board
            count = 0;
            myBreakLabelWhite:
            for (int i = fields.length - 1; i >= 0; i--) {
                for (int j = fields[0].length - 1; j >= 0; j--) {
                    if ((i + j) % 2 != 0) {
                        fields[i][j] = new Pawn(i, j, true, 1);
                        count++;
                        if (count == n * 2) {
                            break myBreakLabelWhite;
                        }
                    }
                }
            }
        }
    }

    public int validN(String input) {
        try {
            this.n = Integer.parseInt(input);
        } catch (NumberFormatException e) {
            System.out.println("Between 10 and 20: ");
            input = scanner.nextLine();
            validN(input);
        }
        return n;
    }

    public void removePawn(int x, int y) {
        fields[x][y] = null;
    }

    public void movePawn(int fromX, int fromY, int toX, int toY) {
        Pawn pawn = fields[fromX][fromY];
        if (validateMove(pawn, toX, toY) && isItEmpty(toX, toY)) {
            fields[toX][toY] = pawn;
            removePawn(fromX, fromY); //using method now
            pawn.setPositionX(toX);
            pawn.setPositionY(toY);
        }
    }


    @Override
    public String toString() {
        String alphabet = "ABCDEFGHIJKLMNOPQRSTUVWXYZ";
        StringBuilder builder = new StringBuilder("Game{" +
                ", fields=\n");

        for (int i = 0; i < fields.length; i++) {
            for (int j = 0; j < fields[0].length; j++) {
                builder.append("Row ").append(i + 1).append(", column ").append(alphabet.charAt(j)).append(": ").append(fields[i][j]).append(", \n");
            }
        }
        builder.append("}");
        return builder.toString();
    }

    private boolean validateMove(Pawn pawn, int targetX, int targetY) {
        try {
            return (pawn.getPositionY() + pawn.getPositionX()) % 2 == (targetY + targetX) % 2 && //not on white tile AND
                    (pawn.getPositionX() != targetX && pawn.getPositionY() != targetY) && //not the same coordinates AND
                    (Math.abs(targetY - pawn.getPositionY()) == 1 && Math.abs(targetX - pawn.getPositionX()) == 1) ||
                    (Math.abs(targetY - pawn.getPositionY()) == 2 && Math.abs(targetX - pawn.getPositionX()) == 2); //diagonal move indicates 1 tile difference from X AND Y
        } catch (NullPointerException e) {
            return false;
        }
    }

    public boolean isItEmpty(int x, int y) {
        try {
            return this.fields[x][y] == null;
        } catch (ArrayIndexOutOfBoundsException e) {
            return false;
        }
    }



}

