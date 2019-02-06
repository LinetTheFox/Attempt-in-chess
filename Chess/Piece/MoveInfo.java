package Chess.Piece;

import java.io.Serializable;

public class MoveInfo implements Serializable {
    private int oldX;
    private int newX;
    private int oldY;
    private int newY;
    private Piece captured;

    public MoveInfo(int oldX, int oldY, int newX, int newY) {
        this.oldX = oldX;
        this.newX = newX;
        this.oldY = oldY;
        this.newY = newY;
    }

    @Override
    public String toString() {
        return getCharLabel(oldX+1)+(oldY+1)+" to "+getCharLabel(newX+1)+(newY+1);
    }

    public int getOldX() {
        return oldX;
    }

    public void setOldX(int oldX) {
        this.oldX = oldX;
    }

    public int getNewX() {
        return newX;
    }

    public void setNewX(int newX) {
        this.newX = newX;
    }

    public int getOldY() {
        return oldY;
    }

    public void setOldY(int oldY) {
        this.oldY = oldY;
    }

    public int getNewY() {
        return newY;
    }

    public void setNewY(int newY) {
        this.newY = newY;
    }

    public int getGapX() {
        return (this.newX-this.oldX);
    }

    public int getGapY() {
        return (this.newY-this.oldY);
    }

    public Piece getCaptured() {
        return captured;
    }

    public void setCaptured(Piece captured) {
        this.captured = captured;
    }

    //Converts x number position into a char label
    private String getCharLabel(int i) {
        return (i>0 && i<27)? String.valueOf((char)(i+64)) : null;
    }
}

