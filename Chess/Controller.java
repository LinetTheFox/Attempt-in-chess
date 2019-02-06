package Chess;

import javafx.fxml.FXML;
import javafx.scene.control.RadioButton;
import javafx.scene.control.TextArea;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.GridPane;

public class Controller {
    protected Chessboard board = new Chessboard();
    protected PieceHolder holder = new PieceHolder();

    @FXML
    protected AnchorPane anchor, root;
    @FXML
    protected TextArea notations;
    @FXML
    protected GridPane pieceButtons;
    @FXML
    protected RadioButton possibleMoves;

    @FXML
    protected void initialize() {
        anchor.getChildren().add(board);
        root.getChildren().add(holder);
        board.setHolder(holder);
        holder.setBoard(board);
    }

    @FXML
    protected void handleNewBoard() {
        board.makeStartingPositions();
    }

    @FXML
    protected void handleClearBoard() {
        board.clearBoard();
        board.clearHistory();
    }

    @FXML
    protected void handleUndoMove() {
        try {
            board.undoLastMove();
        } catch (Exception ex) {
            return;
        }
    }

    @FXML
    protected void handleMakePosition() {
        board.setMoveLocked(true);
        holder.setMoveLocked(true);
    }

    @FXML
    protected void handlePlay() {
        board.setMoveLocked(false);
        holder.setMoveLocked(false);
    }
}
