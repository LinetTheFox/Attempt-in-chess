package Chess.Piece;

import javafx.scene.image.Image;
import static Chess.Piece.Color.*;

public abstract class Piece {
    protected Color color;
    protected Image texture;
    protected boolean hasMoved;

    public Piece(Color color) {
        this.color = color;
        hasMoved = false;

        String location = "Chess/textures/";
        String name = this.color.name()+this.getName()+".png";
        texture = new Image(location+name);
    }

    public boolean getHasMoved() {
        return hasMoved;
    }

    public Color getColor()
    {
        return color;
    }

    public void setHasMoved(boolean True) {
        this.hasMoved = True;
    }

    public Image getTexture() {
        return texture;
    }

    public boolean isWhite() {
        return (color==White);
    }

    public String toString() {
        return this.color+" "+this.getClass().getName().replace("$1", "");
    }

    public abstract MoveList[] possibleMoves();
    public abstract boolean usesSingleMove();
    public abstract String getName();
}