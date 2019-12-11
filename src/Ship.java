public class Ship {
    private int length;
    private int x;
    private int y;
    private int wounds = 0;

    public Ship(int length, int x, int y) {
        this.length = length;
        this.x = x;
        this.y = y;
    }

    public int getLength() { return length; }

    public int getX() {
        return x;
    }

    public int getY() {
        return y;
    }

    public void makeWound() {
        wounds++;
    }

    public boolean isDestroyed() {
        return wounds >= length;
    }
}