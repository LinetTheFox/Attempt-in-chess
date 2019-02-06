package Chess.piece;

import Chess.move.MoveList;
import javafx.scene.image.Image;

import java.io.FileInputStream;
import java.net.URL;
import java.util.ArrayList;

public abstract class Piece {
    private Color color;
    private Image image; //texture
    private boolean hasMoved; //for pawns and kings which can't perform some moves once were moved

    public Piece(Color color) {
        this.color = color;
        this.hasMoved = false;

        String location = "Chess/assets/";
        String name = this.color.name() + this.getName();
        image = new Image(location+name+".png");
    }

    public boolean isWhite() {
        return (this.color == Color.white);
    }

    //contains different possible single moves for each type of piece
    public abstract ArrayList<MoveList> validMoves();

    //for Bishop, Rook and Queen shows that they can move in certain direction on more than 1 space
    public abstract boolean usesSingleMove();

    //returns the name of the piece
    public abstract String getName();

    public Color getColor() {
        return color;
    }

    public void setColor(Color color) {
        this.color = color;
    }

    public Image getImage() {
        return image;
    }

    public boolean hasMoved() {
        return hasMoved;
    }

    public void setHasMoved(boolean hasMoved) {
        this.hasMoved = hasMoved;
    }
}
