/*contains buttons which enables placing pieces on chessboard when making a custom position*/
package Chess.pieceContainer;

import Chess.chessboard.Chessboard;
import Chess.chessboard.Space;
import Chess.piece.*;
import javafx.scene.layout.GridPane;

public class Container extends GridPane {
    private final int WIDTH = 4;
    private final int HEIGHT = 3;

    private Space[][] pieces = new Space[4][3];
    private boolean moveLocked = false;
    private Piece heldPiece;


    private Chessboard board; //reference to Chessboard object

    public Container() {
        for (int x = 0; x < WIDTH; ++x) {
            for (int y = 0; y < HEIGHT; ++y) {
                /*because these are objects of Space class but don't belong to chessboard, decided to give them such
                coordinates*/
                pieces[x][y] = new Space(100+x, 100+y);
                //adding elements to pieces array as to GridPane
                this.add(pieces[x][y], x, y);
                final int xVal = x;
                final int yVal = y;
                pieces[x][y].setOnAction(e -> holdPiece(pieces[xVal][yVal].getPiece()));
            }
        }
        //setting GridPane layouts
        this.setLayoutX(1020);
        this.setLayoutY(500);
        this.setPrefSize(300, 225);
        this.setHgap(0);
        this.setVgap(0);

        //placing pieces
        pieces[0][0].setPiece(new Pawn(Color.white));
        pieces[1][0].setPiece(new Knight(Color.white));
        pieces[2][0].setPiece(new Bishop(Color.white));
        pieces[3][0].setPiece(new Rook(Color.white));
        pieces[0][1].setPiece(new Queen(Color.white));
        pieces[1][1].setPiece(new King(Color.white));
        pieces[2][1].setPiece(new Pawn(Color.black));
        pieces[3][1].setPiece(new Knight(Color.black));
        pieces[0][2].setPiece(new Bishop(Color.black));
        pieces[1][2].setPiece(new Rook(Color.black));
        pieces[2][2].setPiece(new Queen(Color.black));
        pieces[3][2].setPiece(new King(Color.black));
    }

    public void holdPiece(Piece piece) {
        if(moveLocked) {
            heldPiece = piece;
        }
    }

    public void setBoard(Chessboard board) {
        this.board = board;
    }

    public void setMoveLocked(boolean moveLocked) {
        this.moveLocked = moveLocked;
    }

    public Piece getHeldPiece() {
        return heldPiece;
    }


}
