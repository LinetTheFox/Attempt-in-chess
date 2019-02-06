package Chess;

import javafx.scene.layout.ColumnConstraints;
import javafx.scene.layout.GridPane;
import Chess.Piece.*;
import javafx.scene.layout.RowConstraints;

import java.util.ArrayList;
import java.util.Set;

import static Chess.Piece.Color.*;

public class Chessboard extends GridPane {
    private Space[][] spaces = new Space[8][8];

    private PieceHolder holder;

    ArrayList<Space> moves = new ArrayList<>();

    public void setHolder(PieceHolder holder) {
        this.holder = holder;
    }

    //last clicked space
    private Space activeSpace = null;

    private MoveHistory history = new MoveHistory();

    public void clearHistory() {
        history.clearAll();
    }

    private boolean moveLocked = false;

    public void setMoveLocked(boolean moveLocked) {
        this.moveLocked = moveLocked;
    }

    public Chessboard() {
        super();
        this.setLayoutX(50);
        this.setLayoutY(50);
        this.setVgap(0);
        this.setHgap(0);
        for (int x = 0; x < spaces[0].length; ++x) {
            for (int y = 0; y < spaces[1].length; ++y) {
                final int xVal = x;
                final int yVal = y;
                spaces[x][y] = new Space(x, y);
                this.add(spaces[x][y], x, 7 - y);
                spaces[x][y].setOnAction(e -> onSpaceClick(xVal, yVal));
                if ((x + y) % 2 == 0) {
                    spaces[x][y].getStyleClass().add("chess-space-light");
                } else {
                    spaces[x][y].getStyleClass().add("chess-space-dark");
                }
            }
        }
        for (RowConstraints rows : this.getRowConstraints()) {
            rows.setMaxHeight(75);
        }
        for (ColumnConstraints columns : this.getColumnConstraints()) {
            columns.setMaxWidth(75);
        }
    }

    public Space getSpace(int x, int y) {
        return spaces[x][y];
    }

    public void setActiveSpace(Space s) {
        if (this.activeSpace != null) {
            this.activeSpace.getStyleClass().removeAll("chess-space-active");
        }
        this.activeSpace = s;
        if (this.activeSpace != null) {
            this.activeSpace.getStyleClass().add("chess-space-active");
            showPossibleMoves();
        }
    }

    public void makeStartingPositions() {
        this.clearBoard();
        //white pieces
        this.spaces[0][0].setPiece(new Rook(White));
        this.spaces[1][0].setPiece(new Knight(White));
        this.spaces[2][0].setPiece(new Bishop(White));
        this.spaces[3][0].setPiece(new Queen(White));
        this.spaces[4][0].setPiece(new King(White));
        this.spaces[5][0].setPiece(new Bishop(White));
        this.spaces[6][0].setPiece(new Knight(White));
        this.spaces[7][0].setPiece(new Rook(White));

        for (int i = 0; i < 8; ++i) {
            this.spaces[i][1].setPiece(new Pawn(White));
        }

        //black pieces
        this.spaces[0][7].setPiece(new Rook(Black));
        this.spaces[1][7].setPiece(new Knight(Black));
        this.spaces[2][7].setPiece(new Bishop(Black));
        this.spaces[3][7].setPiece(new Queen(Black));
        this.spaces[4][7].setPiece(new King(Black));
        this.spaces[5][7].setPiece(new Bishop(Black));
        this.spaces[6][7].setPiece(new Knight(Black));
        this.spaces[7][7].setPiece(new Rook(Black));

        for (int i = 0; i < 8; ++i) {
            this.spaces[i][6].setPiece(new Pawn(Black));
        }
    }

    public void clearBoard() {
        for (Space[] rows : spaces) {
            for (Space space : rows) {
                space.setPiece(null);
            }
        }
    }

    public void onSpaceClick(int x, int y) {
        if (moveLocked) {
            if (holder.getHeldPiece() == null) {
                return;
            }
            spaces[x][y].setPiece(holder.getHeldPiece());
            return;
        }
        Space clickedSpace = spaces[x][y];

        //if piece is selected and user didn't click on an allied piece
        if (activeSpace != null &&
                activeSpace.getPiece() != null &&
                !clickedSpace.getPieceColor().equals(activeSpace.getPieceColor())) {
            MoveInfo p;
            p = new MoveInfo(activeSpace.getX(), activeSpace.getY(), x, y);
            Space oldSpace = spaces[p.getOldX()][p.getOldY()];
            Space newSpace = spaces[p.getNewX()][p.getNewY()];

            if (newSpace.getPiece() != null) {
                p.setCaptured(newSpace.getPiece());
            }

            newSpace.setPiece(oldSpace.releasePiece());
            this.setActiveSpace(null);
            cleanValidMoves();
            history.addMove(p);
        } else {
            //if there's a piece on the selected square when there's no active square
            if (spaces[x][y].getPiece() != null) {
                cleanValidMoves();
                //make active square a selected one
                this.setActiveSpace(spaces[x][y]);
            }
        }

    }

    public void undoLastMove() {
        try {
            MoveInfo temp = history.getLastMove();
            spaces[temp.getOldX()][temp.getOldY()].setPiece(spaces[temp.getNewX()][temp.getNewY()].releasePiece());
            spaces[temp.getNewX()][temp.getNewY()].setPiece(temp.getCaptured());
            history.undoMove();
        } catch (Exception ex) {
            return;
        }
    }

    public ArrayList<Space> possibleMoves(Space activeSpace) {
        moves.clear();
        if (activeSpace.getPiece() == null) {
            return null;
        }
        if (activeSpace.getPiece().getName().equals("Pawn")) {
            MoveList m;
            for (int i = 0; i < activeSpace.getPiece().possibleMoves().length; ++i) {
                m = activeSpace.getPiece().possibleMoves()[i];
                if (activeSpace.getX() + m.getX() > 7 || activeSpace.getX() + m.getX() < 0 ||
                        activeSpace.getY() + m.getY() > 7 || activeSpace.getY() + m.getY() < 0) {
                    continue;
                }
                if (m == MoveList.UP || m == MoveList.DOWN ||
                        m == MoveList.DOUBLE_UP || m == MoveList.DOUBLE_DOWN) {
                    if (!spaces[activeSpace.getX() + m.getX()][activeSpace.getY() + m.getY()].isOccupied()) {
                        moves.add(spaces[activeSpace.getX() + m.getX()][activeSpace.getY() + m.getY()]);
                    }
                } else {
                    if (spaces[activeSpace.getX() + m.getX()][activeSpace.getY() + m.getY()].isOccupied() &&
                            spaces[activeSpace.getX() + m.getX()][activeSpace.getY() + m.getY()].getPiece().getColor() !=
                                    activeSpace.getPiece().getColor()) {
                        moves.add(spaces[activeSpace.getX() + m.getX()][activeSpace.getY() + m.getY()]);
                    }
                }
            }
        }
        else if (activeSpace.getPiece().getName().equals("Knight")) {
            for (MoveList m :  activeSpace.getPiece().possibleMoves()) {
                if (activeSpace.getX() + m.getX() > 7 || activeSpace.getX() + m.getX() < 0 ||
                        activeSpace.getY() + m.getY() > 7 || activeSpace.getY() + m.getY() < 0) {
                    continue;
                }
                if (spaces[activeSpace.getX() + m.getX()][activeSpace.getY() + m.getY()].isOccupied() &&
                        spaces[activeSpace.getX() + m.getX()][activeSpace.getY() + m.getY()].getPiece().getColor() ==
                                activeSpace.getPiece().getColor()) {
                    continue;
                }
                moves.add(spaces[activeSpace.getX() + m.getX()][activeSpace.getY() + m.getY()]);
            }
        }

        else if(activeSpace.getPiece().getName().equals("Bishop") || activeSpace.getPiece().getName().equals("Rook") ||
                activeSpace.getPiece().getName().equals("Queen")) {
            calculateForMultipleMovePieces(activeSpace.getPiece().possibleMoves(), moves);
        }

        else {
            for (MoveList m :  activeSpace.getPiece().possibleMoves()) {
                if (activeSpace.getX() + m.getX() > 7 || activeSpace.getX() + m.getX() < 0 ||
                        activeSpace.getY() + m.getY() > 7 || activeSpace.getY() + m.getY() < 0) {
                    continue;
                }
                if(activeSpace.getPiece().getHasMoved()) {
                    if(m==MoveList.DOUBLE_LEFT || m==MoveList.DOUBLE_RIGHT) {
                        continue;
                    }
                }
                if(spaceUnderAttack(activeSpace.getPiece().getColor(), spaces[activeSpace.getX()+m.getX()]
                        [activeSpace.getY()+m.getY()])) {
                    continue;
                }
                if (spaces[activeSpace.getX() + m.getX()][activeSpace.getY() + m.getY()].isOccupied() &&
                        spaces[activeSpace.getX() + m.getX()][activeSpace.getY() + m.getY()].getPiece().getColor() ==
                                activeSpace.getPiece().getColor()) {
                    continue;
                }
                moves.add(spaces[activeSpace.getX() + m.getX()][activeSpace.getY() + m.getY()]);
            }
        }

        return moves;
    }

    private boolean spaceUnderAttack(Color currentPieceColor, Space space) {
        for(int x = 0; x < 7; ++x) {
            for (int y = 0; y <7; ++y) {
                if(spaces[x][y].isOccupied() && spaces[x][y].getPiece().getColor() != currentPieceColor) {
                    if(possibleMoves(spaces[x][y]).contains(space)) {
                        return true;
                    }
                }
            }
        }
        return false;
    }

    public void showPossibleMoves() {
        if (possibleMoves(activeSpace) == null) {
            return;
        }
        for (Space s : possibleMoves(activeSpace)) {
            s.getStyleClass().add("chess-space-valid");
        }
    }

    private void cleanValidMoves() {
        for (Space[] rows : spaces) {
            for (Space s : rows) {
                if (s.getStyleClass().contains("chess-space-valid")) {
                    s.getStyleClass().remove("chess-space-valid");
                }
            }
        }
    }

    private void falsenHasCollided(MoveList[] list) {
        for (MoveList m : list) {
            m.setHasCollided(false);
        }
    }

    private void calculateForMultipleMovePieces(MoveList[] list, ArrayList<Space> possibleSpaces) {
        falsenHasCollided(activeSpace.getPiece().possibleMoves());
        for (MoveList m : (activeSpace.getPiece()).possibleMoves()) {
            for (int c = 1; c < 7; c++) {
                if (m.hasCollided()) {
                    continue;
                }
                if (activeSpace.getX() + (m.getX() * c) > 7 || activeSpace.getX() + (m.getX() * c) < 0 ||
                        activeSpace.getY() + (m.getY() * c) > 7 || activeSpace.getY() + (m.getY() * c) < 0) {
                    continue;
                }
                if (spaces[activeSpace.getX() + (m.getX() * c)][activeSpace.getY() + (m.getY() * c)].isOccupied() &&
                        spaces[activeSpace.getX() + (m.getX() * c)][activeSpace.getY() + (m.getY() * c)].getPiece().getColor() ==
                                activeSpace.getPiece().getColor()) {
                    m.setHasCollided(true);
                    continue;
                }
                if (spaces[activeSpace.getX() + (m.getX() * c)][activeSpace.getY() + (m.getY() * c)].isOccupied() &&
                        spaces[activeSpace.getX() + (m.getX() * c)][activeSpace.getY() + (m.getY() * c)].getPiece().getColor() !=
                                activeSpace.getPiece().getColor()) {
                    m.setHasCollided(true);
                }

                possibleSpaces.add(spaces[activeSpace.getX() + (m.getX() * c)][activeSpace.getY() + (m.getY() * c)]);
            }
        }
    }
}