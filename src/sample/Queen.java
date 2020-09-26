package sample;

public class Queen extends Bishop {

    public Queen(Player player) {
        super(player);
        this.type = "Q";
        this.fullName = "Queen";
    }

    public void getMoves() {
        super.getMoves();
        for (int i = 0; i < 4; i++) {
            getMovesOneD(i);
        }
    }

    private void getMovesOneD(int dimIndex) {
        //Same as Rook's function - this one is simpler than bishop's so I made Queen inherit from bishop instead

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
