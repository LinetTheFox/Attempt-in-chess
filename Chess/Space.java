package Chess;

import Chess.Piece.Piece;
import javafx.scene.control.Button;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;

public class Space extends Button {
    private int x;
    private int y;
    private Piece piece;    //piece currently on the space

    public Space(int x, int y) {
        super();
        this.setPrefHeight(75);
        this.setPrefWidth(75);
        this.x = x;
        this.y = y;
        this.piece = null;
    }

    public boolean isOccupied() {
        return (piece != null);
    }

    public Piece releasePiece() {
        Piece tmpPiece = this.piece;
        setPiece(null);
        return tmpPiece;
    }

    public Piece getPiece() {
        return piece;
    }

    public void setPiece(Piece piece) {
        this.piece = piece;

        if (this.getPiece() != null) {
            this.setGraphic(new ImageView(piece.getTexture()));
        }
        else
            this.setGraphic(new ImageView());
    }

    public String getPieceColor() {
        if(getPiece() != null) {
            return getPiece().getColor().name();
        }
        else
            return "";
    }

    public int getX() {
        return x;
    }

    public void setX(int x) {
        this.x = x;
    }

    public int getY() {
        return y;
    }

    public void setY(int y) {
        this.y = y;
    }
}
