package draught;

public class Pawn {
    String color;
    boolean isCrowned = false;

    public String getColor() {
        return color;
    }

    public void setColor(String color) {
        this.color = color;
    }

    public class Coordinates {
        int x;
        int y;
    }

    private boolean getIsWhite() {
        return this.color.equals("white");
    }

    private boolean getIsCrowned() {
        return this.isCrowned;
    }

}
