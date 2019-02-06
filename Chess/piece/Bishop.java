package Chess.piece;

import Chess.move.MoveList;

import java.util.ArrayList;

public class Bishop extends Piece {

    public Bishop (Color color) {
        super(color);
    }

    @Override
    public ArrayList<MoveList> validMoves() {
        ArrayList<MoveList> moves = new ArrayList<>();
        moves.add(MoveList.UP_LEFT);
        moves.add(MoveList.UP_RIGHT);
        moves.add(MoveList.DOWN_LEFT);
        moves.add(MoveList.DOWN_RIGHT);
        return moves;
    }

    @Override
    public boolean usesSingleMove() {
        return false;
    }

    @Override
    public String getName() {
        return "bishop";
    }
}
