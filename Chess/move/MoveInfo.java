/*contains information about every move made*/

package Chess.move;

import Chess.piece.Piece;

public class MoveInfo {
    private int oldX;
    private int oldY;
    private int newX;
    private int newY;
    private Piece capturedPiece;

    public MoveInfo(int oldX, int oldY, int newX, int newY) {
        this.oldX = oldX;
        this.newX = newX;
        this.oldY = oldY;
        this.newY = newY;
    }

    public int getOldX() {
        return oldX;
    }

    public int getOldY() {
        return oldY;
    }

    public int getNewX() {
        return newX;
    }

    public int getNewY() {
        return newY;
    }

    public Piece getCapturedPiece() {
        return capturedPiece;
    }

    public void setCapturedPiece(Piece capturedPiece) {
        this.capturedPiece = capturedPiece;
    }
}
