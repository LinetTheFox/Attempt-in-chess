/*Describes all single moves chess pieces can perform and the coordinates changes on chessboard*/

package Chess.move;

public enum MoveList {
    //singular moves of all directions
    UP(0, 1),
    UP_RIGHT(1, 1),
    RIGHT(1, 0),
    DOWN_RIGHT(1, -1),
    DOWN(0, -1),
    DOWN_LEFT(-1, -1),
    LEFT(-1, 0),
    UP_LEFT(-1, 1),

    //special knight Г-like moves
    KNIGHT_UP_LEFT(-1, 2),
    KNIGHT_UP_RIGHT(1, 2),
    KNIGHT_DOWN_LEFT(-1, -2),
    KNIGHT_DOWN_RIGHT(1, -2),
    KNIGHT_LEFT_UP(-2, 1),
    KNIGHT_LEFT_DOWN(-2, -1),
    KNIGHT_RIGHT_UP(2, 1),
    KNIGHT_RIGHT_DOWN(2, -1),

    //optional first pawn double move
    DOUBLE_UP(0, 2),
    DOUBLE_DOWN(0, -2),

    //king castling moves
    DOUBLE_LEFT(-2, 0),
    DOUBLE_RIGHT(2, 0);

    private int x;
    private int y;
    //for pieces with usesSingleMove() == false, defines whether an another piece blocks further moving
    private boolean hasCollided;

    MoveList(int x, int y) {
        this.x = x;
        this.y = y;
        this.hasCollided = false;
    }

    public boolean hasCollided() {
        return hasCollided;
    }

    public void setHasCollided(boolean hasCollided) {
        this.hasCollided = hasCollided;
    }

    public int getX() {
        return x;
    }

    public int getY() {
        return y;
    }
}
