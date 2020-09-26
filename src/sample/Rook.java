package sample;

public class Rook extends Piece {

    public Rook(Player player) {
        this.type = "R";
        this.player = player;
        this.fullName = "Rook";
    }

    public void getMoves() {
        for (int i = 0; i < 4; i++) {
            getMovesOneD(i);
        }
    }

    private void getMovesOneD(int dimIndex) {
        int[] tempCoors = position.clone();

        tempCoors[dimIndex] -= 1;
        while (tempCoors[dimIndex] >= 0) {
            if (Game.board.getSquare(tempCoors).isEmpty()) {
                Game.board.getSquare(tempCoors).setLegalOption();
                tempCoors[dimIndex] -= 1;
            } else {
                if (player.num != Game.board.getSquare(tempCoors).getPnum()) {
                    Game.board.getSquare(tempCoors).setLegalOption();
                }
                break;
            }
        }

        tempCoors[dimIndex] = position[dimIndex] + 1;
        while (tempCoors[dimIndex] < BS) {
            if (Game.board.getSquare(tempCoors).isEmpty()) {
                Game.board.getSquare(tempCoors).setLegalOption();
                tempCoors[dimIndex] += 1;
            } else {
                if (player.num != Game.board.getSquare(tempCoors).getPnum()) {
                    Game.board.getSquare(tempCoors).setLegalOption();
                }
                break;
            }
        }
    }
}
