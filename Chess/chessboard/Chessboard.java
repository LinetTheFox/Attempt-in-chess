package Chess.chessboard;

import Chess.move.MoveHistory;
import Chess.move.MoveInfo;
import Chess.move.ValidityCheck;
import Chess.piece.*;
import Chess.pieceContainer.Container;
import javafx.scene.layout.GridPane;

import java.util.*;

public class Chessboard extends GridPane {
    private final int BOARD_SIZE = 8;
    private Space[][] chessboard = new Space[BOARD_SIZE][BOARD_SIZE];
    private ArrayList<Space> validSpaces = new ArrayList<>(); //contains list of spaces that piece can move on
    private Map<Space, Piece> whitePieces = new HashMap<>(); //keeps track of all white pieces
    private Map<Space, Piece> blackPieces = new HashMap<>(); //keeps track of all black pieces
    private Space activeSpace = null; //current active space with piece that is about to move
    private boolean moveLocked = false; //flag that locks moving when the custom position is being made
    private boolean enableShowMoves = true;
    private MoveHistory history = new MoveHistory();

    private Container container; //reference to Container object
    private ValidityCheck validityCheck = new ValidityCheck(whitePieces, blackPieces, validSpaces); //contains stuff for checking for valid moves

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
        validityCheck.setReferences(this, chessboard);
    }

    private void onSpaceClick(int x, int y) {
        if(moveLocked) {
            if(container.getHeldPiece() == null) {
                return;
            }
            Color curColor = container.getHeldPiece().getColor();
            switch (container.getHeldPiece().getName()) {
                case "pawn": {
                    chessboard[x][y].setPiece(new Pawn(curColor));
                    if(container.getHeldPiece().isWhite()) {
                        whitePieces.put(chessboard[x][y], chessboard[x][y].getPiece());
                    }
                    else {
                        blackPieces.put(chessboard[x][y], chessboard[x][y].getPiece());
                    }
                    break;
                }
                case "knight": {
                    chessboard[x][y].setPiece(new Knight(curColor));
                    if(container.getHeldPiece().isWhite()) {
                        whitePieces.put(chessboard[x][y], chessboard[x][y].getPiece());
                    }
                    else {
                        blackPieces.put(chessboard[x][y], chessboard[x][y].getPiece());
                    }
                    break;
                }
                case "bishop": {
                    chessboard[x][y].setPiece(new Bishop(curColor));
                    if(container.getHeldPiece().isWhite()) {
                        whitePieces.put(chessboard[x][y], chessboard[x][y].getPiece());
                    }
                    else {
                        blackPieces.put(chessboard[x][y], chessboard[x][y].getPiece());
                    }
                    break;
                }
                case "rook": {
                    chessboard[x][y].setPiece(new Rook(curColor));
                    if(container.getHeldPiece().isWhite()) {
                        whitePieces.put(chessboard[x][y], chessboard[x][y].getPiece());
                    }
                    else {
                        blackPieces.put(chessboard[x][y], chessboard[x][y].getPiece());
                    }
                    break;
                }
                case "queen": {
                    chessboard[x][y].setPiece(new Queen(curColor));
                    if(container.getHeldPiece().isWhite()) {
                        whitePieces.put(chessboard[x][y], chessboard[x][y].getPiece());
                    }
                    else {
                        blackPieces.put(chessboard[x][y], chessboard[x][y].getPiece());
                    }
                    break;
                }
                case "king": {
                    if(container.getHeldPiece().isWhite()) {
                        chessboard[x][y].setPiece(new King(curColor));
                        whitePieces.put(chessboard[x][y], chessboard[x][y].getPiece());
                    }
                    else {
                        chessboard[x][y].setPiece(new King(curColor));
                        blackPieces.put(chessboard[x][y], chessboard[x][y].getPiece());
                    }
                    break;
                }
            }
            return;
        }
        Space clickedSpace = chessboard[x][y];

        //if piece is selected and user didn't click on an allied piece
        if (activeSpace != null && activeSpace.getPiece() != null &&
                !clickedSpace.getPieceColor().equals(activeSpace.getPieceColor())) {
            MoveInfo p = new MoveInfo(activeSpace.getX(), activeSpace.getY(), x, y);
            Space oldSpace = chessboard[p.getOldX()][p.getOldY()];
            Space newSpace = chessboard[p.getNewX()][p.getNewY()];
            //if a piece was captured on newSpace
            if (newSpace.getPiece() != null && !newSpace.getPieceColor().equals(oldSpace.getPieceColor())) {
                if(newSpace.getPiece().isWhite()) {
                    whitePieces.remove(newSpace);
                }
                else {
                    blackPieces.remove(newSpace);
                }
                p.setCapturedPiece(newSpace.getPiece());
            }
            if(!oldSpace.getPiece().hasMoved()) {
                oldSpace.getPiece().setHasMoved(true);
            }
            if(oldSpace.getPiece().isWhite()) {
                whitePieces.remove(oldSpace);
                whitePieces.put(newSpace, oldSpace.getPiece());
            }
            else {
                blackPieces.remove(oldSpace);
                blackPieces.put(newSpace, oldSpace.getPiece());
            }
            newSpace.setPiece(oldSpace.releasePiece());
            setActiveSpace(null);
            clearPossibleMoves();
            history.addMove(p);
        }
            //if there's piece on clicked space when there's no active one
        else {
            if (chessboard[x][y].getPiece() != null) {
                clearPossibleMoves();
                setActiveSpace(chessboard[x][y]);
            }
        }
    }

    public void setStartingPositions() {
        clearBoard();
        //white pieces
        whitePieces.clear();
        chessboard[0][0].setPiece(new Rook(Color.white));
        whitePieces.put(chessboard[0][0], chessboard[0][0].getPiece());
        chessboard[1][0].setPiece(new Knight(Color.white));
        whitePieces.put(chessboard[1][0], chessboard[1][0].getPiece());
        chessboard[2][0].setPiece(new Bishop(Color.white));
        whitePieces.put(chessboard[2][0], chessboard[2][0].getPiece());
        chessboard[3][0].setPiece(new Queen(Color.white));
        whitePieces.put(chessboard[3][0], chessboard[3][0].getPiece());
        chessboard[4][0].setPiece(new King(Color.white));
        whitePieces.put(chessboard[4][0], chessboard[4][0].getPiece());
        chessboard[5][0].setPiece(new Bishop(Color.white));
        whitePieces.put(chessboard[5][0], chessboard[5][0].getPiece());
        chessboard[6][0].setPiece(new Knight(Color.white));
        whitePieces.put(chessboard[6][0], chessboard[6][0].getPiece());
        chessboard[7][0].setPiece(new Rook(Color.white));
        whitePieces.put(chessboard[7][0], chessboard[7][0].getPiece());

        for (int i = 0; i < 8; ++i) {
            chessboard[i][1].setPiece(new Pawn(Color.white));
            whitePieces.put(chessboard[i][1], chessboard[i][1].getPiece());
        }

        //black pieces
        blackPieces.clear();
        chessboard[0][7].setPiece(new Rook(Color.black));
        blackPieces.put(chessboard[0][7], chessboard[0][7].getPiece());
        chessboard[1][7].setPiece(new Knight(Color.black));
        blackPieces.put(chessboard[1][7], chessboard[1][7].getPiece());
        chessboard[2][7].setPiece(new Bishop(Color.black));
        blackPieces.put(chessboard[2][7], chessboard[2][7].getPiece());
        chessboard[3][7].setPiece(new Queen(Color.black));
        blackPieces.put(chessboard[3][7], chessboard[3][7].getPiece());
        chessboard[4][7].setPiece(new King(Color.black));
        blackPieces.put(chessboard[4][7], chessboard[4][7].getPiece());
        chessboard[5][7].setPiece(new Bishop(Color.black));
        blackPieces.put(chessboard[5][7], chessboard[5][7].getPiece());
        chessboard[6][7].setPiece(new Knight(Color.black));
        blackPieces.put(chessboard[6][7], chessboard[6][7].getPiece());
        chessboard[7][7].setPiece(new Rook(Color.black));
        blackPieces.put(chessboard[7][7], chessboard[7][7].getPiece());

        for (int i = 0; i < 8; ++i) {
            chessboard[i][6].setPiece(new Pawn(Color.black));
            blackPieces.put(chessboard[i][6], chessboard[i][6].getPiece());
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


    public void clearHistory() {
        history.clearAll();
    }

    public void undoLastMove() {
            MoveInfo temp = history.getLastMove();
            Space newSpace = chessboard[temp.getNewX()][temp.getNewY()];
            Space oldSpace = chessboard[temp.getOldX()][temp.getOldY()];
            if(newSpace.getPiece().isWhite()) {
                whitePieces.remove(newSpace);
                whitePieces.put(oldSpace, newSpace.getPiece());
            }
            else {
                blackPieces.remove(newSpace);
                blackPieces.put(oldSpace, newSpace.getPiece());
            }
            oldSpace.setPiece(newSpace.releasePiece());
            if(temp.getCapturedPiece() != null) {
                newSpace.setPiece(temp.getCapturedPiece());
                if(!temp.getCapturedPiece().isWhite()) {
                    blackPieces.put(newSpace, temp.getCapturedPiece());
                }
                else {
                    whitePieces.put(newSpace, temp.getCapturedPiece());
                }
            }
            history.undoMove();
    }

    private void validSpaces(Space curSpace) {//curSpace - space where the current piece is standing
        clearPossibleMoves();
        validSpaces.clear();
        validSpaces.addAll(validityCheck.validSpaces(curSpace, this, chessboard));
    }

    //shows possible moves from validMoves
    private void showPossibleMoves() {
        if(!validSpaces.isEmpty()) {
            clearPossibleMoves();
        }
        if(activeSpace.isOccupied()) {
            validSpaces(activeSpace);
        }
        for(Space space : validSpaces) {
            space.getStyleClass().add("chess-space-valid");
        }
    }
    //cleans possible moves
    private void clearPossibleMoves() {
        if(!validSpaces.isEmpty()) {
            for(Space space : validSpaces) {
                space.getStyleClass().remove("chess-space-valid");
            }
        }
    }

    //Place next methods here

    private void setActiveSpace(Space activeSpace) {
        /*if there is already an active space - removes it and puts a new one, showing possible moves for the
        piece on it*/
        if (this.activeSpace != null) {
            this.activeSpace.getStyleClass().removeAll("chess-space-active");
        }
        this.activeSpace = activeSpace;
        if (this.activeSpace != null) {
            this.activeSpace.getStyleClass().add("chess-space-active");
            if(enableShowMoves) {
                showPossibleMoves();
            }
        }
    }

    public void setContainer(Container container) {
        this.container = container;
    }

    public void setMoveLocked(boolean moveLocked) {
        this.moveLocked = moveLocked;
    }

    public void setEnableShowMoves (boolean option) {
        enableShowMoves = option;
    }
}
