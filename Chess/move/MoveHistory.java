/*contains the history of the moves in current game, making it possible to undo moves and properly make notations*/
package Chess.move;

import java.util.LinkedList;

public class MoveHistory {
    private LinkedList<MoveInfo> history = new LinkedList<>();

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