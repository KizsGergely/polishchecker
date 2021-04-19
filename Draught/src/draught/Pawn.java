package draught;

public class Pawn {
    int positionX;
    int positionY;
    boolean isWhite;
    boolean isCrowned = false;

    public Pawn(int positionX, int positionY, boolean isWhite) {
        this.positionX = positionX;
        this.positionY = positionY;
        this.isWhite = isWhite;
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

}
