/*contains some methods for checking conditions in pieces' logic - simplifies code in Chessboard class*/
package Chess.move;

import Chess.chessboard.Chessboard;
import Chess.chessboard.Space;
import Chess.piece.Color;
import Chess.piece.Piece;

import java.util.ArrayList;
import java.util.Map;

public class ValidityCheck {

    private ArrayList<Space> currentOpponentPieces = new ArrayList<>();
    private ArrayList<Space> validSpaces;
    private Map<Space, Piece> whitePieces;
    private Map<Space, Piece> blackPieces;
    private Chessboard chessboard;
    private Space[][] board;

    public ValidityCheck(Map<Space, Piece> whitePieces, Map<Space, Piece> blackPieces, ArrayList<Space> validSpaces) {
        this.validSpaces = validSpaces;
        this.blackPieces = blackPieces;
        this.whitePieces = whitePieces;
    }


    //checking for the space being out of array bounds
    public boolean isOutOfBounds(int x, int y) {
        return (x>7 || x<0 || y>7 || y<0);
    }
    //checking if space has an ally piece
    public boolean containsAllyPiece(int x, int y, Space[][] board, Color color) {
        if(isOutOfBounds(x, y)) return false;
        return (board[x][y].isOccupied() && board[x][y].getPieceColor().equals(color.name()));
    }
    //checking if space has an opponent piece
    public boolean containsOpponentPiece(int x, int y, Space[][] board, Color color) {
        if(isOutOfBounds(x, y)) return false;
        return (board[x][y].isOccupied() && !board[x][y].getPieceColor().equals(color.name()));
    }

    //if the space is a place where an opponent pawn can perform capturing move
    public boolean isAPawnCaptureSpace(int x, int y, Space[][]board, Color color) {
        if(isOutOfBounds(x, y)) return false;
        if(color == Color.white) {
            return ((!isOutOfBounds(x+1, y+1) &&
                    board[x+1][y+1].isOccupied() &&
                    board[x+1][y+1].getPiece().getName().equals("pawn") &&
                    board[x+1][y+1].getPiece().getColor() != color) ||
                            (!isOutOfBounds(x-1, y+1)&&
                            board[x-1][y+1].isOccupied() &&
                            board[x-1][y+1].getPiece().getName().equals("pawn") &&
                            board[x-1][y+1].getPiece().getColor() != color));
        }
        else {
            return ((!isOutOfBounds(x+1, y-1) &&
                    board[x+1][y-1].isOccupied() &&
                    board[x+1][y-1].getPiece().getName().equals("pawn") &&
                    board[x+1][y-1].getPiece().getColor() != color) ||
                            (!isOutOfBounds(x-1, y-1)&&
                            board[x-1][y-1].isOccupied() &&
                            board[x-1][y-1].getPiece().getName().equals("pawn") &&
                            board[x-1][y-1].getPiece().getColor() != color));
        }
    }
    //if the space is right in front of enemy pawn
    public boolean isAPawnForwardSpace(int x, int y, Space[][] board, Color color) {
        if(isOutOfBounds(x, y)) return false;
        if(color == Color.white) {
            return  ((!isOutOfBounds(x, y+1) &&
                    board[x][y+1].isOccupied() &&
                    board[x][y+1].getPiece().getName().equals("pawn") &&
                    board[x][y+1].getPiece().getColor() != color) ||
                            (!isOutOfBounds(x, y+2) &&
                            board[x][y+2].isOccupied() &&
                            board[x][y+2].getPiece().getName().equals("pawn") &&
                            board[x][y+2].getPiece().getColor() != color));
        }
        else {
            return  ((!isOutOfBounds(x, y-1) &&
                    board[x][y-1].isOccupied() &&
                    board[x][y-1].getPiece().getName().equals("pawn") &&
                    board[x][y-1].getPiece().getColor() != color) ||
                            (!isOutOfBounds(x, y-2) &&
                            board[x][y-2].isOccupied() &&
                            board[x][y-2].getPiece().getName().equals("pawn") &&
                            board[x][y-2].getPiece().getColor() != color));
        }
    }

    public ArrayList<Space> validSpaces(Space curSpace, Chessboard chessboard, Space[][] board) {
        ArrayList<Space> validSpaces = new ArrayList<>();
        if(curSpace == null) {
            return validSpaces;
        }
        //if there's no piece which can make a move
        if (!curSpace.isOccupied()) {
            return validSpaces;
        }
        //declaring some simplifying variables
        int addedX; //=curSpace.getX()+m.getX()
        int addedY; //=curSpace.getY()+m.getY()
        int multipliedX; //=curSpace.getX+(c*m.getX()) c - being the loop multiplier for Bishop, Rook and Queen
        int multipliedY; //=curSpace.getX+(c*m.getY())
        Color curColor = curSpace.getPiece().getColor();

        switch (curSpace.getPiece().getName()) {
            //logic for pawn
            case "pawn": {
                MoveList m;
                for (int i = 0; i < curSpace.getPiece().validMoves().size(); ++i) {
                    m = curSpace.getPiece().validMoves().get(i);
                    addedX = curSpace.getX() + m.getX();
                    addedY = curSpace.getY() + m.getY();
                    if (isOutOfBounds(addedX, addedY)) {
                        continue;
                    }
                    //if forward non-capture move: shouldn't contain any piece
                    if (m == MoveList.UP || m == MoveList.DOWN || m == MoveList.DOUBLE_UP || m == MoveList.DOUBLE_DOWN) {
                        if (board[addedX][addedY].isOccupied()) {
                            continue;
                        }
                    }
                    //if diagonal capture move: can contain opponent piece, but not ally piece
                    else {
                        if (!board[addedX][addedY].isOccupied()) {
                            continue;
                        }
                        if (!containsOpponentPiece(addedX, addedY, board, curColor)) {
                            continue;
                        }
                    }
                    validSpaces.add(board[addedX][addedY]);
                }
            }
            break;

            //logic for knight
            case "knight": {
                for (MoveList m : curSpace.getPiece().validMoves()) {
                    addedX = curSpace.getX() + m.getX();
                    addedY = curSpace.getY() + m.getY();
                    if (isOutOfBounds(addedX, addedY)) {
                        continue;
                    }
                    if (containsAllyPiece(addedX, addedY, board, curColor)) {
                        continue;
                    }
                    validSpaces.add(board[addedX][addedY]);
                }
            }
            break;
            //logic for king... oh dear...
            case "king": {
                for (MoveList m : curSpace.getPiece().validMoves()) {
                    addedX = curSpace.getX() + m.getX();
                    addedY = curSpace.getY() + m.getY();
                    if (isOutOfBounds(addedX, addedY)) {
                        continue;
                    }
                    if (containsAllyPiece(addedX, addedY, board, curColor)) {
                        continue;
                    }
                    //can't make castling if there's (99.9% that an ally) piece or if the path for king is under attack
                    if (m == MoveList.DOUBLE_LEFT) {
                        int tempX = curSpace.getX() + MoveList.LEFT.getX();
                        int tempY = curSpace.getY() + MoveList.LEFT.getY();
                        if (board[tempX][tempY].isOccupied()) {
                            continue;
                        }
                        if (isUnderAttack(curSpace, curColor) || isUnderAttack(board[tempX][tempY], curColor) ||
                                isUnderAttack(board[addedX][addedY], curColor))  {
                            continue;
                        }
                    }
                    if (m == MoveList.DOUBLE_RIGHT) {
                        int tempX = curSpace.getX() + MoveList.RIGHT.getX();
                        int tempY = curSpace.getY() + MoveList.RIGHT.getY();
                        if (board[tempX][tempY].isOccupied()) {
                            continue;
                        }
                        if (isUnderAttack(curSpace, curColor) || isUnderAttack(board[tempX][tempY], curColor) ||
                                isUnderAttack(board[addedX][addedY], curColor))  {
                            continue;
                        }
                    }
                    //can't move on spaces that are under attack
                    if (isUnderAttack(board[addedX][addedY], curSpace.getPiece().getColor())) {
                        continue;
                    }
                    //can't capture piece which is protected (if the space was empty, would be underAttack==true)
                    if (containsOpponentPiece(addedX, addedY, board, curColor)) {
                        //temporarily removing the piece under question to check if it's now empty space is under attack
                        Piece tempPiece = board[addedX][addedY].getPiece();
                        if(tempPiece.isWhite()) {
                            whitePieces.remove(board[addedX][addedY]);
                        }
                        else {
                            blackPieces.remove(board[addedX][addedY]);
                        }
                        board[addedX][addedY].setPiece(null);
                        if(isUnderAttack(board[addedX][addedY], curColor)) {
                            board[addedX][addedY].setPiece(tempPiece);
                            if(tempPiece.isWhite()) {
                                whitePieces.put(board[addedX][addedY], board[addedX][addedY].getPiece());
                            }
                            else {
                                blackPieces.put(board[addedX][addedY], board[addedX][addedY].getPiece());
                            }
                            continue;
                        }
                    }
                /*interaction with pawns - king can stand in front of enemy pawn but not on space where pawn
                is capable of capturing*/
                    if(isAPawnCaptureSpace(addedX, addedY, board, curColor)) {
                        continue;
                    }
                    if(isAPawnForwardSpace(addedX, addedY, board, curColor)) {
                        validSpaces.add(board[addedX][addedY]);
                        continue;
                    }
                    validSpaces.add(board[addedX][addedY]);
                }
            }
            break;

            //logic for bishop, rook, queen (they share same move logic, just different directions)
            default: {
                //"unfalsing" all moves' "hasCollided" fields
                for (MoveList m : curSpace.getPiece().validMoves()) {
                    m.setHasCollided(false);
                }
                for (MoveList m : curSpace.getPiece().validMoves()) {
                    //c is multiplier, which repeats the single moves in all directions
                    for (int c = 1; c < 8; ++c) {
                        multipliedX = curSpace.getX() + (m.getX() * c);
                        multipliedY = curSpace.getY() + (m.getY() * c);
                        if (m.hasCollided()) {
                            continue;
                        }
                        if (isOutOfBounds(multipliedX, multipliedY)) {
                            continue;
                        }
                        //if next space has an ally piece - can't go over it
                        if (containsAllyPiece(multipliedX, multipliedY, board, curColor)) {
                            m.setHasCollided(true);
                            continue;
                        }
                        //if next space has an opponent piece - can capture it but can't go beneath it
                        if (containsOpponentPiece(multipliedX, multipliedY, board, curColor)) {
                            m.setHasCollided(true);
                        }
                        validSpaces.add(board[multipliedX][multipliedY]);
                    }
                }
            }
            break;
        }
        return validSpaces;
    }

    private boolean isUnderAttack(Space space, Color color) {
        //currentOpponentPieces contains all spaces that contain opponent pieces
        currentOpponentPieces.clear();
        ArrayList<Space> reserve = new ArrayList<>(); //because validSpces() modifies ArrayList validSpaces
        reserve.addAll(validSpaces);
        //for white king
        if(color == Color.white) {
            for(Space s : blackPieces.keySet()) {
                //to prevent endless recursion
                if(s.getPiece().getName().equals("king")) {
                    if(isUnderAttackByOpponentKing(space, s)) {
                        return true;
                    }
                    continue;
                }
                if(s.getPiece().getName().equals("pawn")) {
                    //is handled by ValidityCheck class
                    continue;
                }
                currentOpponentPieces.add(s);
            }
        }
        //for black king
        else {
            for(Space s : whitePieces.keySet()) {
                //to prevent endless recursion
                if(s.getPiece().getName().equals("king")) {
                    if(isUnderAttackByOpponentKing(space, s)) {
                        return true;
                    }
                    continue;
                }
                if(s.getPiece().getName().equals("pawn")) {
                    //is handled by ValidityCheck class
                    continue;
                }
                currentOpponentPieces.add(s);

            }
        }

        for(Space s : currentOpponentPieces) {
            if(validSpaces(s, chessboard, board).contains(space)) {
                validSpaces.clear();
                validSpaces.addAll(reserve);
                reserve.clear();
                return true;
            }
        }

        validSpaces.clear();
        validSpaces.addAll(reserve);
        reserve.clear();
        return false;
    }

    public boolean isUnderAttackByOpponentKing(Space allyKingCanMoveOn, Space opponentKing) {
        int addedX;
        int addedY;
        //checking all opponent king moves
        for(MoveList m : opponentKing.getPiece().validMoves()) {
            addedX = opponentKing.getX()+m.getX();
            addedY = opponentKing.getY()+m.getY();
            if(allyKingCanMoveOn.getX() == addedX && allyKingCanMoveOn.getY() == addedY) {
                return true;
            }
        }
        return false;
    }

    public void setReferences(Chessboard chessboard, Space[][] board) {
        this.chessboard = chessboard;
        this.board = board;
    }

    public Space getKing(Color color) {
        if (color == Color.white) {
            for(Space s : whitePieces.keySet()) {
                if(s.getPiece().getName().equals("king")) {
                    return s;
                }
            }
            return null;
        }
        else {
            for(Space s : blackPieces.keySet()) {
                if(s.getPiece().getName().equals("king")) {
                    return s;
                }
            }
            return null;
        }
    }
}
