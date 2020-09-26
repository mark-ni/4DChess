package sample;

public abstract class Piece {
    protected String type;
    protected String fullName;
    protected Player player;
    protected int[] position;
    protected int x, y, z, w;
    protected static int[][] groups = {{0, 1}, {0, 2}, {0, 3}, {1, 2}, {1, 3}, {2, 3}};
    protected int BS = Game.board.getBS();

    public abstract void getMoves();

    public String getType() {
        return type;
    }

    public Player getPlayer() {
        return player;
    }

    public String getFullName() { return fullName; }

    public int[] getPosition() {
        return position;
    }

    public void setPosition(int[] position) {
        this.position = position;
        this.x = position[0];
        this.y = position[1];
        this.z = position[2];
        this.w = position[3];
    }
}
