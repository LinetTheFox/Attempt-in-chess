package Chess.Piece;

import java.util.LinkedList;

public class MoveHistory {
    private LinkedList<MoveInfo> history = new LinkedList<MoveInfo>();

    public void addMove(MoveInfo p) {
        history.addLast(p);
    }

    public void undoMove() {
        if(history.getLast()==null) return;
        history.removeLast();
    }

    public MoveInfo getLastMove() {
        if(history.getLast()==null) return null;
        return history.getLast();
    }

    public void clearAll() {
        history.clear();
    }
}
