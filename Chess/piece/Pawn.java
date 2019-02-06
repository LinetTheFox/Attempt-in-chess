package Chess.piece;

import Chess.move.MoveList;

import java.util.ArrayList;

public class Pawn extends Piece {

    public Pawn(Color color) {
        super(color);
    }

    @Override
    public ArrayList<MoveList> validMoves() {
        ArrayList<MoveList> moves = new ArrayList<>();
        if (this.isWhite()) {
            moves.add(MoveList.UP);
            moves.add(MoveList.UP_LEFT);
            moves.add(MoveList.UP_RIGHT);
            if(!this.hasMoved()) moves.add(MoveList.DOUBLE_UP);
        }
        else {
            moves.add(MoveList.DOWN);
            moves.add(MoveList.DOWN_LEFT);
            moves.add(MoveList.DOWN_RIGHT);
            if(!this.hasMoved()) moves.add(MoveList.DOUBLE_DOWN);
        }
        return moves;
    }

    @Override
    public boolean usesSingleMove() {
        return true;
    }

    @Override
    public String getName() {
        return "pawn";
    }
}
