package sample;

public class Bishop extends Piece {

    public Bishop(Player player) {
        this.type = "B";
        this.fullName = "Bishop";
        this.player = player;
    }

    public void getMoves() {
        for (int i = 0; i < 6; i++) {
            getMovesTwoD(groups[i][0], groups[i][1]);
        }
    }

    private void getMovesTwoD(int dimIndex1, int dimIndex2) {
        int[] tempCoors = position.clone();

        tempCoors[dimIndex1] -= 1;
        tempCoors[dimIndex2] -= 1;
        while (tempCoors[dimIndex1] >= 0 && tempCoors[dimIndex2] >= 0) {
            if (Game.board.getSquare(tempCoors).isEmpty()) {
                Game.board.getSquare(tempCoors).setLegalOption();
                tempCoors[dimIndex1] -= 1;
                tempCoors[dimIndex2] -= 1;
            } else {
                if (Game.board.getSquare(tempCoors).getPnum() != player.num) {
                    Game.board.getSquare(tempCoors).setLegalOption();
                }
                break;
            }
        }

        tempCoors[dimIndex1] = position[dimIndex1] - 1;
        tempCoors[dimIndex2] = position[dimIndex2] + 1;
        while (tempCoors[dimIndex1] >= 0 && tempCoors[dimIndex2] < BS) {
            if (Game.board.getSquare(tempCoors).isEmpty()) {
                Game.board.getSquare(tempCoors).setLegalOption();
                tempCoors[dimIndex1] -= 1;
                tempCoors[dimIndex2] += 1;
            } else {
                if (Game.board.getSquare(tempCoors).getPnum() != player.num) {
                    Game.board.getSquare(tempCoors).setLegalOption();
                }
                break;
            }
        }

        tempCoors[dimIndex1] = position[dimIndex1] + 1;
        tempCoors[dimIndex2] = position[dimIndex2] - 1;
        while (tempCoors[dimIndex1] < BS && tempCoors[dimIndex2] >= 0) {
            if (Game.board.getSquare(tempCoors).isEmpty()) {
                Game.board.getSquare(tempCoors).setLegalOption();
                tempCoors[dimIndex1] += 1;
                tempCoors[dimIndex2] -= 1;
            } else {
                if (Game.board.getSquare(tempCoors).getPnum() != player.num) {
                    Game.board.getSquare(tempCoors).setLegalOption();
                }
                break;
            }
        }

        tempCoors[dimIndex1] = position[dimIndex1] + 1;
        tempCoors[dimIndex2] = position[dimIndex2] + 1;
        while (tempCoors[dimIndex1] < BS && tempCoors[dimIndex2] < BS) {
            if (Game.board.getSquare(tempCoors).isEmpty()) {
                Game.board.getSquare(tempCoors).setLegalOption();
                tempCoors[dimIndex1] += 1;
                tempCoors[dimIndex2] += 1;
            } else {
                if (Game.board.getSquare(tempCoors).getPnum() != player.num) {
                    Game.board.getSquare(tempCoors).setLegalOption();
                }
                break;
            }
        }
    }
}
