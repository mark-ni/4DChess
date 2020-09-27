package sample;

import javafx.geometry.Insets;
import javafx.scene.control.Button;
import javafx.scene.layout.*;
import javafx.scene.paint.Color;
import javafx.scene.text.Font;
import javafx.scene.text.FontWeight;

public class GridSquare extends Button {

    private int[] coors;
    private int effCoors;
    private Piece piece;
    private Background baseBG;
    private int state;
    private int pnum;

    public GridSquare() {
        setPadding(new Insets(0, 0, 0, 0));
        setPrefSize((double) Main.height/64 - 1, (double) Main.height/64 - 1);
        setFont(Font.font("Verdana", FontWeight.BOLD, Main.height/64 - 4));
        setBorder(new Border(new BorderStroke(Color.BLACK,
                BorderStrokeStyle.SOLID, CornerRadii.EMPTY, BorderWidths.DEFAULT)));

        //TODO: factor in player influence
        //setTextFill(Color.WHITE);
    }

    public GridSquare(int x, int y, int z, int w, int effCoors, Background bg) {
        this();

        coors = new int[4];
        coors[0] = x;
        coors[1] = y;
        coors[2] = z;
        coors[3] = w;
        this.effCoors = effCoors;
        this.baseBG = bg;

        setBaseState();
        setOnAction((event) -> {
            if (state == 0) {
                Game.clearHighlights();
                if (piece != null && piece.getPlayer().num == Game.whoseTurn) {
                    piece.getMoves();

                    Game.currSquareCoors = coors;

                    setBackground(Backgrounds.yellow);
                    Game.highlightedSquares.add(effCoors);
                }
            } else if (state == 1) {
                Game.movePiece(coors);
            }
        });
    }

    public void setBaseState() {
        if (isEmpty()) {
            setBackground(baseBG);
        } else {
            setBackground(piece.getPlayer().getBg());
        }
        state = 0;
    }

    public void setLegalOption() {
        setBackground(Backgrounds.green);
        Game.highlightedSquares.add(effCoors);
        state = 1;
    }

    public int[] getCoors() {
        return coors;
    }

    public Piece getPiece() {
        return piece;
    }

    public int getPnum() {
        return pnum;
    }

    public void setPiece(Piece piece) {
        this.piece = piece;
        if (piece == null) {
            setText("");
            setBackground(baseBG);
        } else {
            setText(piece.type);
            setTextFill(piece.getPlayer().getColor());
            setBackground(piece.getPlayer().getBg());
            pnum = piece.getPlayer().num;
        }
    }

    public boolean isEmpty() {
        return piece == null;
    }

    public boolean isFriendly(int n) {
        return !isEmpty() && pnum == n;
    }

    public boolean isHostile(int n) {
        return !isEmpty() && pnum != n;
    }
}
