package Chess.piece;

import Chess.move.MoveList;

import java.util.ArrayList;

public class Rook extends Piece {

    public Rook(Color color) {
        super(color);
    }

    @Override
    public ArrayList<MoveList> validMoves() {
        ArrayList<MoveList> moves = new ArrayList<>();
        moves.add(MoveList.UP);
        moves.add(MoveList.DOWN);
        moves.add(MoveList.LEFT);
        moves.add(MoveList.RIGHT);
        return moves;
    }

    @Override
    public boolean usesSingleMove() {
        return false;
    }

    @Override
    public String getName() {
        return "rook";
    }
}
