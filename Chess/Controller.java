package Chess;


import Chess.chessboard.Chessboard;
import Chess.pieceContainer.Container;
import javafx.fxml.FXML;
import javafx.scene.control.RadioButton;
import javafx.scene.control.TextArea;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.GridPane;

public class Controller {
    protected Chessboard chessboard = new Chessboard();
    protected Container container = new Container();

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
        anchor.getChildren().add(chessboard);
        root.getChildren().add(container);
        chessboard.setContainer(container);
        container.setBoard(chessboard);
    }

    @FXML
    protected void handleNewBoard() {
        chessboard.setStartingPositions();
    }

    @FXML
    protected void handleClearBoard() {
        chessboard.clearBoard();
        chessboard.clearHistory();
    }

    @FXML
    protected void handleUndoMove() {
        try {
            chessboard.undoLastMove();
        } catch (Exception ex) {
            return;
        }
    }

    @FXML
    protected void handleMakePosition() {
        chessboard.setMoveLocked(true);
        container.setMoveLocked(true);
    }

    @FXML
    protected void handlePlay() {
        chessboard.setMoveLocked(false);
        container.setMoveLocked(false);
    }

    @FXML
    protected void handleSwitchPossibleMoves() {
        chessboard.setEnableShowMoves(possibleMoves.isSelected());
    }
}
