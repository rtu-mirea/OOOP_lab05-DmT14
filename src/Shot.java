public class Shot {
    private int x;
    private int y;
    private boolean stat = false; // false - промах, true - попадание

    public Shot(int x, int y, boolean stat) {
        this.x = x;
        this.y = y;
        this.stat = stat;
    }

    public int getX() {
        return x;
    }

    public int getY() {
        return y;
    }

    public boolean getStat () {
        return stat;
    }
}