package Chess.Piece;

public class Knight extends Piece{
    public Knight(Color color) {
        //calls the constructor of Piece
        super(color);
    }

    @Override
    public MoveList[] possibleMoves() {
        MoveList[] result = {
                MoveList.KNIGHT_DOWN_LEFT,
                MoveList.KNIGHT_DOWN_RIGHT,
                MoveList.KNIGHT_LEFT_DOWN,
                MoveList.KNIGHT_LEFT_UP,
                MoveList.KNIGHT_RIGHT_DOWN,
                MoveList.KNIGHT_RIGHT_UP,
                MoveList.KNIGHT_UP_LEFT,
                MoveList.KNIGHT_UP_RIGHT
        };
        return result;
    }

    @Override
    public boolean usesSingleMove() {
        return true;
    }

    @Override
    public String getName() {
        return "Knight";
    }
}
