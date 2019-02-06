package Chess;

import Chess.Piece.*;
import javafx.scene.layout.GridPane;

import static Chess.Piece.Color.*;

public class PieceHolder extends GridPane {
    private Space[][] menu = new Space[4][3];

    private Chessboard board;

    public void setBoard(Chessboard board) {
        this.board = board;
    }

    private boolean moveLocked;

    public void setMoveLocked(boolean moveLocked) {
        this.moveLocked = moveLocked;
    }

    private Piece heldPiece;

    public Piece getHeldPiece() {
        return heldPiece;
    }

    public void setHeldPiece(Piece piece) {
        heldPiece = piece;
    }

    PieceHolder() {
        this.setLayoutX(1020);
        this.setLayoutY(500);
        this.setPrefSize(300, 225);
        this.setHgap(0);
        this.setVgap(0);
        for(int x = 0; x < 4; ++x) {
            for(int y = 0; y < 3; ++y) {
                menu[x][y] = new Space(100+x, 100+y);
                this.add(menu[x][y], x, y);
                menu[x][y].setPrefSize(75, 75);
                final int Xval = x;
                final int Yval = y;
                menu[x][y].setOnAction(event -> placePiece(menu[Xval][Yval].getPiece()));
            }
        }
        menu[0][0].setPiece(new Pawn(White));
        menu[1][0].setPiece(new Knight(White));
        menu[2][0].setPiece(new Bishop(White));
        menu[3][0].setPiece(new Rook(White));
        menu[0][1].setPiece(new Queen(White));
        menu[1][1].setPiece(new King(White));
        menu[2][1].setPiece(new Pawn(Black));
        menu[3][1].setPiece(new Knight(Black));
        menu[0][2].setPiece(new Bishop(Black));
        menu[1][2].setPiece(new Rook(Black));
        menu[2][2].setPiece(new Queen(Black));
        menu[3][2].setPiece(new King(Black));
    }

    public void placePiece(Piece piece) {
        if(moveLocked) {
            heldPiece = piece;
        }
    }

}

