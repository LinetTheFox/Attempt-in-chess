package Chess.piece;

import Chess.move.MoveList;

import java.util.ArrayList;

public class King extends Piece{

    public King(Color color) {
        super(color);
    }

    @Override
    public ArrayList<MoveList> validMoves() {
        ArrayList<MoveList> moves = new ArrayList<>();
        moves.add(MoveList.UP);
        moves.add(MoveList.UP_RIGHT);
        moves.add(MoveList.RIGHT);
        moves.add(MoveList.DOWN_RIGHT);
        moves.add(MoveList.DOWN);
        moves.add(MoveList.DOWN_LEFT);
        moves.add(MoveList.LEFT);
        moves.add(MoveList.UP_LEFT);
        if(!this.hasMoved()) {
            moves.add(MoveList.DOUBLE_LEFT);
            moves.add(MoveList.DOUBLE_RIGHT);
        }
        return moves;
    }

    @Override
    public boolean usesSingleMove() {
        return true;
    }

    @Override
    public String getName() {
        return "king";
    }
}
