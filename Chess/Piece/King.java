package Chess.Piece;

import java.util.ArrayList;

public class King extends Piece{
    public King(Color color) {
        //calls the constructor of Piece
        super(color);
    }

    @Override
    public MoveList[] possibleMoves() {
        MoveList[] finalResult = {};
        ArrayList<MoveList> result = new ArrayList<>();
        result.add(MoveList.UP);
        result.add(MoveList.UP_RIGHT);
        result.add(MoveList.RIGHT);
        result.add(MoveList.DOWN_RIGHT);
        result.add(MoveList.DOWN);
        result.add(MoveList.DOWN_LEFT);
        result.add(MoveList.LEFT);
        result.add(MoveList.UP_LEFT);

        if(!hasMoved) {
            result.add(MoveList.DOUBLE_LEFT);
            result.add(MoveList.DOUBLE_RIGHT);
        }

        finalResult = result.toArray(finalResult);
        return finalResult;
    }

    @Override
    public boolean usesSingleMove() {
        return true;
    }

    @Override
    public String getName() {
        return "King";
    }
}
