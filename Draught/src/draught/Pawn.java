package draught;

public class Pawn {
    int positionX;
    int positionY;
    public boolean isWhite;
    boolean isCrowned = false;
    int player;

    public Pawn(int positionX, int positionY, boolean isWhite, int player) {
        this.positionX = positionX;
        this.positionY = positionY;
        this.isWhite = isWhite;
        this.player = player;
    }

    public int getPositionX() {
        return positionX;
    }

    public void setPositionX(int positionX) {
        this.positionX = positionX;
    }

    public int getPositionY() {
        return positionY;
    }

    public void setPositionY(int positionY) {
        this.positionY = positionY;
    }

    public boolean isWhite() {
        return isWhite;
    }

    public void setWhite(boolean white) {
        isWhite = white;
    }

    public boolean isCrowned() {
        return isCrowned;
    }

    public int whichPlayer() { return player;}

}