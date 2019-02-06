package Chess.Piece;

public class Bishop extends Piece{
    public Bishop(Color color) {
        //calls the constructor of Piece
        super(color);
    }

    @Override
    public MoveList[] possibleMoves() {
        MoveList[] result = {
                MoveList.UP_LEFT,
                MoveList.UP_RIGHT,
                MoveList.DOWN_LEFT,
                MoveList.DOWN_RIGHT
        };
        return result;
    }

    @Override
    public boolean usesSingleMove() {
        return false;
    }

    @Override
    public String getName() {
        return "Bishop";
    }
}
