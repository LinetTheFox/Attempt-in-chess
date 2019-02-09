package Chess.chessboard;

import Chess.piece.Color;
import Chess.piece.Piece;
import javafx.scene.control.Button;
import javafx.scene.image.ImageView;

public class Space extends Button {
    //coordinates on chessboard
    private int x;
    private int y;

    //piece currently on this space
    private Piece piece;

    public Space(int x, int y) {
        super();
        this.x = x;
        this.y = y;
        this.setPrefSize(75, 75);
    }

    public boolean isOccupied() {
        return (this.piece != null);
    }

    /*unlike setter for "piece" removes the piece from this space, Chessboard checks if this space contains one,
    preventing NullPointerException*/
    public Piece releasePiece() {
        Piece tmpPiece = this.piece;
        setPiece(null);
        return tmpPiece;
    }

    //as in releasePiece, Chessboard prevents NullPointerException
    public String getPieceColor() {
        if(isOccupied()) {
            return piece.getColor().name();
        }
        else {
            return "";
        }
    }

    public int getX() {
        return x;
    }

    public int getY() {
        return y;
    }

    public Piece getPiece() {
        return piece;
    }

    public void setPiece(Piece piece) {
        this.piece = piece;
        if (this.getPiece() != null) {
            this.setGraphic(new ImageView(piece.getImage()));
        }
        else
            this.setGraphic(null);
    }
}
