public abstract class Item {
    protected int x;
    protected int y;

    public Item(int x, int y) {
        this.x = x;
        this.y = y;
    }

    public int getX() {
        return x;
    }

    public int getY() {
        return y;
    }

    @Override
    public String toString() {
        return "Item at (" + x + ", " + y + ")";
    }
}
