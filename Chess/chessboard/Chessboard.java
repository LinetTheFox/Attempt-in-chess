package Chess.chessboard;

import Chess.move.MoveHistory;
import Chess.move.MoveInfo;
import Chess.piece.*;
import Chess.pieceContainer.Container;
import javafx.scene.layout.GridPane;

import java.util.ArrayList;

public class Chessboard extends GridPane {
    private final int BOARD_SIZE = 8;
    private Space[][] chessboard = new Space[BOARD_SIZE][BOARD_SIZE];
    private ArrayList<Space> validSpaces = new ArrayList<>(); //contains list of spaces that piece can move on
    private ArrayList<Piece> whitePieces = new ArrayList<>(); //keeps track of all white pieces
    private ArrayList<Piece> blackPieces = new ArrayList<>(); //keeps track of all black pieces
    private Space activeSpace = null; //current active space with piece that is about to move
    private boolean moveLocked = false; //flag that locks moving when the custom position is being made
    private MoveHistory history = new MoveHistory();

    private Container container; //reference to Container object

    public Chessboard() {
        super();
        for(int x = 0; x < BOARD_SIZE; ++x) {
            for (int y = 0; y < BOARD_SIZE; ++y) {
                //making these due to setOnAction working only with final values
                final int xVal = x;
                final int yVal = y;
                chessboard[x][y] = new Space(x, y);
                //adding spaces to the chessboard as to a GridPane
                this.add(chessboard[x][y], x, BOARD_SIZE-1-y );
                chessboard[x][y].setOnAction(event -> onSpaceClick(xVal, yVal));
                //setting the colors of spaces with style classes
                if ((x + y) % 2 == 0) {
                    chessboard[x][y].getStyleClass().add("chess-space-light");
                } else {
                    chessboard[x][y].getStyleClass().add("chess-space-dark");
                }
                //setting the GridPane attributes
                this.setLayoutX(50);
                this.setLayoutY(50);
                this.setVgap(0);
                this.setHgap(0);
            }
        }
    }

    public void onSpaceClick(int x, int y) {
        if(moveLocked) {
            if(container.getHeldPiece() == null) {
                return;
            }
            chessboard[x][y].setPiece(container.getHeldPiece());
            return;
        }
        else {
            Space clickedSpace = chessboard[x][y];
            //if piece is selected and user didn't click on an allied piece
            if(activeSpace != null && activeSpace.getPiece() != null &&
            clickedSpace.getPieceColor() != activeSpace.getPieceColor()) {
                MoveInfo p = new MoveInfo(activeSpace.getX(), activeSpace.getY(), x, y);
                Space oldSpace = chessboard[p.getOldX()][p.getOldY()];
                Space newSpace = chessboard[p.getNewX()][p.getNewY()];
                //if a piece was captured on newSpace
                if(newSpace.getPiece() != null && newSpace.getPieceColor() != oldSpace.getPieceColor()) {
                    p. setCapturedPiece(newSpace.getPiece());
                }
                newSpace.setPiece(oldSpace.releasePiece());
                setActiveSpace(null);
                //@TODO cleanValidMoves();
                history.addMove(p);

            }
            //if there's piece on clicked space when there's no active one
            else {
                //@TODO cleanValidMoves();
                setActiveSpace(chessboard[x][y]);
            }
        }
    }

    public void setStartingPositions() {
        clearBoard();
        //white pieces
        whitePieces.clear();
        chessboard[0][0].setPiece(new Rook(Color.white));
        whitePieces.add(chessboard[0][0].getPiece());
        chessboard[1][0].setPiece(new Knight(Color.white));
        whitePieces.add(chessboard[1][0].getPiece());
        chessboard[2][0].setPiece(new Bishop(Color.white));
        whitePieces.add(chessboard[2][0].getPiece());
        chessboard[3][0].setPiece(new Queen(Color.white));
        whitePieces.add(chessboard[3][0].getPiece());
        chessboard[4][0].setPiece(new King(Color.white));
        whitePieces.add(chessboard[4][0].getPiece());
        chessboard[5][0].setPiece(new Bishop(Color.white));
        whitePieces.add(chessboard[5][0].getPiece());
        chessboard[6][0].setPiece(new Knight(Color.white));
        whitePieces.add(chessboard[6][0].getPiece());
        chessboard[7][0].setPiece(new Rook(Color.white));
        whitePieces.add(chessboard[7][0].getPiece());

        for (int i = 0; i < 8; ++i) {
            chessboard[i][1].setPiece(new Pawn(Color.white));
            whitePieces.add(chessboard[i][1].getPiece());
        }

        //black pieces
        blackPieces.clear();
        chessboard[0][7].setPiece(new Rook(Color.black));
        blackPieces.add(chessboard[0][7].getPiece());
        chessboard[1][7].setPiece(new Knight(Color.black));
        blackPieces.add(chessboard[1][7].getPiece());
        chessboard[2][7].setPiece(new Bishop(Color.black));
        blackPieces.add(chessboard[2][7].getPiece());
        chessboard[3][7].setPiece(new Queen(Color.black));
        blackPieces.add(chessboard[3][7].getPiece());
        chessboard[4][7].setPiece(new King(Color.black));
        blackPieces.add(chessboard[4][7].getPiece());
        chessboard[5][7].setPiece(new Bishop(Color.black));
        blackPieces.add(chessboard[5][7].getPiece());
        chessboard[6][7].setPiece(new Knight(Color.black));
        blackPieces.add(chessboard[6][7].getPiece());
        chessboard[7][7].setPiece(new Rook(Color.black));
        blackPieces.add(chessboard[7][7].getPiece());

        for (int i = 0; i < 8; ++i) {
            chessboard[i][6].setPiece(new Pawn(Color.black));
            blackPieces.add(chessboard[i][6].getPiece());
        }
    }

    public void clearBoard() {
        whitePieces.clear();
        blackPieces.clear();
        for (Space[] rows : chessboard) {
            for (Space space : rows) {
                space.setPiece(null);
            }
        }
    }

    public void setActiveSpace(Space activeSpace) {
        this.activeSpace = activeSpace;
    }


    public void clearHistory() {
        history.clearAll();
    }

    public void undoLastMove() {
        try {
            MoveInfo temp = history.getLastMove();
            chessboard[temp.getOldX()][temp.getOldY()].setPiece(chessboard[temp.getNewX()][temp.getNewY()].releasePiece());
            chessboard[temp.getNewX()][temp.getNewY()].setPiece(temp.getCapturedPiece());
            history.undoMove();
        } catch (Exception ex) {
            return;
        }
    }

    //Place next methods here


    public void setContainer(Container container) {
        this.container = container;
    }

    public void setMoveLocked(boolean moveLocked) {
        this.moveLocked = moveLocked;
    }
}
