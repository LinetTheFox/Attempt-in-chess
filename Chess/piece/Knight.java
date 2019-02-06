package Chess.piece;

import Chess.move.MoveList;

import java.util.ArrayList;

public class Knight extends Piece{

    public Knight(Color color){
        super(color);
    }

    @Override
    public ArrayList<MoveList> validMoves() {
        ArrayList<MoveList> moves = new ArrayList<>();
        moves.add(MoveList.KNIGHT_UP_LEFT);
        moves.add(MoveList.KNIGHT_UP_RIGHT);
        moves.add(MoveList.KNIGHT_DOWN_LEFT);
        moves.add(MoveList.KNIGHT_DOWN_RIGHT);
        moves.add(MoveList.KNIGHT_LEFT_UP);
        moves.add(MoveList.KNIGHT_LEFT_DOWN);
        moves.add(MoveList.KNIGHT_RIGHT_UP);
        moves.add(MoveList.KNIGHT_RIGHT_DOWN);
        return moves;
    }

    @Override
    public boolean usesSingleMove() {
        return true;
    }

    @Override
    public String getName() {
        return "knight";
    }
}
