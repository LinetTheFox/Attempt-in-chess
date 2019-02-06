package Chess.Piece;

public class Rook extends Piece {
    public Rook(Color color) {
        //calls the constructor of Piece
        super(color);
    }

    @Override
    public MoveList[] possibleMoves() {
        MoveList[] result = {
                MoveList.UP,
                MoveList.DOWN,
                MoveList.LEFT,
                MoveList.RIGHT
        };
        return result;
    }

    @Override
    public boolean usesSingleMove() {
        return false;
    }

    @Override
    public String getName() {
        return "Rook";
    }
}
