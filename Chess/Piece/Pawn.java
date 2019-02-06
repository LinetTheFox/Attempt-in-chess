package Chess.Piece;

import java.util.ArrayList;

public class Pawn extends Piece {
    public Pawn(Color color) {
        //calls the constructor of Piece
        super(color);
    }



    @Override
    public MoveList[] possibleMoves() {
        MoveList[] result = {};
        if(this.isWhite()) {
            ArrayList<MoveList> whiteMoves = new ArrayList<>();

            whiteMoves.add(MoveList.UP);                    //common straight one field move

            whiteMoves.add(MoveList.UP_LEFT);               //diagonal moves - can and
            whiteMoves.add(MoveList.UP_RIGHT);              //must capture during them

            if(!hasMoved) whiteMoves.add(MoveList.DOUBLE_UP);//two-field move only as first move

            result = whiteMoves.toArray(result);
        }
        else {
            ArrayList<MoveList> blackMoves = new ArrayList<>();

            blackMoves.add(MoveList.DOWN);                    //common straight one field move

            blackMoves.add(MoveList.DOWN_LEFT);               //diagonal moves - can and
            blackMoves.add(MoveList.DOWN_RIGHT);              //must capture during them

            if(!hasMoved) blackMoves.add(MoveList.DOUBLE_DOWN);//two-field move only as first move

            result = blackMoves.toArray(result);
        }
        return result;
    }

    @Override
    public boolean usesSingleMove() {
        return true;
    }

    @Override
    public String getName() {
        return "Pawn";
    }
}
